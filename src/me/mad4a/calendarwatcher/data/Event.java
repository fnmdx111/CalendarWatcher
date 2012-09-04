package me.mad4a.calendarwatcher.data;

import java.util.*;
import java.util.Calendar;

/**
 * User: chsc4698@gmail.com
 * Date: 12-3-15
 * all rights reserved
 */
public class Event {

	private final String title;
	private final boolean isAllDay;
	private final Date startingTimeDate;
	private final Date endingTimeDate;

	public Event(String title, boolean isAllDay, long startingTime, long endingTime) {
		this.isAllDay = isAllDay;
		this.title = title;

		Calendar now = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();

		calendar.setTimeInMillis(startingTime);
		calendar.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
		this.startingTimeDate = new Date(calendar.getTimeInMillis());

		calendar.setTimeInMillis(endingTime);
		calendar.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
		this.endingTimeDate = new Date(calendar.getTimeInMillis());
	}

	public String getTitle() {
		return title;
	}

	public boolean isAllDay() {
		return isAllDay;
	}

	public Date getStartingTimeDate() {
		return startingTimeDate;
	}

	public Date getEndingTimeDate() {
		return endingTimeDate;
	}
}
