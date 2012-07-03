package jp.gr.java_conf.afterthesunrise.commons.comparator;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;

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

	private NullSafeComparator() {
	}

	@Override
	public int compare(T o1, T o2) {

		if (o1 != null && o2 != null) {
			return o1.compareTo(o2);
		}

		return new CompareToBuilder().append(o1, o2).toComparison();

	}

}
