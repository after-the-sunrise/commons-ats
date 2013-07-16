package com.after_sunrise.commons.base.object;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class AdaptersTest {

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Adapters.class;

		Constructor<?> c = clazz.getDeclaredConstructor();

		assertTrue(Modifier.isPrivate(c.getModifiers()));

		c.setAccessible(true);

		try {
			c.newInstance();
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}

	}

	@Test
	public void testRunnable() {

		Runnable value = Adapters.runnable();

		value.run();

	}

	@Test
	public void testCallable() throws Exception {

		Callable<?> value = Adapters.callable();

		assertNull(value.call());

	}

	@Test(timeout = 1000L)
	public void testFuture() throws Exception {

		Future<?> value = Adapters.future();

		assertFalse(value.cancel(true));
		assertNull(value.get());
		assertNull(value.get(1, TimeUnit.MILLISECONDS));
		assertFalse(value.isCancelled());
		assertTrue(value.isDone());

	}

}
