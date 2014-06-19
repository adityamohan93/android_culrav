package mnnit.vinayakAj.culrav2k14.Loaders;

import java.io.IOException;
import java.io.InputStream;

import mnnit.vinayakAj.culrav2k14.util.EventLokTarang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class EventLokTarangLoader extends AsyncTaskLoader<EventLokTarang> {

	private Context context;
	private int position;

	public EventLokTarangLoader(Context context, int position) {
		super(context);
		this.context = context;
		this.position = position;
	}

	@Override
	public EventLokTarang loadInBackground() {
		EventLokTarang result;
		String json = null;
		try {
			InputStream inputStream = context.getAssets()
					.open("loktarang.json");

			int size = inputStream.available();

			byte[] buffer = new byte[size];

			inputStream.read(buffer);
			inputStream.close();

			json = new String(buffer, "UTF-8");

			JSONObject obj = new JSONObject(json);
			JSONArray jsonArray = obj.getJSONArray("LokTarang");
			result = new EventLokTarang(jsonArray.getJSONObject(position));
			return result;
		} catch (IOException e) {
		} catch (JSONException e) {
		}
		return null;
	}

}
