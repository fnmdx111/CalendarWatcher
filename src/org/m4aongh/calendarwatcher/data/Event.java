package org.m4aongh.calendarwatcher.data;

/**
 * User: chsc4698@gmail.com
 * Date: 12-3-15
 * all rights reserved
 */
public class Event {

	private final String title;
	private final boolean isAllDay;

	public Event(String title, boolean isAllDay) {
		this.title = title;
		this.isAllDay = isAllDay;
	}

	public String getTitle() {
		return title;
	}

	public boolean isAllDay() {
		return isAllDay;
	}

}
