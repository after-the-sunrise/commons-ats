package info.after_sunrise.commons.time;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;
import info.after_sunrise.commons.object.Dates;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author takanori.takase
 */
public enum AdjustmentType {

	FOLLOWING {
		@Override
		protected long getNextInternal(long timestamp, TimeZone timeZone,
				Set<Integer> days, Set<Long> sods) {

			Calendar cal = LOCAL_CALENDAR.get();

			cal.setTimeZone(timeZone);

			cal.setTimeInMillis(timestamp);

			while (isHoliday(cal, days, sods, timeZone)) {

				cal.add(DATE, 1);

			}

			return cal.getTimeInMillis();

		}
	},

	PRECEDING {
		@Override
		protected long getNextInternal(long timestamp, TimeZone timeZone,
				Set<Integer> days, Set<Long> sods) {

			Calendar cal = LOCAL_CALENDAR.get();

			cal.setTimeZone(timeZone);

			cal.setTimeInMillis(timestamp);

			while (isHoliday(cal, days, sods, timeZone)) {

				cal.add(DATE, -1);

			}

			return cal.getTimeInMillis();

		}
	},

	MODIFIED_FOLLOWING {
		@Override
		protected long getNextInternal(long ts, TimeZone tz, Set<Integer> days,
				Set<Long> sods) {

			Calendar cal = LOCAL_CALENDAR.get();

			cal.setTimeZone(tz);

			cal.setTimeInMillis(ts);

			int initialMonth = cal.get(MONTH);

			while (isHoliday(cal, days, sods, tz)) {

				cal.add(DATE, 1);

				if (initialMonth == cal.get(MONTH)) {
					continue;
				}

				return PRECEDING.getNextInternal(ts, tz, days, sods);

			}

			return cal.getTimeInMillis();

		}
	},

	MODIFIED_PRECEDING {
		@Override
		protected long getNextInternal(long ts, TimeZone tz, Set<Integer> days,
				Set<Long> sods) {

			Calendar cal = LOCAL_CALENDAR.get();

			cal.setTimeZone(tz);

			cal.setTimeInMillis(ts);

			int initialMonth = cal.get(MONTH);

			while (isHoliday(cal, days, sods, tz)) {

				cal.add(DATE, -1);

				if (initialMonth == cal.get(MONTH)) {
					continue;
				}

				return FOLLOWING.getNextInternal(ts, tz, days, sods);

			}

			return cal.getTimeInMillis();

		}
	};

	public Long getNext(Long timestamp, TimeZone timeZone,
			Collection<DayType> holidayTypes, Collection<Long> holidayDates) {

		if (timestamp == null || timeZone == null) {
			return null;
		}

		Set<Integer> calDays = convert(holidayTypes);

		Set<Long> sods = adjustSod(holidayDates, timeZone);

		return getNextInternal(timestamp, timeZone, calDays, sods);

	}

	protected abstract long getNextInternal(long timestamp, TimeZone timeZone,
			Set<Integer> holidayTypes, Set<Long> holidayDates);

	private static final ThreadLocal<Calendar> LOCAL_CALENDAR = new ThreadLocal<Calendar>() {
		@Override
		protected Calendar initialValue() {
			return Calendar.getInstance();
		}
	};

	private static Set<Integer> convert(Collection<DayType> days) {

		if (CollectionUtils.isEmpty(days)) {
			return null;
		}

		Set<Integer> set = new HashSet<Integer>();

		for (DayType type : days) {

			if (type == null) {
				continue;
			}

			set.add(type.getCalendarField());

		}

		if (CollectionUtils.isEmpty(set)) {
			return null;
		}

		return set;

	}

	private static Set<Long> adjustSod(Collection<Long> dates, TimeZone timeZone) {

		if (CollectionUtils.isEmpty(dates)) {
			return null;
		}

		Set<Long> set = new HashSet<Long>();

		for (Long holiday : dates) {

			if (holiday == null) {
				continue;
			}

			long sod = Dates.adjustStartOfDay(holiday, timeZone);

			set.add(sod);

		}

		if (CollectionUtils.isEmpty(set)) {
			return null;
		}

		return set;

	}

	private static boolean isHoliday(Calendar cal, Set<Integer> days,
			Set<Long> dates, TimeZone timeZone) {

		int dayOfWeek = cal.get(DAY_OF_WEEK);

		if (days != null && days.contains(dayOfWeek)) {
			return true;
		}

		if (dates != null) {

			long sod = Dates.adjustStartOfDay(cal.getTimeInMillis(), timeZone);

			return dates.contains(sod);

		}

		return false;

	}

}
