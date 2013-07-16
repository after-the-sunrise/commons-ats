package com.after_sunrise.commons.base.future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class SuccessFutureTest {

	private SuccessFuture<String> target;

	private String value;

	@Before
	public void setUp() throws Exception {

		value = "test";

		target = new SuccessFuture<String>(value);

	}

	@Test
	public void testCancel() {
		assertFalse(target.cancel(true));
	}

	@Test(timeout = 1000L)
	public void testGet() throws Exception {
		assertEquals(value, target.get());
	}

	@Test(timeout = 1000L)
	public void testGetLongTimeUnit() throws Exception {
		assertEquals(value, target.get(1L, TimeUnit.MILLISECONDS));
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
