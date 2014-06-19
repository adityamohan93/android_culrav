package mnnit.vinayakAj.culrav2k14.Loaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import mnnit.vinayakAj.culrav2k14.Config;
import mnnit.vinayakAj.culrav2k14.db.ScheduleDBInterface;
import mnnit.vinayakAj.culrav2k14.util.Schedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.AsyncTaskLoader;

public class ScheduleLoader extends AsyncTaskLoader<ArrayList<Schedule>> {

	Context context;

	public ScheduleLoader(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public ArrayList<Schedule> loadInBackground() {
		SharedPreferences sharePref = PreferenceManager
				.getDefaultSharedPreferences(context);
		boolean firstRun = sharePref.getBoolean(Config.SCHEDULE_FILE, true);
		boolean firstUpdate = sharePref.getBoolean(Config.SCHEDULE_UPDATE, false);
		if (firstRun || !firstUpdate) {
			ArrayList<Schedule> result = new ArrayList<Schedule>();
			String json = null;
			try {
				InputStream inputStream = context.getAssets().open(
						"schedules.json");

				int size = inputStream.available();

				byte[] buffer = new byte[size];

				inputStream.read(buffer);
				inputStream.close();

				json = new String(buffer, "UTF-8");

				JSONObject obj = new JSONObject(json);

				JSONArray jsonArray = obj.getJSONArray("Schedules");

				int len = jsonArray.length();

				for (int i = 0; i < len; i++) {
					result.add(new Schedule(jsonArray.getJSONObject(i)));
				}
				if(firstRun){
					ScheduleDBInterface dbInterface = new ScheduleDBInterface(
						context);

					dbInterface.insert(result);					
					SharedPreferences.Editor editor = sharePref.edit();
					editor.putBoolean(Config.SCHEDULE_FILE, false);
					editor.commit();

				return dbInterface.getSchedules();
				}else{
					return result;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			ScheduleDBInterface dbInterface = new ScheduleDBInterface(context);
			ArrayList<Schedule> schedules = dbInterface.getSchedules();
			return schedules;
		}
	}

}
