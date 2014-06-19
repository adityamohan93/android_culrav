package mnnit.vinayakAj.culrav2k14.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventGlamStreet extends BaseEvent {

	String[] rules;	

	public EventGlamStreet(JSONObject object) throws JSONException {
		super(object);
		
		JSONArray array = object.getJSONArray("rules");
		rules = new String[array.length()];
		for (int i = 0; i < array.length(); i++)
			rules[i] = array.optString(i);
	}

	public String[] getRules() {
		return rules;
	}

}
