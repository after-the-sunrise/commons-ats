package com.after_sunrise.commons.spring.editor;

import static org.junit.Assert.assertEquals;

import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

import com.after_sunrise.commons.base.time.Time;
import com.after_sunrise.commons.base.time.ZoneTimeRange;

/**
 * @author takanori.takase
 */
public class ZoneTimeRangeEditorTest {

	private ZoneTimeRangeEditor target;

	@Before
	public void setUp() throws Exception {
		target = new ZoneTimeRangeEditor();
	}

	@Test
	public void testSetAsText() {

		target.setAsText("01:23:45:678-12:34:56:789 Asia/Tokyo");

		ZoneTimeRange result = (ZoneTimeRange) target.getValue();

		TimeZone tz = result.getTimeZone();
		assertEquals("Asia/Tokyo", tz.getID());

		Time start = result.getStart();
		assertEquals(1, start.getHour());
		assertEquals(23, start.getMinute());
		assertEquals(45, start.getSecond());
		assertEquals(678, start.getMillisecond());

		Time end = result.getEnd();
		assertEquals(12, end.getHour());
		assertEquals(34, end.getMinute());
		assertEquals(56, end.getSecond());
		assertEquals(789, end.getMillisecond());

	}

	@Test
	public void testSetAsText_ShortStyle() {

		target.setAsText("01:23:45-12:34 Asia/Tokyo");

		ZoneTimeRange result = (ZoneTimeRange) target.getValue();

		TimeZone tz = result.getTimeZone();
		assertEquals("Asia/Tokyo", tz.getID());

		Time start = result.getStart();
		assertEquals(1, start.getHour());
		assertEquals(23, start.getMinute());
		assertEquals(45, start.getSecond());
		assertEquals(0, start.getMillisecond());

		Time end = result.getEnd();
		assertEquals(12, end.getHour());
		assertEquals(34, end.getMinute());
		assertEquals(0, end.getSecond());
		assertEquals(0, end.getMillisecond());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_NoTimeZone() {
		target.setAsText("01:23:45:678-12:34:56:789");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_InvalidTimeZone() {
		target.setAsText("01:23:45:678-12:34:56:789 Foo/Bar");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_InvalidTime() {
		target.setAsText("12:34:56:789 Asia/Tokyo");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_PartialTime() {
		target.setAsText("-12:34:56:789 Asia/Tokyo");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_ParseFailure() {
		target.setAsText("HH:mm:ss:SSS-12:34:56:789 Asia/Tokyo");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_TooManyStartArguments() {
		target.setAsText("01:23:45:678:000-12:34:56:789 Asia/Tokyo");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_TooManyEndArguments() {
		target.setAsText("01:23:45:678-12:34:56:789:000 Asia/Tokyo");
	}

}
