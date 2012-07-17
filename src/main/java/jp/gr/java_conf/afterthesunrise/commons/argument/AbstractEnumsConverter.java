package jp.gr.java_conf.afterthesunrise.commons.argument;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

/**
 * @author takanori.takase
 */
public abstract class AbstractEnumsConverter<E extends Enum<E>> implements
		IStringConverter<List<E>>, IParameterValidator {

	protected static final char DEFAULT_SEPARATOR = ',';

	private final Class<E> clazz;

	private final char separator;

	protected AbstractEnumsConverter(Class<E> clazz) {
		this(clazz, DEFAULT_SEPARATOR);
	}

	protected AbstractEnumsConverter(Class<E> clazz, char separator) {
		this.clazz = checkNotNull(clazz);
		this.separator = separator;
	}

	@Override
	public void validate(String name, String value) throws ParameterException {
		try {
			convert(value);
		} catch (IllegalArgumentException e) {
			throw new ParameterException(e);
		}
	}

	@Override
	public List<E> convert(String value) throws IllegalArgumentException {

		if (StringUtils.isBlank(value)) {
			return Collections.emptyList();
		}

		String[] enums = StringUtils.split(value, separator);

		List<E> list = new ArrayList<>(enums.length);

		for (String val : enums) {

			list.add(Enum.valueOf(clazz, val));

		}

		return list;

	}

}
