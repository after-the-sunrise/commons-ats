package jp.gr.java_conf.afterthesunrise.commons.argument;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.beust.jcommander.ParameterException;

/**
 * @author takanori.takase
 */
public class PathConverterTest {

	private PathConverter target;

	@Before
	public void setUp() throws Exception {
		target = new PathConverter();
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
		assertEquals("pom.xml", target.convert("pom.xml").toString());
	}

	@Test(expected = ParameterException.class)
	public void testConvert_Empty() {
		target.convert("");
	}

}