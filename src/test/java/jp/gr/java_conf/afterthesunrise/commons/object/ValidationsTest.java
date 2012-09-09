package jp.gr.java_conf.afterthesunrise.commons.object;

import static jp.gr.java_conf.afterthesunrise.commons.object.Validations.checkDates;
import static jp.gr.java_conf.afterthesunrise.commons.object.Validations.checkNotNegative;
import static jp.gr.java_conf.afterthesunrise.commons.object.Validations.checkPositive;
import static jp.gr.java_conf.afterthesunrise.commons.object.Validations.checkRange;
import static jp.gr.java_conf.afterthesunrise.commons.object.Validations.checkTimestamps;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class ValidationsTest {

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Validations.class;

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
	public void testCheckTimestamps() {
		checkTimestamps(1L, 2L);
		checkTimestamps(1L, 1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckTimestamps_Invalid() {
		checkTimestamps(2L, 1L);
	}

	@Test
	public void testCheckDates_Long() {
		checkDates((Long) 1L, (Long) 2L);
		checkDates((Long) 1L, (Long) 1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckDates_Long_Invalid() {
		checkDates((Long) 2L, (Long) 1L);
	}

	@Test
	public void testCheckDates_Date() {
		checkDates(new Date(1L), new Date(2L));
		checkDates(new Date(1L), new Date(1L));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckDates_Date_Invalid() {
		checkDates(new Date(2L), new Date(1L));
	}

	@Test
	public void testCheckNotNegative_PrimitiveLong() {
		assertEquals(1L, checkNotNegative(1L));
		assertEquals(0L, checkNotNegative(0L));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckNotNegative_PrimitiveLong_Invalid() {
		checkNotNegative(-1L);
	}

	@Test
	public void testCheckNotNegative_ObjectLong() {
		assertEquals((Long) 1L, checkNotNegative((Long) 1L));
		assertEquals((Long) 0L, checkNotNegative((Long) 0L));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckNotNegative_ObjectLong_Invalid() {
		checkNotNegative(Long.valueOf(-1L));
	}

	@Test
	public void testCheckNotNegative_BigDecimal() {
		assertEquals(BigDecimal.ONE, checkNotNegative(BigDecimal.ONE));
		assertEquals(BigDecimal.ZERO, checkNotNegative(BigDecimal.ZERO));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckNotNegative_BigDecimal_Invalid() {
		checkNotNegative(BigDecimal.valueOf(-1L));
	}

	@Test
	public void testCheckPositive_PrimitiveLong() {
		assertEquals(1L, checkPositive(1L));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckPositive_PrimitiveLong_Invalid() {
		checkPositive(0L);
	}

	@Test
	public void testCheckPositive_ObjectLong() {
		assertEquals((Long) 1L, checkPositive((Long) 1L));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckPositive_ObjectLong_Invalid() {
		checkPositive((Long) 0L);
	}

	@Test
	public void testCheckPositive_BigDecimal() {
		assertEquals(BigDecimal.ONE, checkPositive(BigDecimal.ONE));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckPositive_BigDecimal_Invalid() {
		checkPositive(BigDecimal.valueOf(0L));
	}

	@Test
	public void testCheckRange() {
		assertEquals((Integer) 8, checkRange(8, 7, 9));
		assertEquals((Integer) 8, checkRange(8, 8, 9));
		assertEquals((Integer) 8, checkRange(8, 7, 8));
		assertEquals((Integer) 8, checkRange(8, 8, 8));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckRange_Invalid_Min() {
		checkRange(6, 7, 9);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckRange_Invalid_Max() {
		checkRange(10, 7, 9);
	}

	@Test
	public void testCheckRange_MinExclusive() {
		assertEquals((Integer) 8, checkRange(8, 7, 9, false, true));
		assertEquals((Integer) 8, checkRange(8, 7, 8, false, true));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckRange_MinExclusive_Invalid() {
		checkRange(8, 8, 9, false, true);
	}

	@Test
	public void testCheckRange_MaxExclusive() {
		assertEquals((Integer) 8, checkRange(8, 7, 9, true, false));
		assertEquals((Integer) 8, checkRange(8, 8, 9, true, false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckRange_MaxExclusive_Invalid() {
		checkRange(8, 7, 8, true, false);
	}

}
