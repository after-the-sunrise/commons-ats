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
public class UniKeyTest {

	@Test
	public void testUniKey() {

		UniKey<String> target = new UniKey<>("foo");

		assertEquals("foo", target.getKey());

		assertEquals(target, target);

		assertTrue(new HashSet<>(Arrays.asList(target)).contains(target));

	}

	@Test
	public void testUniKey_Null() {

		UniKey<String> target = new UniKey<>(null);

		assertNull(target.getKey());

		assertEquals(target, target);

		assertTrue(new HashSet<>(Arrays.asList(target)).contains(target));

	}

}
