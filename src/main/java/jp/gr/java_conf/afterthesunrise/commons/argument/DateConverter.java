package jp.gr.java_conf.afterthesunrise.commons.argument;

import static com.google.common.base.Preconditions.checkNotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

/**
 * @author takanori.takase
 */
public class DateConverter implements IStringConverter<Date>,
		IParameterValidator {

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd";

	private final DateFormat dateFormat;

	public DateConverter() {
		this(DEFAULT_FORMAT, TimeZone.getDefault());
	}

	public DateConverter(String format, TimeZone timeZone) {

		dateFormat = new SimpleDateFormat(format);

		dateFormat.setTimeZone(checkNotNull(timeZone));

	}

	@Override
	public void validate(String name, String value) throws ParameterException {
		try {
			convert(value);
		} catch (RuntimeException e) {
			throw new ParameterException(e);
		}
	}

	@Override
	public Date convert(String value) {
		try {
			return dateFormat.parse(value);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
