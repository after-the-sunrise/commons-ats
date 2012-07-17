package jp.gr.java_conf.afterthesunrise.commons.argument;

import java.util.TimeZone;

/**
 * @author takanori.takase
 */
public class TimeConverter extends DateConverter {

	public static final String FORMAT = "yyyy-MM-dd_HH:mm:ss.SSS";

	public TimeConverter() {
		super(FORMAT, TimeZone.getDefault());
	}

}
