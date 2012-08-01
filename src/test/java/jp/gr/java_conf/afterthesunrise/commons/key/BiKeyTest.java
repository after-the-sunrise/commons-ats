package jp.gr.java_conf.afterthesunrise.commons.key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class BiKeyTest {

	@Test
	public void testBiKey() {

		BiKey<String, Integer> target = new BiKey<>("foo", 1);

		assertEquals("foo", target.getKey1());

		assertEquals(Integer.valueOf(1), target.getKey2());

		assertEquals(target, target);

		assertTrue(new HashSet<>(Arrays.asList(target)).contains(target));

	}

	@Test
	public void testBiKey_Null() {

		BiKey<String, Integer> target = new BiKey<>(null, null);

		assertNull(target.getKey1());

		assertNull(target.getKey2());

		assertEquals(target, target);

		assertTrue(new HashSet<>(Arrays.asList(target)).contains(target));

	}

}
