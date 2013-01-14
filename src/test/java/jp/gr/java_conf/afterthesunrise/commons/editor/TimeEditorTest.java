package jp.gr.java_conf.afterthesunrise.commons.editor;

import static org.junit.Assert.assertEquals;
import jp.gr.java_conf.afterthesunrise.commons.time.Time;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class TimeEditorTest {

	private TimeEditor target;

	@Before
	public void setUp() throws Exception {
		target = new TimeEditor();
	}

	@Test
	public void testSetAsText() {

		target.setAsText("01:23:45:678");

		Time result = (Time) target.getValue();

		assertEquals(1, result.getHour());
		assertEquals(23, result.getMinute());
		assertEquals(45, result.getSecond());
		assertEquals(678, result.getMillisecond());

	}

	@Test
	public void testSetAsText_ShortStyle() {

		target.setAsText("12:34");

		Time result = (Time) target.getValue();

		assertEquals(12, result.getHour());
		assertEquals(34, result.getMinute());
		assertEquals(0, result.getSecond());
		assertEquals(0, result.getMillisecond());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_InvalidTime() {
		target.setAsText("12:34:56:foo");
	}

}
