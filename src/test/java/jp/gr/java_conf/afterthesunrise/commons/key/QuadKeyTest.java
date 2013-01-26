package jp.gr.java_conf.afterthesunrise.commons.key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class QuadKeyTest {

	@Test
	public void testQuadKey() {

		QuadKey<String, Integer, Long, Double> target = new QuadKey<String, Integer, Long, Double>(
				"foo", 1, 2L, 3.0);

		assertEquals("foo", target.getKey1());
		assertEquals(Integer.valueOf(1), target.getKey2());
		assertEquals(Long.valueOf(2), target.getKey3());
		assertEquals(Double.valueOf(3), target.getKey4());

		assertTrue(target.equals(target));
		assertEquals(target.hashCode(), target.hashCode());

		QuadKey<String, Integer, Long, Double> other = QuadKey.create("foo", 1,
				2L, 3.0);
		assertTrue(target.equals(other));
		assertEquals(target.hashCode(), other.hashCode());

	}

	@Test
	public void testQuadKey_Null() {

		QuadKey<String, Integer, Long, Double> target = new QuadKey<String, Integer, Long, Double>(
				null, null, null, null);

		assertNull(target.getKey1());
		assertNull(target.getKey2());
		assertNull(target.getKey3());
		assertNull(target.getKey4());

		assertTrue(target.equals(target));
		assertEquals(target.hashCode(), target.hashCode());

		QuadKey<String, Integer, Long, Double> other = QuadKey.create(null,
				null, null, null);
		assertTrue(target.equals(other));
		assertEquals(target.hashCode(), other.hashCode());

	}

}
