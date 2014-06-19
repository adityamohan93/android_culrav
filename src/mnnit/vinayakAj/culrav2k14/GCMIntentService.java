package mnnit.vinayakAj.culrav2k14;

import static mnnit.vinayakAj.culrav2k14.Config.GOOGLE_PROJECT_NUMBER;
import static mnnit.vinayakAj.culrav2k14.Config.displayMessage;
import mnnit.vinayakAj.culrav2k14.db.ScheduleDBInterface;
import mnnit.vinayakAj.culrav2k14.db.StreamDBInterface;
import mnnit.vinayakAj.culrav2k14.util.Schedule;
import mnnit.vinayakAj.culrav2k14.util.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

/**
 * IntentService responsible for handling GCM messages.\ Docs:
 * http://developer.android
 * .com/guide/google/gcm/client-javadoc/com/google/android
 * /gcm/GCMBaseIntentService.html
 */
public class GCMIntentService extends GCMBaseIntentService {

	String[] titles = { "schedule", "stream", "notification", "reset schedule",
			"reset stream" };
	Context messageContext;
	static long[] vibrate = {100,200,300,400,500,550,550,550,600};

	public GCMIntentService(boolean withLocation) {
		super(GOOGLE_PROJECT_NUMBER);
	}

	public GCMIntentService() {
		super(GOOGLE_PROJECT_NUMBER);
	}

	/**
	 * Called after the app has been registered on the GCM service We now have
	 * the regID that we can use to register with the AirBop servers.
	 */
	@Override
	protected void onRegistered(Context context, String registrationId) {

		displayMessage(context, getString(R.string.gcm_registered));
		// Get our data for the server
		GCMServerUtilities server_data = GCMServerUtilities
				.fillDefaults(registrationId);
		// server_data.loadCurrentLocation(context);
		server_data.loadDataFromPrefs(context);
		// Get rid of the location from the prefs so we requery next time
		GCMServerUtilities.clearLocationPrefs(context);
		GCMServerUtilities.register(getApplicationContext(), server_data);
	}

	/**
	 * Called after the device has been unregisterd from the GCM server. We we
	 * are registered on the AirBop servers we should unregister from there as
	 * well.
	 */
	@Override
	protected void onUnregistered(Context context, String registrationId) {

		// displayMessage(context, getString(R.string.gcm_unregistered));
		// If we are still registered with AirBop it is time to unregister
		if (GCMRegistrar.isRegisteredOnServer(context)) {
			GCMServerUtilities.unregister(context, registrationId);
		} else {
			// This callback results from the call to unregister made on
			// ServerUtilities when the registration to the server failed.

		}
	}

