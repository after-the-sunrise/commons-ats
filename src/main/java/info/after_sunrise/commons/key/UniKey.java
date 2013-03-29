package info.after_sunrise.commons.key;

/**
 * @author takanori.takase
 */
public final class UniKey<K> extends AbstractKey {

	private static final long serialVersionUID = -5833149240838130707L;

	public static <T1> UniKey<T1> create(T1 key1) {
		return new UniKey<T1>(key1);
	}

	public UniKey(K key) {
		super(key);
	}

	public K getKey() {
		return getKey(0);
	}

}
