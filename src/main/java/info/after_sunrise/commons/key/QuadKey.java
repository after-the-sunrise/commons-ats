package info.after_sunrise.commons.key;

/**
 * @author takanori.takase
 */
public final class QuadKey<K1, K2, K3, K4> extends AbstractKey {

	private static final long serialVersionUID = 2946457726260527074L;

	public static <T1, T2, T3, T4> QuadKey<T1, T2, T3, T4> create(T1 key1,
			T2 key2, T3 key3, T4 key4) {
		return new QuadKey<T1, T2, T3, T4>(key1, key2, key3, key4);
	}

	public QuadKey(K1 key1, K2 key2, K3 key3, K4 key4) {
		super(key1, key2, key3, key4);
	}

	public K1 getKey1() {
		return getKey(0);
	}

	public K2 getKey2() {
		return getKey(1);
	}

	public K3 getKey3() {
		return getKey(2);
	}

	public K4 getKey4() {
		return getKey(3);
	}

}