	/**
	 * We have received a push notification from GCM, analyze the intents bundle
	 * for the payload.
	 */
	@Override
	protected void onMessage(Context context, Intent intent) {

		String message = null;
		String title = null;
		String url = null;
		messageContext = context;
		if (intent != null) {
			// Check the bundle for the pay load body and title
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				// displayMessage(context, "Message bundle: " + bundle);

				message = bundle.getString("message");

				title = bundle.getString("title");

				url = bundle.getString("url");
			}
		}
		if (title.equalsIgnoreCase(titles[0])) {
			// Schedule update
			new SchedulingTask().execute(message);
		} else if (title.equalsIgnoreCase(titles[1])) {
			// Stream update
			new StreamTask().execute(message);
		} else if (title.equalsIgnoreCase(titles[2])) {
			// Minor Notification
			generateNotification(context, "Culrav", message, url);
		} else if (title.equalsIgnoreCase(titles[3])) {
			// Reset Schedule Database
			resetScheduleDB();
			String msg = getString(R.string.scheduleReset);
			generateNotification(messageContext, "Culrav", msg);
		} else if (title.equalsIgnoreCase(titles[4])) {
			// Reset Stream Database
			resetStreamDB();
			String msg = getString(R.string.streamReset);
			generateNotification(messageContext, "Culrav", msg);
		}else{
			generateNotification(context, title, message, url);
		}
	}

	@Override
	protected void onDeletedMessages(Context context, int total) {
		/*
		 * Called when the GCM servers tells that app that pending messages have
		 * been deleted because the device was idle.
		 */
		String message = getString(R.string.gcm_deleted, total);
		displayMessage(context, message);
		// notifies user
		generateNotification(context, "Culrav", message);
	}

	/**
	 * Called on registration or unregistration error. Whatever this error is,
	 * it is not recoverable
	 */
	@Override
	public void onError(Context context, String errorId) {

	}

	/**
	 * Called on a registration error that could be retried. By default, it does
	 * nothing and returns true, but could be overridden to change that behavior
	 * and/or display the error.
	 */
	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	private static void generateNotification(Context context, String title,
			String message) {
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		if ((title == null) || (title.equals(""))) {
			title = context.getString(R.string.app_name);
		}
		Intent notificationIntent = new Intent(context, HomeUI.class);
		// set intent so it does not start a new activity
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		
		
		Notification notification = new NotificationCompat.Builder(context)
				.setContentTitle(title)
				.setContentText(message)
				.setContentIntent(intent)
				.setSound(Config.SOUND_PATH)
				.setVibrate(vibrate)
				.setSmallIcon(icon)
				.setWhen(when)				
				.setSound(Config.SOUND_PATH)				
				.setStyle(
						new NotificationCompat.BigTextStyle().bigText(message))
				.build();

		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, notification);
	}

	private static void generateNotification(Context context, String title,
			String message, String url) {
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		if ((title == null) || (title.equals(""))) {
			title = context.getString(R.string.app_name);
		}

		Intent notificationIntent = null;
		if ((url == null) || (url.equals(""))) {
			// just bring up the app
			notificationIntent = new Intent(context, HomeUI.class);
		} else {
			// Launch the URL
			notificationIntent = new Intent(Intent.ACTION_VIEW);
			notificationIntent.setData(Uri.parse(url));
			notificationIntent.addCategory(Intent.CATEGORY_BROWSABLE);
		}

		// set intent so it does not start a new activity
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		Notification notification = new NotificationCompat.Builder(context)
				.setContentTitle(title)
				.setContentText(message)
				.setContentIntent(intent)
				.setSound(Config.SOUND_PATH)
				.setSmallIcon(icon)
				.setVibrate(vibrate)
				.setWhen(when)
				.setStyle(
						new NotificationCompat.BigTextStyle().bigText(message))
				.build();

		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, notification);
	}

	private class SchedulingTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			ScheduleDBInterface dbHelper = new ScheduleDBInterface(
					getApplicationContext());
			String message = params[0];
			try {
				JSONObject jsonData = new JSONObject(message);
				JSONArray jsonArray = jsonData.getJSONArray("Schedules");
				int size = jsonArray.length();
				for (int i = 0; i < size; i++) {
					JSONObject object = jsonArray.getJSONObject(i);
					dbHelper.insert(new Schedule(object));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);			
			SharedPreferences sharePref = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			SharedPreferences.Editor editor = sharePref.edit();
			editor.putBoolean(Config.SCHEDULE_UPDATE, true);
			editor.putInt(Config.PAGER_POSITION, 3);
			editor.commit();
			generateNotification(getApplicationContext(), "Culrav",
					"Schedule Updated", null);
		}
	}

	private class StreamTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			StreamDBInterface dbHelper = new StreamDBInterface(
					getApplicationContext());
			String message = params[0];
			try {
				JSONObject jsonData = new JSONObject(message);
				JSONArray jsonArray = jsonData.getJSONArray("Streams");
				int size = jsonArray.length();
				for (int i = 0; i < size; i++) {
					JSONObject object = jsonArray.getJSONObject(i);
					dbHelper.insert(new Stream(object));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);			
			SharedPreferences sharePref = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			SharedPreferences.Editor editor = sharePref.edit();
			editor.putInt(Config.PAGER_POSITION, 0);			
			editor.commit();
			generateNotification(getApplicationContext(), "Culrav",
					"Stream update", null);
		}

	}

	private void resetStreamDB() {
		StreamDBInterface dbHelper = new StreamDBInterface(
				getApplicationContext());
		dbHelper.reset();
		dbHelper.close();
	}

	private void resetScheduleDB() {
		ScheduleDBInterface dbHelper = new ScheduleDBInterface(
				getApplicationContext());
		dbHelper.reset();
		dbHelper.close();
	}
}
