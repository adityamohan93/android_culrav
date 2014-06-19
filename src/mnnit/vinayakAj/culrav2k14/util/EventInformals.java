package mnnit.vinayakAj.culrav2k14.util;

import org.json.JSONException;
import org.json.JSONObject;

public class EventInformals extends BaseEvent {
	
	String format;
	
	public EventInformals(JSONObject object) throws JSONException {
		super(object);
		format = object.optString("format"); 
	}

	public String getFormat() {
		return format;
	}

}
