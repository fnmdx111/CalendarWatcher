package org.m4aongh.calendarwatcher;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import org.m4aongh.calendarwatcher.application.CalendarWatcher;
import org.m4aongh.calendarwatcher.data.CalendarQuerier;

import static org.m4aongh.calendarwatcher.constants.Constants.*;

public class Settings extends Activity {

	private Intent intent;
	private CalendarListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initView();
		checkIfServingRunning();
	}

	private void checkIfServingRunning() {
		TextView serviceStatus = (TextView)findViewById(R.id.service_status);

		ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo serviceInfo: activityManager.getRunningServices(Integer.MAX_VALUE)) {
			if (WatcherService.class.getName().equals(serviceInfo.service.getClassName())) {
				serviceStatus.setText(R.string.running);
				serviceStatus.setTextColor(Color.GREEN);

				return;
			}
		}

		serviceStatus.setText(R.string.down);
		serviceStatus.setTextColor(Color.RED);
	}

	private void initView() {
		Button btnStartService = (Button)findViewById(R.id.start_service);
		Button btnStopService = (Button)findViewById(R.id.stop_service);

		intent = new Intent(this, WatcherService.class);
		btnStartService.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				((CalendarWatcher)getApplication()).checkedCalendars = adapter.getCheckedCalendars();
				startService(intent);
				checkIfServingRunning();
			}
		});
		btnStopService.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				((CalendarWatcher)getApplication()).checkedCalendars = null;
				stopService(intent);
				checkIfServingRunning();
			}
		});

		final ToggleButton originalMode = (ToggleButton)findViewById(R.id.original_mode);
		final ToggleButton vibrateMode = (ToggleButton)findViewById(R.id.vibrate_mode);
		final ToggleButton silentMode = (ToggleButton)findViewById(R.id.silent_mode);

		View.OnClickListener onToggleButtonsClickListener = new View.OnClickListener() {
			public void onClick(View view) {
				originalMode.setChecked(false);
				vibrateMode.setChecked(false);
				silentMode.setChecked(false);

				ToggleButton toggleButton = (ToggleButton)view;
				toggleButton.setChecked(true);
				((CalendarWatcher)getApplication()).globalRingerMode = (String)toggleButton.getTextOn();
			}
		};

		originalMode.setOnClickListener(onToggleButtonsClickListener);
		vibrateMode.setOnClickListener(onToggleButtonsClickListener);
		silentMode.setOnClickListener(onToggleButtonsClickListener);

		silentMode.performClick();

		ListView calendarList = (ListView)findViewById(R.id.calendar_list);
		adapter = new CalendarListAdapter(this, new CalendarQuerier(getContentResolver()).fetchAllCalendar());
		adapter.checkCalendarsByList(((CalendarWatcher)getApplication()).checkedCalendars);
		calendarList.setAdapter(adapter);

		String ringerMode = ((CalendarWatcher)getApplication()).globalRingerMode;
		if (ringerMode == null) {
			return;
		}

		if (ringerMode.equals(getString(R.string.silent))) {
			silentMode.performClick();
		} else if (ringerMode.equals(getString(R.string.vibrate))) {
			vibrateMode.performClick();
		} else if (ringerMode.equals(getString(R.string.original))) {
			originalMode.performClick();
		}
	}

}
