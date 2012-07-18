package jp.gr.java_conf.afterthesunrise.commons.editor;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.TimeZone;

import jp.gr.java_conf.afterthesunrise.commons.object.DateFormats;

/**
 * @author takanori.takase
 */
public class DateEditor extends PropertyEditorSupport {

	private static final String FORMAT = "yyyy-MM-dd";

	private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT");

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		Date date = DateFormats.parse(text, FORMAT, TIME_ZONE);

		if (date == null) {
			throw new IllegalArgumentException("Invalid date : " + text);
		}

		setValue(date);

	}

}
