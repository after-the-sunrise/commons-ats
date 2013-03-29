package info.after_sunrise.commons.time;

import java.util.Calendar;

/**
 * <pre>
 * Enum constants to represent day of a week in single byte. Each day is
 * assigned to a bit, which will allow a single byte to contain multiple days.
 * 8th bit (the bit on the very left) is not used and will be considered as undefined bit.
 * 
 * <table border="1">
 * <tr><th>DayOfWeek</th><th>bits</th><th>byte value</th><tr>
 * <tr><td>Sunday</td><td>01000000</td><td>64</td><tr>
 * <tr><td>Monday</td><td>00100000</td><td>32</td><tr>
 * <tr><td>Tuesday</td><td>00010000</td><td>16</td><tr>
 * <tr><td>Wednesday</td><td>00001000</td><td>8</td><tr>
 * <tr><td>Thursday</td><td>00000100</td><td>4</td><tr>
 * <tr><td>Friday</td><td>00000010</td><td>2</td><tr>
 * <tr><td>Saturday</td><td>00000001</td><td>1</td><tr>
 * </table>
 * </pre>
 * 
 * @author takanori.takase
 */
public enum DayType {

	SUNDAY(64, Calendar.SUNDAY),

	MONDAY(32, Calendar.MONDAY),

	TUESDAY(16, Calendar.TUESDAY),

	WEDNESDAY(8, Calendar.WEDNESDAY),

	THURSDAY(4, Calendar.THURSDAY),

	FRIDAY(2, Calendar.FRIDAY),

	SATURDAY(1, Calendar.SATURDAY);

	private final byte bits;

	private final int calendarField;

	private DayType(int bits, int calendarField) {
		this.bits = (byte) bits;
		this.calendarField = calendarField;
	}

	public byte getBits() {
		return bits;
	}

	public int getCalendarField() {
		return calendarField;
	}

	public boolean is(byte bits) {
		return is(bits, this);
	}

	public byte add(byte bits) {
		return add(bits, this);
	}

	public byte remove(byte bits) {
		return remove(bits, this);
	}

	public static boolean is(byte bits, DayType type) {
		return type != null && (byte) (bits & type.bits) == type.bits;
	}

	public static byte add(byte bits, DayType type) {
		return type == null ? bits : (byte) (bits | type.bits);
	}

	public static byte remove(byte bits, DayType type) {
		return type == null ? bits : (byte) (bits & ~type.bits);
	}

}
