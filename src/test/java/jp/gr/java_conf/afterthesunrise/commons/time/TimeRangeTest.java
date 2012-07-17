package jp.gr.java_conf.afterthesunrise.commons.time;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class TimeRangeTest {

	private TimeRange target;

	private TimeZone timeZone;

	private DateFormat df;

	@Before
	public void setUp() throws Exception {

		timeZone = TimeZone.getTimeZone("Asia/Tokyo");

		df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		df.setTimeZone(timeZone);

		Time start = new Time(9, 0, 0, 0);

		Time end = new Time(15, 0, 0, 0);

		target = new TimeRange(timeZone, start, end);

	}

	@Test
	public void testInRange() throws Exception {

		assertTrue(target.inRange(df.parse("2012-01-01 09:00").getTime()));
		assertTrue(target.inRange(df.parse("2012-01-01 12:00").getTime()));
		assertTrue(target.inRange(df.parse("2012-01-01 15:00").getTime()));

		assertFalse(target.inRange(df.parse("2012-01-01 08:59").getTime()));
		assertFalse(target.inRange(df.parse("2012-01-01 15:01").getTime()));
		assertFalse(target.inRange(df.parse("2012-01-01 23:00").getTime()));

	}

	@Test
	public void testInRange_PastMidnight() throws Exception {

		Time start = new Time(16, 0, 0, 0);
		Time end = new Time(3, 0, 0, 0);
		target = new TimeRange(timeZone, start, end);

		assertTrue(target.inRange(df.parse("2012-01-01 16:00").getTime()));
		assertTrue(target.inRange(df.parse("2012-01-01 20:00").getTime()));
		assertTrue(target.inRange(df.parse("2012-01-01 01:00").getTime()));
		assertTrue(target.inRange(df.parse("2012-01-01 03:00").getTime()));

		assertFalse(target.inRange(df.parse("2012-01-01 15:59").getTime()));
		assertFalse(target.inRange(df.parse("2012-01-01 03:01").getTime()));
		assertFalse(target.inRange(df.parse("2012-01-01 12:00").getTime()));
		assertFalse(target.inRange(df.parse("2012-01-01 04:00").getTime()));

	}

}
