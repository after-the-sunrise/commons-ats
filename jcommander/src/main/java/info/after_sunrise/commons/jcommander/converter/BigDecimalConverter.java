package info.after_sunrise.commons.jcommander.converter;

import java.math.BigDecimal;

/**
 * @author takanori.takase
 */
public class BigDecimalConverter extends AbstractIStringConverter<BigDecimal> {

	@Override
	public BigDecimal convert(String value) {
		return new BigDecimal(value);
	}

}
