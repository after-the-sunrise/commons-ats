package info.after_sunrise.commons.time;

import static info.after_sunrise.commons.time.AdjustmentType.FOLLOWING;
import static info.after_sunrise.commons.time.AdjustmentType.MODIFIED_FOLLOWING;
import static info.after_sunrise.commons.time.AdjustmentType.MODIFIED_PRECEDING;
import static info.after_sunrise.commons.time.AdjustmentType.PRECEDING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class AdjustmentTypeTest {

	private static final List<DayType> EMPTY1 = Arrays.asList((DayType) null);

	private static final List<Long> EMPTY2 = Arrays.asList((Long) null);

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

	@Test
	public void testGetNext_Following() throws ParseException {

		// Null
		Long timestamp = df.parse("2012-04-29 12:34:56.789").getTime();
		assertNull(FOLLOWING.getNext(timestamp, null, null, null));
		assertNull(FOLLOWING.getNext(null, tz, null, null));

		// Same
		Long next = FOLLOWING.getNext(timestamp, tz, null, null);
		assertEquals("2012-04-29 12:34:56.789", df.format(new Date(next)));

		next = FOLLOWING.getNext(timestamp, tz, EMPTY1, null);
		assertEquals("2012-04-29 12:34:56.789", df.format(new Date(next)));

		next = FOLLOWING.getNext(timestamp, tz, null, EMPTY2);
		assertEquals("2012-04-29 12:34:56.789", df.format(new Date(next)));

		// Weekend
		List<DayType> weekends = Arrays.asList(DayType.SUNDAY, null);
		next = FOLLOWING.getNext(timestamp, tz, weekends, null);
		assertEquals("2012-04-30 12:34:56.789", df.format(new Date(next)));

		// Holiday
		List<Long> holidays = Arrays.asList(next, null);
		next = FOLLOWING.getNext(timestamp, tz, weekends, holidays);
		assertEquals("2012-05-01 12:34:56.789", df.format(new Date(next)));

	}

	@Test
	public void testGetNext_Preceding() throws ParseException {

		// Null
		Long timestamp = df.parse("2012-04-02 12:34:56.789").getTime();
		assertNull(FOLLOWING.getNext(timestamp, null, null, null));
		assertNull(FOLLOWING.getNext(null, tz, null, null));

		// Same
		Long next = PRECEDING.getNext(timestamp, tz, null, null);
		assertEquals("2012-04-02 12:34:56.789", df.format(new Date(next)));

		next = PRECEDING.getNext(timestamp, tz, EMPTY1, null);
		assertEquals("2012-04-02 12:34:56.789", df.format(new Date(next)));

		next = PRECEDING.getNext(timestamp, tz, null, EMPTY2);
		assertEquals("2012-04-02 12:34:56.789", df.format(new Date(next)));

		// Weekend
		List<DayType> weekends = Arrays.asList(DayType.MONDAY, null);
		next = PRECEDING.getNext(timestamp, tz, weekends, null);
		assertEquals("2012-04-01 12:34:56.789", df.format(new Date(next)));

		// Holiday
		List<Long> holidays = Arrays.asList(next, null);
		next = PRECEDING.getNext(timestamp, tz, weekends, holidays);
		assertEquals("2012-03-31 12:34:56.789", df.format(new Date(next)));

	}

	@Test
	public void testGetNext_ModifiedFollowing() throws ParseException {

		// Null
		Long timestamp = df.parse("2012-04-29 12:34:56.789").getTime();
		assertNull(FOLLOWING.getNext(timestamp, null, null, null));
		assertNull(FOLLOWING.getNext(null, tz, null, null));

		// Same
		Long next = MODIFIED_FOLLOWING.getNext(timestamp, tz, null, null);
		assertEquals("2012-04-29 12:34:56.789", df.format(new Date(next)));

		next = MODIFIED_FOLLOWING.getNext(timestamp, tz, EMPTY1, null);
		assertEquals("2012-04-29 12:34:56.789", df.format(new Date(next)));

		next = MODIFIED_FOLLOWING.getNext(timestamp, tz, null, EMPTY2);
		assertEquals("2012-04-29 12:34:56.789", df.format(new Date(next)));

		// Weekend
		List<DayType> weekends = Arrays.asList(DayType.SUNDAY, null);
		next = MODIFIED_FOLLOWING.getNext(timestamp, tz, weekends, null);
		assertEquals("2012-04-30 12:34:56.789", df.format(new Date(next)));

		// Holiday
		List<Long> holidays = Arrays.asList(next, null);
		next = MODIFIED_FOLLOWING.getNext(timestamp, tz, weekends, holidays);
		assertEquals("2012-04-28 12:34:56.789", df.format(new Date(next)));

	}

	@Test
	public void testGetNext_ModifiedPreceding() throws ParseException {

		// Null
		Long timestamp = df.parse("2012-04-02 12:34:56.789").getTime();
		assertNull(FOLLOWING.getNext(timestamp, null, null, null));
		assertNull(FOLLOWING.getNext(null, tz, null, null));

		// Same
		Long next = MODIFIED_PRECEDING.getNext(timestamp, tz, null, null);
		assertEquals("2012-04-02 12:34:56.789", df.format(new Date(next)));

		next = MODIFIED_PRECEDING.getNext(timestamp, tz, EMPTY1, null);
		assertEquals("2012-04-02 12:34:56.789", df.format(new Date(next)));

		next = MODIFIED_PRECEDING.getNext(timestamp, tz, null, EMPTY2);
		assertEquals("2012-04-02 12:34:56.789", df.format(new Date(next)));

		// Weekend
		List<DayType> weekends = Arrays.asList(DayType.MONDAY, null);
		next = MODIFIED_PRECEDING.getNext(timestamp, tz, weekends, null);
		assertEquals("2012-04-01 12:34:56.789", df.format(new Date(next)));

		// Holiday
		List<Long> holidays = Arrays.asList(next, null);
		next = MODIFIED_PRECEDING.getNext(timestamp, tz, weekends, holidays);
		assertEquals("2012-04-03 12:34:56.789", df.format(new Date(next)));

	}

}
