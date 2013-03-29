package info.after_sunrise.commons.argument;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

/**
 * @author takanori.takase
 */
public class FileConverter implements IStringConverter<File>,
		IParameterValidator {

	@Override
	public void validate(String name, String value) throws ParameterException {
		try {
			convert(value);
		} catch (Exception e) {
			throw new ParameterException(e);
		}
	}

	@Override
	public File convert(String value) throws ParameterException {

		if (StringUtils.isBlank(value)) {
			throw new ParameterException("Invalid path : " + value);
		}

		return new File(value);
	}

}
