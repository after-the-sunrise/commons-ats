package com.after_sunrise.commons.base.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class PatternsTest {

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Patterns.class;

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
	public void testCompile() {

		Pattern pattern = Patterns.compile("^$");

		assertEquals("^$", pattern.pattern());

		assertNull(Patterns.compile("["));

		assertNull(Patterns.compile(null));

	}

	@Test
	public void testMatch() {

		assertTrue(Patterns.match("^$", ""));

		assertFalse(Patterns.match("^$", null));

		assertFalse(Patterns.match("^$", "a"));

		assertFalse(Patterns.match(null, "a"));

		assertFalse(Patterns.match("[", ""));

		assertFalse(Patterns.match("[", null));

	}

}
