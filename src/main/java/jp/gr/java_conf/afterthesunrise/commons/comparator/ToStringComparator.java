package jp.gr.java_conf.afterthesunrise.commons.comparator;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Objects;

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

		if (Objects.equal(o1, o2)) {
			return EQUALS;
		}

		String s1 = o1 == null ? null : o1.toString();

		String s2 = o2 == null ? null : o2.toString();

		int comparison = NullSafeComparator.get().compare(s1, s2);

		if (comparison == 0 || !ignoreCase) {
			return comparison;
		}

		String l1 = StringUtils.lowerCase(s1);

		String l2 = StringUtils.lowerCase(s2);

		return NullSafeComparator.get().compare(l1, l2);

	}

}
