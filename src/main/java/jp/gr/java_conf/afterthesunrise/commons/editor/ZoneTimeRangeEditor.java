package jp.gr.java_conf.afterthesunrise.commons.editor;

import static org.apache.commons.lang.StringUtils.split;
import static org.apache.commons.lang.StringUtils.splitPreserveAllTokens;

import java.beans.PropertyEditorSupport;
import java.util.TimeZone;

import jp.gr.java_conf.afterthesunrise.commons.time.Time;
import jp.gr.java_conf.afterthesunrise.commons.time.ZoneTimeRange;

/**
 * @author takanori.takase
 */
public class ZoneTimeRangeEditor extends PropertyEditorSupport {

	private static final char ZONE_DELIMITER = ' ';

	private static final char RANGE_DELIMITER = '-';

	private static final char TIME_DELIMITER = ':';

	private static final String MSG = "Invalid format : ";

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		// "HH:mm:ss:SSS-HH:mm:ss:SSS Asia/Tokyo"

		String[] fields = split(text, ZONE_DELIMITER);

		if (fields.length != 2) {
			throw new IllegalArgumentException(MSG + text);
		}

		TimeZone tz = TimeZone.getTimeZone(fields[1]);

		if (!tz.getID().equals(fields[1])) {
			throw new IllegalArgumentException(MSG + text);
		}

		String[] times = split(fields[0], RANGE_DELIMITER);

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

		String[] times = splitPreserveAllTokens(value, TIME_DELIMITER);

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
