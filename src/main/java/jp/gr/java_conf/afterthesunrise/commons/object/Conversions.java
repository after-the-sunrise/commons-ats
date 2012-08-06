package jp.gr.java_conf.afterthesunrise.commons.object;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author takanori.takase
 */
public final class Conversions {

	private Conversions() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static <K, V> List<V> convert(List<K> keys, Map<K, V> mapping) {

		checkNotNull(keys, "keys cannot be null.");

		checkNotNull(mapping, "mapping cannot be null.");

		List<V> values = new ArrayList<V>(keys.size());

		for (K key : keys) {
			values.add(mapping.get(key));
		}

		return values;

	}

	public static <K, V> Set<V> convert(Set<K> keys, Map<K, V> mapping) {

		checkNotNull(keys, "keys cannot be null.");

		checkNotNull(mapping, "mapping cannot be null.");

		Set<V> values = new HashSet<V>();

		for (K key : keys) {
			values.add(mapping.get(key));
		}

		return values;

	}

	public static <K1, K2, V> Map<K2, V> convertKey(Map<K1, V> source,
			Map<K1, K2> mapping) {

		checkNotNull(source, "source cannot be null.");

		checkNotNull(mapping, "mapping cannot be null.");

		Map<K2, V> values = new HashMap<K2, V>();

		for (Entry<K1, V> entry : source.entrySet()) {

			K1 oldKey = entry.getKey();

			K2 newKey = mapping.get(oldKey);

			values.put(newKey, entry.getValue());

		}

		return values;

	}

}
