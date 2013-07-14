package info.after_sunrise.commons.jcommander.converter;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

/**
 * @author takanori.takase
 */
abstract class AbstractIStringConverter<V> implements IStringConverter<V>,
		IParameterValidator {

	@Override
	public void validate(String name, String value) throws ParameterException {
		try {
			convert(value);
		} catch (IllegalArgumentException e) {
			throw new ParameterException(e);
		}
	}

}
