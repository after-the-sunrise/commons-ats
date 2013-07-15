package com.after_sunrise.commons.base.comparator;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author takanori.takase
 */
public class ToStringComparator implements Comparator<Object>, Serializable {

	private static final long serialVersionUID = 4580718469939176830L;

	private static final int EQUALS = 0;

	private static final ToStringComparator C = new ToStringComparator(false);

	private static final ToStringComparator IC = new ToStringComparator(true);

	private final boolean ignoreCase;

	public static final Comparator<Object> get() {
		return get(false);
	}

	public static final Comparator<Object> getIgnoreCase() {
		return get(true);
	}

	public static final Comparator<Object> get(boolean ignoreCase) {
		return ignoreCase ? IC : C;
	}

	private ToStringComparator(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	@Override
	public final int compare(Object o1, Object o2) {

		if (o1 == o2) {
			return EQUALS;
		}

		String s1 = o1 == null ? null : o1.toString();

		String s2 = o2 == null ? null : o2.toString();

		int comparison = NullSafeComparator.get().compare(s1, s2);

		if (comparison == 0 || !ignoreCase) {
			return comparison;
		}

		String l1 = s1 == null ? null : s1.toLowerCase();

		String l2 = s2 == null ? null : s2.toLowerCase();

		return NullSafeComparator.get().compare(l1, l2);

	}

}
