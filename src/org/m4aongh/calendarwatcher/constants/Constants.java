package org.m4aongh.calendarwatcher.constants;

import android.os.Build;

/**
 * User: chsc4698@gmail.com
 * Date: 12-3-15
 * all rights reserved
 */
public class Constants {

	public static final String APPLICATION_TAG = "m4aongh";

	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String KEY_ORIGINAL_RINGER_MODE = "original_ringer_mode";
	public static final String KEY_EVENTS_CACHE = "events_cache";

	public static final int DEFAULT_VALUE_ORIGINAL_RINGER_MODE = 0xffffffff;
	public static final int MENU_ID_STOP_SERVICE = 1;
	public static final int MENU_ID_START_SERVICE = 0;

	public enum ContentType {
		TYPE_CALENDAR,
		TYPE_EVENTS,
	}

	public static String getContentUri(ContentType type) {
		String head = "content://" + (Build.VERSION.SDK_INT < 8 ? "calendar" : "com.android.calendar") + "/";
		switch (type) {
			case TYPE_EVENTS:
				return head + "instances/when";
			case TYPE_CALENDAR:
				return head + "calendars";
		}

		return head;
	}

}
