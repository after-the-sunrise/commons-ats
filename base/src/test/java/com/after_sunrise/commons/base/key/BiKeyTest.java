package com.after_sunrise.commons.base.key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class BiKeyTest {

	@Test
	public void testBiKey() {

		BiKey<String, Integer> target = new BiKey<String, Integer>("foo", 1);

		assertEquals("foo", target.getKey1());
		assertEquals(Integer.valueOf(1), target.getKey2());

		assertTrue(target.equals(target));
		assertEquals(target.hashCode(), target.hashCode());

		BiKey<String, Integer> other = BiKey.create("foo", 1);
		assertTrue(target.equals(other));
		assertEquals(target.hashCode(), other.hashCode());

	}

	@Test
	public void testBiKey_Null() {

		BiKey<String, Integer> target = new BiKey<String, Integer>(null, null);

		assertNull(target.getKey1());
		assertNull(target.getKey2());

		assertTrue(target.equals(target));
		assertEquals(target.hashCode(), target.hashCode());

		BiKey<String, Integer> other = BiKey.create(null, null);
		assertTrue(target.equals(other));
		assertEquals(target.hashCode(), other.hashCode());

	}
}
