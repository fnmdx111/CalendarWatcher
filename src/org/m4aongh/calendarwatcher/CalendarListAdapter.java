package org.m4aongh.calendarwatcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import org.m4aongh.calendarwatcher.data.Calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: chsc4698@gmail.com
 * Date: 12-3-16
 * all rights reserved
 */
public class CalendarListAdapter extends BaseAdapter {

	private Context context;
	private List<Calendar> calendars;
	private Map<Integer, Boolean> isCalendarsChecked;

	public CalendarListAdapter(Context context, List<Calendar> calendars) {
		this.context = context;
		this.calendars = calendars;
		this.isCalendarsChecked = new HashMap<Integer, Boolean>();
	}

	public int getCount() {
		return calendars.size();
	}

	public Calendar getItem(int i) {
		return calendars.get(i);
	}

	public long getItemId(int i) {
		return i;
	}

	public View getView(final int position, View convertView, ViewGroup viewGroup) {
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.calendar_list_item, null);
		}

		final CheckBox calendarCheckBox = (CheckBox)convertView.findViewById(R.id.calendar_checkbox);
		calendarCheckBox.setText(getItem(position).getTitle());
		calendarCheckBox.setChecked(false);
		calendarCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
				if (isCalendarsChecked == null) {
					isCalendarsChecked = new HashMap<Integer, Boolean>();
				}
				isCalendarsChecked.put(position, isChecked);
			}
		});

		if (isCalendarsChecked != null) {
			if (isCalendarsChecked.containsKey(position)) {
				calendarCheckBox.setChecked(!(isCalendarsChecked.get(position) == null));
			}
		}

		return convertView;
	}

	public void checkCalendarsByList(List<Calendar> checkedCalendars) {
		if (checkedCalendars == null) {
			return;
		}

		isCalendarsChecked = new HashMap<Integer, Boolean>();

		for (int i = 0; i < checkedCalendars.size(); i++) {
			if (checkedCalendars.contains(calendars.get(i))) {
				isCalendarsChecked.put(i, true);
			}
		}

		notifyDataSetChanged();
	}

	public List<Calendar> getCheckedCalendars() {
		List<Calendar> checkedCalendars = new ArrayList<Calendar>();
		for (int position: isCalendarsChecked.keySet()) {
			if (isCalendarsChecked.get(position)) {
				checkedCalendars.add(getItem(position));
			}
		}

		return checkedCalendars;
	}

}
