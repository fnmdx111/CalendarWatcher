package org.m4aongh.calendarwatcher.application;

import android.app.Application;
import org.m4aongh.calendarwatcher.data.Calendar;

import java.util.List;

/**
 * User: chsc4698@gmail.com
 * Date: 12-3-15
 * all rights reserved
 */
public class CalendarWatcher extends Application {

	public List<Calendar> checkedCalendars;

	public String globalRingerMode;
}
