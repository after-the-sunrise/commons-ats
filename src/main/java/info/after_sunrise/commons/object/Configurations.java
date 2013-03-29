package info.after_sunrise.commons.object;

import static java.lang.String.format;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.google.common.io.Closeables;
import com.google.common.io.Resources;

/**
 * @author takanori.takase
 */
public final class Configurations {

	public static final String PROP_PORT_MAIN = "ats.config.port.main";

	public static final String PROP_PORT_TEST = "ats.config.port.test";

	public static final int EPHEMERAL_PORT_START = 49152;

	public static final int EPHEMERAL_PORT_END = 65535;

	private Configurations() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static Properties loadProperties(String... paths) {
		return loadProperties(true, paths);
	}

	public static Properties loadProperties(boolean classPath, String... paths)
			throws IllegalArgumentException {

		Properties prop = new Properties();

		for (String path : paths) {

			InputStream in = null;

			try {

				if (classPath) {
					in = Resources.getResource(path).openStream();
				} else {
					in = new FileInputStream(path);
				}

				prop.load(in);

			} catch (Exception e) {

				String msg = "Invalid property : " + path;

				throw new IllegalArgumentException(msg, e);

			} finally {
				Closeables.closeQuietly(in);
			}

		}

		return prop;

	}

	public static int getRandomPort() {
		return getRandomPort(EPHEMERAL_PORT_START, EPHEMERAL_PORT_END);
	}

	public static int getRandomPort(int lBound, int uBound) {

		Validations.checkPort(lBound);

		Validations.checkPort(uBound);

		if (lBound > uBound) {

			String msg = format("Invalid range : %s - %s", lBound, uBound);

			throw new IllegalArgumentException(msg);

		}

		if (lBound == uBound) {
			return lBound;
		}

		int range = uBound - lBound + 1;

		int random = (int) (System.nanoTime() % range);

		return lBound + Math.abs(random);

	}

	public static int getSystemPort(String key) {

		String val = System.getProperty(key);

		if (val == null) {
			throw new IllegalArgumentException("Port not configured : " + key);
		}

		int port;

		try {

			port = Integer.parseInt(val);

		} catch (NumberFormatException e) {

			String msg = format("Invalid port : %s=%s", key, val);

			throw new IllegalArgumentException(msg, e);

		}

		return Validations.checkPort(port);

	}

}
