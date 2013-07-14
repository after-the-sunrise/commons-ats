package info.after_sunrise.commons.jcommander.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.RoundingMode;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.beust.jcommander.ParameterException;

/**
 * @author takanori.takase
 */
public class AbstractEnumsConverterTest {

	private AbstractEnumsConverter<RoundingMode> target;

	@Before
	public void setUp() throws Exception {
		target = new AbstractEnumsConverter<RoundingMode>(RoundingMode.class) {
		};
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAbstractEnumsConverter() {
		target = new AbstractEnumsConverter<RoundingMode>(null) {
		};
	}

	@Test
	public void testValidate() {
		target.validate(null, "DOWN,UP");
	}

	@Test(expected = ParameterException.class)
	public void testValidate_Failure() {
		target.validate(null, "DOWN,HOGE");
	}

	@Test
	public void testConvert() {

		List<RoundingMode> list = target.convert("DOWN,UP");

		assertEquals(2, list.size());

		assertTrue(list.contains(RoundingMode.DOWN));

		assertTrue(list.contains(RoundingMode.UP));

	}

	@Test
	public void testConvert_Blank() {

		List<RoundingMode> list = target.convert("");

		assertEquals(0, list.size());

	}

	@Test
	public void testConvert_CustomDelimiter() {

		target = new AbstractEnumsConverter<RoundingMode>(RoundingMode.class,
				"%") {
		};

		List<RoundingMode> list = target.convert("DOWN%UP");

		assertEquals(2, list.size());

		assertTrue(list.contains(RoundingMode.DOWN));

		assertTrue(list.contains(RoundingMode.UP));

	}

}
