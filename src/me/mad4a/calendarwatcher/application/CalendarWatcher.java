package me.mad4a.calendarwatcher.application;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import me.mad4a.calendarwatcher.AlarmReceiver;
import me.mad4a.calendarwatcher.data.Calendar;

import java.util.Date;
import java.util.List;

import static me.mad4a.calendarwatcher.constants.Constants.*;

/**
 * User: chsc4698@gmail.com
 * Date: 12-3-15
 * all rights reserved
 */
public class CalendarWatcher extends Application {

	public List<Calendar> checkedCalendars;

	public String globalRingerMode;

	public PendingIntent dateChangeAlarm;

	public void setDateChangeAlarm() {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);

		calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calendar.set(java.util.Calendar.MINUTE, 1);
		calendar.set(java.util.Calendar.SECOND, 0);

		Intent intent = new Intent(this, AlarmReceiver.class).setAction(ACTION_DAILY_FETCH);

		dateChangeAlarm = PendingIntent.getBroadcast(this, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), dateChangeAlarm);

		Log.v(APPLICATION_TAG, new Date(calendar.getTimeInMillis()) + " alarm set");
	}

}
