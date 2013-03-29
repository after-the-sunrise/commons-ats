package info.after_sunrise.commons.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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
public class ZoneTimeTest {

	private ZoneTime target;

	private Time time;

	private TimeZone timeZone;

	private String pattern;

	private DateFormat df;

	@Before
	public void setUp() throws Exception {

		time = new Time(20, 30, 40, 50);

		timeZone = TimeZone.getTimeZone("America/Los_Angeles");

		pattern = "yyyy-MM-dd HH:mm:ss.SSS";

		df = new SimpleDateFormat(pattern);

		df.setTimeZone(timeZone);

		target = new ZoneTime(time, timeZone);

	}

	@Test(expected = NullPointerException.class)
	public void testZoneTime_NullTime() {
		target = new ZoneTime(null, timeZone);
	}

	@Test(expected = NullPointerException.class)
	public void testZoneTime_NullTimeZone() {
		target = new ZoneTime(time, null);
	}

	@Test
	public void testHashCode() {
		assertEquals(target.hashCode(), target.hashCode());
	}

	@Test
	public void testToString() {
		assertEquals("20:30:40.050 America/Los_Angeles", target.toString());
	}

	@Test
	public void testEquals() {
		assertTrue(target.equals(target));
		assertTrue(target.equals(new ZoneTime(time, timeZone)));
		assertFalse(target.equals(new ZoneTime(new Time(), timeZone)));
		assertFalse(target.equals(new ZoneTime(time, TimeZone.getDefault())));
		assertFalse(target.equals(new Object()));
		assertFalse(target.equals(null));
	}

	@Test
	public void testCompareTo() {
		TimeZone tz = TimeZone.getTimeZone("America/New_York");
		assertEquals(+0, target.compareTo(target));
		assertEquals(+0, target.compareTo(new ZoneTime(time, timeZone)));
		assertEquals(+1, target.compareTo(new ZoneTime(new Time(), timeZone)));
		assertEquals(+1, target.compareTo(new ZoneTime(time, tz)));
	}

	@Test
	public void testAdjust() throws ParseException {

		Date date = df.parse("2012-01-08 12:34:56.789");

		Long result = target.adjust(date.getTime());

		assertEquals("2012-01-08 20:30:40.050", df.format(new Date(result)));

		assertNull(target.adjust(null));

	}

	@Test
	public void testGetTime() {
		assertSame(time, target.getTime());
	}

	@Test
	public void testGetTimeZone() {
		assertEquals(timeZone, target.getTimeZone());
		assertNotSame(timeZone, target.getTimeZone());
	}

	@Test
	public void testGetTimeZoneId() {
		assertEquals(timeZone.getID(), target.getTimeZoneId());
	}

}
