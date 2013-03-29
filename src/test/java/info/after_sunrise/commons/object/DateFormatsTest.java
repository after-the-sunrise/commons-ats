package info.after_sunrise.commons.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
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

		assertTrue(Modifier.isPrivate(c.getModifiers()));

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
	public void testGet_Invalid() {

		assertNull(DateFormats.get(null));

		assertNull(DateFormats.get(""));

		assertNull(DateFormats.get(" "));

		assertNull(DateFormats.get("foo"));

	}

	@Test
	public void testParse() {

		Date date = new Date();

		String value = dateFormat.format(date);

		assertNull(DateFormats.parse(value));

		assertNull(DateFormats.parse(null));

	}

	@Test
	public void testParse_WithFormat() {

		// Default = Local time
		dateFormat.setTimeZone(TimeZone.getDefault());

		Date date = new Date();

		String value = dateFormat.format(date);

		assertEquals(date, DateFormats.parse(value, pattern));

		assertNull(DateFormats.parse(null, pattern));

		assertNull(DateFormats.parse(value, null));

		assertNull(DateFormats.parse(value, "foo"));

	}

	@Test
	public void testParse_WithFormat_WithZone() {

		Date date = new Date();

		String value = dateFormat.format(date);

		assertEquals(date, DateFormats.parse(value, pattern, timeZone));

		assertNull(DateFormats.parse(null, pattern, timeZone));

		assertNull(DateFormats.parse(value, null, timeZone));

		assertNull(DateFormats.parse(value, pattern, null));

		assertNull(DateFormats.parse(value, "foo", timeZone));

	}

	@Test
	public void testFormat() {

		// Default = Local time
		dateFormat.setTimeZone(TimeZone.getDefault());

		Date date = new Date();

		assertNull(DateFormats.format((Date) null));

		assertTrue(DateFormats.format(date).startsWith(dateFormat.format(date)));

	}

	@Test
	public void testFormat_WithFormat() {

		// Default = Local time
		dateFormat.setTimeZone(TimeZone.getDefault());

		Date date = new Date();

		assertEquals(dateFormat.format(date), DateFormats.format(date, pattern));

		assertNull(DateFormats.format((Date) null, pattern));

		assertNull(DateFormats.format(date, (String) null));

		assertNull(DateFormats.format(date, "foo"));

	}

	@Test
	public void testFormat_WithZone() {

		Date date = new Date(0L);

		assertEquals("1969-12-31 16:00:00.000 PST",
				DateFormats.format(date, timeZone));

		assertNull(DateFormats.format((Date) null, timeZone));

		assertNull(DateFormats.format(date, (TimeZone) null));

	}

	@Test
	public void testFormat_WithFormat_WithZone() {

		Date date = new Date();

		assertEquals(dateFormat.format(date),
				DateFormats.format(date, pattern, timeZone));

		assertNull(DateFormats.format((Date) null, pattern, timeZone));

		assertNull(DateFormats.format(date, null, timeZone));

		assertNull(DateFormats.format(date, pattern, null));

		assertNull(DateFormats.format(date, "foo", timeZone));

	}

	@Test
	public void testFormatLong() {

		// Default = Local time
		dateFormat.setTimeZone(TimeZone.getDefault());

		Date date = new Date(0L);

		assertNull(DateFormats.format((Long) null));

		assertTrue(DateFormats.format(date).startsWith(dateFormat.format(0L)));

	}

	@Test
	public void testFormatLong_WithFormat() {

		// Default = Local time
		dateFormat.setTimeZone(TimeZone.getDefault());

		Date date = new Date(0L);

		assertEquals(dateFormat.format(date), DateFormats.format(0L, pattern));

		assertNull(DateFormats.format((Long) null, pattern));

		assertNull(DateFormats.format(0L, (String) null));

		assertNull(DateFormats.format(0L, "foo"));

	}

	@Test
	public void testFormatLong_WithZone() {

		assertEquals("1969-12-31 16:00:00.000 PST",
				DateFormats.format(0L, timeZone));

		assertNull(DateFormats.format((Long) null, timeZone));

		assertNull(DateFormats.format(0L, (TimeZone) null));

	}

	@Test
	public void testFormatLong_WithFormat_WithZone() {

		Date date = new Date(0L);

		assertEquals(dateFormat.format(date),
				DateFormats.format(0L, pattern, timeZone));

		assertNull(DateFormats.format((Long) null, pattern, timeZone));

		assertNull(DateFormats.format(0L, null, timeZone));

		assertNull(DateFormats.format(0L, pattern, null));

		assertNull(DateFormats.format(0L, "foo", timeZone));

	}

	@Test
	public void testFormatGMTLong() {

		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date date = new Date(0L);

		assertTrue(DateFormats.formatGMT(0L)
				.startsWith(dateFormat.format(date)));

		assertNull(DateFormats.formatGMT((Long) null));

	}

	@Test
	public void testFormatGMTLong_WithFormat() {

		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date date = new Date(0L);

		assertEquals(dateFormat.format(date),
				DateFormats.formatGMT(0L, pattern));

		assertNull(DateFormats.formatGMT((Long) null, pattern));

		assertNull(DateFormats.formatGMT(0L, null));

		assertNull(DateFormats.formatGMT(0L, "foo"));

	}

	@Test
	public void testFormatGMTDate() {

		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date date = new Date();

		assertTrue(DateFormats.formatGMT(date).startsWith(
				dateFormat.format(date)));

		assertNull(DateFormats.formatGMT((Date) null));

	}

	@Test
	public void testFormatGMTDate_WithFormat() {

		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date date = new Date();

		assertEquals(dateFormat.format(date),
				DateFormats.formatGMT(date, pattern));

		assertNull(DateFormats.formatGMT((Date) null, pattern));

		assertNull(DateFormats.formatGMT(date, null));

		assertNull(DateFormats.formatGMT((Date) date, "foo"));

	}

}
