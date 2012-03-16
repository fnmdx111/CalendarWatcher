package org.m4aongh.calendarwatcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import org.m4aongh.calendarwatcher.data.*;
import org.m4aongh.calendarwatcher.utils.Utilities;

import java.util.*;

import static org.m4aongh.calendarwatcher.constants.Constants.*;

public class Settings extends Activity {

	private View.OnClickListener onRetrieveClickListener = new View.OnClickListener() {
		public void onClick(View view) {
			DatePicker startingDatePicker = (DatePicker)findViewById(R.id.starting_date_picker);
			DatePicker endingDatePicker = (DatePicker)findViewById(R.id.ending_date_picker);

			long startingDateTime = Utilities.clearHourMinuteSecond(
					Utilities.extractDateFromPicker(
							startingDatePicker
					)
			);
			long endingDateTime = Utilities.clearHourMinuteSecond(
					Utilities.extractDateFromPicker(
							endingDatePicker
					)
			);

			if (startingDateTime >= endingDateTime) {
				Toast.makeText(Settings.this, R.string.invalid_dates, Toast.LENGTH_SHORT).show();
				return;
			}

			traverseCalendars(startingDateTime, endingDateTime);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ID_START_SERVICE, 0, R.string.start_service);
		menu.add(0, MENU_ID_STOP_SERVICE, 1, R.string.stop_service);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, WatcherService.class);

		switch (item.getItemId()) {
			case MENU_ID_START_SERVICE:
				startService(intent);

				return true;
			case MENU_ID_STOP_SERVICE:
				stopService(intent);

				return true;
		}

		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button retrieve = (Button)findViewById(R.id.retrieve);
		retrieve.setOnClickListener(onRetrieveClickListener);
	}


	private void traverseCalendars(long startingTime, long endingTime) {
		Map<Integer, org.m4aongh.calendarwatcher.data.Calendar> map = Utilities.getAllCalendars(getContentResolver());
		String info = "";

		for (int id : map.keySet()) {
			EventsQuerier querier = new EventsQuerier(getContentResolver(), id);
			List<Event> events = querier.fetchEventsOfParticularDay(startingTime, endingTime);

			info += map.get(id).getTitle() + ":\n" + Utilities.organizeEventsTitles(events);
		}

		TextView infos = (TextView)findViewById(R.id.events_info);
		infos.setText(info);
	}

}
