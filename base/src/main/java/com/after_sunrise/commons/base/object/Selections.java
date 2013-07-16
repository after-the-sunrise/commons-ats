package com.after_sunrise.commons.base.object;

import java.util.Collection;
import java.util.Map;

/**
 * @author takanori.takase
 */
public final class Selections {

	private Selections() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static <T> T selectNotNull(T value1, T value2) {

		if (value1 == null && value2 == null) {
			throw new IllegalArgumentException("Null parameters.");
		}

		return value1 != null ? value1 : value2;

	}

	public static <T> T selectFirst(Collection<T> c, T value) {

		if (c == null || c.isEmpty()) {
			return null;
		}

		return c.contains(value) ? value : c.iterator().next();

	}

	public static <T> T selectFirst(T[] c, T value) {

		if (c == null || c.length == 0) {
			return null;
		}

		for (T val : c) {
			if (Comparisons.isEqual(val, value)) {
				return val;
			}
		}

		return c[0];

	}

	public static <K> K selectFirstKey(Map<K, ?> m, K key) {

		if (m == null || m.isEmpty()) {
			return null;
		}

		return m.containsKey(key) ? key : m.keySet().iterator().next();

	}

	public static <V> V selectFirstValue(Map<?, V> m, V value) {

		if (m == null || m.isEmpty()) {
			return null;
		}

		return m.containsValue(value) ? value : m.values().iterator().next();

	}

}
