package mnnit.vinayakAj.culrav2k14.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventAnunaad extends BaseEvent {

	private String[] format;
	private String[] rules;

	public EventAnunaad(JSONObject object) throws JSONException {
		super(object);
		JSONArray rulesArray = object.getJSONArray("rules");
		JSONArray formatArray = object.getJSONArray("format");
		int rulesSize = rulesArray.length(), formatSize = formatArray.length();

		rules = new String[rulesSize];
		format = new String[formatSize];

		for (int i = 0; i < rulesSize; i++)
			rules[i] = rulesArray.optString(i);

		for (int i = 0; i < formatSize; i++)
			format[i] = formatArray.optString(i);

	}

	public String[] getRules() {
		return rules;
	}

	public String[] getFormat() {
		return format;
	}

}
