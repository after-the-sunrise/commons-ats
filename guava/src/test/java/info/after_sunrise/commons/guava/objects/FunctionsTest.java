package info.after_sunrise.commons.guava.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import info.after_sunrise.commons.guava.object.Functions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

	@Test
	public void test_TO_STRING() {

		List<String> input = Arrays.asList("foo", null);

		Function<Object, String> f = Functions.TO_STRING;

		Collection<String> output = Lists.transform(input, f);

		assertEquals(2, output.size());

		assertTrue(output.contains("foo"));

		assertTrue(output.contains(null));

	}

	@Test
	public void test_TO_NAME() {

		List<RoundingMode> input = Arrays.asList(RoundingMode.DOWN, null);

		Function<Enum<?>, String> f = Functions.TO_NAME;

		Collection<String> output = Lists.transform(input, f);

		assertEquals(2, output.size());

		assertTrue(output.contains("DOWN"));

		assertTrue(output.contains(null));

	}

	@Test
	public void test_REAL_PERCENT() {

		Function<BigDecimal, BigDecimal> f = Functions.REAL_PERCENT;

		assertNull(f.apply(null));

		assertEquals(new BigDecimal("1000"), f.apply(BigDecimal.TEN));

	}

	@Test
	public void test_PERCENT_REAL() {

		Function<BigDecimal, BigDecimal> f = Functions.PERCENT_REAL;

		assertNull(f.apply(null));

		assertEquals(new BigDecimal("0.10"), f.apply(BigDecimal.TEN));

	}

	@Test
	public void test_REAL_BASIS() {

		Function<BigDecimal, BigDecimal> f = Functions.REAL_BASIS;

		assertNull(f.apply(null));

		assertEquals(new BigDecimal("100000"), f.apply(BigDecimal.TEN));

	}

	@Test
	public void test_BASIS_REAL() {

		Function<BigDecimal, BigDecimal> f = Functions.BASIS_REAL;

		assertNull(f.apply(null));

		assertEquals(new BigDecimal("0.0010"), f.apply(BigDecimal.TEN));

	}

}
