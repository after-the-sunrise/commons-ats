package com.after_sunrise.commons.base.object;

import static com.after_sunrise.commons.base.object.Configurations.EPHEMERAL_PORT_END;
import static com.after_sunrise.commons.base.object.Configurations.EPHEMERAL_PORT_START;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.rmi.server.UID;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class ConfigurationsTest {

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Configurations.class;

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
	public void testLoadProperties() {

		Properties prop = Configurations.loadProperties("test.properties");

		assertEquals("bar", prop.getProperty("foo"));

	}

	@Test
	public void testLoadProperties_ClassPath() {

		Properties prop = Configurations
				.loadProperties(true, "test.properties");

		assertEquals("bar", prop.getProperty("foo"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testLoadProperties_ClassPath_InvalidFile() {

		Configurations.loadProperties(true, "foo");

	}

	@Test
	public void testLoadProperties_FilePath() {

		Properties prop = Configurations.loadProperties(false,
				"src/test/resources/test.properties");

		assertEquals("bar", prop.getProperty("foo"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testLoadProperties_FilePath_InvalidFile() {

		Configurations.loadProperties(false, "foo");

	}

	@Test
	public void testGetRandomPort() {

		int loop = 1000;

		Set<Integer> vals = new HashSet<Integer>();

		for (int i = 0; i < loop; i++) {

			int port = Configurations.getRandomPort();

			assertTrue(port >= EPHEMERAL_PORT_START);

			assertTrue(port <= EPHEMERAL_PORT_END);

			vals.add(port);

		}

		// Assume at least 10% is unique
		assertTrue(vals.size() >= loop * 0.10);

	}

	@Test
	public void testGetRandomPort_Specified() {

		int loop = 1000;

		Set<Integer> vals = new HashSet<Integer>();

		for (int i = 0; i < loop; i++) {

			int port = Configurations.getRandomPort(10000, 20000);

			assertTrue(port >= 10000);

			assertTrue(port <= 20000);

			vals.add(port);

		}

		// Assume at least 10% is unique
		assertTrue(vals.size() >= loop * 0.10);

	}

	@Test
	public void testGetRandomPort_Specified_Same() {
		assertEquals(8080, Configurations.getRandomPort(8080, 8080));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetRandomPort_Specified_TooSmall() {
		Configurations.getRandomPort(-1, EPHEMERAL_PORT_END);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetRandomPort_Specified_TooBig() {
		Configurations.getRandomPort(EPHEMERAL_PORT_START, 99999);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetRandomPort_Specified_Inversed() {
		Configurations.getRandomPort(EPHEMERAL_PORT_END, EPHEMERAL_PORT_START);
	}

	@Test
	public void testGetSystemPort() {

		String key = new UID().toString();

		try {

			System.setProperty(key, "8080");

			assertEquals(8080, Configurations.getSystemPort(key));

		} finally {
			System.clearProperty(key);
		}

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSystemPort_InvalidNumber() {

		String key = new UID().toString();

		try {

			System.setProperty(key, "8080a");

			Configurations.getSystemPort(key);

		} finally {
			System.clearProperty(key);
		}

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSystemPort_InvalidRange() {

		String key = new UID().toString();

		try {

			System.setProperty(key, "99999");

			Configurations.getSystemPort(key);

		} finally {
			System.clearProperty(key);
		}

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSystemPort_Undefined() {
		Configurations.getSystemPort("foo");
	}

}
