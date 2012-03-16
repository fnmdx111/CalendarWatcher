package org.m4aongh.calendarwatcher.data;

/**
 * User: chsc4698@gmail.com
 * Date: 12-3-15
 * all rights reserved
 */
public class Calendar {

	private final String title;
	private final int id;

	public Calendar(String title, int id) {
		this.title = title;
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Calendar calendar = (Calendar)o;

		if (id != calendar.id) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return id;
	}

}
