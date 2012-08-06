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

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class ConversionsTest {

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

}
