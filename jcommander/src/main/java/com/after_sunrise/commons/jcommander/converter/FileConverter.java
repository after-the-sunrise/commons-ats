package com.after_sunrise.commons.jcommander.converter;

import java.io.File;

import com.beust.jcommander.ParameterException;

/**
 * @author takanori.takase
 */
public class FileConverter extends AbstractIStringConverter<File> {

	@Override
	public File convert(String value) throws ParameterException {

		if (value == null || value.isEmpty()) {
			throw new IllegalArgumentException("Invalid path : " + value);
		}

		return new File(value);
	}

}
