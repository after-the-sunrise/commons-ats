package jp.gr.java_conf.afterthesunrise.commons.object;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class NumbersTest {

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Numbers.class;

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
	public void testConvert() {

		assertEquals("1.23", Numbers.convert("1.23").toPlainString());

		assertEquals("1.00", Numbers.convert("1.00").toPlainString());

		assertNull(Numbers.convert("foo"));

		assertNull(Numbers.convert(""));

		assertNull(Numbers.convert(null));

	}

	@Test
	public void testConvert_WithDefault() {

		assertEquals("1.23", Numbers.convert("1.23", TEN).toPlainString());

		assertEquals("1.00", Numbers.convert("1.00", TEN).toPlainString());

		assertSame(TEN, Numbers.convert("foo", TEN));

		assertSame(TEN, Numbers.convert("", TEN));

		assertSame(TEN, Numbers.convert(null, TEN));

	}

	@Test
	public void testRound() {

		assertEquals("123", Numbers.round(new BigDecimal("123.1"))
				.toPlainString());

		assertEquals("123", Numbers.round(new BigDecimal("123.0"))
				.toPlainString());

		assertEquals("-123", Numbers.round(new BigDecimal("-123"))
				.toPlainString());

		assertNull(Numbers.round(null));

	}

	@Test
	public void testRound_WithUnit() {

		assertEquals("120", Numbers.round(new BigDecimal("123"), TEN)
				.toPlainString());

		assertEquals("-120", Numbers.round(new BigDecimal("-123"), TEN)
				.toPlainString());

		assertEquals("120.0",
				Numbers.round(new BigDecimal("123"), TEN.setScale(1))
						.toPlainString());

		assertEquals("120", Numbers.round(new BigDecimal("123"), TEN.negate())
				.toPlainString());

		assertNull(Numbers.round(null, TEN));

		assertNull(Numbers.round(new BigDecimal("123"), null));

		assertNull(Numbers.round(new BigDecimal("123"), ZERO));

		assertNull(Numbers.round(new BigDecimal("123"), ZERO.setScale(1)));

	}

	@Test
	public void testRound_WithUnit_WithDefault() {

		assertEquals("120", Numbers.round(new BigDecimal("123"), TEN, ONE)
				.toPlainString());

		assertEquals("-120", Numbers.round(new BigDecimal("-123"), TEN, ONE)
				.toPlainString());

		assertEquals("120.0",
				Numbers.round(new BigDecimal("123"), TEN.setScale(1), ONE)
						.toPlainString());

		assertEquals("120",
				Numbers.round(new BigDecimal("123"), TEN.negate(), ONE)
						.toPlainString());

		assertSame(ONE, Numbers.round(null, TEN, ONE));

		assertSame(ONE, Numbers.round(new BigDecimal("123"), null, ONE));

		assertSame(ONE, Numbers.round(new BigDecimal("123"), ZERO, ONE));

		assertSame(ONE,
				Numbers.round(new BigDecimal("123"), ZERO.setScale(1), ONE));

	}

}
