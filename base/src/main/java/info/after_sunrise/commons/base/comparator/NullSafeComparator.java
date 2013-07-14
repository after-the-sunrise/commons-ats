package info.after_sunrise.commons.base.comparator;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author takanori.takase
 */
public final class NullSafeComparator<T extends Comparable<T>> implements
		Comparator<T>, Serializable {

	private static final long serialVersionUID = 4580718469939176830L;

	@SuppressWarnings("rawtypes")
	private static final NullSafeComparator INSTANCE = new NullSafeComparator();

	@SuppressWarnings("unchecked")
	public static final <T> Comparator<T> get() {
		return INSTANCE;
	}

	public static final <T> int toComparison(T o1, T o2) {
		return get().compare(o1, o2);
	}

	private NullSafeComparator() {
	}

	@Override
	public final int compare(T o1, T o2) {

		if (o1 == o2) {
			return 0;
		}

		if (o1 == null) {
			return -1;
		}

		if (o2 == null) {
			return +1;
		}

		return o1.compareTo(o2);

	}

}
