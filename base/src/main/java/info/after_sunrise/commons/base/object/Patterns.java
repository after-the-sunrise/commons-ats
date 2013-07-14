package info.after_sunrise.commons.base.object;

import static info.after_sunrise.commons.base.object.References.referecenCache;
import info.after_sunrise.commons.base.bean.Reference;
import info.after_sunrise.commons.base.bean.ReferenceCache;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author takanori.takase
 */
public final class Patterns {

	private Patterns() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	private static final int MAX = 100;

	private static final ReferenceCache<String, Pattern> CACHE = referecenCache(MAX);

	public static Pattern compile(String pattern) {

		if (pattern == null) {
			return null;
		}

		Reference<Pattern> ref = CACHE.get(pattern);

		Pattern p;

		if (ref == null) {

			try {
				p = Pattern.compile(pattern);
			} catch (PatternSyntaxException e) {
				p = null;
			}

			CACHE.put(pattern, p);

		} else {

			p = ref.get();

		}

		return p;

	}

	public static boolean match(String pattern, String value) {

		if (value == null) {
			return false;
		}

		Pattern p = compile(pattern);

		if (p == null) {
			return false;
		}

		return p.matcher(value).matches();

	}

}
