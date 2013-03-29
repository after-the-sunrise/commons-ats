package info.after_sunrise.commons.object;

import static com.google.common.base.Preconditions.checkNotNull;
import info.after_sunrise.commons.comparator.NullSafeComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import com.google.common.collect.Maps;

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

	public static String toIdString(Identifiable<?> identifiable) {

		if (identifiable == null) {
			return null;
		}

		Object id = identifiable.getId();

		return id == null ? null : id.toString();

	}

	public static <K, V extends Identifiable<K>> Map<K, V> map(V... values) {

		if (ArrayUtils.isEmpty(values)) {
			return Collections.emptyMap();
		}

		Map<K, V> map = new HashMap<K, V>();

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

	public static <K, V extends Identifiable<K>> Set<K> set(V... values) {
		return map(values).keySet();
	}

	public static <K, V extends Identifiable<K>> List<K> list(V... values) {

		if (ArrayUtils.isEmpty(values)) {
			return Collections.emptyList();
		}

		List<K> list = new ArrayList<K>(values.length);

		for (int i = 0; i < values.length; i++) {

			K id = values[i].getId();

			list.add(id);

		}

		return Collections.unmodifiableList(list);

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

	public static <K extends Comparable<K>, V> SortedSet<V> convertSorted(
			Set<K> keys, Map<K, V> mapping) {
		return convertNavigable(keys, mapping);
	}

	public static <K extends Comparable<K>, V> NavigableSet<V> convertNavigable(
			Set<K> keys, Map<K, V> mapping) {

		checkNotNull(keys, "keys cannot be null.");

		checkNotNull(mapping, "mapping cannot be null.");

		NavigableSet<V> values = new TreeSet<V>(NullSafeComparator.get());

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

	public static <K1, K2 extends Comparable<K2>, V> SortedMap<K2, V> convertKeySorted(
			Map<K1, V> source, Map<K1, K2> mapping) {
		return convertKeyNavigable(source, mapping);
	}

	public static <K1, K2 extends Comparable<K2>, V> NavigableMap<K2, V> convertKeyNavigable(
			Map<K1, V> source, Map<K1, K2> mapping) {

		checkNotNull(source, "source cannot be null.");

		checkNotNull(mapping, "mapping cannot be null.");

		NavigableMap<K2, V> values = Maps.newTreeMap(NullSafeComparator.get());

		for (Entry<K1, V> entry : source.entrySet()) {

			K1 oldKey = entry.getKey();

			K2 newKey = mapping.get(oldKey);

			values.put(newKey, entry.getValue());

		}

		return values;

	}

	public static <K, V1, V2> Map<K, V2> convertValue(Map<K, V1> source,
			Map<V1, V2> mapping) {

		checkNotNull(source, "source cannot be null.");

		checkNotNull(mapping, "mapping cannot be null.");

		Map<K, V2> values = new HashMap<K, V2>();

		for (Entry<K, V1> entry : source.entrySet()) {

			V1 oldVal = entry.getValue();

			V2 newVal = mapping.get(oldVal);

			values.put(entry.getKey(), newVal);

		}

		return values;

	}

	public static <K extends Comparable<K>, V1, V2> SortedMap<K, V2> convertValueSorted(
			Map<K, V1> source, Map<V1, V2> mapping) {
		return convertValueNavigable(source, mapping);
	}

	public static <K extends Comparable<K>, V1, V2> NavigableMap<K, V2> convertValueNavigable(
			Map<K, V1> source, Map<V1, V2> mapping) {

		checkNotNull(source, "source cannot be null.");

		checkNotNull(mapping, "mapping cannot be null.");

		NavigableMap<K, V2> values = Maps.newTreeMap(NullSafeComparator.get());

		for (Entry<K, V1> entry : source.entrySet()) {

			V1 oldVal = entry.getValue();

			V2 newVal = mapping.get(oldVal);

			values.put(entry.getKey(), newVal);

		}

		return values;

	}

	public static <T> List<T> list(List<T> list1, List<T> list2) {

		if (CollectionUtils.isEmpty(list1)) {
			return list2;
		}

		if (CollectionUtils.isEmpty(list2)) {
			return list1;
		}

		List<T> list = new ArrayList<T>(list1.size() + list2.size());

		list.addAll(list1);

		list.addAll(list2);

		return list;

	}

	public static <T> List<T> list(List<T> list, T... args) {

		if (ArrayUtils.isEmpty(args)) {
			return list;
		}

		if (CollectionUtils.isEmpty(list)) {
			return Arrays.asList(args);
		}

		List<T> result = new ArrayList<T>(list.size() + args.length);

		result.addAll(list);

		result.addAll(Arrays.asList(args));

		return result;

	}

	public static <T> List<T> asList(T... args) {

		if (ArrayUtils.isEmpty(args)) {
			return null;
		}

		return Arrays.asList(args);

	}

}
