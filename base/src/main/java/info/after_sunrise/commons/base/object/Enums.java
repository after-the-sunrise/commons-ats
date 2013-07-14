package info.after_sunrise.commons.base.object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author takanori.takase
 */
public final class Enums {

	private Enums() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static String toName(Enum<?> value) {
		return toName(value, null);
	}

	public static String toName(Enum<?> value, String nullvalue) {
		return value == null ? nullvalue : value.name();
	}

	public static <E extends Enum<E>> Map<String, E> map(Class<E> clazz) {

		Map<String, E> map = new HashMap<String, E>();

		for (E e : clazz.getEnumConstants()) {
			map.put(e.name(), e);
		}

		return Collections.unmodifiableMap(map);

	}

	public static <K extends Enum<K>, V extends Enum<V>> Map<K, V> map(
			Class<K> keyClass, Class<V> valClass) {

		Map<K, V> map = new EnumMap<K, V>(keyClass);

		for (K key : keyClass.getEnumConstants()) {

			map.put(key, Enum.valueOf(valClass, key.name()));

		}

		return Collections.unmodifiableMap(map);

	}

	public static <E extends Enum<E>> E find(Class<E> clazz, String value) {

		if (clazz == null || value == null || value.isEmpty()) {
			return null;
		}

		try {
			return Enum.valueOf(clazz, value);
		} catch (IllegalArgumentException e) {
			return null;
		}

	}

	public static <E extends Enum<E>> Set<E> findSet(Class<E> clazz,
			Collection<String> values) {

		if (clazz == null || values == null) {
			return null;
		}

		Set<E> result = new HashSet<E>();

		for (String value : values) {
			result.add(find(clazz, value));
		}

		return Collections.unmodifiableSet(result);

	}

	public static <E extends Enum<E>> List<E> findList(Class<E> clazz,
			Collection<String> values) {

		if (clazz == null || values == null) {
			return null;
		}

		List<E> result = new ArrayList<E>(values.size());

		for (String value : values) {
			result.add(find(clazz, value));
		}

		return Collections.unmodifiableList(result);

	}

	public static <E extends Enum<E>> Collection<E> list(Class<E> clazz) {
		return list(clazz, false);
	}

	public static <E extends Enum<E>> Collection<E> list(Class<E> clazz,
			boolean blank) {

		E[] enums = clazz.getEnumConstants();

		List<E> list;

		if (blank) {

			list = new ArrayList<E>(enums.length + 1);

			list.add(null);

		} else {

			list = new ArrayList<E>(enums.length);

		}

		list.addAll(Arrays.asList(enums));

		return Collections.unmodifiableList(list);

	}

}
