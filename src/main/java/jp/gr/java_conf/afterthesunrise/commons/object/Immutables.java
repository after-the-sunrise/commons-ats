package jp.gr.java_conf.afterthesunrise.commons.object;

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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

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

	public static final <T> List<T> arrayList(Collection<T> col) {

		if (col == null) {
			return emptyList();
		}

		return unmodifiableList(new ArrayList<>(col));

	}

	public static final <T> List<T> linkedList(Collection<T> col) {

		if (col == null) {
			return emptyList();
		}

		return unmodifiableList(new LinkedList<>(col));

	}

	public static final <T> Set<T> hashSet(Collection<T> col) {

		if (col == null) {
			return emptySet();
		}

		return unmodifiableSet(new HashSet<>(col));

	}

	public static final <T> SortedSet<T> treeSet(Collection<T> col) {

		@SuppressWarnings("unchecked")
		SortedSet<T> result = SET;

		if (col != null) {
			result = unmodifiableSortedSet(new TreeSet<>(col));
		}

		return result;

	}

	public static final <K, V> Map<K, V> hashMap(Map<K, V> map) {

		if (map == null) {
			return emptyMap();
		}

		return unmodifiableMap(new HashMap<>(map));

	}

	public static final <K, V> SortedMap<K, V> treeMap(Map<K, V> map) {

		@SuppressWarnings("unchecked")
		SortedMap<K, V> result = MAP;

		if (map != null) {
			result = unmodifiableSortedMap(new TreeMap<>(map));
		}

		return result;

	}

}
