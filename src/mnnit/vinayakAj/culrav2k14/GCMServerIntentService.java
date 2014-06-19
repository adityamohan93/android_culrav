package mnnit.vinayakAj.culrav2k14;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gcm.GCMRegistrar;

public class GCMServerIntentService extends IntentService {

	public static final String ACTION_REGISTRATION_PROCESSED = "mnnit.vinayakAj.culrav2k14.intent.action.REGISTRATION_PROCESSED";
	public static final String BUNDLE_REG_ID = "REG_ID";
	public static final String BUNDLE_SUCCESS = "SUCCESS";
	public static final String BUNDLE_REGISTRATION_TASK = "REG_TASK";

	public GCMServerIntentService() {
		super("GCMServerIntentService");

	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Context appContext = getApplicationContext();
		boolean registered = false;
		boolean register_task = true;
		if (appContext != null) {
			registered = GCMRegistrar.isRegisteredOnServer(appContext);

			Bundle extras = intent.getExtras();
			String regID = null;

			if (extras != null) {
				regID = extras.getString(BUNDLE_REG_ID);
				register_task = extras.getBoolean(BUNDLE_REGISTRATION_TASK,
						true);
			}

			if (regID != null) {

				if (register_task) {
					if (!registered) {
						GCMServerUtilities server_data = GCMServerUtilities
								.fillDefaults(regID);
						// server_data.loadCurrentLocation(appContext);
						server_data.loadDataFromPrefs(appContext);
						// Get rid of the location from the prefs so we requery
						// next time
						GCMServerUtilities.clearLocationPrefs(appContext);

						registered = GCMServerUtilities.register(appContext,
								server_data);
						// At this point all attempts to register with the
						// AirBop
						// server failed, so we need to unregister the device
						// from GCM - the app will try to register again when
						// it is restarted. Note that GCM will send an
						// unregistered callback upon completion, but
						// GCMIntentService.onUnregistered() will ignore it.
						if (!registered) {
							GCMRegistrar.unregister(appContext);
						}
					}
				} else {
					if (registered) {
						registered = GCMServerUtilities.unregister(appContext,
								regID);
						// If this worked unregister from the GCM servers
						if (registered) {
							GCMRegistrar.unregister(appContext);
						}
					}

				}
			}
		}

		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ACTION_REGISTRATION_PROCESSED);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(BUNDLE_SUCCESS, registered);
		broadcastIntent.putExtra(BUNDLE_REGISTRATION_TASK, register_task);

		sendBroadcast(broadcastIntent);

	}

}
