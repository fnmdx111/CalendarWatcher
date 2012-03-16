package org.m4aongh.calendarwatcher.data;

import android.os.Parcel;
import android.os.Parcelable;
import org.m4aongh.calendarwatcher.constants.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: chsc4698@gmail.com
 * Date: 12-3-15
 * all rights reserved
 */
public class Event implements Parcelable {

	private final String title;
	private final boolean isAllDay;
	private final String startingTime;
	private final String endingTime;

	public Event(String title, boolean isAllDay, String startingDateTime, String endingDateTime) {
		this.title = title;
		this.isAllDay = isAllDay;

		this.startingTime = new SimpleDateFormat(Constants.TIME_FORMAT).format(new Date(Long.parseLong(startingDateTime)));
		this.endingTime = new SimpleDateFormat(Constants.TIME_FORMAT).format(new Date(Long.parseLong(endingDateTime)));
	}

	public String getTitle() {
		return title;
	}

	public boolean isAllDay() {
		return isAllDay;
	}

	public String getEndingTime() {
		return endingTime;
	}

	public String getStartingTime() {
		return startingTime;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(isAllDay ? 1 : 0);
		parcel.writeStringArray(
				new String[] {
						title,
						startingTime,
						endingTime
				}
		);
	}

	public static final Creator<Event> CREATOR = new Creator<Event>() {
		public Event createFromParcel(Parcel parcel) {
			String[] strings = new String[3];
			parcel.readStringArray(strings);
			boolean isAllDay = parcel.readInt() == 1;

			return new Event(strings[0], isAllDay, strings[1], strings[2]);
		}

		public Event[] newArray(int i) {
			return new Event[0];  //To change body of implemented methods use File | Settings | File Templates.
		}
	};

}
