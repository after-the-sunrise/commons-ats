package com.after_sunrise.commons.base.object;

import java.io.File;
import java.lang.management.ManagementFactory;

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

	public static String getUserName() {
		return System.getProperty("user.name", null);
	}

	public static File getUserHome() {
		return new File(System.getProperty("user.home", null));
	}

}
