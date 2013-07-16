package com.after_sunrise.commons.spring.editor;

import static org.junit.Assert.assertSame;

import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class EnumEditorTest {

	private EnumEditor<RoundingMode> target;

	@Before
	public void setUp() throws Exception {
		target = new EnumEditor<RoundingMode>(RoundingMode.class) {
		};
	}

	@Test
	public void testSetAsText() {

		target.setAsText("DOWN");

		assertSame(RoundingMode.DOWN, target.getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_Invalid() {
		target.setAsText("hoge");
	}

}
