package jp.gr.java_conf.afterthesunrise.commons.object;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;

/**
 * @author takanori.takase
 */
public final class Comparisons {

	private Comparisons() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static boolean equals(Object o1, Object o2, Object o3) {
		return ObjectUtils.equals(o1, o2) && ObjectUtils.equals(o2, o3);
	}

	public static <T> boolean equals(Collection<T> c1, Collection<T> c2) {

		if (c1 == c2) {
			return true;
		}

		if (c1 == null || c2 == null) {
			return false;
		}

		return CollectionUtils.isEqualCollection(c1, c2);

	}

	public static <K, V> boolean equals(Map<K, V> m1, Map<K, V> m2) {

		if (m1 == m2) {
			return true;
		}

		if (m1 == null || m2 == null) {
			return false;
		}

		if (m1.size() != m2.size()) {
			return false;
		}

		for (Entry<K, V> entry : m1.entrySet()) {

			V v1 = entry.getValue();

			V v2 = m2.get(entry.getKey());

			if (ObjectUtils.equals(v1, v2)) {
				continue;
			}

			return false;

		}

		return true;

	}

	public static BigDecimal getMin(BigDecimal n1, BigDecimal n2) {

		if (n1 == n2) {
			return n1;
		}

		if (n1 == null) {
			return n2;
		}

		if (n2 == null) {
			return n1;
		}

		return n1.min(n2);

	}

	public static BigDecimal getMax(BigDecimal n1, BigDecimal n2) {

		if (n1 == n2) {
			return n1;
		}

		if (n1 == null) {
			return n2;
		}

		if (n2 == null) {
			return n1;
		}

		return n1.max(n2);

	}

}
