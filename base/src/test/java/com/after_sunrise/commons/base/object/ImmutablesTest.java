package com.after_sunrise.commons.base.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class ImmutablesTest {

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Immutables.class;

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
	public void testEmptySortedSet() {

		SortedSet<?> result = Immutables.emptySortedSet();

		assertTrue(result.isEmpty());

		try {

			result.clear();

			fail();

		} catch (UnsupportedOperationException e) {
			// Success
		}

	}

	@Test
	public void testEmptySortedMap() {

		SortedMap<?, ?> result = Immutables.emptySortedMap();

		assertTrue(result.isEmpty());

		try {

			result.clear();

			fail();

		} catch (UnsupportedOperationException e) {
			// Success
		}

	}

	@Test
	public void testArrayList() {

		List<String> in = new ArrayList<String>();

		in.add("foo");

		List<String> out = Immutables.arrayList(in);

		in.clear();

		assertTrue(out.contains("foo"));

		try {

			out.clear();

			fail();

		} catch (UnsupportedOperationException e) {
			// Success
		}

		assertSame(Collections.emptyList(), Immutables.arrayList(null));

		assertSame(Collections.emptyList(), Immutables.arrayList(in));

	}

	@Test
	public void testLinkedList() {

		List<String> in = new ArrayList<String>();

		in.add("foo");

		List<String> out = Immutables.linkedList(in);

		in.clear();

		assertTrue(out.contains("foo"));

		try {

			out.clear();

			fail();

		} catch (UnsupportedOperationException e) {
			// Success
		}

		assertSame(Collections.emptyList(), Immutables.linkedList(null));

		assertSame(Collections.emptyList(), Immutables.linkedList(in));

	}

	@Test
	public void testHashSet() {

		List<String> in = new ArrayList<String>();

		in.add("foo");

		Set<String> out = Immutables.hashSet(in);

		in.clear();

		assertTrue(out.contains("foo"));

		try {

			out.clear();

			fail();

		} catch (UnsupportedOperationException e) {
			// Success
		}

		assertSame(Collections.emptySet(), Immutables.hashSet(null));

		assertSame(Collections.emptySet(), Immutables.hashSet(in));

	}

	@Test
	public void testTreeSet() {

		List<String> in = new ArrayList<String>();

		in.add("foo");

		SortedSet<String> out = Immutables.treeSet(in);

		in.clear();

		assertTrue(out.contains("foo"));

		try {

			out.clear();

			fail();

		} catch (UnsupportedOperationException e) {
			// Success
		}

		assertSame(Immutables.emptySortedSet(), Immutables.treeSet(null));

		assertSame(Immutables.emptySortedSet(), Immutables.treeSet(in));

	}

	@Test
	public void testHashMap() {

		Map<String, String> in = new HashMap<String, String>();

		in.put("foo", "bar");

		Map<String, String> out = Immutables.hashMap(in);

		in.clear();

		assertEquals("bar", out.get("foo"));

		try {

			out.clear();

			fail();

		} catch (UnsupportedOperationException e) {
			// Success
		}

		assertSame(Collections.emptyMap(), Immutables.hashMap(null));

		assertSame(Collections.emptyMap(), Immutables.hashMap(in));

	}

	@Test
	public void testTreeMap() {

		Map<String, String> in = new HashMap<String, String>();

		in.put("foo", "bar");

		SortedMap<String, String> out = Immutables.treeMap(in);

		in.clear();

		assertEquals("bar", out.get("foo"));

		try {

			out.clear();

			fail();

		} catch (UnsupportedOperationException e) {
			// Success
		}

		assertSame(Immutables.emptySortedMap(), Immutables.treeMap(null));

		assertSame(Immutables.emptySortedMap(), Immutables.treeMap(in));

	}

	@Test
	public void testIdentityHashMap() {

		Integer i1 = new Integer(1);
		Integer i2 = new Integer(1);

		Map<Integer, String> in = new IdentityHashMap<Integer, String>();
		in.put(i1, "1");
		in.put(i2, "2");

		Map<Integer, String> out = Immutables.identityHashMap(in);

		in.clear();

		assertEquals(2, out.size());
		assertEquals("1", out.get(i1));
		assertEquals("2", out.get(i2));

		try {

			out.clear();

			fail();

		} catch (UnsupportedOperationException e) {
			// Success
		}

		assertSame(Collections.emptyMap(), Immutables.identityHashMap(null));

		assertSame(Collections.emptyMap(), Immutables.identityHashMap(in));

	}

}
