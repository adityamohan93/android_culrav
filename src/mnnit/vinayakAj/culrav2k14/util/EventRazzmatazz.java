package mnnit.vinayakAj.culrav2k14.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventRazzmatazz extends BaseEvent {

	String[] rules;
	String[] judging;

	public EventRazzmatazz(JSONObject object) throws JSONException {
		super(object);
		JSONArray rulesArray = object.getJSONArray("rules");
		JSONArray judgingArray = object.getJSONArray("judging");

		rules = new String[rulesArray.length()];
		judging = new String[judgingArray.length()];

		for (int i = 0; i < rulesArray.length(); i++)
			rules[i] = rulesArray.optString(i);

		for (int i = 0; i < judgingArray.length(); i++)
			judging[i] = judgingArray.optString(i);

	}

	public String[] getRules() {
		return rules;
	}

	public String[] getJudging() {
		return judging;
	}

}
