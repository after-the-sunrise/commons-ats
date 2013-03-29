package info.after_sunrise.commons.editor;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class TimeUnitEditorTest {

	private TimeUnitEditor target;

	@Before
	public void setUp() throws Exception {
		target = new TimeUnitEditor();
	}

	@Test
	public void testSetAsText() {

		target.setAsText("DAYS");

		assertEquals(TimeUnit.DAYS, target.getValue());

	}

}
