package jp.gr.java_conf.afterthesunrise.commons.object;

import static java.lang.Double.NaN;
import static org.apache.commons.lang.ArrayUtils.EMPTY_DOUBLE_ARRAY;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author takanori.takase
 */
public final class Statistics {

	private Statistics() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static double[] extractDifference(double... values) {

		if (ArrayUtils.isEmpty(values)) {
			return EMPTY_DOUBLE_ARRAY;
		}

		double[] arr = new double[values.length - 1];

		for (int i = 1; i < values.length; i++) {
			arr[i - 1] = values[i] - values[i - 1];
		}

		return arr;

	}

	public static double[] extractRatio(double... values) {

		if (ArrayUtils.isEmpty(values)) {
			return EMPTY_DOUBLE_ARRAY;
		}

		double[] arr = new double[values.length - 1];

		for (int i = 1; i < values.length; i++) {
			arr[i - 1] = values[i] / values[i - 1] - 1.0;
		}

		return arr;

	}

	public static double[] extractLogarithm(double... values) {

		if (ArrayUtils.isEmpty(values)) {
			return EMPTY_DOUBLE_ARRAY;
		}

		double[] arr = new double[values.length - 1];

		for (int i = 1; i < values.length; i++) {
			arr[i - 1] = Math.log(values[i] / values[i - 1]);
		}

		return arr;

	}

	public static double getArithmeticMean(double... values) {

		if (ArrayUtils.isEmpty(values)) {
			return NaN;
		}

		double sum = 0.0;

		for (double val : values) {
			sum += val;
		}

		return sum / values.length;

	}

	public static double getGeometricMean(double... values) {

		if (ArrayUtils.isEmpty(values)) {
			return NaN;
		}

		double sum = values[0];

		for (int i = 1; i < values.length; i++) {
			sum *= values[i];
		}

		double multiplier = 1.0 / values.length;

		return Math.pow(sum, multiplier);

	}

	public static double getHarmonicMean(double... values) {

		if (ArrayUtils.isEmpty(values)) {
			return NaN;
		}

		double sum = 0.0;

		for (double val : values) {
			sum += 1.0 / val;
		}

		return values.length / sum;

	}

	public static double getVariance(boolean population, double base,
			double... values) {

		if (ArrayUtils.isEmpty(values)) {
			return NaN;
		}

		int denominator = values.length;

		if (!population) {
			denominator--;
		}

		if (denominator <= 0) {
			return NaN;
		}

		double sum = 0.0;

		for (double val : values) {

			double dev = val - base;

			sum += (dev * dev);

		}

		return sum / denominator;

	}

	public static double getStandardDeviation(boolean population, double base,
			double... values) {
		return Math.sqrt(getVariance(population, base, values));
	}

	public static double getAbsoluteDeviation(boolean population, double base,
			double... values) {

		if (ArrayUtils.isEmpty(values)) {
			return NaN;
		}

		int denominator = values.length;

		if (!population) {
			denominator--;
		}

		if (denominator <= 0) {
			return NaN;
		}

		double sum = 0.0;

		for (double val : values) {

			double dev = val - base;

			sum += Math.abs(dev);

		}

		return sum / denominator;

	}

}
