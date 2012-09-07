package me.mad4a.calendarwatcher.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static me.mad4a.calendarwatcher.constants.Constants.*;

/**
 * User: chsc4698@gmail.com
 * Date: 12-3-15
 * all rights reserved
 */
public class CalendarQuerier extends Querier<Calendar> {

	private final TraverseHandler<Calendar> createNewCalendarObject = new TraverseHandler<Calendar>() {
		public Calendar handle(final Object input) {
			Cursor cursor = (Cursor)input;
			String account = cursor.getString(1);
			account = account.substring(0, account.indexOf("@"));
			return new Calendar(cursor.getString(2) + " (" + account + ")", cursor.getInt(0));
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
				CalendarContract.Calendars.CONTENT_URI,
				new String[] {
						CalendarContract.Calendars._ID,
						CalendarContract.Calendars.ACCOUNT_NAME,
						CalendarContract.Calendars.CALENDAR_DISPLAY_NAME
				},
				"(" + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?)",
				new String[] {
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
			Log.w(APPLICATION_TAG, "Null cursor when fetching calendars");
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
