package info.after_sunrise.commons.base.time;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class DayTypeTest {

	private static final byte ALL = Byte.MAX_VALUE;

	private static final byte NONE = (byte) 0;

	@Test
	public void testGetCalendarField() {
		for (DayType type : DayType.values()) {
			switch (type) {
			case SUNDAY:
				assertThat(type.getCalendarField(), is(Calendar.SUNDAY));
				break;
			case MONDAY:
				assertThat(type.getCalendarField(), is(Calendar.MONDAY));
				break;
			case TUESDAY:
				assertThat(type.getCalendarField(), is(Calendar.TUESDAY));
				break;
			case WEDNESDAY:
				assertThat(type.getCalendarField(), is(Calendar.WEDNESDAY));
				break;
			case THURSDAY:
				assertThat(type.getCalendarField(), is(Calendar.THURSDAY));
				break;
			case FRIDAY:
				assertThat(type.getCalendarField(), is(Calendar.FRIDAY));
				break;
			case SATURDAY:
				assertThat(type.getCalendarField(), is(Calendar.SATURDAY));
				break;
			default:
				fail("Unknown type : " + type);
			}
		}
	}

	@Test
	public void testIs() {

		// Single bit
		for (DayType target : DayType.values()) {
			for (DayType other : DayType.values()) {
				assertThat(target.is(other.getBits()), is(target == other));
			}
		}

		// All bits
		for (DayType target : DayType.values()) {
			assertTrue(target.is(ALL));
		}

		// No bits
		for (DayType target : DayType.values()) {
			assertFalse(target.is(NONE));
		}

	}

	@Test
	public void testAdd() {

		byte bits = NONE;

		for (DayType target : DayType.values()) {

			assertFalse(target.is(bits));

			bits = target.add(bits);

			assertTrue(target.is(bits));

		}

		assertThat(bits, is(ALL));

	}

	@Test
	public void testRemove() {

		byte bits = ALL;

		for (DayType target : DayType.values()) {

			assertTrue(target.is(bits));

			bits = target.remove(bits);

			assertFalse(target.is(bits));

		}

		assertThat(bits, is(NONE));

	}

	@Test
	public void testIs_Static() {

		// Single bit
		for (DayType target : DayType.values()) {
			assertTrue(DayType.is(target.getBits(), target));
		}

		// All bits
		for (DayType target : DayType.values()) {
			assertTrue(DayType.is(ALL, target));
		}

		// No bits
		for (DayType target : DayType.values()) {
			assertFalse(DayType.is(NONE, target));
		}

		// Check null
		assertFalse(DayType.is(ALL, null));

	}

	@Test
	public void testAdd_Static() {

		byte bits = NONE;

		for (DayType target : DayType.values()) {

			assertFalse(DayType.is(bits, target));

			bits = DayType.add(bits, target);

			assertTrue(DayType.is(bits, target));

		}

		assertThat(bits, is(ALL));

		// Check null
		assertThat(NONE, is(DayType.add(NONE, null)));

	}

	@Test
	public void testRemove_Static() {

		byte bits = ALL;

		for (DayType target : DayType.values()) {

			assertTrue(DayType.is(bits, target));

			bits = DayType.remove(bits, target);

			assertFalse(DayType.is(bits, target));

		}

		assertThat(bits, is(NONE));

		// Check null
		assertThat(ALL, is(DayType.remove(ALL, null)));

	}

}
