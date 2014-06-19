package mnnit.vinayakAj.culrav2k14.util;

import org.json.JSONObject;

import android.database.Cursor;

public class Schedule {
	String time, title, subtitle;
	int day;

	public Schedule(JSONObject object) {
		day = object.optInt("day", 3);
		time = object.optString("time");
		title = object.optString("title");
		subtitle = object.optString("subtitle");
	}

	public Schedule(Cursor c) {
		super();
		this.day = c.getInt(1);
		this.time = c.getString(2);
		this.title = c.getString(3);
		this.subtitle = c.getString(4);
	}

	public String getTime() {
		return time;
	}

	public String getTitle() {
		return title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public int getDay() {
		return day;
	}

}
