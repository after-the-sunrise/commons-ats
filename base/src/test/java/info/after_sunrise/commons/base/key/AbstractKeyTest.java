package info.after_sunrise.commons.base.key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class AbstractKeyTest {

	private AbstractKey target;

	@SuppressWarnings("serial")
	private AbstractKey create(Object... args) {
		return new AbstractKey(args) {
		};
	}

	@Before
	public void setUp() throws Exception {
		target = create("foo", 1L);
	}

	@Test
	public void testAbstractKey_NullElement() {
		target = create((Object) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAbstractKey_NullArray() {
		target = create((Object[]) null);
	}

	@Test
	public void testHashCode() {
		assertEquals(target.hashCode(), target.hashCode());
		assertEquals(target.hashCode(), create("foo", 1L).hashCode());
		assertFalse(target.hashCode() == create("foo", 2L).hashCode());
		assertFalse(target.hashCode() == create("bar", 1L).hashCode());
		assertFalse(target.hashCode() == create(1L, "foo").hashCode());
		assertFalse(target.hashCode() == create("foo").hashCode());
		assertFalse(target.hashCode() == create(1L).hashCode());
		assertFalse(target.hashCode() == create("foo", 1L, 2).hashCode());
	}

	@Test
	public void testToString() {
		assertEquals("Key[foo, 1]", target.toString());
	}

	@Test
	public void testEquals() {
		assertTrue(target.equals(target));
		assertTrue(target.equals(create("foo", 1L)));
		assertFalse(target.equals(create("foo", 2L)));
		assertFalse(target.equals(create("bar", 1L)));
		assertFalse(target.equals(create(1L, "foo")));
		assertFalse(target.equals(create("foo")));
		assertFalse(target.equals(create(1L)));
		assertFalse(target.equals(create("foo", 1L, 2)));
		assertFalse(target.equals(new Object()));
		assertFalse(target.equals(null));
	}

	@Test
	public void testGetKey() {
		assertEquals("foo", target.getKey(0));
		assertEquals(1L, target.getKey(1));
	}

	@Test
	public void testGetKeys() {
		assertEquals(2, target.getKeys().length);
		assertEquals("foo", target.getKeys()[0]);
		assertEquals(1L, target.getKeys()[1]);
		assertNotSame(target.getKeys(), target.getKeys());
	}

	@Test
	public void testGetKeyList() {
		assertEquals(2, target.getKeyList().size());
		assertEquals("foo", target.getKeyList().get(0));
		assertEquals(1L, target.getKeyList().get(1));
	}

	@Test
	public void testGetKeySet() {
		assertEquals(2, target.getKeySet().size());
		assertTrue(target.getKeySet().contains("foo"));
		assertTrue(target.getKeySet().contains(1L));
	}

}
