package mnnit.vinayakAj.culrav2k14.Loaders;

import java.io.IOException;
import java.io.InputStream;

import mnnit.vinayakAj.culrav2k14.util.EventRazzmatazz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class EventRazzmatazzLoader extends AsyncTaskLoader<EventRazzmatazz> {

	private Context context;
	private int position;

	public EventRazzmatazzLoader(Context context, int position) {
		super(context);
		this.context = context;
		this.position = position;
	}

	@Override
	public EventRazzmatazz loadInBackground() {
		EventRazzmatazz result;
		String json = null;
		try {
			InputStream inputStream = context.getAssets().open(
					"razzmatazz.json");

			int size = inputStream.available();

			byte[] buffer = new byte[size];

			inputStream.read(buffer);
			inputStream.close();

			json = new String(buffer, "UTF-8");

			JSONObject obj = new JSONObject(json);

			JSONArray jsonArray = obj.getJSONArray("Razzmatazz");

			result = new EventRazzmatazz(jsonArray.getJSONObject(position));
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
