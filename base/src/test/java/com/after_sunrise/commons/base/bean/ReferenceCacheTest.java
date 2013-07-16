package com.after_sunrise.commons.base.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class ReferenceCacheTest {

	private ReferenceCache<String, BigDecimal> target;

	@Before
	public void setUp() throws Exception {
		target = new ReferenceCache<String, BigDecimal>();
	}

	@Test
	public void testCache() {

		String key = "123";

		BigDecimal val = new BigDecimal(key);

		assertNull(target.get(key));

		target.put(key, val);

		Reference<BigDecimal> cached = target.get(key);

		assertEquals(val, cached.get());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testCache_NullKey() {
		target.put(null, new BigDecimal("123"));
	}

	@Test
	public void testCache_NullValue() {

		String key = "foo";

		assertNull(target.get(key));

		target.put(key, null);

		Reference<BigDecimal> cached = target.get(key);

		assertNull(cached.get());

	}

	@Test
	public void testCache_Cleared() {

		int loop = 500;

		for (int i = 0; i < loop; i++) {

			String key = String.valueOf(i);

			BigDecimal val = BigDecimal.valueOf(i);

			target.put(key, val);

		}

		Set<Reference<BigDecimal>> set = new HashSet<Reference<BigDecimal>>();

		for (int i = 0; i < loop; i++) {

			String key = String.valueOf(i);

			set.add(target.get(key));

		}

		assertTrue(set.size() < loop);

	}

}
