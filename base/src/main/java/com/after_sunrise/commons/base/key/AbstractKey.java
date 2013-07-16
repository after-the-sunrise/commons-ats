package com.after_sunrise.commons.base.key;

import static com.after_sunrise.commons.base.object.Validations.checkNotNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author takanori.takase
 */
public abstract class AbstractKey implements Serializable {

	private static final long serialVersionUID = -9156653380875289363L;

	protected final Object[] keys;

	protected AbstractKey(Object... keys) {
		this.keys = checkNotNull(keys);
	}

	@Override
	public String toString() {
		return "Key" + Arrays.toString(keys);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(keys);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof AbstractKey) {
			return Arrays.equals(keys, ((AbstractKey) o).keys);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	protected <K> K getKey(int idx) {
		return (K) keys[idx];
	}

	public Object[] getKeys() {
		return Arrays.copyOf(keys, keys.length);
	}

	public List<?> getKeyList() {
		return Arrays.asList(keys);
	}

	public Set<?> getKeySet() {
		return new HashSet<Object>(getKeyList());
	}

}
