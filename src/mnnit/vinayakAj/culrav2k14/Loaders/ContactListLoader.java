package mnnit.vinayakAj.culrav2k14.Loaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import mnnit.vinayakAj.culrav2k14.util.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;


public class ContactListLoader extends AsyncTaskLoader<ArrayList<Contact>> {

	Context context;

	public ContactListLoader(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public ArrayList<Contact> loadInBackground() {
		ArrayList<Contact> result = new ArrayList<Contact>();
		String json = null;
		try {
			InputStream inputStream = context.getAssets().open("contacts.json");

			int size = inputStream.available();

			byte[] buffer = new byte[size];

			inputStream.read(buffer);
			inputStream.close();

			json = new String(buffer, "UTF-8");

			JSONObject obj = new JSONObject(json);

			JSONArray jsonArray = obj.getJSONArray("contacts");

			for (int i = 0; i < jsonArray.length(); i++) {
				result.add(new Contact(jsonArray.getJSONObject(i)));
			}

			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

}
