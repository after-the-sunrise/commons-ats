package jp.gr.java_conf.afterthesunrise.commons.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class TimeRangeTest {

	private TimeRange target;

	private Time start;

	private Time end;

	@Before
	public void setUp() throws Exception {

		start = new Time(9, 0, 0, 0);

		end = new Time(15, 0, 0, 0);

		target = new TimeRange(start, end);

	}

	@Test(expected = NullPointerException.class)
	public void testTimeRange_NullStart() {
		target = new TimeRange(null, end);
	}

	@Test(expected = NullPointerException.class)
	public void testTimeRange_NullEnd() {
		target = new TimeRange(start, null);
	}

	@Test
	public void testToString() {
		assertEquals("09:00:00.000-15:00:00.000", target.toString());
	}

	@Test
	public void testHashCode() {
		assertEquals(target.hashCode(), target.hashCode());
	}

	@Test
	public void testEquals() {
		assertTrue(target.equals(target));
		assertTrue(target.equals(new TimeRange(start, end)));
		assertFalse(target.equals(new TimeRange(new Time(), end)));
		assertFalse(target.equals(new TimeRange(start, new Time())));
		assertFalse(target.equals(new Object()));
		assertFalse(target.equals(null));
	}

	@Test
	public void testCompareTo() {
		assertEquals(+0, target.compareTo(target));
		assertEquals(+0, target.compareTo(new TimeRange(start, end)));
		assertEquals(+1, target.compareTo(new TimeRange(new Time(), end)));
		assertEquals(+1, target.compareTo(new TimeRange(start, new Time())));
	}

	@Test
	public void testGetStart() {
		assertSame(start, target.getStart());
	}

	@Test
	public void testGetEnd() {
		assertSame(end, target.getEnd());
	}

	@Test
	public void testInRange_Default() throws Exception {

		assertFalse(target.inRange(null));

		assertFalse(target.inRange(new Time(8, 59, 59, 999)));
		assertTrue(target.inRange(new Time(9, 0, 0, 000)));
		assertTrue(target.inRange(new Time(9, 0, 0, 001)));

		assertTrue(target.inRange(new Time(14, 59, 59, 999)));
		assertFalse(target.inRange(new Time(15, 0, 0, 000)));
		assertFalse(target.inRange(new Time(15, 0, 0, 001)));

		// End time past midnight (15 -> 09)
		target = new TimeRange(end, start);

		assertFalse(target.inRange(new Time(14, 59, 59, 999)));
		assertTrue(target.inRange(new Time(15, 0, 0, 000)));
		assertTrue(target.inRange(new Time(15, 0, 0, 001)));

		assertTrue(target.inRange(new Time(8, 59, 59, 999)));
		assertFalse(target.inRange(new Time(9, 0, 0, 000)));
		assertFalse(target.inRange(new Time(9, 0, 0, 001)));

		// Same (09 -> 09)
		target = new TimeRange(start, start);

		assertFalse(target.inRange(new Time(8, 59, 59, 999)));
		assertFalse(target.inRange(new Time(9, 0, 0, 000)));
		assertFalse(target.inRange(new Time(9, 0, 0, 001)));

	}

	@Test
	public void testInRange_Include_Include() throws Exception {

		assertFalse(target.inRange(new Time(8, 59, 59, 999), true, true));
		assertTrue(target.inRange(new Time(9, 0, 0, 000), true, true));
		assertTrue(target.inRange(new Time(9, 0, 0, 001), true, true));

		assertTrue(target.inRange(new Time(14, 59, 59, 999), true, true));
		assertTrue(target.inRange(new Time(15, 0, 0, 000), true, true));
		assertFalse(target.inRange(new Time(15, 0, 0, 001), true, true));

		// End time past midnight (15 -> 09)
		target = new TimeRange(end, start);

		assertFalse(target.inRange(new Time(14, 59, 59, 999), true, true));
		assertTrue(target.inRange(new Time(15, 0, 0, 000), true, true));
		assertTrue(target.inRange(new Time(15, 0, 0, 001), true, true));

		assertTrue(target.inRange(new Time(8, 59, 59, 999), true, true));
		assertTrue(target.inRange(new Time(9, 0, 0, 000), true, true));
		assertFalse(target.inRange(new Time(9, 0, 0, 001), true, true));

		// Same (09 -> 09)
		target = new TimeRange(start, start);

		assertFalse(target.inRange(new Time(8, 59, 59, 999), true, true));
		assertTrue(target.inRange(new Time(9, 0, 0, 000), true, true));
		assertFalse(target.inRange(new Time(9, 0, 0, 001), true, true));

	}

	@Test
	public void testInRange_Include_Exclude() throws Exception {

		assertFalse(target.inRange(new Time(8, 59, 59, 999), true, false));
		assertTrue(target.inRange(new Time(9, 0, 0, 000), true, false));
		assertTrue(target.inRange(new Time(9, 0, 0, 001), true, false));

		assertTrue(target.inRange(new Time(14, 59, 59, 999), true, false));
		assertFalse(target.inRange(new Time(15, 0, 0, 000), true, false));
		assertFalse(target.inRange(new Time(15, 0, 0, 001), true, false));

		// End time past midnight (15 -> 09)
		target = new TimeRange(end, start);

		assertFalse(target.inRange(new Time(14, 59, 59, 999), true, false));
		assertTrue(target.inRange(new Time(15, 0, 0, 000), true, false));
		assertTrue(target.inRange(new Time(15, 0, 0, 001), true, false));

		assertTrue(target.inRange(new Time(8, 59, 59, 999), true, false));
		assertFalse(target.inRange(new Time(9, 0, 0, 000), true, false));
		assertFalse(target.inRange(new Time(9, 0, 0, 001), true, false));

		// Same (09 -> 09)
		target = new TimeRange(start, start);

		assertFalse(target.inRange(new Time(8, 59, 59, 999), true, false));
		assertFalse(target.inRange(new Time(9, 0, 0, 000), true, false));
		assertFalse(target.inRange(new Time(9, 0, 0, 001), true, false));

	}

	@Test
	public void testInRange_Exclude_Include() throws Exception {

		assertFalse(target.inRange(new Time(8, 59, 59, 999), false, true));
		assertFalse(target.inRange(new Time(9, 0, 0, 000), false, true));
		assertTrue(target.inRange(new Time(9, 0, 0, 001), false, true));

		assertTrue(target.inRange(new Time(14, 59, 59, 999), false, true));
		assertTrue(target.inRange(new Time(15, 0, 0, 000), false, true));
		assertFalse(target.inRange(new Time(15, 0, 0, 001), false, true));

		// End time past midnight (15 -> 09)
		target = new TimeRange(end, start);

		assertFalse(target.inRange(new Time(14, 59, 59, 999), false, true));
		assertFalse(target.inRange(new Time(15, 0, 0, 000), false, true));
		assertTrue(target.inRange(new Time(15, 0, 0, 001), false, true));

		assertTrue(target.inRange(new Time(8, 59, 59, 999), false, true));
		assertTrue(target.inRange(new Time(9, 0, 0, 000), false, true));
		assertFalse(target.inRange(new Time(9, 0, 0, 001), false, true));

		// Same (09 -> 09)
		target = new TimeRange(start, start);

		assertFalse(target.inRange(new Time(8, 59, 59, 999), false, true));
		assertFalse(target.inRange(new Time(9, 0, 0, 000), false, true));
		assertFalse(target.inRange(new Time(9, 0, 0, 001), false, true));

	}

	@Test
	public void testInRange_Exclude_Exclude() throws Exception {

		assertFalse(target.inRange(new Time(8, 59, 59, 999), false, false));
		assertFalse(target.inRange(new Time(9, 0, 0, 000), false, false));
		assertTrue(target.inRange(new Time(9, 0, 0, 001), false, false));

		assertTrue(target.inRange(new Time(14, 59, 59, 999), false, false));
		assertFalse(target.inRange(new Time(15, 0, 0, 000), false, false));
		assertFalse(target.inRange(new Time(15, 0, 0, 001), false, false));

		// End time past midnight (15 -> 09)
		target = new TimeRange(end, start);

		assertFalse(target.inRange(new Time(14, 59, 59, 999), false, false));
		assertFalse(target.inRange(new Time(15, 0, 0, 000), false, false));
		assertTrue(target.inRange(new Time(15, 0, 0, 001), false, false));

		assertTrue(target.inRange(new Time(8, 59, 59, 999), false, false));
		assertFalse(target.inRange(new Time(9, 0, 0, 000), false, false));
		assertFalse(target.inRange(new Time(9, 0, 0, 001), false, false));

		// Same (09 -> 09)
		target = new TimeRange(start, start);

		assertFalse(target.inRange(new Time(8, 59, 59, 999), false, false));
		assertFalse(target.inRange(new Time(9, 0, 0, 000), false, false));
		assertFalse(target.inRange(new Time(9, 0, 0, 001), false, false));

	}

}
