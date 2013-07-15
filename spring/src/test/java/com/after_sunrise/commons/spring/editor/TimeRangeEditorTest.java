package com.after_sunrise.commons.spring.editor;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.after_sunrise.commons.base.time.Time;
import com.after_sunrise.commons.base.time.TimeRange;

/**
 * @author takanori.takase
 */
public class TimeRangeEditorTest {

	private TimeRangeEditor target;

	@Before
	public void setUp() throws Exception {
		target = new TimeRangeEditor();
	}

	@Test
	public void testSetAsText() {

		target.setAsText("01:23:45:678-12:34:56:789");

		TimeRange result = (TimeRange) target.getValue();

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

		target.setAsText("01:23:45-12:34");

		TimeRange result = (TimeRange) target.getValue();

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
	public void testSetAsText_InvalidTime() {
		target.setAsText("-12:34:56:789");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_ParseFailure() {
		target.setAsText("HH:mm:ss:SSS-12:34:56:789");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_TooManyStartArguments() {
		target.setAsText("01:23:45:678:000-12:34:56:789");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_TooManyEndArguments() {
		target.setAsText("01:23:45:678-12:34:56:789:000");
	}

}
