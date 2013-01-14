package jp.gr.java_conf.afterthesunrise.commons.object;

import java.math.BigDecimal;

import com.google.common.base.Function;

/**
 * @author takanori.takase
 */
public final class Functions {

	private Functions() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static final Function<BigDecimal, Double> DECIMAL_DOUBLE = new Function<BigDecimal, Double>() {
		@Override
		public Double apply(BigDecimal input) {
			return input == null ? null : input.doubleValue();
		}
	};

	public static final Function<Double, BigDecimal> DOUBLE_DECIMAL = new Function<Double, BigDecimal>() {
		@Override
		public BigDecimal apply(Double input) {
			return input == null ? null : BigDecimal.valueOf(input);
		}
	};

	public static final Function<BigDecimal, String> DECIMAL_STRING = new Function<BigDecimal, String>() {
		@Override
		public String apply(BigDecimal input) {
			return input == null ? null : input.toPlainString();
		}
	};

	public static final Function<String, BigDecimal> STRING_DECIMAL = new Function<String, BigDecimal>() {
		@Override
		public BigDecimal apply(String input) {
			return input == null ? null : new BigDecimal(input);
		}
	};

}
