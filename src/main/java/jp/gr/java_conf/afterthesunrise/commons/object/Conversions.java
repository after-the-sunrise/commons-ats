package jp.gr.java_conf.afterthesunrise.commons.object;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import jp.gr.java_conf.afterthesunrise.commons.comparator.NullSafeComparator;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author takanori.takase
 */
public final class Conversions {

	private Conversions() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static interface Identifiable<T> {
		T getId();
	}

	@SuppressWarnings("unchecked")
	public static <K, V extends Identifiable<K>> Map<K, V> map(V... values) {

		if (ArrayUtils.isEmpty(values)) {
			return Collections.emptyMap();
		}

		Map<K, V> map = new HashMap<>();

		for (V value : values) {

			K key = value.getId();

			V prev = map.put(key, value);

			if (prev != null) {

				String msg = "Duplicate : " + key;

				throw new IllegalArgumentException(msg);

			}

		}

		return Collections.unmodifiableMap(map);

	}

	@SuppressWarnings("unchecked")
	public static <K, V extends Identifiable<K>> List<K> list(V... values) {

		if (ArrayUtils.isEmpty(values)) {
			return Collections.emptyList();
		}

		List<K> list = new ArrayList<>(values.length);

		for (int i = 0; i < values.length; i++) {

			K id = values[i].getId();

			list.add(id);

		}

		return Collections.unmodifiableList(list);

	}

	public static <K, V> List<V> convert(List<K> keys, Map<K, V> mapping) {

		checkNotNull(keys, "keys cannot be null.");

		checkNotNull(mapping, "mapping cannot be null.");

		List<V> values = new ArrayList<>(keys.size());

		for (K key : keys) {
			values.add(mapping.get(key));
		}

		return values;

	}

	public static <K, V> Set<V> convert(Set<K> keys, Map<K, V> mapping) {

		checkNotNull(keys, "keys cannot be null.");

		checkNotNull(mapping, "mapping cannot be null.");

		Set<V> values = new HashSet<>();

		for (K key : keys) {
			values.add(mapping.get(key));
		}

		return values;

	}

	public static <K extends Comparable<K>, V> SortedSet<V> convertSorted(
			Set<K> keys, Map<K, V> mapping) {

		checkNotNull(keys, "keys cannot be null.");

		checkNotNull(mapping, "mapping cannot be null.");

		SortedSet<V> values = new TreeSet<>(NullSafeComparator.get());

		for (K key : keys) {
			values.add(mapping.get(key));
		}

		return values;

	}

	public static <K1, K2, V> Map<K2, V> convertKey(Map<K1, V> source,
			Map<K1, K2> mapping) {

		checkNotNull(source, "source cannot be null.");

		checkNotNull(mapping, "mapping cannot be null.");

		Map<K2, V> values = new HashMap<>();

		for (Entry<K1, V> entry : source.entrySet()) {

			K1 oldKey = entry.getKey();

			K2 newKey = mapping.get(oldKey);

			values.put(newKey, entry.getValue());

		}

		return values;

	}

	public static <K1, K2 extends Comparable<K2>, V> SortedMap<K2, V> convertKeySorted(
			Map<K1, V> source, Map<K1, K2> mapping) {

		checkNotNull(source, "source cannot be null.");

		checkNotNull(mapping, "mapping cannot be null.");

		SortedMap<K2, V> values = new TreeMap<>(NullSafeComparator.get());

		for (Entry<K1, V> entry : source.entrySet()) {

			K1 oldKey = entry.getKey();

			K2 newKey = mapping.get(oldKey);

			values.put(newKey, entry.getValue());

		}

		return values;

	}

}
