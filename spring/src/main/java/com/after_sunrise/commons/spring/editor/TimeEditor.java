package com.after_sunrise.commons.spring.editor;

import java.beans.PropertyEditorSupport;

import com.after_sunrise.commons.base.time.Time;

/**
 * @author takanori.takase
 */
public class TimeEditor extends PropertyEditorSupport {

	private static final String TIME_DELIMITER = ":";

	private static final String MSG = "Invalid format : ";

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		// "HH:mm:ss:SSS"

		int[] values = { 0, 0, 0, 0 };

		String[] fields = text.split(TIME_DELIMITER, values.length);

		try {

			for (int i = 0; i < fields.length; i++) {
				values[i] = Integer.parseInt(fields[i]);
			}

			setValue(new Time(values[0], values[1], values[2], values[3]));

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(MSG + text);
		}

	}

}
