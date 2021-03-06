package com.after_sunrise.commons.base.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import org.junit.Test;

import com.after_sunrise.commons.base.object.Conversions.Identifiable;

/**
 * @author takanori.takase
 */
public class ConversionsTest {

	private static class TestIdentifiable implements Identifiable<String> {

		private final String id;

		private TestIdentifiable(String id) {
			this.id = id;
		}

		@Override
		public String getId() {
			return id;
		}

	}

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Conversions.class;

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
	public void testtoIdString() {

		TestIdentifiable id1 = new TestIdentifiable("foo");
		TestIdentifiable id2 = new TestIdentifiable(null);

		assertEquals("foo", Conversions.toIdString(id1));
		assertNull(Conversions.toIdString(id2));
		assertNull(Conversions.toIdString(null));

	}

	@Test
	public void testMap() {

		TestIdentifiable id1 = new TestIdentifiable("foo");
		TestIdentifiable id2 = new TestIdentifiable("bar");
		TestIdentifiable id3 = new TestIdentifiable(null);

		Map<String, TestIdentifiable> map = Conversions.map(id1, id2, id3);

		assertEquals(3, map.size());
		assertEquals(id1, map.get(id1.getId()));
		assertEquals(id2, map.get(id2.getId()));
		assertEquals(id3, map.get(id3.getId()));

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMap_Empty() {

		assertTrue(Conversions.map((Identifiable[]) null).isEmpty());

		assertTrue(Conversions.map(new Identifiable[0]).isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testMap_DuplicateId() {

		TestIdentifiable id1 = new TestIdentifiable("foo");
		TestIdentifiable id2 = new TestIdentifiable("foo");

		Conversions.map(id1, id2);

	}

	@Test
	public void testSet() {

		TestIdentifiable id1 = new TestIdentifiable("foo");
		TestIdentifiable id2 = new TestIdentifiable("bar");
		TestIdentifiable id3 = new TestIdentifiable(null);

		Set<String> set = Conversions.set(id1, id2, id3);

		assertEquals(3, set.size());
		assertTrue(set.contains(id1.getId()));
		assertTrue(set.contains(id2.getId()));
		assertTrue(set.contains(id3.getId()));

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSet_Empty() {

		assertTrue(Conversions.set((Identifiable[]) null).isEmpty());

		assertTrue(Conversions.set(new Identifiable[0]).isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSet_DuplicateId() {

		TestIdentifiable id1 = new TestIdentifiable("foo");
		TestIdentifiable id2 = new TestIdentifiable("foo");

		Conversions.set(id1, id2);

	}

	@Test
	public void testList() {

		TestIdentifiable id1 = new TestIdentifiable("foo");
		TestIdentifiable id2 = new TestIdentifiable("bar");
		TestIdentifiable id3 = new TestIdentifiable(null);

		List<String> list = Conversions.list(id1, id2, id3);

		assertEquals(3, list.size());
		assertEquals("foo", list.get(0));
		assertEquals("bar", list.get(1));
		assertEquals(null, list.get(2));

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testList_Empty() {

		assertTrue(Conversions.list((Identifiable[]) null).isEmpty());

		assertTrue(Conversions.list(new Identifiable[0]).isEmpty());

	}

	@Test
	public void testConvert_List() {

		List<String> keys = Arrays.asList("foo", "bar", null);
		Map<String, BigDecimal> mapping = new HashMap<String, BigDecimal>();
		mapping.put("foo", BigDecimal.valueOf(1L));
		mapping.put("bar", BigDecimal.valueOf(2L));

		List<BigDecimal> result = Conversions.convert(keys, mapping);

		assertEquals(3, result.size());
		assertEquals(BigDecimal.valueOf(1L), result.get(0));
		assertEquals(BigDecimal.valueOf(2L), result.get(1));
		assertNull(result.get(2));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvert_List_NullKeys() {

		Map<String, BigDecimal> mapping = new HashMap<String, BigDecimal>();

		Conversions.convert((List<String>) null, mapping);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvert_List_NullMapping() {

		List<String> keys = Arrays.asList("hoge");

		Conversions.convert(keys, null);

	}

	@Test
	public void testConvert_Set() {

		Set<String> keys = new HashSet<String>(
				Arrays.asList("foo", "bar", null));
		Map<String, BigDecimal> mapping = new HashMap<String, BigDecimal>();
		mapping.put("foo", BigDecimal.valueOf(1L));
		mapping.put("bar", BigDecimal.valueOf(2L));

		Set<BigDecimal> result = Conversions.convert(keys, mapping);

		assertEquals(3, result.size());
		assertTrue(result.contains(BigDecimal.valueOf(1L)));
		assertTrue(result.contains(BigDecimal.valueOf(2L)));
		assertTrue(result.contains(null));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvert_Set_NullKeys() {

		Map<String, BigDecimal> mapping = new HashMap<String, BigDecimal>();

		Conversions.convert((Set<String>) null, mapping);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvert_Set_NullMapping() {

		Set<String> keys = new HashSet<String>(Arrays.asList("hoge"));

		Conversions.convert(keys, null);

	}

	@Test
	public void testConvertSorted_Set() {

		Set<String> keys = new HashSet<String>(
				Arrays.asList("foo", "bar", null));
		Map<String, BigDecimal> mapping = new HashMap<String, BigDecimal>();
		mapping.put("foo", BigDecimal.valueOf(1L));
		mapping.put("bar", BigDecimal.valueOf(2L));

		SortedSet<BigDecimal> result = Conversions.convertSorted(keys, mapping);

		assertEquals(3, result.size());
		assertTrue(result.contains(BigDecimal.valueOf(1L)));
		assertTrue(result.contains(BigDecimal.valueOf(2L)));
		assertTrue(result.contains(null));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertSorted_Set_NullKeys() {

		Map<String, BigDecimal> mapping = new HashMap<String, BigDecimal>();

		Conversions.convertSorted((Set<String>) null, mapping);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertSorted_Set_NullMapping() {

		Set<String> keys = new HashSet<String>(Arrays.asList("hoge"));

		Conversions.convertSorted(keys, null);

	}

	@Test
	public void testConvertKey() {

		Map<String, BigDecimal> source = new HashMap<String, BigDecimal>();
		source.put("foo", BigDecimal.valueOf(1L));
		source.put("bar", BigDecimal.valueOf(2L));
		source.put("hoge", BigDecimal.valueOf(3L));

		Map<String, Integer> mapping = new HashMap<String, Integer>();
		mapping.put("foo", 100);
		mapping.put("bar", 200);

		Map<Integer, BigDecimal> map = Conversions.convertKey(source, mapping);

		assertEquals(3, map.size());
		assertEquals(BigDecimal.valueOf(1L), map.get(100));
		assertEquals(BigDecimal.valueOf(2L), map.get(200));
		assertEquals(BigDecimal.valueOf(3L), map.get(null));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertKey_NullSource() {

		Map<String, Integer> mapping = new HashMap<String, Integer>();

		Conversions.convertKey(null, mapping);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertKey_NullMapping() {

		Map<String, BigDecimal> source = new HashMap<String, BigDecimal>();

		Conversions.convertKey(source, null);

	}

	@Test
	public void testConvertKeySorted() {

		Map<String, BigDecimal> source = new HashMap<String, BigDecimal>();
		source.put("foo", BigDecimal.valueOf(1L));
		source.put("bar", BigDecimal.valueOf(2L));
		source.put("hoge", BigDecimal.valueOf(3L));

		Map<String, Integer> mapping = new HashMap<String, Integer>();
		mapping.put("foo", 100);
		mapping.put("bar", 200);

		SortedMap<Integer, BigDecimal> map = Conversions.convertKeySorted(
				source, mapping);

		assertEquals(3, map.size());
		assertEquals(BigDecimal.valueOf(1L), map.get(100));
		assertEquals(BigDecimal.valueOf(2L), map.get(200));
		assertEquals(BigDecimal.valueOf(3L), map.get(null));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertKeySorted_NullSource() {

		Map<String, BigDecimal> source = null;

		Map<String, Integer> mapping = new HashMap<String, Integer>();

		Conversions.convertKeySorted(source, mapping);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertKeySorted_NullMapping() {

		Map<String, BigDecimal> source = new HashMap<String, BigDecimal>();

		Map<String, Integer> mapping = null;

		Conversions.convertKeySorted(source, mapping);

	}

	@Test
	public void testConvertValue() {

		Map<String, BigDecimal> source = new HashMap<String, BigDecimal>();
		source.put("foo", BigDecimal.valueOf(1L));
		source.put("bar", BigDecimal.valueOf(2L));
		source.put("hoge", BigDecimal.valueOf(3L));

		Map<BigDecimal, Integer> mapping = new HashMap<BigDecimal, Integer>();
		mapping.put(BigDecimal.valueOf(1L), 100);
		mapping.put(BigDecimal.valueOf(2L), 200);

		Map<String, Integer> map = Conversions.convertValue(source, mapping);

		assertEquals(3, map.size());
		assertEquals(100, map.get("foo").intValue());
		assertEquals(200, map.get("bar").intValue());
		assertEquals(null, map.get("hoge"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertValue_NullSource() {

		Map<String, Integer> mapping = new HashMap<String, Integer>();

		Conversions.convertValue(null, mapping);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertValue_NullMapping() {

		Map<String, BigDecimal> source = new HashMap<String, BigDecimal>();

		Conversions.convertValue(source, null);

	}

	@Test
	public void testConvertValueSorted() {

		Map<String, BigDecimal> source = new HashMap<String, BigDecimal>();
		source.put("foo", BigDecimal.valueOf(1L));
		source.put("bar", BigDecimal.valueOf(2L));
		source.put("hoge", BigDecimal.valueOf(3L));

		Map<BigDecimal, Integer> mapping = new HashMap<BigDecimal, Integer>();
		mapping.put(BigDecimal.valueOf(1L), 100);
		mapping.put(BigDecimal.valueOf(2L), 200);

		SortedMap<String, Integer> map = Conversions.convertValueSorted(source,
				mapping);

		assertEquals(3, map.size());
		assertEquals(100, map.get("foo").intValue());
		assertEquals(200, map.get("bar").intValue());
		assertEquals(null, map.get("hoge"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertValueSorted_NullSource() {

		Map<String, BigDecimal> source = null;

		Map<BigDecimal, Integer> mapping = new HashMap<BigDecimal, Integer>();

		Conversions.convertValueSorted(source, mapping);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertValueSorted_NullMapping() {

		Map<String, BigDecimal> source = new HashMap<String, BigDecimal>();

		Map<BigDecimal, Integer> mapping = null;

		Conversions.convertValueSorted(source, mapping);

	}

	@Test
	public void testList_List_List() {

		BigDecimal o1 = BigDecimal.ONE;
		BigDecimal o2 = BigDecimal.TEN;

		List<BigDecimal> list1 = Arrays.asList(o1);
		List<BigDecimal> list2 = Arrays.asList(o2);

		List<BigDecimal> result = Conversions.list(list1, list2);
		assertEquals(2, result.size());
		assertTrue(result.contains(o1));
		assertTrue(result.contains(o2));

		result = Conversions.list(list1, (List<BigDecimal>) null);
		assertEquals(1, result.size());
		assertTrue(result.contains(o1));

		result = Conversions.list((List<BigDecimal>) null, list2);
		assertEquals(1, result.size());
		assertTrue(result.contains(o2));

		result = Conversions.list(list1, (List<BigDecimal>) null);
		assertSame(list1, result);

		result = Conversions.list(list1, Collections.<BigDecimal> emptyList());
		assertSame(list1, result);

		result = Conversions.list((List<BigDecimal>) null, list2);
		assertSame(list2, result);

		result = Conversions.list(Collections.<BigDecimal> emptyList(), list2);
		assertSame(list2, result);

		list1 = null;
		list2 = null;
		result = Conversions.list(list1, list2);
		assertNull(result);

	}

	@Test
	public void testList_List_Array() {

		BigDecimal o1 = BigDecimal.ONE;
		BigDecimal o2 = BigDecimal.TEN;

		List<BigDecimal> list = Arrays.asList(o1);
		BigDecimal[] array = new BigDecimal[] { o2 };

		List<BigDecimal> result = Conversions.list(list, array);
		assertEquals(2, result.size());
		assertTrue(result.contains(o1));
		assertTrue(result.contains(o2));

		result = Conversions.list(list, (BigDecimal[]) null);
		assertEquals(1, result.size());
		assertTrue(result.contains(o1));

		result = Conversions.list(list, (new BigDecimal[0]));
		assertEquals(1, result.size());
		assertTrue(result.contains(o1));

		result = Conversions.list(list);
		assertEquals(1, result.size());
		assertTrue(result.contains(o1));

		result = Conversions.list(Collections.<BigDecimal> emptyList(), array);
		assertEquals(1, result.size());
		assertTrue(result.contains(o2));

		result = Conversions.list((List<BigDecimal>) null, array);
		assertEquals(1, result.size());
		assertTrue(result.contains(o2));

		result = Conversions.list((List<BigDecimal>) null, (BigDecimal[]) null);
		assertNull(result);

		result = Conversions.list((List<BigDecimal>) null, new BigDecimal[0]);
		assertNull(result);

		result = Conversions.list((List<BigDecimal>) null);
		assertNull(result);

	}

	@Test
	public void testList_Array() {

		BigDecimal c = BigDecimal.ONE;

		List<BigDecimal> result = Conversions.asList(c);
		assertEquals(1, result.size());
		assertTrue(result.contains(c));

		result = Conversions.asList((BigDecimal) null);
		assertEquals(1, result.size());

		result = Conversions.asList((BigDecimal[]) null);
		assertNull(result);

		result = Conversions.asList(new BigDecimal[0]);
		assertNull(result);

		result = Conversions.asList();
		assertNull(result);

	}

}
