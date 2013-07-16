package com.after_sunrise.commons.base.key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class UniKeyTest {

	@Test
	public void testUniKey() {

		UniKey<String> target = new UniKey<String>("foo");

		assertEquals("foo", target.getKey());

		assertTrue(target.equals(target));
		assertEquals(target.hashCode(), target.hashCode());

		UniKey<String> other = UniKey.create("foo");
		assertTrue(target.equals(other));
		assertEquals(target.hashCode(), other.hashCode());

	}

	@Test
	public void testUniKey_Null() {

		UniKey<String> target = new UniKey<String>(null);

		assertNull(target.getKey());

		assertTrue(target.equals(target));
		assertEquals(target.hashCode(), target.hashCode());

		UniKey<String> other = UniKey.create(null);
		assertTrue(target.equals(other));
		assertEquals(target.hashCode(), other.hashCode());

	}

}
