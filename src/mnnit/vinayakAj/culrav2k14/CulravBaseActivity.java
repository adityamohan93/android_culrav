package mnnit.vinayakAj.culrav2k14;

import static mnnit.vinayakAj.culrav2k14.Config.APP_KEY;
import static mnnit.vinayakAj.culrav2k14.Config.APP_SECRET;
import static mnnit.vinayakAj.culrav2k14.Config.GOOGLE_PROJECT_NUMBER;
import static mnnit.vinayakAj.culrav2k14.Config.SERVER_URL;
import static mnnit.vinayakAj.culrav2k14.Config.USE_SERVICE;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gcm.GCMRegistrar;

/**
 * Simply base class
 */
public class CulravBaseActivity extends ActionBarActivity implements
		GCMRegisterTask.RegTaskCompleteListener, LocationListener {

	// 21 days in milliseconds
	public static final long DEFAULT_ON_SERVER_LIFESPAN_MS = 1000L * 3600L * 24L * 14L;

	private GCMRegisterTask mRegisterTask = null;
	private AsyncTask<Void, Void, Void> mUnRegisterTask = null;
	protected GCMServerUtilities mServerData = null;
	private AirBopRegisterReceiver mRegisterReceiver = null;
	protected boolean mServiceRunning = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkNotNull(SERVER_URL, "SERVER_URL");
		checkNotNull(APP_KEY, "APP_KEY");
		checkNotNull(APP_SECRET, "APP_SECRET");
		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		// Set the AirBop registration lifespan to be 21 days. This means that
		// after 14 days
		// this client will attempt to reregister with AirBop.
		// Make sure that this value is positive, otherwise you will run into
		// issues
		GCMRegistrar.setRegisterOnServerLifespan(this,
				DEFAULT_ON_SERVER_LIFESPAN_MS);

		mServerData = GCMServerUtilities.fillDefaults("");
		mServerData.mLabel = "Culrav 2k14";
	}

	protected void register(final boolean withLocation) {

		if (withLocation) {

			Location location = Config.getLastLocation(this);
			if (location == null) {
				// We didn't get the location
				// displayMessage(this, getString(R.string.getting_location));
				// We have to query the Location Manager for the location
				// and get the result in onLocationChanged
				Config.getCurrentLocation(this, this);
			} else {
				// Save the data to the prefs
				mServerData.mLocation = location;
				mServerData.saveCurrentDataToPrefs(getApplicationContext());
				// register
				internalRegister();
			}
		} else {
			// Remove any old location data
			GCMServerUtilities.clearLocationPrefs(getApplicationContext());
			// Save the label if it's there
			mServerData.saveCurrentDataToPrefs(getApplicationContext());
			// register
			internalRegister();
		}
	}
	protected void register() {
			// Remove any old location data
			GCMServerUtilities.clearLocationPrefs(getApplicationContext());
			// Save the label if it's there
			mServerData.saveCurrentDataToPrefs(getApplicationContext());
			// register
			internalRegister();
	}

	private void internalRegister() {
		final Context appContext = getApplicationContext();
		final String regId = GCMRegistrar.getRegistrationId(appContext);
		if (regId.equals("")) {
			// We don't have a REG ID from GCM so let's ask for one.
			// The response from the GCM server will trigger:
			// GCMIntentService.onRegistered() where we will then register with
			// AiRBop's servers.
			// displayMessage(appContext,
			// getString(R.string.gcm_register_attempt));
			GCMRegistrar.register(appContext, GOOGLE_PROJECT_NUMBER);
		} else {

			// We have a regID from the GCM, now check to see if
			// we have successfully registered with AirBop's servers:
			if (GCMRegistrar.isRegisteredOnServer(appContext)) {
				// This device is already registered with GCM and with the
				// AirBop
				// servers, nothing to do here.
				// displayMessage(appContext,getString(R.string.already_registered));

				return;
			} else if (USE_SERVICE) {
				// Register with a service
				internalRegisterService(regId);
			} else if (mRegisterTask == null) {
				// Register with
				internalRegisterAsyncTask(regId);
			} else {
				// displayMessage(appContext,getString(R.string.reg_thread_running));
			}
		}
	}

	private void internalRegisterService(final String regId) {
		if (mServiceRunning == false) {
			// We have previously gotten the regID from the GCM server, but
			// when we tried to register with AirBop in the
			// GCMIntentService.onRegistered() callback it failed, so let's try
			// again

			// This will be done outside of the GUI thread.

			if (mRegisterReceiver == null) {
				// Register receiver
				registerAirBopRegisterReceiver();
			}

			Intent intent = new Intent(this, GCMServerIntentService.class);
			intent.putExtra(GCMServerIntentService.BUNDLE_REG_ID, regId);
			intent.putExtra(GCMServerIntentService.BUNDLE_REGISTRATION_TASK,
					true);

			mServiceRunning = true;
			// Start Service
			startService(intent);

		} else {
			// displayMessage(this, getString(R.string.reg_thread_running));
		}
	}

	private void internalRegisterAsyncTask(final String regId) {
		if (mRegisterTask == null) {

			// We have previously gotten the regID from the GCM server, but
			// when we tried to register with AirBop in the
			// GCMIntentService.onRegistered() callback it failed, so let's try
			// again

			// This will be done outside of the GUI thread.

			// It's also necessary to cancel the thread onDestroy(),
			// hence the use of AsyncTask instead of a raw thread.
			mServerData.mRegId = regId;
			mRegisterTask = new GCMRegisterTask(this, getApplicationContext(),
					regId, mServerData);
			mRegisterTask.execute(null, null, null);

		} else {
			// displayMessage(this, getString(R.string.reg_thread_running));
		}
	}

	public void unRegister() {

		if (USE_SERVICE) {
			// Use the Service
			internalUnRegisterService();
		} else {
			// use the ASYNC TASK
			internalUnRegisterAsyncTask();
		}
	}

	private void internalUnRegisterService() {

		// Use the Service
		if (mServiceRunning == false) {
			final String regId = GCMRegistrar.getRegistrationId(this);
			// Only bother if we actually have a regID from GCM, otherwise
			// there is nothing to unregister
			if (!regId.equals("")) {
				if (mRegisterReceiver == null) {
					// Register receiver
					registerAirBopRegisterReceiver();
				}

				Intent intent = new Intent(this, GCMServerIntentService.class);
				intent.putExtra(GCMServerIntentService.BUNDLE_REG_ID, regId);
				intent.putExtra(
						GCMServerIntentService.BUNDLE_REGISTRATION_TASK, false);

				mServiceRunning = true;

				// Start Service
				startService(intent);
			}
		} else {
			// displayMessage(this, getString(R.string.unreg_thread_running));
		}
	}

	private void internalUnRegisterAsyncTask() {
		// Try to unregister, but not in the UI thread.
		// It's also necessary to cancel the thread onDestroy(),
		// hence the use of AsyncTask instead of a raw thread.
		final Context appContext = getApplicationContext();

		// use the ASYNC TASK
		if (mUnRegisterTask == null) {
			final String regId = GCMRegistrar.getRegistrationId(this);
			// Only bother if we actually have a regID from GCM, otherwise
			// there is nothing to unregister
			if (!regId.equals("")) {
				// final Context context = this;
				mUnRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {

						boolean unregistered = GCMServerUtilities.unregister(
								appContext, regId);
						// If this worked unregister from the GCM servers
						if (unregistered) {
							GCMRegistrar.unregister(appContext);
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mUnRegisterTask = null;
					}

				};
				mUnRegisterTask.execute(null, null, null);
			}
		} else {
			// displayMessage(appContext,
			// getString(R.string.unreg_thread_running));
		}
	}

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		if (mUnRegisterTask != null) {
			mUnRegisterTask.cancel(true);
		}
		unregisterAirBopRegisterReceiver();
		unregisterFromLocationManager();
		GCMRegistrar.onDestroy(getApplicationContext());
		super.onDestroy();
	}

	private void checkNotNull(Object reference, String name) {
		if (reference == null) {
			throw new NullPointerException(getString(R.string.error_config,
					name));
		}
	}

	@Override
	public void onTaskComplete() {
		// We have registered
		mRegisterTask = null;
	}

	private void unregisterFromLocationManager() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (locationManager != null) {
			locationManager.removeUpdates(this);
		}
	}

	@Override
	public void onLocationChanged(Location location) {

		// Unregister from location manager
		unregisterFromLocationManager();

		// displayMessage(this,getString(R.string.got_current_location,location.getLatitude(),
		// location.getLongitude()));

		// Set the location and save the data so that the intent services can
		// read
		// it
		mServerData.mLocation = location;
		mServerData.saveCurrentDataToPrefs(getApplicationContext());
		// register
		internalRegister();

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	public void registerAirBopRegisterReceiver() {

		if (mRegisterReceiver == null) {
			// Receiver
			IntentFilter filter = new IntentFilter(
					GCMServerIntentService.ACTION_REGISTRATION_PROCESSED);

			filter.addCategory(Intent.CATEGORY_DEFAULT);
			mRegisterReceiver = new AirBopRegisterReceiver();
			registerReceiver(mRegisterReceiver, filter);
		}
	}

	protected void unregisterAirBopRegisterReceiver() {
		if (mRegisterReceiver != null) {

			unregisterReceiver(mRegisterReceiver);
			mRegisterReceiver = null;
		}
	}

	public class AirBopRegisterReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			mServiceRunning = false;
			/*
			boolean registration_task = intent.getBooleanExtra(
					GCMServerIntentService.BUNDLE_REGISTRATION_TASK, true);
			if (registration_task) {
				*
				 * displayMessage( context,
				 * getString(R.string.airbop_service_registration_complete));
				 *
			} else {
				*
				 * displayMessage( context,
				 * getString(R.string.airbop_service_unregistration_complete));
				 *
			}
			*/
		}
	}
}
