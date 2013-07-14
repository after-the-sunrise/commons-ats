package info.after_sunrise.commons.jcommander.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.beust.jcommander.ParameterException;

/**
 * @author takanori.takase
 */
public class FileConverterTest {

	private FileConverter target;

	@Before
	public void setUp() throws Exception {
		target = new FileConverter();
	}

	@Test
	public void testValidate() {
		target.validate(null, "pom.xml");
	}

	@Test(expected = ParameterException.class)
	public void testValidate_Empty() {
		target.validate(null, "");
	}

	@Test(expected = ParameterException.class)
	public void testValidate_Invalid() {
		target.validate(null, null);
	}

	@Test
	public void testConvert() {
		assertEquals("pom.xml", target.convert("pom.xml").getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvert_Empty() {
		target.convert("").getName();
	}

}
