package com.after_sunrise.commons.spring.editor;

import static org.junit.Assert.assertEquals;

import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class RoundingModeEditorTest {

	private RoundingModeEditor target;

	@Before
	public void setUp() throws Exception {
		target = new RoundingModeEditor();
	}

	@Test
	public void testSetAsText() {

		target.setAsText("DOWN");

		assertEquals(RoundingMode.DOWN, target.getValue());

	}

}
