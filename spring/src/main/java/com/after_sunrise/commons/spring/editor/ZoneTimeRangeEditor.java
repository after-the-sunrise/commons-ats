package com.after_sunrise.commons.spring.editor;

import java.beans.PropertyEditorSupport;
import java.util.TimeZone;

import com.after_sunrise.commons.base.time.Time;
import com.after_sunrise.commons.base.time.ZoneTimeRange;

/**
 * @author takanori.takase
 */
public class ZoneTimeRangeEditor extends PropertyEditorSupport {

	private static final String ZONE_DELIMITER = "\\s";

	private static final String RANGE_DELIMITER = "-";

	private static final String TIME_DELIMITER = ":";

	private static final String MSG = "Invalid format : ";

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		// "HH:mm:ss:SSS-HH:mm:ss:SSS Asia/Tokyo"

		String[] fields = text.split(ZONE_DELIMITER);

		if (fields.length != 2) {
			throw new IllegalArgumentException(MSG + text);
		}

		TimeZone tz = TimeZone.getTimeZone(fields[1]);

		if (!tz.getID().equals(fields[1])) {
			throw new IllegalArgumentException(MSG + text);
		}

		String[] times = fields[0].split(RANGE_DELIMITER);

		if (times.length != 2) {
			throw new IllegalArgumentException(MSG + text);
		}

		try {

			Time s = convert(times[0]);

			Time e = convert(times[1]);

			setValue(new ZoneTimeRange(tz, s, e));

		} catch (RuntimeException e) {
			throw new IllegalArgumentException(MSG + text, e);
		}

	}

	private Time convert(String value) {

		String[] times = value.split(TIME_DELIMITER);

		if (times.length > 4) {
			throw new IllegalArgumentException(MSG + value);
		}

		int[] v = new int[4];

		for (int i = 0; i < times.length; i++) {
			v[i] = Integer.parseInt(times[i]);
		}

		return new Time(v[0], v[1], v[2], v[3]);

	}
}
