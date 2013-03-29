package info.after_sunrise.commons.object;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;

/**
 * @author takanori.takase
 */
public final class Selections {

	private Selections() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static <T> T selectFirst(Collection<T> c, T value) {

		if (CollectionUtils.isEmpty(c)) {
			return null;
		}

		return c.contains(value) ? value : c.iterator().next();

	}

	public static <T> T selectFirst(T[] c, T value) {

		if (ArrayUtils.isEmpty(c)) {
			return null;
		}

		return ArrayUtils.contains(c, value) ? value : c[0];

	}

	public static <K> K selectFirstKey(Map<K, ?> m, K key) {

		if (MapUtils.isEmpty(m)) {
			return null;
		}

		return m.containsKey(key) ? key : m.keySet().iterator().next();

	}

	public static <V> V selectFirstValue(Map<?, V> m, V value) {

		if (MapUtils.isEmpty(m)) {
			return null;
		}

		return m.containsValue(value) ? value : m.values().iterator().next();

	}

}
