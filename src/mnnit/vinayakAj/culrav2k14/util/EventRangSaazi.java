package mnnit.vinayakAj.culrav2k14.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventRangSaazi extends BaseEvent {

	String format;
	String[] rules;

	public EventRangSaazi(JSONObject object) throws JSONException {
		super(object);
		format = object.optString("format");

		JSONArray rulesArray = object.getJSONArray("rules");
		rules = new String[rulesArray.length()];

		for (int i = 0; i < rulesArray.length(); i++)
			rules[i] = rulesArray.optString(i);
	}

	public String getFormat() {
		return format;
	}

	public String[] getRules() {
		return rules;
	}

}
