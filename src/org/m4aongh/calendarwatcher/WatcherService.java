package org.m4aongh.calendarwatcher;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import org.m4aongh.calendarwatcher.data.Calendar;
import org.m4aongh.calendarwatcher.data.Event;
import org.m4aongh.calendarwatcher.data.EventsQuerier;
import org.m4aongh.calendarwatcher.utils.Utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.m4aongh.calendarwatcher.constants.Constants.*;

/**
 * User: chsc4698@gmail.com
 * Date: 12-3-16
 * all rights reserved
 */
public class WatcherService extends Service {

	private List<Event> events = new ArrayList<Event>();
	private int originalRingerMode = DEFAULT_VALUE_ORIGINAL_RINGER_MODE;
	private BroadcastReceiver timeChangedReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			checkEventsStates();
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		fetchEventsOfToday();

		startTimeListening();
	}

	private void fetchEventsOfToday() {
		Map<Integer, Calendar> map = Utilities.getAllCalendars(getContentResolver());

		for (int id: map.keySet()) {
			EventsQuerier querier = new EventsQuerier(getContentResolver(), id);
			List<Event> eventsOfThisCalendar = querier.fetchEventsOfToday();

			for (Event event: eventsOfThisCalendar) {
				if (!event.isAllDay()) {
					events.add(event);
				}
			}
		}
	}

	private void checkEventsStates() {
		String now = new SimpleDateFormat(TIME_FORMAT).format(new Date());
		AudioManager audioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);

		for (Event event : events) {
			if (now.compareTo(event.getStartingTime()) >= 0
					&& now.compareTo(event.getEndingTime()) <= 0) {
				originalRingerMode = audioManager.getRingerMode();
				audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

				Toast.makeText(
						this,
						"event " + event.getTitle() + " commences, setting to silent",
						Toast.LENGTH_SHORT
				).show();

				return;
			}
		}
		// set to original
		if (originalRingerMode != DEFAULT_VALUE_ORIGINAL_RINGER_MODE) {
			audioManager.setRingerMode(originalRingerMode);
		}
	}

	private void startTimeListening() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_TIME_TICK);
		intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
		intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);

		registerReceiver(timeChangedReceiver, intentFilter);
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(timeChangedReceiver);

		Log.v(APPLICATION_TAG, "service stopped");
		Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();

		super.onDestroy();
	}

}
