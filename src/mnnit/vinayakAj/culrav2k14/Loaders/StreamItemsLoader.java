package mnnit.vinayakAj.culrav2k14.Loaders;

import java.util.ArrayList;

import mnnit.vinayakAj.culrav2k14.db.StreamDBInterface;
import mnnit.vinayakAj.culrav2k14.util.Stream;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class StreamItemsLoader extends AsyncTaskLoader<ArrayList<Stream>> {

	Context context;

	public StreamItemsLoader(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public ArrayList<Stream> loadInBackground() {
		/*
		 * ArrayList<Stream> result = new ArrayList<Stream>(); String json =
		 * null; try { InputStream inputStream =
		 * context.getAssets().open("streams.json");
		 * 
		 * int size = inputStream.available();
		 * 
		 * byte[] buffer = new byte[size];
		 * 
		 * inputStream.read(buffer); inputStream.close();
		 * 
		 * json = new String(buffer, "UTF-8");
		 * 
		 * JSONObject obj = new JSONObject(json);
		 * 
		 * JSONArray jsonArray = obj.getJSONArray("Streams");
		 * 
		 * int len = jsonArray.length();
		 * 
		 * for (int i = 0; i < len; i++) { result.add(new
		 * Stream(jsonArray.getJSONObject(i))); }
		 * 
		 * StreamDBInterface streamDB = new StreamDBInterface(context);
		 * 
		 * streamDB.insert(result);
		 * 
		 * return streamDB.getStreamItems(); } catch (IOException e) {
		 * e.printStackTrace(); } catch (JSONException e) { e.printStackTrace();
		 * } return null;
		 */
		StreamDBInterface streamDB = new StreamDBInterface(context);
		return streamDB.getStreamItems();
	}

}
