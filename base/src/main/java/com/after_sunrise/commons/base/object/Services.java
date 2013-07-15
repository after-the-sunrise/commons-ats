package com.after_sunrise.commons.base.object;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author takanori.takase
 */
public class Services {

	private Services() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static <T> List<T> getServices(Class<T> clazz) {

		ServiceLoader<T> loader = ServiceLoader.load(clazz);

		List<T> list = new ArrayList<T>();

		for (Iterator<T> itr = loader.iterator(); itr.hasNext();) {
			list.add(itr.next());
		}

		return list;

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
