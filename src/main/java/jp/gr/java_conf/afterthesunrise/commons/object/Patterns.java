package jp.gr.java_conf.afterthesunrise.commons.object;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Objects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * @author takanori.takase
 */
public final class Patterns {

	private Patterns() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	private static final int MAX = 100;

	private static final long DURATION = 1L;

	private static final TimeUnit UNIT = TimeUnit.MINUTES;

	private static final LoadingCache<String, Pattern> CACHE;

	private static final Pattern NULL = Pattern.compile("^$");

	static {

		CacheLoader<String, Pattern> loader = new CacheLoader<String, Pattern>() {
			@Override
			public Pattern load(String key) throws Exception {
				try {
					return Pattern.compile(key);
				} catch (Exception e) {
					return NULL;
				}
			}
		};

		CACHE = CacheBuilder.newBuilder().expireAfterAccess(DURATION, UNIT)
				.maximumSize(MAX).build(loader);

	}

	public static Pattern compile(String pattern) {

		if (pattern == null) {
			return null;
		}

		Pattern p = CACHE.getUnchecked(pattern.intern());

		return p == NULL ? null : p;

	}

	public static boolean match(String pattern, String value) {

		Pattern p = compile(pattern);

		if (p == null) {
			return false;
		}

		String target = Objects.firstNonNull(value, StringUtils.EMPTY);

		return p.matcher(target).matches();

	}

}
