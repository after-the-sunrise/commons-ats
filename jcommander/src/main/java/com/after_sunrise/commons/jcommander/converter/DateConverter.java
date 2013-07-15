package com.after_sunrise.commons.jcommander.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author takanori.takase
 */
public class DateConverter extends AbstractIStringConverter<Date> {

	private final List<DateFormat> dateFormats = new ArrayList<DateFormat>();

	public DateConverter() {
		dateFormats.add(new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS z"));
		dateFormats.add(new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss z"));
		dateFormats.add(new SimpleDateFormat("yyyy-MM-dd_HH:mm z"));
		dateFormats.add(new SimpleDateFormat("yyyy-MM-dd_HH z"));
		dateFormats.add(new SimpleDateFormat("yyyy-MM-dd z"));
		dateFormats.add(new SimpleDateFormat("yyyy-MM z"));
		dateFormats.add(new SimpleDateFormat("yyyy z"));
		dateFormats.add(new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS"));
		dateFormats.add(new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"));
		dateFormats.add(new SimpleDateFormat("yyyy-MM-dd_HH:mm"));
		dateFormats.add(new SimpleDateFormat("yyyy-MM-dd_HH"));
		dateFormats.add(new SimpleDateFormat("yyyy-MM-dd"));
		dateFormats.add(new SimpleDateFormat("yyyy-MM"));
		dateFormats.add(new SimpleDateFormat("yyyy"));
	}

	@Override
	public Date convert(String value) {

		for (DateFormat format : dateFormats) {
			try {
				return format.parse(value);
			} catch (ParseException e) {
				// Ignore
			}
		}

		throw new IllegalArgumentException("Invalid format : " + value);

	}

}
