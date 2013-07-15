package com.after_sunrise.commons.spring.editor;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class URLEditorTest {

	private URLEditor target;

	@Before
	public void setUp() throws Exception {
		target = new URLEditor();
	}

	@Test
	public void testSetAsText() {

		target.setAsText("test.properties");

		URL url = (URL) target.getValue();

		assertEquals("test.properties", new File(url.getFile()).getName());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_Invalid() {

		target.setAsText(null);

	}

}
