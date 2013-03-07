package jp.gr.java_conf.afterthesunrise.commons.executor.impl;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author takanori.takase
 */
public class FixedExecutorImplTest {

	private FixedExecutorImpl target;

	@Before
	public void setUp() throws Exception {

		target = new FixedExecutorImpl();

		target.setThreads(2);

		target.setDaemon(true);

		target.setNamePrefix("test");

	}

	@After
	public void tearDown() throws Exception {
		target.close();
	}

	private Runnable getNull() {
		return null;
	}

	@Test(timeout = 5000L)
	public void testExecute_Runnable() throws Exception {

		Runnable runnable1 = mock(Runnable.class);
		Runnable runnable2 = mock(Runnable.class);

		final CountDownLatch latch1 = new CountDownLatch(2);
		final CountDownLatch latch2 = new CountDownLatch(1);

		Answer<Void> answer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {

				latch1.countDown();

				latch2.await();

				return null;

			}
		};

		doAnswer(answer).when(runnable1).run();
		doAnswer(answer).when(runnable2).run();

		target.execute(runnable1);
		target.execute(runnable2);
		target.execute(getNull());

		latch1.await();

		latch2.countDown();

		target.close();

	}

	@Test(timeout = 5000L)
	public void testExecute_Callable() throws Exception {

		Callable<?> callable1 = mock(Callable.class);
		Callable<?> callable2 = mock(Callable.class);

		final CountDownLatch latch1 = new CountDownLatch(2);
		final CountDownLatch latch2 = new CountDownLatch(1);

		Answer<Object> answer = new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {

				latch1.countDown();

				latch2.await();

				return this;

			}
		};

		doAnswer(answer).when(callable1).call();
		doAnswer(answer).when(callable2).call();

		Future<?> result1 = target.execute(callable1);
		Future<?> result2 = target.execute(callable2);
		Future<?> result3 = target.execute((Callable<?>) null);

		latch1.await();

		latch2.countDown();

		assertSame(answer, result1.get());
		assertSame(answer, result2.get());

		try {

			result3.get();

			fail();

		} catch (ExecutionException e) {
			assertTrue(e.getCause() instanceof NullPointerException);
		}

		target.close();

	}
}
