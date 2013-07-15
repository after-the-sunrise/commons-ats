package com.after_sunrise.commons.guava.object;

import java.math.BigDecimal;

import com.google.common.base.Function;

/**
 * @author takanori.takase
 */
public final class Functions {

	private Functions() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static final int SCALE_PERCENT = 2;

	public static final int SCALE_BASIS = 4;

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

	public static final Function<Object, String> TO_STRING = new Function<Object, String>() {
		@Override
		public String apply(Object input) {
			return input == null ? null : input.toString();
		}
	};

	public static final Function<Enum<?>, String> TO_NAME = new Function<Enum<?>, String>() {
		@Override
		public String apply(Enum<?> input) {
			return input == null ? null : input.name();
		}
	};

	public static final Function<BigDecimal, BigDecimal> REAL_PERCENT = new Function<BigDecimal, BigDecimal>() {
		@Override
		public BigDecimal apply(BigDecimal input) {
			return input == null ? null : input.movePointRight(SCALE_PERCENT);
		}
	};

	public static final Function<BigDecimal, BigDecimal> PERCENT_REAL = new Function<BigDecimal, BigDecimal>() {
		@Override
		public BigDecimal apply(BigDecimal input) {
			return input == null ? null : input.movePointLeft(SCALE_PERCENT);
		}
	};

	public static final Function<BigDecimal, BigDecimal> REAL_BASIS = new Function<BigDecimal, BigDecimal>() {
		@Override
		public BigDecimal apply(BigDecimal input) {
			return input == null ? null : input.movePointRight(SCALE_BASIS);
		}
	};

	public static final Function<BigDecimal, BigDecimal> BASIS_REAL = new Function<BigDecimal, BigDecimal>() {
		@Override
		public BigDecimal apply(BigDecimal input) {
			return input == null ? null : input.movePointLeft(SCALE_BASIS);
		}
	};

}
