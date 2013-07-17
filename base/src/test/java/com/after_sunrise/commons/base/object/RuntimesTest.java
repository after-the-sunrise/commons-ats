package com.after_sunrise.commons.base.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class RuntimesTest {

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Runtimes.class;

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
	public void testGetProcessId() {

		Long exp = Runtimes.retrieveProcessId("@");

		Long act = Runtimes.getProcessId();

		assertEquals(exp, act);

		assertSame(act, Runtimes.getProcessId());
		assertSame(act, Runtimes.getProcessId());
		assertSame(act, Runtimes.getProcessId());

	}

	@Test
	public void testGetProcessId_Exception() {

		Long act = Runtimes.retrieveProcessId("*");

		assertNull(act);

	}

	@Test
	public void testGetUserName() {

		String exp = System.getProperty("user.name");

		String act = Runtimes.getUserName();

		assertEquals(exp, act);

	}

	@Test
	public void testGetUserHome() {

		File exp = new File(System.getProperty("user.home"));

		File act = Runtimes.getUserHome();

		assertEquals(exp.getAbsolutePath(), act.getAbsolutePath());

	}

}
