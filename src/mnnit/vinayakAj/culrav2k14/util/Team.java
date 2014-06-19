package mnnit.vinayakAj.culrav2k14.util;

import org.json.JSONException;
import org.json.JSONObject;

public class Team {
	Contact contact;
	String position;

	public Team(JSONObject object) throws JSONException {
		this.position = object.optString("position");
		this.contact = new Contact(object);
	}

	public Contact getContact() {
		return contact;
	}

	public String getPosition() {
		return position;
	}

}
