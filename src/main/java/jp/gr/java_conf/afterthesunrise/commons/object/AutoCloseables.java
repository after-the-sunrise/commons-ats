package jp.gr.java_conf.afterthesunrise.commons.object;

import com.google.common.io.Closeables;

/**
 * @author takanori.takase
 */
public final class AutoCloseables {

	private AutoCloseables() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	/**
	 * @see Closeables#closeQuietly(java.io.Closeable)
	 */
	public static void closeQuietly(AutoCloseable closeable) {

		if (closeable == null) {
			return;
		}

		try {
			closeable.close();
		} catch (Exception e) {
			// Ignore
		}

	}

}
