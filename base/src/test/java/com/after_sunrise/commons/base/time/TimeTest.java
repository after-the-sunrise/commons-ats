package com.after_sunrise.commons.base.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class TimeTest {

	private Time target;

	@Before
	public void setUp() throws Exception {
		target = new Time(20, 30, 40, 50);
	}

	@Test
	public void testTime() {

		assertEquals(20, target.getHour());
		assertEquals(30, target.getMinute());
		assertEquals(40, target.getSecond());
		assertEquals(50, target.getMillisecond());

		target = new Time(20, 30, 40);
		assertEquals(20, target.getHour());
		assertEquals(30, target.getMinute());
		assertEquals(40, target.getSecond());
		assertEquals(0, target.getMillisecond());

		target = new Time(20, 30);
		assertEquals(20, target.getHour());
		assertEquals(30, target.getMinute());
		assertEquals(0, target.getSecond());
		assertEquals(0, target.getMillisecond());

		target = new Time(20);
		assertEquals(20, target.getHour());
		assertEquals(0, target.getMinute());
		assertEquals(0, target.getSecond());
		assertEquals(0, target.getMillisecond());

		target = new Time();
		assertEquals(0, target.getHour());
		assertEquals(0, target.getMinute());
		assertEquals(0, target.getSecond());
		assertEquals(0, target.getMillisecond());

	}

	@Test
	public void testHashCode() {
		assertEquals(target.hashCode(), target.hashCode());
	}

	@Test
	public void testToString() {
		assertEquals("20:30:40.050", target.toString());
	}

	@Test
	public void testEquals() {
		assertTrue(target.equals(target));
		assertTrue(target.equals(new Time(20, 30, 40, 50)));
		assertFalse(target.equals(new Time(21, 30, 40, 50)));
		assertFalse(target.equals(new Time(20, 31, 40, 50)));
		assertFalse(target.equals(new Time(20, 30, 41, 50)));
		assertFalse(target.equals(new Time(20, 30, 40, 51)));
		assertFalse(target.equals(new Object()));
		assertFalse(target.equals(null));
	}

	@Test
	public void testCompareTo() {
		assertEquals(+0, target.compareTo(target));
		assertEquals(+0, target.compareTo(new Time(20, 30, 40, 50)));
		assertEquals(-1, target.compareTo(new Time(21, 30, 40, 50)));
		assertEquals(-1, target.compareTo(new Time(20, 31, 40, 50)));
		assertEquals(-1, target.compareTo(new Time(20, 30, 41, 50)));
		assertEquals(-1, target.compareTo(new Time(20, 30, 40, 51)));
		assertEquals(+1, target.compareTo(new Time(19, 30, 40, 50)));
		assertEquals(+1, target.compareTo(new Time(20, 29, 40, 50)));
		assertEquals(+1, target.compareTo(new Time(20, 30, 39, 50)));
		assertEquals(+1, target.compareTo(new Time(20, 30, 40, 49)));
	}

}
