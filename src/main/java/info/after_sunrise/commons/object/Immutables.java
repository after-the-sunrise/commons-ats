package info.after_sunrise.commons.object;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.Collections.unmodifiableSortedMap;
import static java.util.Collections.unmodifiableSortedSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

/**
 * @author takanori.takase
 */
public final class Immutables {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static final SortedSet SET = unmodifiableSortedSet(new TreeSet());

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static final SortedMap MAP = unmodifiableSortedMap(new TreeMap());

	private Immutables() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	@SuppressWarnings("unchecked")
	public static final <T> SortedSet<T> emptySortedSet() {
		return SET;
	}

	@SuppressWarnings("unchecked")
	public static final <K, V> SortedMap<K, V> emptySortedMap() {
		return MAP;
	}

	public static final <T> List<T> arrayList(Collection<? extends T> col) {

		if (CollectionUtils.isEmpty(col)) {
			return emptyList();
		}

		return unmodifiableList(new ArrayList<T>(col));

	}

	public static final <T> List<T> linkedList(Collection<? extends T> col) {

		if (CollectionUtils.isEmpty(col)) {
			return emptyList();
		}

		return unmodifiableList(new LinkedList<T>(col));

	}

	public static final <T> Set<T> hashSet(Collection<? extends T> col) {

		if (CollectionUtils.isEmpty(col)) {
			return emptySet();
		}

		return unmodifiableSet(new HashSet<T>(col));

	}

	public static final <T> SortedSet<T> treeSet(Collection<? extends T> col) {

		if (CollectionUtils.isEmpty(col)) {
			return emptySortedSet();
		}

		return unmodifiableSortedSet(new TreeSet<T>(col));

	}

	public static final <K, V> Map<K, V> hashMap(
			Map<? extends K, ? extends V> map) {

		if (MapUtils.isEmpty(map)) {
			return emptyMap();
		}

		return unmodifiableMap(new HashMap<K, V>(map));

	}

	public static final <K, V> SortedMap<K, V> treeMap(
			Map<? extends K, ? extends V> map) {

		if (MapUtils.isEmpty(map)) {
			return emptySortedMap();
		}

		return unmodifiableSortedMap(new TreeMap<K, V>(map));

	}

	public static final <K, V> Map<K, V> identityHashMap(
			Map<? extends K, ? extends V> map) {

		if (MapUtils.isEmpty(map)) {
			return emptyMap();
		}

		return unmodifiableMap(new IdentityHashMap<K, V>(map));

	}

}
