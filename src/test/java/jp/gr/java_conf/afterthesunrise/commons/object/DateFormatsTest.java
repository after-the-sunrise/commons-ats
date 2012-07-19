package jp.gr.java_conf.afterthesunrise.commons.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class DateFormatsTest {

	private String pattern;

	private TimeZone timeZone;

	private DateFormat dateFormat;

	@Before
	public void setUp() throws Exception {

		pattern = "yyyy-MM-dd HH:mm:ss.SSS";

		timeZone = TimeZone.getTimeZone("America/Los_Angeles");

		dateFormat = new SimpleDateFormat(pattern);

		dateFormat.setTimeZone(timeZone);

	}

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = DateFormats.class;

		Constructor<?> c = clazz.getDeclaredConstructor();

		c.setAccessible(true);

		try {
			c.newInstance();
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}

	}

	@Test
	public void testGet() {

		// Default = Local time
		dateFormat.setTimeZone(TimeZone.getDefault());

		Date date = new Date();

		DateFormat format = DateFormats.get(pattern);

		assertEquals(dateFormat.format(date), format.format(date));

	}

	@Test
	public void testParse() {

		Date date = new Date();

		String value = dateFormat.format(date);

		assertNull(DateFormats.parse(value));

	}

	@Test
	public void testParse_WithFormat() {

		// Default = Local time
		dateFormat.setTimeZone(TimeZone.getDefault());

		Date date = new Date();

		String value = dateFormat.format(date);

		assertEquals(date, DateFormats.parse(value, pattern));

	}

	@Test
	public void testParse_WithFormat_WithZone() {

		Date date = new Date();

		String value = dateFormat.format(date);

		assertEquals(date, DateFormats.parse(value, pattern, timeZone));

	}

	@Test
	public void testFormat() {

		// Default = Local time
		dateFormat.setTimeZone(TimeZone.getDefault());

		Date date = new Date();

		assertTrue(DateFormats.format(date).startsWith(dateFormat.format(date)));

	}

	@Test
	public void testFormat_WithFormat() {

		// Default = Local time
		dateFormat.setTimeZone(TimeZone.getDefault());

		Date date = new Date();

		assertEquals(dateFormat.format(date), DateFormats.format(date, pattern));

	}

	@Test
	public void testFormat_WithFormat_WithZone() {

		Date date = new Date();

		assertEquals(dateFormat.format(date),
				DateFormats.format(date, pattern, timeZone));

	}

	@Test
	public void testFormatLong() {

		// Default = Local time
		dateFormat.setTimeZone(TimeZone.getDefault());

		Date date = new Date(0L);

		assertTrue(DateFormats.format(date).startsWith(dateFormat.format(0L)));

	}

	@Test
	public void testFormatLong_WithFormat() {

		// Default = Local time
		dateFormat.setTimeZone(TimeZone.getDefault());

		Date date = new Date(0L);

		assertEquals(dateFormat.format(date), DateFormats.format(0L, pattern));

	}

	@Test
	public void testFormatLong_WithFormat_WithZone() {

		Date date = new Date(0L);

		assertEquals(dateFormat.format(date),
				DateFormats.format(0L, pattern, timeZone));

	}

	@Test
	public void testFormatGMTLong() {

		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date date = new Date(0L);

		assertTrue(DateFormats.formatGMT(0L)
				.startsWith(dateFormat.format(date)));

	}

	@Test
	public void testFormatGMTLong_WithFormat() {

		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date date = new Date(0L);

		assertEquals(dateFormat.format(date),
				DateFormats.formatGMT(0L, pattern));

	}

	@Test
	public void testFormatGMTDate() {

		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date date = new Date();

		assertTrue(DateFormats.formatGMT(date).startsWith(
				dateFormat.format(date)));

	}

	@Test
	public void testFormatGMTDate_WithFormat() {

		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date date = new Date();

		assertEquals(dateFormat.format(date),
				DateFormats.formatGMT(date, pattern));

	}

}
