package jp.gr.java_conf.afterthesunrise.commons.bean;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import jp.gr.java_conf.afterthesunrise.commons.object.Proxies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class TimedInvocationHandlerTest {

	private TimedInvocationHandler<Closeable> target;

	private int concurrency;

	private ExecutorService executorService;

	private AtomicLong invocationCount;

	private AtomicLong closeCount;

	private AtomicLong errorCount;

	@Before
	public void setUp() throws Exception {

		concurrency = 8;

		executorService = Executors.newFixedThreadPool(concurrency);

		// Accumulated "toString()" invocation count.
		invocationCount = new AtomicLong();

		// Accumulated "close()" invocation count.
		closeCount = new AtomicLong();

		// Accumulated error count. Should be zero.
		errorCount = new AtomicLong();

		target = new TimedInvocationHandler<Closeable>() {
			@Override
			protected Closeable generateTarget() {

				return new Closeable() {

					private final AtomicBoolean closed = new AtomicBoolean();

					@Override
					public void close() throws IOException {

						closeCount.incrementAndGet();

						if (closed.getAndSet(true)) {

							errorCount.incrementAndGet();

							throw new IOException("Closed multiple times.");

						}

					}

					@Override
					public String toString() {

						invocationCount.incrementAndGet();

						if (closed.get()) {

							errorCount.incrementAndGet();

							throw new RuntimeException("Already closed.");

						}

						return super.toString();

					}
				};
			}
		};

	}

	@After
	public void tearDown() throws Exception {

		executorService.shutdown();

		executorService.awaitTermination(1, TimeUnit.MINUTES);

	}

	@Test(timeout = 10000L)
	public void testConcurrency() throws Exception {

		final Method m = Object.class.getMethod("toString");

		final Object[] v = new Object[0];

		final AtomicInteger actionCount = new AtomicInteger();

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (!executorService.isShutdown()) {
					try {

						int i = actionCount.incrementAndGet();

						target.setTimeoutInMillis(i);

						switch (i % 8) {
						case 0:
							target.load();
							break;
						case 1:
							target.clear();
							break;
						case 2:
							target.refresh();
							break;
						case 3:
							target.close();
							break;
						case 4:
							target.closeIfStale();
							break;
						default:
							target.invoke(null, m, v);
							break;
						}

						Thread.sleep(1L);

					} catch (Throwable e) {
						errorCount.incrementAndGet();
					}
				}
			}
		};

		for (int i = 0; i < concurrency; i++) {
			executorService.execute(runnable);
		}

		Thread.sleep(1000L);

		// System.out.println("Invoke : " + invocationCount.get());
		// System.out.println("Closed : " + closeCount.get());
		// System.out.println("Error  : " + errorCount.get());
		// System.out.println("Action : " + actionCount.get());

		assertTrue(concurrency * 8 <= invocationCount.get());
		assertTrue(concurrency * 8 <= closeCount.get());
		assertTrue(concurrency * 0 == errorCount.get());
		assertTrue(concurrency * 8 <= actionCount.get());

	}

	@Test(expected = RuntimeException.class)
	public void testLoad_Exception() throws Exception {

		target = new TimedInvocationHandler<Closeable>() {
			@Override
			protected Closeable generateTarget() {
				return null;
			}
		};

		target.load();

	}

	@Test(expected = IOException.class)
	public void testInvoke_Exception() throws Exception {

		final Closeable delegate = mock(Closeable.class);

		doThrow(new IOException("test")).when(delegate).close();

		target = new TimedInvocationHandler<Closeable>() {
			@Override
			protected Closeable generateTarget() {
				return delegate;
			}
		};

		Closeable proxy = Proxies.delegate(Closeable.class, delegate, target);

		proxy.close();

	}

}
