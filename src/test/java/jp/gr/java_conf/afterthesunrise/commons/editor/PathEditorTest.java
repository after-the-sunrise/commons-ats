package jp.gr.java_conf.afterthesunrise.commons.editor;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class PathEditorTest {

	private PathEditor target;

	@Before
	public void setUp() throws Exception {
		target = new PathEditor();
	}

	@Test
	public void testSetAsText() {

		target.setAsText("pom.xml");

		Path path = (Path) target.getValue();

		assertEquals("pom.xml", path.getFileName().toString());

	}

}
