package mnnit.vinayakAj.culrav2k14.util;

import org.json.JSONException;
import org.json.JSONObject;

public class Contact {

	String name;
	String mobileNo;
	String email;

	public Contact(String name, String position, String mobileNo, String email) {
		this.name = name;
		this.mobileNo = mobileNo;
		this.email = email;
	}

	public Contact(JSONObject object) throws JSONException {
		this.name = object.optString("name");
		this.mobileNo = object.optString("mobile");
		this.email = object.optString("email");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
