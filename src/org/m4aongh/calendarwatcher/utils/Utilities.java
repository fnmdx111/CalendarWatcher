package org.m4aongh.calendarwatcher.utils;

import android.widget.DatePicker;
import org.m4aongh.calendarwatcher.data.Event;

import java.util.Calendar;
import java.util.List;

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

}
