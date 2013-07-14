package info.after_sunrise.commons.jcommander.converter;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.beust.jcommander.ParameterException;

/**
 * @author takanori.takase
 */
public class BigDecimalConverterTest {

	private BigDecimalConverter target;

	@Before
	public void setUp() throws Exception {
		target = new BigDecimalConverter();
	}

	@Test
	public void testValidate() {
		target.validate(null, "123.45");
	}

	@Test(expected = ParameterException.class)
	public void testValidate_Failure() {
		target.validate(null, "HOGE");
	}

	@Test
	public void testConvert() {

		BigDecimal value = target.convert("123.45");

		assertEquals("123.45", value.toPlainString());

	}

}
