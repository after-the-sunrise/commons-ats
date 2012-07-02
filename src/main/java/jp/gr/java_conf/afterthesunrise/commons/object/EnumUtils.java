package jp.gr.java_conf.afterthesunrise.commons.object;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author takanori.takase
 */
public final class EnumUtils {

	private EnumUtils() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static <E extends Enum<E>> Map<String, E> map(Class<E> clazz) {

		Map<String, E> map = new HashMap<String, E>();

		for (E e : clazz.getEnumConstants()) {
			map.put(e.name(), e);
		}

		return Collections.unmodifiableMap(map);

	}

}
