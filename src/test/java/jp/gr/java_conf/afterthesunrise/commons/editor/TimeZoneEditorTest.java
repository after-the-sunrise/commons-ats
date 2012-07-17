package jp.gr.java_conf.afterthesunrise.commons.editor;

import static org.junit.Assert.assertEquals;

import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class TimeZoneEditorTest {

	private TimeZoneEditor target;

	@Before
	public void setUp() throws Exception {
		target = new TimeZoneEditor();
	}

	@Test
	public void testSetAsText_Tokyo() {

		target.setAsText("Asia/Tokyo");

		TimeZone tz = (TimeZone) target.getValue();

		assertEquals("Asia/Tokyo", tz.getID());

	}

	@Test
	public void testSetAsText_Default() {

		target.setAsText("GMT");

		TimeZone tz = (TimeZone) target.getValue();

		assertEquals("GMT", tz.getID());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_Invalid() {

		target.setAsText("Foo/Bar");

	}

}
