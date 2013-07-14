package info.after_sunrise.commons.base.bean;

import info.after_sunrise.commons.base.object.Comparisons;

/**
 * @author takanori.takase
 */
public final class Reference<T> {

	private final T value;

	public Reference(T value) {
		this.value = value;
	}

	public T get() {
		return value;
	}

	@Override
	public String toString() {
		return "Reference[value=" + value + "]";
	}

	@Override
	public int hashCode() {
		return value == null ? 31 : value.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Reference) {
			return Comparisons.isEqual(value, ((Reference<?>) o).value);
		}
		return false;
	}

}
