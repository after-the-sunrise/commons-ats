package info.after_sunrise.commons.editor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Charsets;

/**
 * @author takanori.takase
 */
public class CharsetEditorTest {

	private CharsetEditor target;

	@Before
	public void setUp() {
		target = new CharsetEditor();
	}

	@Test
	public void testSetAsText() {

		target.setAsText("UTF-8");

		assertSame(Charsets.UTF_8, target.getValue());

		target.setAsText("EUC-JP");

		Object value = target.getValue();

		assertEquals(Charset.forName("EUC-JP"), value);

		target.setAsText("EUC-JP");

		assertSame(value, target.getValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_Invalid() {
		target.setAsText("FOOBAR");
	}

}
