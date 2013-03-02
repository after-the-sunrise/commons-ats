package jp.gr.java_conf.afterthesunrise.commons.object;

import static jp.gr.java_conf.afterthesunrise.commons.object.Statistics.extractDifference;
import static jp.gr.java_conf.afterthesunrise.commons.object.Statistics.extractLogarithm;
import static jp.gr.java_conf.afterthesunrise.commons.object.Statistics.extractRatio;
import static jp.gr.java_conf.afterthesunrise.commons.object.Statistics.getAbsoluteDeviation;
import static jp.gr.java_conf.afterthesunrise.commons.object.Statistics.getArithmeticMean;
import static jp.gr.java_conf.afterthesunrise.commons.object.Statistics.getGeometricMean;
import static jp.gr.java_conf.afterthesunrise.commons.object.Statistics.getHarmonicMean;
import static jp.gr.java_conf.afterthesunrise.commons.object.Statistics.getStandardDeviation;
import static jp.gr.java_conf.afterthesunrise.commons.object.Statistics.getVariance;
import static org.apache.commons.lang.ArrayUtils.EMPTY_DOUBLE_ARRAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public strictfp class StatisticsTest {

	private static final double[] EMPTY = EMPTY_DOUBLE_ARRAY;

	private static final double DELTA = 0.00000001;

	private double[] values;

	@Before
	public void setUp() throws Exception {
		values = new double[] { 0.1, 0.1, 0.2, 0.3, 0.5 };
	}

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Statistics.class;

		Constructor<?> c = clazz.getDeclaredConstructor();

		assertTrue(Modifier.isPrivate(c.getModifiers()));

		c.setAccessible(true);

		try {
			c.newInstance();
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}

	}

	@Test
	public void testExtractDifference() {

		double[] result = extractDifference(values);

		assertEquals(4, result.length);
		assertEquals(0.0, result[0], DELTA);
		assertEquals(0.1, result[1], DELTA);
		assertEquals(0.1, result[2], DELTA);
		assertEquals(0.2, result[3], DELTA);

		assertEquals(0, extractDifference(null).length);
		assertEquals(0, extractDifference(EMPTY).length);
		assertEquals(0, extractDifference(new double[] { 1.1 }).length);

	}

	@Test
	public void testExtractRatio() {

		double[] result = extractRatio(values);

		assertEquals(4, result.length);
		assertEquals((0.1 - 0.1) / 0.1, result[0], DELTA);
		assertEquals((0.2 - 0.1) / 0.1, result[1], DELTA);
		assertEquals((0.3 - 0.2) / 0.2, result[2], DELTA);
		assertEquals((0.5 - 0.3) / 0.3, result[3], DELTA);

		assertEquals(0, extractRatio(null).length);
		assertEquals(0, extractRatio(EMPTY).length);
		assertEquals(0, extractRatio(new double[] { 1.1 }).length);

	}

	@Test
	public void testExtractLogarithm() {

		double[] result = extractLogarithm(values);

		assertEquals(4, result.length);
		assertEquals(Math.log(0.1 / 0.1), result[0], DELTA);
		assertEquals(Math.log(0.2 / 0.1), result[1], DELTA);
		assertEquals(Math.log(0.3 / 0.2), result[2], DELTA);
		assertEquals(Math.log(0.5 / 0.3), result[3], DELTA);

		assertEquals(0, extractLogarithm(null).length);
		assertEquals(0, extractLogarithm(EMPTY).length);
		assertEquals(0, extractLogarithm(new double[] { 1.1 }).length);

	}

	@Test
	public void testGetArithmeticMean() {

		Double avg = getArithmeticMean(values);

		assertEquals(0.24, avg, DELTA);

		assertTrue(Double.isNaN(getArithmeticMean(null)));
		assertTrue(Double.isNaN(getArithmeticMean(EMPTY)));

	}

	@Test
	public void testGetGeometricMean() {

		Double avg = getGeometricMean(values);

		assertEquals(0.19743504, avg, DELTA);

		assertTrue(Double.isNaN(getGeometricMean(null)));
		assertTrue(Double.isNaN(getGeometricMean(EMPTY)));

	}

	@Test
	public void testGetHarmonicMean() {

		Double avg = getHarmonicMean(values);

		assertEquals(0.16483516, avg, DELTA);

		assertTrue(Double.isNaN(getHarmonicMean(null)));
		assertTrue(Double.isNaN(getHarmonicMean(EMPTY)));

	}

	@Test
	public void testGetVariance() {

		double base = getArithmeticMean(values);

		Double sample = getVariance(false, base, values);
		assertEquals(0.02800000, sample, DELTA);

		Double population = getVariance(true, base, values);
		assertEquals(0.02240000, population, DELTA);

		assertTrue(Double.isNaN(getVariance(false, base, null)));
		assertTrue(Double.isNaN(getVariance(false, base, EMPTY)));
		assertTrue(Double.isNaN(getVariance(false, base, new double[] { 1.1 })));

		assertTrue(Double.isNaN(getVariance(true, base, null)));
		assertTrue(Double.isNaN(getVariance(true, base, EMPTY)));

	}

	@Test
	public void testGetStandardDeviation() {

		double base = getArithmeticMean(values);

		Double sample = getStandardDeviation(false, base, values);
		assertEquals(0.16733200, sample, DELTA);

		Double population = getStandardDeviation(true, base, values);
		assertEquals(0.14966629, population, DELTA);

		assertTrue(Double.isNaN(getStandardDeviation(false, base, null)));
		assertTrue(Double.isNaN(getStandardDeviation(false, base, EMPTY)));
		assertTrue(Double.isNaN(getStandardDeviation(false, base,
				new double[] { 1.1 })));

		assertTrue(Double.isNaN(getStandardDeviation(true, base, null)));
		assertTrue(Double.isNaN(getStandardDeviation(true, base, EMPTY)));

	}

	@Test
	public void testGetAbsoluteDeviation() {

		double base = getArithmeticMean(values);

		Double sample = getAbsoluteDeviation(false, base, values);
		assertEquals(0.16000000, sample, DELTA);

		Double population = getAbsoluteDeviation(true, base, values);
		assertEquals(0.12800000, population, DELTA);

		assertTrue(Double.isNaN(getAbsoluteDeviation(false, base, null)));
		assertTrue(Double.isNaN(getAbsoluteDeviation(false, base, EMPTY)));
		assertTrue(Double.isNaN(getAbsoluteDeviation(false, base,
				new double[] { 1.1 })));

		assertTrue(Double.isNaN(getAbsoluteDeviation(true, base, null)));
		assertTrue(Double.isNaN(getAbsoluteDeviation(true, base, EMPTY)));

	}

}
