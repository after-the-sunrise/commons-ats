package info.after_sunrise.commons.editor;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class PropertiesEditorTest {

	private PropertiesEditor target;

	@Before
	public void setUp() throws Exception {
		target = new PropertiesEditor();
	}

	@Test
	public void testSetAsText() {

		target.setAsText("test.properties");

		Properties p = (Properties) target.getValue();

		assertEquals("bar", p.getProperty("foo"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_Invalid() {

		target.setAsText("foo.bar");

	}

}
