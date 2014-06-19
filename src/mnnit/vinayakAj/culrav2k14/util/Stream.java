package mnnit.vinayakAj.culrav2k14.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;

public class Stream {
	int id;
	String title, subtitle, message;

	public Stream(JSONObject object) throws JSONException {
		id = object.optInt("id");
		title = object.optString("title");
		subtitle = object.optString("subtitle");
		message = object.optString("message");
	}

	public Stream(Cursor c) {
		id = c.getInt(1);
		title = c.getString(2);
		subtitle = c.getString(3);
		message = c.getString(4);
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public String getMessage() {
		return message;
	}

}
