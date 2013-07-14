package info.after_sunrise.commons.spring.editor;

import static org.junit.Assert.assertEquals;

import javax.management.ObjectName;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class ObjectNameEditorTest {

	private ObjectNameEditor target;

	@Before
	public void setUp() throws Exception {
		target = new ObjectNameEditor();
	}

	@Test
	public void testSetAsText() {

		target.setAsText("com.example.test:type=Test");

		ObjectName name = (ObjectName) target.getValue();

		assertEquals("com.example.test", name.getDomain());

		assertEquals("Test", name.getKeyProperty("type"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_Invalid() {

		target.setAsText("foobar");

	}

}
