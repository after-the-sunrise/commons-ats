package jp.gr.java_conf.afterthesunrise.commons.time;

import static java.lang.String.format;
import static java.util.Objects.hash;

import java.io.Serializable;

/**
 * @author takanori.takase
 */
public class Time implements Serializable, Comparable<Time> {

	private static final long serialVersionUID = 1761936346784382781L;

	private final int hour;

	private final int minute;

	private final int second;

	private final int millisecond;

	public Time(int hour, int minute, int second, int millisecond) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.millisecond = millisecond;
	}

	@Override
	public String toString() {
		return format("%02d:%02d:%02d.%03d", hour, minute, second, millisecond);
	}

	@Override
	public int hashCode() {
		return hash(hour, minute, second, millisecond);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Time) {
			return compareTo((Time) o) == 0;
		}
		return false;
	}

	@Override
	public int compareTo(Time o) {

		int comparison = Integer.compare(hour, o.hour);
		if (comparison != 0) {
			return comparison;
		}

		comparison = Integer.compare(minute, o.minute);
		if (comparison != 0) {
			return comparison;
		}

		comparison = Integer.compare(second, o.second);
		if (comparison != 0) {
			return comparison;
		}

		return Integer.compare(millisecond, o.millisecond);

	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public int getSecond() {
		return second;
	}

	public int getMillisecond() {
		return millisecond;
	}

}
