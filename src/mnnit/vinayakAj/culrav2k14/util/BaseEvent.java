package mnnit.vinayakAj.culrav2k14.util;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseEvent {

	String eventId;
	String event;
	String description;

	public BaseEvent(JSONObject object) throws JSONException {
		eventId = object.optString("eventID");
		event = object.optString("event");
		description = object.optString("description");
	}

	public String getEventId() {
		return eventId;
	}

	public String getEvent() {
		return event;
	}

	public String getDescription() {
		return description;
	}

}
