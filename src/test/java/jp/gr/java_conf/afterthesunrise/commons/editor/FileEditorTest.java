package jp.gr.java_conf.afterthesunrise.commons.editor;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class FileEditorTest {

	private FileEditor target;

	@Before
	public void setUp() throws Exception {
		target = new FileEditor();
	}

	@Test
	public void testSetAsText() {

		target.setAsText("pom.xml");

		File file = (File) target.getValue();

		assertEquals("pom.xml", file.getName());

	}

}
