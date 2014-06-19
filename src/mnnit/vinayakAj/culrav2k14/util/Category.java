package mnnit.vinayakAj.culrav2k14.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Category {
	String title;
	String[] events;
	ArrayList<Contact> contacts;

	public Category(JSONObject object) throws JSONException {
		title = object.getString("category");
		JSONArray array = object.getJSONArray("events");
		events = new String[array.length()];
		for (int i = 0; i < array.length(); i++) {
			events[i] = array.optString(i);
		}

		JSONArray jsonContacts = object.getJSONArray("contacts");
		int noOfContacts = jsonContacts.length();
		contacts = new ArrayList<Contact>();
		for (int i = 0; i < noOfContacts; i++) {
			contacts.add(new Contact(jsonContacts.getJSONObject(i)));
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getEvents() {
		return events;
	}

	public void setEvents(String[] events) {
		this.events = events;
	}

	public ArrayList<Contact> getContacts() {
		return contacts;
	}

	public String[] getContactsNames() {
		int size = contacts.size();
		String[] names = new String[size];

		for (int i = 0; i < size; i++) {
			names[i] = contacts.get(i).getName();
		}
		return names;
	}

	public String[] getContactsMobileNos() {
		int size = contacts.size();
		String[] mobiles = new String[size];

		for (int i = 0; i < size; i++) {
			mobiles[i] = contacts.get(i).getMobileNo();
		}
		return mobiles;
	}

	public String[] getContactsEmails() {
		int size = contacts.size();
		String[] emails = new String[size];

		for (int i = 0; i < size; i++) {
			emails[i] = contacts.get(i).getEmail();
		}
		return emails;
	}
}
