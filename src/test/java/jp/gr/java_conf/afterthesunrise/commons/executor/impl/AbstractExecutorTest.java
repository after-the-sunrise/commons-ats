package jp.gr.java_conf.afterthesunrise.commons.executor.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.Thread.State;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class AbstractExecutorTest {

	private AbstractExecutor target;

	@Before
	public void setUp() throws Exception {
		target = new AbstractExecutor() {
		};
	}

	@Test(timeout = 5000L)
	public void testNewThread() throws Exception {

		target.setDaemon(true);

		target.setNamePrefix("foo");

		Runnable runnable = mock(Runnable.class);

		Thread t = target.newThread(runnable);

		assertTrue(t.isDaemon());

		assertSame(State.NEW, t.getState());

		assertEquals("foo-001", t.getName());

		t.start();

		t.join();

		verify(runnable).run();

	}

	@Test(timeout = 5000L)
	public void testUncaughtException() throws Exception {

		target.setDaemon(false);

		target.setNamePrefix(null);

		Runnable runnable = mock(Runnable.class);

		doThrow(new RuntimeException("test")).when(runnable).run();

		Thread t = target.newThread(runnable);

		assertFalse(t.isDaemon());

		assertSame(State.NEW, t.getState());

		assertNotNull(t.getName());

		t.start();

		t.join();

		verify(runnable).run();

	}

}
