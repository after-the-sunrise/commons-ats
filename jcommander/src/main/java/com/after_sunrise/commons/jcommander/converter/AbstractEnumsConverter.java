package com.after_sunrise.commons.jcommander.converter;

import static com.after_sunrise.commons.base.object.Validations.checkNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author takanori.takase
 */
public abstract class AbstractEnumsConverter<E extends Enum<E>> extends
		AbstractIStringConverter<List<E>> {

	protected static final String DEFAULT_SEPARATOR = ",";

	private final Class<E> clazz;

	private final String separator;

	protected AbstractEnumsConverter(Class<E> clazz) {
		this(clazz, DEFAULT_SEPARATOR);
	}

	protected AbstractEnumsConverter(Class<E> clazz, String separator) {
		this.clazz = checkNotNull(clazz);
		this.separator = separator;
	}

	@Override
	public List<E> convert(String value) throws IllegalArgumentException {

		if (value == null || value.isEmpty()) {
			return Collections.emptyList();
		}

		String[] enums = value.split(separator);

		List<E> list = new ArrayList<E>(enums.length);

		for (String val : enums) {

			E e = Enum.valueOf(clazz, val);

			list.add(e);

		}

		return list;

	}

}
