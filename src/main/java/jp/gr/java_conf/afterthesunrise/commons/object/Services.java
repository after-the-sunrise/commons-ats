package jp.gr.java_conf.afterthesunrise.commons.object;

import java.util.List;
import java.util.ServiceLoader;

import com.google.common.collect.Lists;

/**
 * @author takanori.takase
 */
public class Services {

	private Services() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static <T> List<T> getServices(Class<T> clazz) {

		ServiceLoader<T> loader = ServiceLoader.load(clazz);

		return Lists.newArrayList(loader);

	}

	public static <T> T getService(Class<T> clazz) {

		List<T> services = getServices(clazz);

		if (services.isEmpty()) {

			String msg = "No spi found : " + clazz;

			throw new IllegalArgumentException(msg);

		}

		if (services.size() != 1) {

			String msg = "Multiple spi found : " + services;

			throw new IllegalArgumentException(msg);

		}

		return services.get(0);

	}

}
