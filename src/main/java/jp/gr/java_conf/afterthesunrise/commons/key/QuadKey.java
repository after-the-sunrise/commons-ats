package jp.gr.java_conf.afterthesunrise.commons.key;

/**
 * @author takanori.takase
 */
public final class QuadKey<K1, K2, K3, K4> extends AbstractKey {

	private static final long serialVersionUID = 2946457726260527074L;

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
