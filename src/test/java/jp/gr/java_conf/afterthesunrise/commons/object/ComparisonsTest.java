package jp.gr.java_conf.afterthesunrise.commons.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class ComparisonsTest {

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Comparisons.class;

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
	public void testEquals() {

		BigDecimal o1 = new BigDecimal("1");
		BigDecimal o2 = new BigDecimal("1");
		BigDecimal o3 = new BigDecimal("3");

		assertTrue(Comparisons.equals(o1, o1, o1));
		assertTrue(Comparisons.equals(o1, o1, o2));
		assertTrue(Comparisons.equals(o1, o2, o2));
		assertTrue(Comparisons.equals(o2, o2, o2));
		assertTrue(Comparisons.equals(null, null, null));

		assertFalse(Comparisons.equals(o1, o1, o3));
		assertFalse(Comparisons.equals(o1, o1, null));
		assertFalse(Comparisons.equals(o1, o3, o1));
		assertFalse(Comparisons.equals(o1, null, o1));
		assertFalse(Comparisons.equals(o3, o1, o1));
		assertFalse(Comparisons.equals(null, o1, o1));

	}

	@Test
	public void testCompare() {

		BigDecimal o1 = new BigDecimal("1");
		BigDecimal o2 = new BigDecimal("1.0");
		BigDecimal o3 = new BigDecimal("1.1");

		assertEquals(+0, Comparisons.compare(o1, o2));
		assertEquals(-1, Comparisons.compare(o1, o3));
		assertEquals(+1, Comparisons.compare(o3, o1));

		assertEquals(+1, Comparisons.compare(o1, null));
		assertEquals(-1, Comparisons.compare(null, o1));

		assertEquals(0, Comparisons.compare(null, null));

	}

	@Test
	public void testEquals_Collection() {

		Collection<String> c1 = Arrays.asList("foo");
		Collection<String> c2 = new HashSet<>(c1);
		Collection<String> c3 = Arrays.asList("bar");
		Collection<String> c4 = Arrays.asList("foo", "bar");
		Collection<String> c5 = null;

		assertTrue(Comparisons.equals(c1, c1));
		assertTrue(Comparisons.equals(c1, c2));

		assertFalse(Comparisons.equals(c1, c3));
		assertFalse(Comparisons.equals(c1, c4));
		assertFalse(Comparisons.equals(c1, c5));

		assertFalse(Comparisons.equals(c3, c1));
		assertFalse(Comparisons.equals(c4, c1));
		assertFalse(Comparisons.equals(c5, c1));

		assertTrue(Comparisons.equals(c5, c5));

	}

	@Test
	public void testEquals_Map() {

		Map<String, String> c1 = Collections.singletonMap("foo", "bar");
		Map<String, String> c2 = new TreeMap<>(c1);
		Map<String, String> c3 = Collections.singletonMap("bar", "foo");
		Map<String, String> c4 = Collections.emptyMap();
		Map<String, String> c5 = null;

		assertTrue(Comparisons.equals(c1, c1));
		assertTrue(Comparisons.equals(c1, c2));

		assertFalse(Comparisons.equals(c1, c3));
		assertFalse(Comparisons.equals(c1, c4));
		assertFalse(Comparisons.equals(c1, c5));

		assertFalse(Comparisons.equals(c3, c1));
		assertFalse(Comparisons.equals(c4, c1));
		assertFalse(Comparisons.equals(c5, c1));

		assertTrue(Comparisons.equals(c5, c5));
	}

	@Test
	public void testGetMin() {

		BigDecimal o1 = new BigDecimal("1");
		BigDecimal o2 = new BigDecimal("1.0");
		BigDecimal o3 = new BigDecimal("1.1");

		assertSame(o1, Comparisons.getMin(o1, o1));

		assertSame(o1, Comparisons.getMin(o1, o2));
		assertSame(o1, Comparisons.getMin(o1, o3));
		assertSame(o1, Comparisons.getMin(o1, null));

		assertSame(o2, Comparisons.getMin(o2, o1));
		assertSame(o1, Comparisons.getMin(o3, o1));
		assertSame(o1, Comparisons.getMin(null, o1));

	}

	@Test
	public void testGetMax() {

		BigDecimal o1 = new BigDecimal("1");
		BigDecimal o2 = new BigDecimal("1.0");
		BigDecimal o3 = new BigDecimal("1.1");

		assertSame(o1, Comparisons.getMax(o1, o1));

		assertSame(o1, Comparisons.getMax(o1, o2));
		assertSame(o3, Comparisons.getMax(o1, o3));
		assertSame(o1, Comparisons.getMax(o1, null));

		assertSame(o2, Comparisons.getMax(o2, o1));
		assertSame(o3, Comparisons.getMax(o3, o1));
		assertSame(o1, Comparisons.getMax(null, o1));

	}

}
