package jp.gr.java_conf.afterthesunrise.commons.bean;

import static java.lang.Thread.State.WAITING;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import jp.gr.java_conf.afterthesunrise.commons.object.Proxies;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.io.Closeables;

/**
 * @author takanori.takase
 */
public class LockedInvocationHandlerTest {

	private LockedInvocationHandler target;

	private Closeable delegate;

	private Closeable proxy;

	@Before
	public void setUp() throws Exception {

		delegate = mock(Closeable.class);

		target = new LockedInvocationHandler(delegate);

		proxy = Proxies.delegate(Closeable.class, target);

	}

	@Test(timeout = 5000)
	public void testInvoke() throws Exception {

		final CountDownLatch latch = new CountDownLatch(1);

		final AtomicInteger count = new AtomicInteger();

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {

				count.incrementAndGet();

				latch.await();

				return null;

			}
		}).when(delegate).close();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				Closeables.closeQuietly(proxy);
			}
		});

		t1.start();

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				Closeables.closeQuietly(proxy);
			}
		});

		t2.start();

		try {

			while (t1.getState() != WAITING || t2.getState() != WAITING) {
				Thread.sleep(1L);
			}

			assertEquals(1, count.get());

		} finally {

			latch.countDown();

		}

		t1.join();

		t2.join();

		assertEquals(2, count.get());

	}

	@Test(expected = IOException.class)
	public void testInvoke_Exception() throws Exception {

		doThrow(new IOException("test")).when(delegate).close();

		proxy.close();

	}

}
