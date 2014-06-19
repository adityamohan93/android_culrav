package mnnit.vinayakAj.culrav2k14.Loaders;

import java.io.IOException;
import java.io.InputStream;

import mnnit.vinayakAj.culrav2k14.util.EventAnunaad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class EventAnunaadLoader extends AsyncTaskLoader<EventAnunaad> {

	Context context;
	private int position;

	public EventAnunaadLoader(Context context, int position) {
		super(context);
		this.context = context;
		this.position = position;
	}

	@Override
	public EventAnunaad loadInBackground() {
		String json = null;
		try {
			InputStream inputStream = context.getAssets().open("anunaad.json");

			int size = inputStream.available();

			byte[] buffer = new byte[size];

			inputStream.read(buffer);
			inputStream.close();

			json = new String(buffer, "UTF-8");

			JSONObject obj = new JSONObject(json);

			JSONArray jsonArray = obj.getJSONArray("Anunaad");

			return new EventAnunaad(jsonArray.getJSONObject(position));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
