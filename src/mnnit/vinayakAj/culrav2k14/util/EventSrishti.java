package mnnit.vinayakAj.culrav2k14.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventSrishti extends BaseEvent {

	String[] rounds;

	public EventSrishti(JSONObject object) throws JSONException {
		super(object);
		JSONArray array = object.getJSONArray("rounds");
		rounds = new String[array.length()];
		for (int i = 0; i < array.length(); i++)
			rounds[i] = array.optString(i);
	}

	public String[] getRounds() {
		return rounds;
	}

}
