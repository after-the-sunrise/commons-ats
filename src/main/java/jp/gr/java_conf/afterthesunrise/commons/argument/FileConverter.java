package jp.gr.java_conf.afterthesunrise.commons.argument;

import java.io.File;

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
		} catch (RuntimeException e) {
			throw new ParameterException(e);
		}
	}

	@Override
	public File convert(String value) {
		return new File(value);
	}

}
