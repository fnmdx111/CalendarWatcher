package org.m4aongh.calendarwatcher.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import org.m4aongh.calendarwatcher.constants.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * User: chsc4698@gmail.com
 * Date: 12-3-15
 * all rights reserved
 */
public class CalendarQuerier extends Querier<Calendar> {

	private final TraverseTransactor<Calendar> createNewCalendarObject = new TraverseTransactor<Calendar>() {
		public Calendar transact(final Cursor input) {
			return new Calendar(input.getString(1), input.getInt(0));
		}
	};

	public CalendarQuerier(ContentResolver contentResolver) {
		super(contentResolver);
	}

	@Override
	protected Uri.Builder genBuilder() {
		return null;
	}

	@Override
	protected Cursor postQuery(Uri.Builder builder) {
		return contentResolver.query(
				Uri.parse(Constants.getContentUri(Constants.ContentType.TYPE_CALENDAR)),
				new String[] {"_id", "displayName"},
				"selected=1",
				null,
				null
		);
	}

	@Override
	protected List<Calendar> fetch(Uri.Builder builder) {
		List<Calendar> calendars = new ArrayList<Calendar>();

		Cursor cursor = postQuery(builder);
		traverseCursor(
				cursor,
				createNewCalendarObject,
				calendars
		);
		if (cursor != null) {
			Log.w(Constants.APPLICATION_TAG, "null cursor when fetching calendars");
			cursor.close();
		}

		return calendars;
	}

	public List<Calendar> fetchAllCalendar() {
		return fetch(genBuilder());
	}

	public List<Calendar> fetchCalendarWithNameContained(String name) {
		List<Calendar> calendars = fetch(genBuilder());
		for (Calendar calendar: calendars) {
			if (!calendar.getTitle().contains(name)) {
				calendars.remove(calendar);
			}
		}

		return calendars;
	}

}
