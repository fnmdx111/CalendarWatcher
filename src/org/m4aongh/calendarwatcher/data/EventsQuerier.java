package org.m4aongh.calendarwatcher.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.text.format.DateUtils;
import android.util.Log;
import org.m4aongh.calendarwatcher.constants.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: chsc4698@gmail.com
 * Date: 12-3-15
 * all rights reserved
 */
public class EventsQuerier extends Querier<Event> {

	private final int calendarID;
	private final TraverseTransactor<Event> createNewEventObject = new TraverseTransactor<Event>() {
		public Event transact(final Cursor input) {
			return new Event(input.getString(0));
		}
	};

	public EventsQuerier(ContentResolver contentResolver, int calendarID) {
		super(contentResolver);
		this.calendarID = calendarID;
	}

	@Override
	protected Uri.Builder genBuilder() {
		return Uri.parse(Constants.getContentUri(Constants.ContentType.TYPE_EVENTS)).buildUpon();
	}

	@Override
	protected Cursor postQuery(Uri.Builder builder) {
		return contentResolver.query(
				builder.build(),
				new String[] {"title"},
				"Calendars._id=" + calendarID,
				null,
				"startDay ASC, startMinute ASC"
		);
	}

	@Override
	protected List<Event> fetch(Uri.Builder builder) {
		List<Event> events = new ArrayList<Event>();

		Cursor cursor = postQuery(builder);
		traverseCursor(
				cursor,
				createNewEventObject,
				events
		);
		if (cursor != null) {
			Log.w(Constants.APPLICATION_TAG, "null cursor when fetching events");
			cursor.close();
		}

		return events;
	}

	public List<Event> fetchEventsOfToday() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		long startingTime = now.getTimeInMillis();

		now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) + 1);
		long endingTime = now.getTimeInMillis();

		return fetchEventsOfParticularDay(startingTime, endingTime);
	}

	public List<Event> fetchEventsOfParticularDay(long startingTime, long endingTime) {
		Uri.Builder builder = genBuilder();

		ContentUris.appendId(builder, startingTime);
		ContentUris.appendId(builder, endingTime);

		return fetch(builder);
	}

	public List<Event> fetchAllEvents() {
		Date now = new Date();
		long startingTime = now.getTime() - DateUtils.DAY_IN_MILLIS * 10000;
		long endingTime = now.getTime() + DateUtils.DAY_IN_MILLIS * 10000;

		return fetchEventsOfParticularDay(startingTime, endingTime);
	}

}
