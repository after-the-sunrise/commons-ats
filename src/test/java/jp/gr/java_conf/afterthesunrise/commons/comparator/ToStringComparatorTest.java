package jp.gr.java_conf.afterthesunrise.commons.comparator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ToStringComparatorTest {

	@Test
	public void testGet() {

		Object o1 = new Object() {
			@Override
			public String toString() {
				return "a";
			}
		};

		Object o2 = new Object() {
			@Override
			public String toString() {
				return "A";
			}
		};

		assertTrue(0 < ToStringComparator.get().compare(o1, o2));
		assertTrue(0 == ToStringComparator.get().compare(o1, o1));
		assertTrue(0 > ToStringComparator.get().compare(o2, o1));
		assertTrue(0 == ToStringComparator.get().compare(null, null));

		assertTrue(0 < ToStringComparator.get(false).compare(o1, o2));
		assertTrue(0 == ToStringComparator.get(false).compare(o1, o1));
		assertTrue(0 > ToStringComparator.get(false).compare(o2, o1));
		assertTrue(0 == ToStringComparator.get(false).compare(null, null));

	}

	@Test
	public void testGetIgnoreCase() {

		Object o1 = new Object() {
			@Override
			public String toString() {
				return "a";
			}
		};

		Object o2 = new Object() {
			@Override
			public String toString() {
				return "A";
			}
		};

		assertTrue(0 == ToStringComparator.getIgnoreCase().compare(o1, o2));
		assertTrue(0 == ToStringComparator.getIgnoreCase().compare(o1, o1));
		assertTrue(0 == ToStringComparator.getIgnoreCase().compare(o2, o1));
		assertTrue(0 == ToStringComparator.getIgnoreCase().compare(null, null));

		assertTrue(0 == ToStringComparator.get(true).compare(o1, o2));
		assertTrue(0 == ToStringComparator.get(true).compare(o1, o1));
		assertTrue(0 == ToStringComparator.get(true).compare(o2, o1));
		assertTrue(0 == ToStringComparator.get(true).compare(null, null));

	}

}
