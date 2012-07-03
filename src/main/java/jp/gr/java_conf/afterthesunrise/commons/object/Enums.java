package jp.gr.java_conf.afterthesunrise.commons.object;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author takanori.takase
 */
public final class Enums {

	private Enums() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static <E extends Enum<E>> Map<String, E> map(Class<E> clazz) {

		Map<String, E> map = new HashMap<String, E>();

		for (E e : clazz.getEnumConstants()) {
			map.put(e.name(), e);
		}

		return Collections.unmodifiableMap(map);

	}

	public static <E extends Enum<E>> E find(Class<E> clazz, String value) {

		if (clazz == null || StringUtils.isBlank(value)) {
			return null;
		}

		try {
			return Enum.valueOf(clazz, value);
		} catch (IllegalArgumentException e) {
			return null;
		}

	}

	public static <E extends Enum<E>> Collection<E> list(Class<E> clazz) {
		return list(clazz, false);
	}

	public static <E extends Enum<E>> Collection<E> list(Class<E> clazz,
			boolean blank) {

		E[] enums = checkNotNull(clazz).getEnumConstants();

		List<E> list;

		if (blank) {

			list = new ArrayList<>(enums.length + 1);

			list.add(null);

		} else {

			list = new ArrayList<>(enums.length);

		}

		list.addAll(Arrays.asList(enums));

		return Collections.unmodifiableList(list);

	}

}
