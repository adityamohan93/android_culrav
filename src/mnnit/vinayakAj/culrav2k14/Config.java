package mnnit.vinayakAj.culrav2k14;

import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;

import com.suredigit.inappfeedback.FeedbackSettings;

public final class Config {

	final public static int CULRAV_YEAR = 2014;

	final public static String APP_NAME = "Culrav 2k14";
	
	final public static String WEB_PAGE = "http://www.culrav.in";

	final public static String SERVER_URL = "http://www.airbop.com/api/v1/";

	final public static String GOOGLE_PROJECT_NUMBER = "771317020692";

	final public static String APP_KEY = "113565ed-31af-4b39-84ba-daf40f3dbdc7";

	final public static String APP_SECRET = "e301e2e22553ab8c5264ff511c6584df326ad685df84e967ee765525ee8fb1e6";

	final public static boolean USE_LOCATION = false;

	final public static boolean USE_SERVICE = true;

	final public static String DISPLAY_MESSAGE_ACTION = "mnnit.vinayakAj.culrav2k14.app.DISPLAY_MESSAGE";

	final public static String EXTRA_MESSAGE = "MESSAGE";

	final public static String PAGER_POSITION = "mnnit.vinayakAj.culrav2k14.pagerPosition";

	final public static String PREF_NAME = "mnnit.culrav.sharedPreferences";

	final public static Uri SOUND_PATH = Settings.System.DEFAULT_NOTIFICATION_URI;

	final public static String SCHEDULE_FILE = "mnnit.culrav2k14.schedule";
	
	final public static String SCHEDULE_UPDATE = "mnnit.culrav2k14.schedule.update";	
	
	final public static String FB_PAGE_LITMUSE = "https://www.facebook.com/englitculrav2014";
	final public static String FB_PAGE_LITMUSE_ID = "1418478205069735";
	
	final public static String FB_PAGE_DARKROOM = "https://www.facebook.com/Culrav.Darkroom";
	final public static String FB_PAGE_DARKROOM_ID = "314330168692269";
	
	final public static String FB_PAGE_GLAMSTREET = "https://www.facebook.com/spandan2k14";
	final public static String FB_PAGE_GLAMSTREET_ID = "480104988775604";
	
	final public static String FB_PAGE_NATYAMANCH = "https://www.facebook.com/Natyamanch2k14"; 
	final public static String FB_PAGE_NATYAMANCH_ID = "264334257081195";
	
	final public static String FB_PAGE_CULRAV = "http://www.facebook.com/mnnit.culrav";
	final public static String FB_PAGE_CULARV_ID = "200290116782899";
	
	public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by the
	 * UI and the background service.
	 * 
	 * @param context
	 *            application's context.
	 * @param message
	 *            message to be displayed.
	 */
	static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}

	static void displayMessageFromIntent(Context context, Intent intent) {
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Set<String> keys = bundle.keySet();
				if (keys != null) {
					for (String key : keys) {
						Object o = bundle.get(key);
						if (o != null) {
							displayMessage(context, "Key: " + key + " value: "
									+ o);
						}
					}
				}
			} else {
				displayMessage(context, "Extras are null");
			}
		} else {
			displayMessage(context, "Intent is null");
		}
	}

	/************************
	 * Language helpers
	 */

	/**
	 * Simple helper that gets the location criteria that we want.
	 * 
	 * @return
	 */
	public static Criteria getCriteria() {
		if (USE_LOCATION) {
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_COARSE);
			criteria.setPowerRequirement(Criteria.POWER_LOW);
			criteria.setAltitudeRequired(false);
			criteria.setBearingRequired(false);
			criteria.setSpeedRequired(false);
			criteria.setCostAllowed(true);

			return criteria;
		}
		return null;
	}

	/**
	 * Get the last location from the LocationManager, if it's available, if not
	 * return null.
	 * 
	 * @param appContext
	 * @return
	 */
	public static Location getLastLocation(final Context appContext) {
		Location location = null;
		if (USE_LOCATION) {
			Criteria criteria = getCriteria();
			LocationManager locationManager = (LocationManager) appContext
					.getSystemService(Context.LOCATION_SERVICE);
			if (locationManager != null) {
				String provider = locationManager.getBestProvider(criteria,
						true);
				location = locationManager.getLastKnownLocation(provider);

				if (location != null) {
					displayMessage(appContext, appContext.getString(
							R.string.got_last_location, location.getLatitude(),
							location.getLongitude()));
				}
			}
		}
		return location;
	}

	/**
	 * Get the current location from the location manager, and when we get it
	 * post that information to the Airbop servers
	 * 
	 * @param appContext
	 * @param regId
	 * @return
	 */
	public static boolean getCurrentLocation(LocationListener locationListener,
			final Context appContext) {
		if (USE_LOCATION) {
			Criteria criteria = getCriteria();
			LocationManager locationManager = (LocationManager) appContext
					.getSystemService(Context.LOCATION_SERVICE);
			if (locationManager != null) {
				String provider = locationManager.getBestProvider(criteria,
						true);

				locationManager.requestLocationUpdates(provider, 2000, 10,
						locationListener);
				// We've posted so let the caller know
				return true;
			}
		}
		// We couldn't get the location manager so let the caller know
		return false;
	}
	
	public static FeedbackSettings getFeedBackSettings(){
		FeedbackSettings fbSettings = new FeedbackSettings();
		//SUBMIT-CANCEL BUTTONS
		fbSettings.setCancelButtonText("Cancel");
		fbSettings.setSendButtonText("Send");
		
		fbSettings.setText("Hey, how did you find this app? Love it, hate it? Would you like to give us a feedback so that we can improve your experience?");
		fbSettings.setTitle("Culrav App Feedback");
		
		fbSettings.setYourComments("Feedback...");
		
		fbSettings.setIdeaLabel("Response");
		fbSettings.setGravity(Gravity.CENTER_HORIZONTAL);
		fbSettings.setToast("Thanks for your feedback and time!");
		
		return fbSettings;
	}
	
}
