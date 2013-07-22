package com.after_sunrise.commons.base.future;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class FailureFutureTest {

	private FailureFuture<String> target;

	private Throwable throwable;

	@Before
	public void setUp() throws Exception {

		throwable = new RuntimeException("test");

		target = new FailureFuture<String>(throwable);

	}

	@Test(timeout = 1000L)
	public void testCreate() throws Exception {
		try {
			FailureFuture.create(throwable).get();
		} catch (ExecutionException e) {
			assertSame(throwable, e.getCause());
		}
	}

	@Test
	public void testCancel() {
		assertFalse(target.cancel(true));
	}

	@Test(timeout = 1000L)
	public void testGet() throws Exception {
		try {
			target.get();
		} catch (ExecutionException e) {
			assertSame(throwable, e.getCause());
		}
	}

	@Test(timeout = 1000L)
	public void testGetLongTimeUnit() throws Exception {
		try {
			target.get(1L, TimeUnit.MILLISECONDS);
		} catch (ExecutionException e) {
			assertSame(throwable, e.getCause());
		}
	}

	@Test
	public void testIsCancelled() {
		assertFalse(target.isCancelled());
	}

	@Test
	public void testIsDone() {
		assertTrue(target.isDone());
	}

}
