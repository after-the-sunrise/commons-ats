package info.after_sunrise.commons.argument;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.beust.jcommander.ParameterException;

/**
 * @author takanori.takase
 */
public class TimeConverterTest {

	private TimeConverter target;

	@Before
	public void setUp() throws Exception {
		target = new TimeConverter();
	}

	@Test
	public void testValidate() {
		target.validate(null, "2012-01-23_01:23:45.678");
	}

	@Test(expected = ParameterException.class)
	public void testValidate_Failure() {
		target.validate(null, "HOGE");
	}

	@Test
	public void testConvert() {

		Date value = target.convert("2012-01-23_01:23:45.678");

		assertEquals(1327249425678L, value.getTime());

	}

}
