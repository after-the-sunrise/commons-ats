package jp.gr.java_conf.afterthesunrise.commons.object;

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

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class EnumsTest {

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
	public void testMap() {

		Map<String, RoundingMode> map = Enums.map(RoundingMode.class);

		for (RoundingMode type : RoundingMode.values()) {
			assertEquals(type, map.get(type.name()));
		}

		assertEquals(RoundingMode.values().length, map.size());

	}

	@Test
	public void testFind() {

		for (RoundingMode type : RoundingMode.values()) {
			assertSame(type, Enums.find(RoundingMode.class, type.name()));
		}

		assertNull(Enums.find(RoundingMode.class, "foo"));

		assertNull(Enums.find(RoundingMode.class, null));

		assertNull(Enums.find(null, "foo"));

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
