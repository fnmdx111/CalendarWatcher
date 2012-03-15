package org.m4aongh.calendarwatcher;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import org.m4aongh.calendarwatcher.constants.Constants;
import org.m4aongh.calendarwatcher.data.*;

import java.util.*;

public class Settings extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Map<Integer, org.m4aongh.calendarwatcher.data.Calendar> map = getAllCalendars();
		for (int id: map.keySet()) {
			EventsQuerier querier = new EventsQuerier(getContentResolver(), id);

			showEvents(querier.fetchEventsOfToday());
		}
	}

	private void showEvents(List<Event> events) {
		for (Event event: events) {
			Log.v(
					Constants.APPLICATION_TAG,
					"title: " + event.getTitle()
			);
		}
	}

	private Map<Integer, org.m4aongh.calendarwatcher.data.Calendar> getAllCalendars() {
		CalendarQuerier querier = new CalendarQuerier(getContentResolver());
		List<org.m4aongh.calendarwatcher.data.Calendar> calendars = querier.fetchAllCalendar();

		Map<Integer, org.m4aongh.calendarwatcher.data.Calendar> map = new HashMap<Integer, org.m4aongh.calendarwatcher.data.Calendar>();
		for (org.m4aongh.calendarwatcher.data.Calendar calendar: calendars) {
			map.put(calendar.getId(), calendar);
		}

		return map;
	}

}
