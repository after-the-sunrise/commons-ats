package jp.gr.java_conf.afterthesunrise.commons.argument;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.beust.jcommander.ParameterException;

/**
 * @author takanori.takase
 */
public class DateConverterTest {

	private DateConverter target;

	@Before
	public void setUp() throws Exception {
		target = new DateConverter();
	}

	@Test
	public void testValidate() {
		target.validate(null, "2012-01-23");
	}

	@Test(expected = ParameterException.class)
	public void testValidate_Failure() {
		target.validate(null, "HOGE");
	}

	@Test
	public void testConvert() {

		Date value = target.convert("2012-01-23");

		assertEquals(1327244400000L, value.getTime());

	}

}
