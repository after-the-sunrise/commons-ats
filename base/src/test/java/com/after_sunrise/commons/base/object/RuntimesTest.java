package com.after_sunrise.commons.base.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class RuntimesTest {

	private static final String KEY = RuntimesTest.class.getName();

	@BeforeClass
	public static void setUpBefore() {

		System.setProperty(KEY, "123");

		System.setProperty(KEY + "_empty", "");

	}

	@AfterClass
	public static void tearDownAfter() {

		System.clearProperty(KEY);

		System.clearProperty(KEY + "_empty");

	}

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
	public void testGetStringProperty() {

		assertNull(Runtimes.getStringProperty(null));

		assertNull(Runtimes.getStringProperty(""));

		assertNull(Runtimes.getStringProperty(KEY + "_FOO"));

		assertEquals("123", Runtimes.getStringProperty(KEY));

	}

	@Test
	public void testGetBooleanProperty() {

		assertFalse(Runtimes.getBooleanProperty(null));

		assertFalse(Runtimes.getBooleanProperty(""));

		assertFalse(Runtimes.getBooleanProperty(KEY + "_FOO"));

		assertFalse(Runtimes.getBooleanProperty(KEY));

	}

	@Test
	public void testGetIntProperty() {

		assertNull(Runtimes.getIntProperty(null));

		assertNull(Runtimes.getIntProperty(""));

		assertNull(Runtimes.getIntProperty(KEY + "_FOO"));

		assertEquals((Integer) 123, Runtimes.getIntProperty(KEY));

	}

	@Test
	public void testGetLongProperty() {

		assertNull(Runtimes.getLongProperty(null));

		assertNull(Runtimes.getLongProperty(""));

		assertNull(Runtimes.getLongProperty(KEY + "_FOO"));

		assertEquals((Long) 123L, Runtimes.getLongProperty(KEY));

	}

	@Test
	public void testGetDecimalProperty() {

		assertNull(Runtimes.getDecimalProperty(null));

		assertNull(Runtimes.getDecimalProperty(""));

		assertNull(Runtimes.getDecimalProperty(KEY + "_FOO"));

		assertEquals(new BigDecimal("123"), Runtimes.getDecimalProperty(KEY));

	}

	@Test
	public void testGetFileProperty() {

		assertNull(Runtimes.getFileProperty(null));

		assertNull(Runtimes.getFileProperty(""));

		assertNull(Runtimes.getFileProperty(KEY + "_FOO"));

		assertNull(Runtimes.getFileProperty(KEY + "_empty"));

		assertEquals("123", Runtimes.getFileProperty(KEY).getPath());

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
