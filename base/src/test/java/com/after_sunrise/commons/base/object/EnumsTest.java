package com.after_sunrise.commons.base.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class EnumsTest {

	private static enum Test1 {
		FOO, BAR
	}

	private static enum Test2 {
		FOO, BAR, HOGE
	}

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Enums.class;

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
	public void testToName() {
		assertEquals(Test1.BAR.name(), Enums.toName(Test1.BAR));
		assertNull(Enums.toName(null));
	}

	@Test
	public void testToName_WithDefault() {
		assertEquals(Test1.BAR.name(), Enums.toName(Test1.BAR, "bar"));
		assertEquals("bar", Enums.toName(null, "bar"));
	}

	@Test
	public void testMap() {

		Map<String, RoundingMode> map = Enums.map(RoundingMode.class);

		for (RoundingMode type : RoundingMode.values()) {
			assertEquals(type, map.get(type.name()));
		}

		assertEquals(RoundingMode.values().length, map.size());

	}

	@Test
	public void testMap_Enums() {
		Map<Test1, Test2> map = Enums.map(Test1.class, Test2.class);
		assertEquals(Test1.values().length, map.size());
		assertSame(Test2.FOO, map.get(Test1.FOO));
		assertSame(Test2.BAR, map.get(Test1.BAR));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMap_Enums_Mismatch() {
		Enums.map(Test2.class, Test1.class);
	}

	@Test
	public void testFind() {

		for (RoundingMode type : RoundingMode.values()) {
			assertSame(type, Enums.find(RoundingMode.class, type.name()));
		}

		assertNull(Enums.find(RoundingMode.class, "foo"));

		assertNull(Enums.find(RoundingMode.class, (String) null));

		assertNull(Enums.find((Class<RoundingMode>) null, "foo"));

	}

	@Test
	public void testFindList() {

		Collection<String> values = new ArrayList<String>();

		for (RoundingMode type : RoundingMode.values()) {
			values.add(type.name());
		}

		List<RoundingMode> result = Enums.findList(RoundingMode.class, values);

		assertEquals(values.size(), result.size());

		for (RoundingMode type : result) {
			assertTrue(values.contains(type.name()));
		}

		assertNull(Enums.findList((Class<RoundingMode>) null, values));

		assertNull(Enums.findList(RoundingMode.class, null));

	}

	@Test
	public void testFindSet() {

		Collection<String> values = new ArrayList<String>();

		for (RoundingMode type : RoundingMode.values()) {
			values.add(type.name());
		}

		Set<RoundingMode> result = Enums.findSet(RoundingMode.class, values);

		assertEquals(values.size(), result.size());

		for (RoundingMode type : result) {
			assertTrue(values.contains(type.name()));
		}

		assertNull(Enums.findSet((Class<RoundingMode>) null, values));

		assertNull(Enums.findSet(RoundingMode.class, null));

	}

	@Test
	public void testList() {

		Collection<RoundingMode> exp = Arrays.asList(RoundingMode.values());

		assertEquals(exp, Enums.list(RoundingMode.class));

		assertEquals(exp, Enums.list(RoundingMode.class, false));

	}

	@Test
	public void testList_WithBlank() {

		List<RoundingMode> exp = new ArrayList<RoundingMode>();

		exp.add(null);

		exp.addAll(Arrays.asList(RoundingMode.values()));

		assertEquals(exp, Enums.list(RoundingMode.class, true));

	}

}
