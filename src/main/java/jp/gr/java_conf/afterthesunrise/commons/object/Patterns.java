package jp.gr.java_conf.afterthesunrise.commons.object;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
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

	private static final int CACHE_MAX = 100;

	private static final long CACHE_MINUTE = 3L;

	private static final LoadingCache<String, Optional<Pattern>> CACHE;

	static {

		CACHE = CacheBuilder.newBuilder().maximumSize(CACHE_MAX)
				.expireAfterAccess(CACHE_MINUTE, MINUTES)
				.build(new CacheLoader<String, Optional<Pattern>>() {
					@Override
					public Optional<Pattern> load(String key) throws Exception {
						try {
							return Optional.of(Pattern.compile(key));
						} catch (Exception e) {
							return Optional.absent();
						}
					}
				});

	}

	public static Pattern compile(String pattern) {

		if (pattern == null) {
			return null;
		}

		return CACHE.getUnchecked(pattern).orNull();

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
