package mnnit.vinayakAj.culrav2k14.util;

import org.json.JSONException;
import org.json.JSONObject;

public class ExploreItem {
	String event;
	String category;
	String description;

	public ExploreItem(JSONObject jsonObject) throws JSONException {
		this.event = jsonObject.optString("event");
		this.category = jsonObject.optString("category");
		this.description = jsonObject.optString("description");
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
