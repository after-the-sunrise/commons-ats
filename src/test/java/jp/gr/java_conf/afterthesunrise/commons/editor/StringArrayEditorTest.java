package jp.gr.java_conf.afterthesunrise.commons.editor;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class StringArrayEditorTest {

	private StringArrayEditor target;

	@Before
	public void setUp() throws Exception {
		target = new StringArrayEditor();
	}

	@Test
	public void testSetAsText() {

		target.setAsText("foo,bar,hoge");

		String[] arr = (String[]) target.getValue();

		assertEquals(3, arr.length);

		assertEquals("foo", arr[0]);
		assertEquals("bar", arr[1]);
		assertEquals("hoge", arr[2]);

	}

	@Test
	public void testSetAsText_Blank() {

		target.setAsText("");

		String[] arr = (String[]) target.getValue();

		assertEquals(0, arr.length);

	}

}
