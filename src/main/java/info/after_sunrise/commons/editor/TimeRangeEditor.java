package info.after_sunrise.commons.editor;

import static org.apache.commons.lang.StringUtils.split;
import static org.apache.commons.lang.StringUtils.splitPreserveAllTokens;
import info.after_sunrise.commons.time.Time;
import info.after_sunrise.commons.time.TimeRange;

import java.beans.PropertyEditorSupport;

/**
 * @author takanori.takase
 */
public class TimeRangeEditor extends PropertyEditorSupport {

	private static final char RANGE_DELIMITER = '-';

	private static final char TIME_DELIMITER = ':';

	private static final String MSG = "Invalid format : ";

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		// "HH:mm:ss:SSS-HH:mm:ss:SSS"

		String[] times = split(text, RANGE_DELIMITER);

		if (times.length != 2) {
			throw new IllegalArgumentException(MSG + text);
		}

		try {

			Time s = convert(times[0]);

			Time e = convert(times[1]);

			setValue(new TimeRange(s, e));

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
