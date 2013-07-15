package com.after_sunrise.commons.spring.editor;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author takanori.takase
 */
public class DateEditor extends PropertyEditorSupport {

	private static final String FORMAT = "yyyy-MM-dd";

	private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT");

	private final DateFormat format;

	public DateEditor() {

		format = new SimpleDateFormat(FORMAT);

		format.setTimeZone(TIME_ZONE);

	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		Date date;

		try {
			date = format.parse(text);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date : " + text);
		}

		setValue(date);

	}

}
