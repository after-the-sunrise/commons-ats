package info.after_sunrise.commons.base.executor.impl;

import static org.junit.Assert.assertTrue;
import info.after_sunrise.commons.base.object.IOs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class TimerAdapterTest {

	@Test(timeout = 5000L)
	public void testSequence() throws Exception {

		final CountDownLatch latch = new CountDownLatch(5);

		Runnable r = new Runnable() {
			@Override
			public void run() {

				Thread t = Thread.currentThread();

				String name = t.getName();

				assertTrue(name, name.startsWith("TimerAdapter-"));

				assertTrue(t.isDaemon());

				latch.countDown();

			}
		};

		TimerAdapter target = new TimerAdapter(r, 10L);

		try {

			target.initialize();

			assertTrue(latch.await(6000L, TimeUnit.MILLISECONDS));

		} finally {
			IOs.closeQuietly(target);
		}

	}

}
