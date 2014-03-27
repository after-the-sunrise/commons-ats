package com.after_sunrise.commons.base.object;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;

/**
 * @author takanori.takase
 */
public final class Runtimes {

	private Runtimes() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	private static final Long PROCESS_ID = retrieveProcessId("@");

	static Long retrieveProcessId(String delimiter) {

		Long pid;

		try {

			String name = ManagementFactory.getRuntimeMXBean().getName();

			int idx = name.indexOf(delimiter);

			pid = Long.valueOf(name.substring(0, idx));

		} catch (Exception e) {
			pid = null;
		}

		return pid;

	}

	public static Long getProcessId() {
		return PROCESS_ID;
	}

	public static final String getStringProperty(String key) {

		if (key == null || key.isEmpty()) {
			return null;
		}

		return System.getProperty(key);

	}

	public static final Boolean getBooleanProperty(String key) {

		String value = getStringProperty(key);

		return Boolean.valueOf(value);

	}

	public static final Integer getIntProperty(String key) {

		String value = getStringProperty(key);

		Integer val;

		try {
			val = Integer.valueOf(value);
		} catch (Exception e) {
			val = null;
		}

		return val;

	}

	public static final Long getLongProperty(String key) {

		String value = getStringProperty(key);

		Long val;

		try {
			val = Long.valueOf(value);
		} catch (Exception e) {
			val = null;
		}

		return val;

	}

	public static final Double getDoubleProperty(String key) {

		String value = getStringProperty(key);

		Double val;

		try {
			val = Double.valueOf(value);
		} catch (Exception e) {
			val = null;
		}

		return val;

	}

	public static final BigDecimal getDecimalProperty(String key) {

		String value = getStringProperty(key);

		BigDecimal val;

		try {
			val = new BigDecimal(value);
		} catch (Exception e) {
			val = null;
		}

		return val;

	}

	public static final File getFileProperty(String key) {

		String value = getStringProperty(key);

		if (value == null || value.isEmpty()) {
			return null;
		}

		return new File(value);

	}

	public static String getUserName() {
		return getStringProperty("user.name");
	}

	public static File getUserHome() {
		return getFileProperty("user.home");
	}

}
