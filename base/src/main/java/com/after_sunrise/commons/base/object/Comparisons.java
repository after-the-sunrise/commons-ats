package com.after_sunrise.commons.base.object;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.after_sunrise.commons.base.comparator.NullSafeComparator;

/**
 * @author takanori.takase
 */
public final class Comparisons {

	private Comparisons() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static boolean isEqual(Object o1, Object o2) {
		return o1 == o2 || (o1 != null && o2 != null && o1.equals(o2));
	}

	public static boolean equals(Object o1, Object o2, Object o3) {
		return isEqual(o1, o2) && isEqual(o2, o3);
	}

	public static boolean equalsNonNull(Object o1, Object o2) {
		return o1 != null && o1.equals(o2);
	}

	public static <T> int compare(Comparable<T> o1, Comparable<T> o2) {
		return NullSafeComparator.get().compare(o1, o2);
	}

	public static <T> boolean compareEquals(Comparable<T> o1, Comparable<T> o2) {
		return compare(o1, o2) == 0;
	}

	public static <T> boolean compareEquals(Comparable<T> o1, Comparable<T> o2,
			Comparable<T> o3) {
		return compareEquals(o1, o2) && compareEquals(o2, o3);
	}

	public static <T> boolean equals(Collection<T> c1, Collection<T> c2) {

		if (c1 == c2) {
			return true;
		}

		if (c1 == null || c2 == null || c1.size() != c2.size()) {
			return false;
		}

		Iterator<T> itr1 = c1.iterator();

		Iterator<T> itr2 = c2.iterator();

		while (itr1.hasNext()) {

			if (isEqual(itr1.next(), itr2.next())) {
				continue;
			}

			return false;

		}

		return true;

	}

	public static <K, V> boolean equals(Map<K, V> m1, Map<K, V> m2) {

		if (m1 == m2) {
			return true;
		}

		if (m1 == null || m2 == null || m1.size() != m2.size()) {
			return false;
		}

		for (Entry<K, V> entry : m1.entrySet()) {

			V v1 = entry.getValue();

			V v2 = m2.get(entry.getKey());

			if (isEqual(v1, v2)) {
				continue;
			}

			return false;

		}

		return true;

	}

	public static <T extends Comparable<T>> T getMin(T n1, T n2) {

		if (n1 == n2) {
			return n1;
		}

		if (n1 == null) {
			return n2;
		}

		if (n2 == null) {
			return n1;
		}

		return n1.compareTo(n2) <= 0 ? n1 : n2;

	}

	public static <T extends Comparable<T>> T getMax(T n1, T n2) {

		if (n1 == n2) {
			return n1;
		}

		if (n1 == null) {
			return n2;
		}

		if (n2 == null) {
			return n1;
		}

		return n1.compareTo(n2) >= 0 ? n1 : n2;

	}

	public static int compare(int i1, int i2) {

		if (i1 == i2) {
			return 0;
		}

		return i1 < i2 ? -1 : 1;

	}

	public static int compare(long l1, long l2) {

		if (l1 == l2) {
			return 0;
		}

		return l1 < l2 ? -1 : 1;

	}

}
