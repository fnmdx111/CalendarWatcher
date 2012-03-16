package org.m4aongh.calendarwatcher;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import org.m4aongh.calendarwatcher.constants.Constants;
import org.m4aongh.calendarwatcher.data.*;
import org.m4aongh.calendarwatcher.utils.Utilities;

import java.util.*;

public class Settings extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button retrieve = (Button)findViewById(R.id.retrieve);

		retrieve.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				DatePicker startingDatePicker = (DatePicker)findViewById(R.id.starting_date_picker);
				DatePicker endingDatePicker = (DatePicker)findViewById(R.id.ending_date_picker);

				long startingDateTime = Utilities.clearHourMinuteSecond(Utilities.extractDateFromPicker(startingDatePicker));
				long endingDateTime = Utilities.clearHourMinuteSecond(Utilities.extractDateFromPicker(endingDatePicker));

				if (startingDateTime >= endingDateTime) {
					Toast.makeText(Settings.this, R.string.invalid_dates, Toast.LENGTH_SHORT).show();
					return;
				}

				traverseCalendars(
						startingDateTime,
						endingDateTime,
						new TraverseTransactor<Event>() {
							public Event transact(final Object input) {
								Log.v(
										Constants.APPLICATION_TAG,
										"title: " + ((Event)input).getTitle()
								);

								return null;
							}
						}
				);
			}
		});
	}

	private void traverseCalendars(long startingTime, long endingTime, TraverseTransactor<Event> transactor) {
		Map<Integer, org.m4aongh.calendarwatcher.data.Calendar> map = getAllCalendars();
		String info = "";

		for (int id: map.keySet()) {
			EventsQuerier querier = new EventsQuerier(getContentResolver(), id);
			List<Event> events = querier.fetchEventsOfParticularDay(startingTime, endingTime);

			info += map.get(id).getTitle() + ":\n" + Utilities.organizeEventsTitles(events);

			for (Event event: events) {
				transactor.transact(event);
			}
		}
		TextView infos = (TextView)findViewById(R.id.events_info);
		infos.setText(info);
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
