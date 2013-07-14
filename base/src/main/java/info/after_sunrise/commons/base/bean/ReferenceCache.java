package info.after_sunrise.commons.base.bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author takanori.takase
 */
public class ReferenceCache<K, V> {

	private static final int CAPACITY = 20;

	private final Map<K, Reference<V>> cache = new ConcurrentHashMap<K, Reference<V>>();

	private final int capacity;

	public ReferenceCache() {
		this(CAPACITY);
	}

	public ReferenceCache(int capacity) {
		this.capacity = capacity;
	}

	public Reference<V> get(K key) {

		if (key == null) {
			return null;
		}

		return cache.get(key);

	}

	public void put(K key, V val) {

		if (key == null) {
			throw new IllegalArgumentException("Cache key cannot be null.");
		}

		if (cache.size() >= capacity) {

			Object[] keys = cache.keySet().toArray();

			int idx = (int) System.currentTimeMillis() % keys.length;

			cache.remove(keys[idx]);

		}

		cache.put(key, new Reference<V>(val));

	}

}
