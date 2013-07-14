package info.after_sunrise.commons.base.executor.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author takanori.takase
 */
public class ScheduledExecutorImplTest {

	private ScheduledExecutorImpl target;

	@Before
	public void setUp() throws Exception {

		target = new ScheduledExecutorImpl();

		target.setThreads(2);

		target.setDaemon(true);

		target.setNamePrefix("test");

	}

	@After
	public void tearDown() throws Exception {
		target.close();
	}

	@Test
	public void testClose() throws Exception {
		target.close();
		target.close();
		target.close();
	}

	@Test(timeout = 5000L)
	public void testScheduleOnce_Runnable() throws Exception {

		Runnable runnable = mock(Runnable.class);

		final AtomicInteger count = new AtomicInteger();

		final CountDownLatch latch = new CountDownLatch(2);

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {

				count.incrementAndGet();

				latch.countDown();

				return null;

			}
		}).when(runnable).run();

		target.scheduleOnce(runnable, 1L);
		target.scheduleOnce(runnable, 1L);
		target.scheduleOnce(null, 1L);

		latch.await();

		target.close();

		assertEquals(2, count.get());

	}

	@Test(timeout = 5000L)
	public void testScheduleFixed_Runnable() throws Exception {

		Runnable runnable = mock(Runnable.class);

		final AtomicInteger count = new AtomicInteger();

		final CountDownLatch latch = new CountDownLatch(10);

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {

				count.incrementAndGet();

				latch.countDown();

				return null;

			}
		}).when(runnable).run();

		target.scheduleFixed(runnable, 1L);
		target.scheduleFixed(runnable, 1L);
		target.scheduleFixed(null, 1L);

		latch.await();

		target.close();

		assertTrue(1 < count.get());

	}

	@Test(timeout = 5000L)
	public void testScheduleInterval_Runnable() throws Exception {

		Runnable runnable = mock(Runnable.class);

		final AtomicInteger count = new AtomicInteger();

		final CountDownLatch latch = new CountDownLatch(10);

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {

				count.incrementAndGet();

				latch.countDown();

				return null;

			}
		}).when(runnable).run();

		target.scheduleInterval(runnable, 1L);
		target.scheduleInterval(runnable, 1L);
		target.scheduleInterval(null, 1L);

		latch.await();

		target.close();

		assertTrue(1 < count.get());

	}

}
