package jp.gr.java_conf.afterthesunrise.commons.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import jp.gr.java_conf.afterthesunrise.commons.object.Conversions.Identifiable;

import org.junit.Test;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

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

	@Test
	public void testMap_Empty() {

		Identifiable<String>[] args = null;

		assertTrue(Conversions.map(args).isEmpty());

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

	@Test
	public void testSet_Empty() {

		Identifiable<String>[] args = null;

		assertTrue(Conversions.set(args).isEmpty());

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

	@Test
	public void testList_Empty() {

		Identifiable<String>[] args = null;

		assertTrue(Conversions.list(args).isEmpty());

	}

	@Test
	public void testConvert_List() {

		List<String> keys = Arrays.asList("foo", "bar", null);
		Map<String, BigDecimal> mapping = new HashMap<>();
		mapping.put("foo", BigDecimal.valueOf(1L));
		mapping.put("bar", BigDecimal.valueOf(2L));

		List<BigDecimal> result = Conversions.convert(keys, mapping);

		assertEquals(3, result.size());
		assertEquals(BigDecimal.valueOf(1L), result.get(0));
		assertEquals(BigDecimal.valueOf(2L), result.get(1));
		assertNull(result.get(2));

	}

	@Test(expected = NullPointerException.class)
	public void testConvert_List_NullKeys() {

		Map<String, BigDecimal> mapping = new HashMap<>();

		Conversions.convert((List<String>) null, mapping);

	}

	@Test(expected = NullPointerException.class)
	public void testConvert_List_NullMapping() {

		List<String> keys = Arrays.asList("hoge");

		Conversions.convert(keys, null);

	}

	@Test
	public void testConvert_Set() {

		Set<String> keys = new HashSet<>(Arrays.asList("foo", "bar", null));
		Map<String, BigDecimal> mapping = new HashMap<>();
		mapping.put("foo", BigDecimal.valueOf(1L));
		mapping.put("bar", BigDecimal.valueOf(2L));

		Set<BigDecimal> result = Conversions.convert(keys, mapping);

		assertEquals(3, result.size());
		assertTrue(result.contains(BigDecimal.valueOf(1L)));
		assertTrue(result.contains(BigDecimal.valueOf(2L)));
		assertTrue(result.contains(null));

	}

	@Test(expected = NullPointerException.class)
	public void testConvert_Set_NullKeys() {

		Map<String, BigDecimal> mapping = new HashMap<>();

		Conversions.convert((Set<String>) null, mapping);

	}

	@Test(expected = NullPointerException.class)
	public void testConvert_Set_NullMapping() {

		Set<String> keys = new HashSet<>(Arrays.asList("hoge"));

		Conversions.convert(keys, null);

	}

	@SuppressWarnings(value = "NP_NONNULL_PARAM_VIOLATION")
	@Test
	public void testConvertSorted_Set() {

		Set<String> keys = new HashSet<>(Arrays.asList("foo", "bar", null));
		Map<String, BigDecimal> mapping = new HashMap<>();
		mapping.put("foo", BigDecimal.valueOf(1L));
		mapping.put("bar", BigDecimal.valueOf(2L));

		SortedSet<BigDecimal> result = Conversions.convertSorted(keys, mapping);

		assertEquals(3, result.size());
		assertTrue(result.contains(BigDecimal.valueOf(1L)));
		assertTrue(result.contains(BigDecimal.valueOf(2L)));
		assertTrue(result.contains(null));

	}

	@Test(expected = NullPointerException.class)
	public void testConvertSorted_Set_NullKeys() {

		Map<String, BigDecimal> mapping = new HashMap<>();

		Conversions.convertSorted((Set<String>) null, mapping);

	}

	@Test(expected = NullPointerException.class)
	public void testConvertSorted_Set_NullMapping() {

		Set<String> keys = new HashSet<>(Arrays.asList("hoge"));

		Conversions.convertSorted(keys, null);

	}

	@Test
	public void testConvertKey() {

		Map<String, BigDecimal> source = new HashMap<>();
		source.put("foo", BigDecimal.valueOf(1L));
		source.put("bar", BigDecimal.valueOf(2L));
		source.put("hoge", BigDecimal.valueOf(3L));

		Map<String, Integer> mapping = new HashMap<>();
		mapping.put("foo", 100);
		mapping.put("bar", 200);

		Map<Integer, BigDecimal> map = Conversions.convertKey(source, mapping);

		assertEquals(3, map.size());
		assertEquals(BigDecimal.valueOf(1L), map.get(100));
		assertEquals(BigDecimal.valueOf(2L), map.get(200));
		assertEquals(BigDecimal.valueOf(3L), map.get(null));

	}

	@Test(expected = NullPointerException.class)
	public void testConvertKey_NullSource() {

		Map<String, Integer> mapping = new HashMap<>();

		Conversions.convertKey(null, mapping);

	}

	@Test(expected = NullPointerException.class)
	public void testConvertKey_NullMapping() {

		Map<String, BigDecimal> source = new HashMap<>();

		Conversions.convertKey(source, null);

	}

	@SuppressWarnings(value = "NP_NONNULL_PARAM_VIOLATION")
	@Test
	public void testConvertKeySorted() {

		Map<String, BigDecimal> source = new HashMap<>();
		source.put("foo", BigDecimal.valueOf(1L));
		source.put("bar", BigDecimal.valueOf(2L));
		source.put("hoge", BigDecimal.valueOf(3L));

		Map<String, Integer> mapping = new HashMap<>();
		mapping.put("foo", 100);
		mapping.put("bar", 200);

		SortedMap<Integer, BigDecimal> map = Conversions.convertKeySorted(
				source, mapping);

		assertEquals(3, map.size());
		assertEquals(BigDecimal.valueOf(1L), map.get(100));
		assertEquals(BigDecimal.valueOf(2L), map.get(200));
		assertEquals(BigDecimal.valueOf(3L), map.get(null));

	}

	@Test(expected = NullPointerException.class)
	public void testConvertKeySorted_NullSource() {

		Map<String, Integer> mapping = new HashMap<>();

		Conversions.convertKeySorted(null, mapping);

	}

	@Test(expected = NullPointerException.class)
	public void testConvertKeySorted_NullMapping() {

		Map<String, BigDecimal> source = new HashMap<>();

		Conversions.convertKeySorted(source, null);

	}

}
