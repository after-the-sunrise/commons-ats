package jp.gr.java_conf.afterthesunrise.commons.comparator;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

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

		Object o3 = new Object() {
			@Override
			public String toString() {
				return "A";
			}
		};

		Comparator<Object> comparator = ToStringComparator.get();

		assertTrue(0 == comparator.compare(o1, o1));
		assertTrue(0 < comparator.compare(o1, o2));
		assertTrue(0 < comparator.compare(o1, o3));
		assertTrue(0 < comparator.compare(o1, null));
		assertTrue(0 > comparator.compare(o2, o1));
		assertTrue(0 == comparator.compare(o2, o2));
		assertTrue(0 == comparator.compare(o2, o3));
		assertTrue(0 < comparator.compare(o2, null));
		assertTrue(0 > comparator.compare(null, o1));
		assertTrue(0 > comparator.compare(null, o2));
		assertTrue(0 > comparator.compare(null, o3));
		assertTrue(0 == comparator.compare(null, null));

		assertSame(comparator, ToStringComparator.get(false));

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

		Object o3 = new Object() {
			@Override
			public String toString() {
				return "A";
			}
		};

		Comparator<Object> comparator = ToStringComparator.getIgnoreCase();

		assertTrue(0 == comparator.compare(o1, o1));
		assertTrue(0 == comparator.compare(o1, o2));
		assertTrue(0 == comparator.compare(o1, o3));
		assertTrue(0 < comparator.compare(o1, null));
		assertTrue(0 == comparator.compare(o2, o1));
		assertTrue(0 == comparator.compare(o2, o2));
		assertTrue(0 == comparator.compare(o2, o3));
		assertTrue(0 < comparator.compare(o2, null));
		assertTrue(0 > comparator.compare(null, o1));
		assertTrue(0 > comparator.compare(null, o2));
		assertTrue(0 > comparator.compare(null, o3));
		assertTrue(0 == comparator.compare(null, null));

		assertSame(comparator, ToStringComparator.get(true));

	}

}
