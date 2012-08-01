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
public class TriKeyTest {

	@Test
	public void testTriKey() {

		TriKey<String, Integer, Long> target = new TriKey<>("foo", 1, 2L);

		assertEquals("foo", target.getKey1());

		assertEquals(Integer.valueOf(1), target.getKey2());

		assertEquals(Long.valueOf(2), target.getKey3());

		assertEquals(target, target);

		assertTrue(new HashSet<>(Arrays.asList(target)).contains(target));

	}

	@Test
	public void testTriKey_Null() {

		TriKey<String, Integer, Long> target = new TriKey<>(null, null, null);

		assertNull(target.getKey1());

		assertNull(target.getKey2());

		assertNull(target.getKey3());

		assertEquals(target, target);

		assertTrue(new HashSet<>(Arrays.asList(target)).contains(target));

	}

}
