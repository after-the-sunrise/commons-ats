package jp.gr.java_conf.afterthesunrise.commons.key;

/**
 * @author takanori.takase
 */
public final class TriKey<K1, K2, K3> extends AbstractKey {

	private static final long serialVersionUID = -6533043020197748707L;

	public TriKey(K1 key1, K2 key2, K3 key3) {
		super(key1, key2, key3);
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

}
