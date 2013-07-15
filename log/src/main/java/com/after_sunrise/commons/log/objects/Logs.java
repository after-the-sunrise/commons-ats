package com.after_sunrise.commons.log.objects;

import org.apache.commons.logging.Log;

/**
 * @author takanori.takase
 */
public final class Logs {

	private Logs() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static String logTrace(Log log, String m, Object... v) {
		return logTraces(log, m, null, v);
	}

	public static String logTraces(Log log, String m, Throwable t, Object... v) {

		if (log == null || !log.isTraceEnabled() || m == null) {
			return null;
		}

		String message = String.format(m, v);

		if (t == null) {
			log.trace(message);
		} else {
			log.trace(message, t);
		}

		return message;

	}

	public static String logDebug(Log log, String m, Object... v) {
		return logDebugs(log, m, null, v);
	}

	public static String logDebugs(Log log, String m, Throwable t, Object... v) {

		if (log == null || !log.isDebugEnabled() || m == null) {
			return null;
		}

		String message = String.format(m, v);

		if (t == null) {
			log.debug(message);
		} else {
			log.debug(message, t);
		}

		return message;

	}

	public static String logInfo(Log log, String m, Object... v) {
		return logInfos(log, m, null, v);
	}

	public static String logInfos(Log log, String m, Throwable t, Object... v) {

		if (log == null || !log.isInfoEnabled() || m == null) {
			return null;
		}

		String message = String.format(m, v);

		if (t == null) {
			log.info(message);
		} else {
			log.info(message, t);
		}

		return message;

	}

	public static String logWarn(Log log, String m, Object... v) {
		return logWarns(log, m, null, v);
	}

	public static String logWarns(Log log, String m, Throwable t, Object... v) {

		if (log == null || !log.isWarnEnabled() || m == null) {
			return null;
		}

		String message = String.format(m, v);

		if (t == null) {
			log.warn(message);
		} else {
			log.warn(message, t);
		}

		return message;

	}

	public static String logError(Log log, String m, Object... v) {
		return logErrors(log, m, null, v);
	}

	public static String logErrors(Log log, String m, Throwable t, Object... v) {

		if (log == null || !log.isErrorEnabled() || m == null) {
			return null;
		}

		String message = String.format(m, v);

		if (t == null) {
			log.error(message);
		} else {
			log.error(message, t);
		}

		return message;

	}

	public static String logFatal(Log log, String m, Object... v) {
		return logFatals(log, m, null, v);
	}

	public static String logFatals(Log log, String m, Throwable t, Object... v) {

		if (log == null || !log.isFatalEnabled() || m == null) {
			return null;
		}

		String message = String.format(m, v);

		if (t == null) {
			log.fatal(message);
		} else {
			log.fatal(message, t);
		}

		return message;

	}

}
