package com.after_sunrise.commons.base.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class ReferenceTest {

	private Reference<Integer> target;

	private Reference<Integer> nullTarget;

	private Integer value;

	@Before
	public void setUp() throws Exception {

		value = 123;

		target = new Reference<Integer>(value);

		nullTarget = new Reference<Integer>(null);

	}

	@Test
	public void testHashCode() {

		assertEquals(value.hashCode(), target.hashCode());

		assertEquals(31, nullTarget.hashCode());

	}

	@Test
	public void testGet() {

		assertSame(value, target.get());

		assertNull(nullTarget.get());

	}

	@Test
	public void testToString() {

		assertEquals("Reference[value=123]", target.toString());

		assertEquals("Reference[value=null]", nullTarget.toString());

	}

	@Test
	public void testEqualsObject() {

		assertTrue(target.equals(target));
		assertTrue(target.equals(new Reference<Integer>(value)));
		assertFalse(target.equals(nullTarget));
		assertFalse(target.equals(new Object()));
		assertFalse(target.equals(null));

		assertTrue(nullTarget.equals(nullTarget));
		assertTrue(nullTarget.equals(new Reference<Integer>(null)));
		assertFalse(nullTarget.equals(target));
		assertFalse(nullTarget.equals(new Object()));
		assertFalse(nullTarget.equals(null));

	}

}
