package org.m4aongh.calendarwatcher.utils;

import android.content.ContentResolver;
import android.widget.DatePicker;
import org.m4aongh.calendarwatcher.data.CalendarQuerier;
import org.m4aongh.calendarwatcher.data.Event;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: chsc4698@gmail.com
 * Date: 12-3-16
 * all rights reserved
 */
public class Utilities {

	public static long clearHourMinuteSecond(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTimeInMillis();
	}

	public static Calendar extractDateFromPicker(DatePicker picker) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(
				picker.getYear(),
				picker.getMonth(),
				picker.getDayOfMonth()
		);

		return calendar;
	}

	public static String organizeEventsTitles(List<Event> events) {
		String result = "";

		for (Event event: events) {
			result += "\t" + event.getTitle() + "\n";
		}

		return result;
	}

	public static Map<Integer, org.m4aongh.calendarwatcher.data.Calendar> getAllCalendars(ContentResolver resolver) {
		CalendarQuerier querier = new CalendarQuerier(resolver);
		List<org.m4aongh.calendarwatcher.data.Calendar> calendars = querier.fetchAllCalendar();

		Map<Integer, org.m4aongh.calendarwatcher.data.Calendar> map = new HashMap<Integer, org.m4aongh.calendarwatcher.data.Calendar>();
		for (org.m4aongh.calendarwatcher.data.Calendar calendar : calendars) {
			map.put(calendar.getId(), calendar);
		}

		return map;
	}
}
