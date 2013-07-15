package com.after_sunrise.commons.spring.editor;

import java.beans.PropertyEditorSupport;
import java.util.TimeZone;

/**
 * @author takanori.takase
 */
public class TimeZoneEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		TimeZone timeZone = TimeZone.getTimeZone(text);

		if (!timeZone.getID().equals(text)) {

			String msg = "Invalid time zone : " + text;

			throw new IllegalArgumentException(msg);

		}

		setValue(timeZone);

	}

}
