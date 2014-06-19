package mnnit.vinayakAj.culrav2k14.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventLokTarang extends BaseEvent {

	String[] format;
	String[] judging;

	public EventLokTarang(JSONObject object) throws JSONException {
		super(object);
		JSONArray formatArray = object.getJSONArray("format");
		JSONArray judgingArray = object.getJSONArray("judging");

		format = new String[formatArray.length()];
		judging = new String[judgingArray.length()];

		for (int i = 0; i < formatArray.length(); i++)
			format[i] = formatArray.optString(i);

		for (int i = 0; i < judgingArray.length(); i++)
			judging[i] = judgingArray.optString(i);
	}

	public String[] getFormat() {
		return format;
	}

	public String[] getJudging() {
		return judging;
	}

}
