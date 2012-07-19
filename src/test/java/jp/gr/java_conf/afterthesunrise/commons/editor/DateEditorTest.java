package jp.gr.java_conf.afterthesunrise.commons.editor;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class DateEditorTest {

	private DateEditor target;

	@Before
	public void setUp() throws Exception {
		target = new DateEditor();
	}

	@Test
	public void testSetAsText() {

		target.setAsText("1970-01-01");

		assertEquals(0L, ((Date) target.getValue()).getTime());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_Invalid() {

		target.setAsText("foo");

	}

}
