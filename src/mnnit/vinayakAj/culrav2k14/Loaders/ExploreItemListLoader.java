package mnnit.vinayakAj.culrav2k14.Loaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import mnnit.vinayakAj.culrav2k14.util.ExploreItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class ExploreItemListLoader extends
		AsyncTaskLoader<ArrayList<ExploreItem>> {

	Context context;

	public ExploreItemListLoader(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public ArrayList<ExploreItem> loadInBackground() {
		ArrayList<ExploreItem> result = new ArrayList<ExploreItem>();
		String json = null;
		try {
			InputStream inputStream = context.getAssets().open("events.json");

			int size = inputStream.available();

			byte[] buffer = new byte[size];

			inputStream.read(buffer);
			inputStream.close();

			json = new String(buffer, "UTF-8");

			JSONObject obj = new JSONObject(json);

			JSONArray jsonArray = obj.getJSONArray("events");

			for (int i = 0; i < jsonArray.length(); i++) {
				result.add(new ExploreItem(jsonArray.getJSONObject(i)));
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
