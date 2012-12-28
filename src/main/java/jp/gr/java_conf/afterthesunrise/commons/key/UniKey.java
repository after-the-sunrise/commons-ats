package jp.gr.java_conf.afterthesunrise.commons.key;

/**
 * @author takanori.takase
 */
public final class UniKey<K> extends AbstractKey {

	private static final long serialVersionUID = -5833149240838130707L;

	public UniKey(K key) {
		super(key);
	}

	public K getKey() {
		return getKey(0);
	}

}
