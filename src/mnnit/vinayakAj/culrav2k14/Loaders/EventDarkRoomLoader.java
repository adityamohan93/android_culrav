package mnnit.vinayakAj.culrav2k14.Loaders;

import java.io.IOException;
import java.io.InputStream;

import mnnit.vinayakAj.culrav2k14.util.EventDarkRoom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class EventDarkRoomLoader extends AsyncTaskLoader<EventDarkRoom> {

	Context context;
	private int position;

	public EventDarkRoomLoader(Context context, int position) {
		super(context);
		this.context = context;
		this.position = position;
	}

	@Override
	public EventDarkRoom loadInBackground() {
		String json = null;
		try {
			InputStream inputStream = context.getAssets().open("darkroom.json");

			int size = inputStream.available();

			byte[] buffer = new byte[size];

			inputStream.read(buffer);
			inputStream.close();

			json = new String(buffer, "UTF-8");

			JSONObject obj = new JSONObject(json);

			JSONArray jsonArray = obj.getJSONArray("DarkRoom");

			return new EventDarkRoom(jsonArray.getJSONObject(position));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
