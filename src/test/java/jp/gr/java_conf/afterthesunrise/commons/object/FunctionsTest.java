package jp.gr.java_conf.afterthesunrise.commons.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * @author takanori.takase
 */
public class FunctionsTest {

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Functions.class;

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
	public void test_DOUBLE_DECIMAL() {

		List<Double> input = Arrays.asList(1.1, null);

		Function<Double, BigDecimal> f = Functions.DOUBLE_DECIMAL;

		Collection<BigDecimal> output = Lists.transform(input, f);

		assertEquals(2, output.size());

		assertTrue(output.contains(BigDecimal.valueOf(1.1)));

		assertTrue(output.contains(null));

	}

	@Test
	public void test_DECIMAL_DOUBLE() {

		List<BigDecimal> input = Arrays.asList(BigDecimal.valueOf(1.1), null);

		Function<BigDecimal, Double> f = Functions.DECIMAL_DOUBLE;

		Collection<Double> output = Lists.transform(input, f);

		assertEquals(2, output.size());

		assertTrue(output.contains(1.1));

		assertTrue(output.contains(null));

	}

	@Test
	public void test_STRING_DECIMAL() {

		List<String> input = Arrays.asList("1.1", null);

		Function<String, BigDecimal> f = Functions.STRING_DECIMAL;

		Collection<BigDecimal> output = Lists.transform(input, f);

		assertEquals(2, output.size());

		assertTrue(output.contains(BigDecimal.valueOf(1.1)));

		assertTrue(output.contains(null));

	}

	@Test
	public void test_DECIMAL_STRING() {

		List<BigDecimal> input = Arrays.asList(BigDecimal.valueOf(1.1), null);

		Function<BigDecimal, String> f = Functions.DECIMAL_STRING;

		Collection<String> output = Lists.transform(input, f);

		assertEquals(2, output.size());

		assertTrue(output.contains("1.1"));

		assertTrue(output.contains(null));

	}

}
