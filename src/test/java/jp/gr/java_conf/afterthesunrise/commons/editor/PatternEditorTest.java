package jp.gr.java_conf.afterthesunrise.commons.editor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class PatternEditorTest {

	private PatternEditor target;

	@Before
	public void setUp() throws Exception {
		target = new PatternEditor();
	}

	@Test
	public void testSetAsText() {

		target.setAsText("^$");

		Object value = target.getValue();

		assertEquals("^$", ((Pattern) value).pattern());

		target.setAsText("^$");

		assertSame(value, target.getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_Invalid() {

		target.setAsText("[");

	}

}
