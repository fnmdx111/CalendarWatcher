package me.mad4a.calendarwatcher.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import me.mad4a.calendarwatcher.constants.Constants;

import java.util.ArrayList;
import java.util.List;

import android.provider.CalendarContract.*;

/**
 * User: chsc4698@gmail.com
 * Date: 12-3-15
 * all rights reserved
 */
public class CalendarQuerier extends Querier<Calendar> {

	private final TraverseTransactor<Calendar> createNewCalendarObject = new TraverseTransactor<Calendar>() {
		public Calendar transact(final Object input) {
			return new Calendar(((Cursor)input).getString(1), ((Cursor)input).getInt(0));
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
				Calendars.CONTENT_URI,
				new String[] {
						Calendars._ID,
						Calendars.ACCOUNT_NAME,
						Calendars.CALENDAR_DISPLAY_NAME
				},
				"((" + Calendars.ACCOUNT_NAME + " = ?) AND (" + Calendars.ACCOUNT_TYPE + " = ?))",
				new String[] {
						Constants.USER_ID,
						"com.google"
				},
				null
		);
	}

	@Override
	protected List<Calendar> fetch(Uri.Builder builder) {
		List<Calendar> calendars = new ArrayList<Calendar>();

		Cursor cursor = postQuery(builder);
		if (cursor == null) {
			Log.w(Constants.APPLICATION_TAG, "null cursor when fetching calendars");
			return null;
		}
		traverseCursor(
				cursor,
				createNewCalendarObject,
				calendars
		);
		cursor.close();

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
