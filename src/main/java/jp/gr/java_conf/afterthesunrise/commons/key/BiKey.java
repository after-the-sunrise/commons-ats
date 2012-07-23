package jp.gr.java_conf.afterthesunrise.commons.key;

/**
 * @author takanori.takase
 */
public final class BiKey<K1, K2> extends AbstractKey {

	private static final long serialVersionUID = 1279141968039096818L;

	public BiKey(K1 key1, K2 key2) {
		super(key1, key2);
	}

	public K1 getKey1() {
		return getKey(0);
	}

	public K2 getKey2() {
		return getKey(1);
	}

}