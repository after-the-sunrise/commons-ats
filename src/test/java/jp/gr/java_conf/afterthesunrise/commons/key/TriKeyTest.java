package jp.gr.java_conf.afterthesunrise.commons.key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class TriKeyTest {

	@Test
	public void testTriKey() {

		TriKey<String, Integer, Long> target = new TriKey<String, Integer, Long>(
				"foo", 1, 2L);

		assertEquals("foo", target.getKey1());
		assertEquals(Integer.valueOf(1), target.getKey2());
		assertEquals(Long.valueOf(2), target.getKey3());

		assertTrue(target.equals(target));
		assertEquals(target.hashCode(), target.hashCode());

		TriKey<String, Integer, Long> other = TriKey.create("foo", 1, 2L);
		assertTrue(target.equals(other));
		assertEquals(target.hashCode(), other.hashCode());

	}

	@Test
	public void testTriKey_Null() {

		TriKey<String, Integer, Long> target = new TriKey<String, Integer, Long>(
				null, null, null);

		assertNull(target.getKey1());
		assertNull(target.getKey2());
		assertNull(target.getKey3());

		assertTrue(target.equals(target));
		assertEquals(target.hashCode(), target.hashCode());

		TriKey<String, Integer, Long> other = TriKey.create(null, null, null);
		assertTrue(target.equals(other));
		assertEquals(target.hashCode(), other.hashCode());

	}

}
