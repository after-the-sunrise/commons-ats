package jp.gr.java_conf.afterthesunrise.commons.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class DatesTest {

	private String pattern;

	private TimeZone tz;

	private DateFormat df;

	@Before
	public void setUp() throws Exception {

		pattern = "yyyy-MM-dd HH:mm:ss.SSS";

		tz = TimeZone.getTimeZone("America/Los_Angeles");

		df = new SimpleDateFormat(pattern);

		df.setTimeZone(tz);

	}

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Dates.class;

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
	public void testAdjustStartOfDay() throws ParseException {

		Date date = df.parse("2010-01-08 12:34:56.789");

		Date expect = df.parse("2010-01-08 00:00:00.000");

		Long result = Dates.adjustStartOfDay(date.getTime(), tz);

		assertEquals(Long.valueOf(expect.getTime()), result);

		assertNull(Dates.adjustStartOfDay(null, tz));

		assertNull(Dates.adjustStartOfDay(date.getTime(), null));

	}

	@Test
	public void testAdjustEndOfDay() throws ParseException {

		Date date = df.parse("2010-01-08 12:34:56.789");

		Date expect = df.parse("2010-01-08 23:59:59.999");

		Long result = Dates.adjustEndOfDay(date.getTime(), tz);

		assertEquals(Long.valueOf(expect.getTime()), result);

		assertNull(Dates.adjustEndOfDay(null, tz));

		assertNull(Dates.adjustEndOfDay(date.getTime(), null));

	}

	@Test
	public void testSwapTimeZone() throws ParseException {

		// LosAngeles -> NewYork
		Date date = df.parse("2010-01-08 12:34:56.789");

		// 12 o'clock NewYork == 09 o'clock LosAngeles
		Date expect = df.parse("2010-01-08 09:34:56.789");

		TimeZone toTz = TimeZone.getTimeZone("America/New_York");

		Long result = Dates.swapTimeZone(date.getTime(), tz, toTz);

		assertEquals(Long.valueOf(expect.getTime()), result);

		assertNull(Dates.swapTimeZone(null, tz, toTz));

		assertNull(Dates.swapTimeZone(date.getTime(), tz, null));

		assertNull(Dates.swapTimeZone(date.getTime(), null, toTz));

	}

	@Test
	public void testSanitize() {

		long now = System.currentTimeMillis();

		Date date = new Date(now);
		assertEquals(now, Dates.sanitize(date).getTime());
		assertEquals(Date.class, Dates.sanitize(date).getClass());

		date = new java.sql.Date(now);
		assertEquals(now, Dates.sanitize(date).getTime());
		assertEquals(Date.class, Dates.sanitize(date).getClass());

		date = new java.sql.Timestamp(now);
		assertEquals(now, Dates.sanitize(date).getTime());
		assertEquals(Date.class, Dates.sanitize(date).getClass());

		assertNull(Dates.sanitize(null));

	}

	@Test
	public void testToSqlDate_Long() {

		long now = System.currentTimeMillis();

		java.sql.Date date = Dates.toSqlDate(new Date(now));

		assertEquals(now, date.getTime());

		assertNull(Dates.toSqlDate((Long) null));

		assertSame(date, Dates.toSqlDate(date));

	}

	@Test
	public void testToSqlDate_Date() {

		long now = System.currentTimeMillis();

		java.sql.Date date = Dates.toSqlDate(new Date(now));

		assertEquals(now, date.getTime());

		assertNull(Dates.toSqlDate((Date) null));

		assertSame(date, Dates.toSqlDate(date));

	}

	@Test
	public void testToSqlTime_Long() {

		long now = System.currentTimeMillis();

		java.sql.Timestamp time = Dates.toSqlTime(now);

		assertEquals(now, time.getTime());

		assertNull(Dates.toSqlTime((Long) null));

		assertSame(time, Dates.toSqlTime(time));

	}

	@Test
	public void testToSqlTime_Date() {

		long now = System.currentTimeMillis();

		java.sql.Timestamp time = Dates.toSqlTime(new Date(now));

		assertEquals(now, time.getTime());

		assertNull(Dates.toSqlTime((Date) null));

		assertSame(time, Dates.toSqlTime(time));

	}

}