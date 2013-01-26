package jp.gr.java_conf.afterthesunrise.commons.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.beust.jcommander.internal.Maps;

public class SelectionsTest {

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Selections.class;

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
	public void testSelectFirst_Collection() {

		List<String> c = Arrays.asList("foo", "bar");

		assertEquals("bar", Selections.selectFirst(c, "bar"));

		assertEquals("foo", Selections.selectFirst(c, "hoge"));

		assertNull(Selections.selectFirst(new ArrayList<String>(), "bar"));

		assertNull(Selections.selectFirst((Collection<String>) null, "bar"));

	}

	@Test
	public void testSelectFirst_Array() {

		String[] c = { "foo", "bar" };

		assertEquals("bar", Selections.selectFirst(c, "bar"));

		assertEquals("foo", Selections.selectFirst(c, "hoge"));

		assertNull(Selections.selectFirst(new String[0], "bar"));

		assertNull(Selections.selectFirst((String[]) null, "bar"));

	}

	@Test
	public void testSelectFirstKey() {

		Map<String, String> m = new TreeMap<String, String>();
		m.put("foo", "val1");
		m.put("bar", "val2");

		assertEquals("foo", Selections.selectFirstKey(m, "foo"));

		assertEquals("bar", Selections.selectFirstKey(m, "hoge"));

		assertNull(Selections.selectFirstKey(Maps.newHashMap(), "bar"));

		assertNull(Selections.selectFirstKey(null, "bar"));

	}

	@Test
	public void testSelectFirstValue() {

		Map<String, String> m = new TreeMap<String, String>();
		m.put("foo", "val1");
		m.put("bar", "val2");

		assertEquals("val1", Selections.selectFirstValue(m, "val1"));

		assertEquals("val2", Selections.selectFirstValue(m, "hoge"));

		assertNull(Selections.selectFirstValue(Maps.newHashMap(), "val2"));

		assertNull(Selections.selectFirstValue(null, "val2"));

	}

}
