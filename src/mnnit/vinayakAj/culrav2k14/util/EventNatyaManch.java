package mnnit.vinayakAj.culrav2k14.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventNatyaManch extends BaseEvent {

	String[] format;

	public EventNatyaManch(JSONObject object) throws JSONException {
		super(object);

		JSONArray formatArray = object.getJSONArray("format");
		format = new String[formatArray.length()];

		for (int i = 0; i < formatArray.length(); i++)
			format[i] = formatArray.optString(i);
	}

	public String[] getFormat() {
		return format;
	}
}
