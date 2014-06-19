package mnnit.vinayakAj.culrav2k14;

import java.lang.ref.WeakReference;

import com.google.android.gcm.GCMRegistrar;

import android.content.Context;
import android.os.AsyncTask;

public class GCMRegisterTask extends AsyncTask<Void, Void, Void> {

	private WeakReference<RegTaskCompleteListener> mListener;
	private Context mAppContext = null;
	private GCMServerUtilities mServerData = null;

	// private String mRegId;

	public GCMRegisterTask(RegTaskCompleteListener listener,
			final Context appContext, final String regId,
			GCMServerUtilities server_data) {

		mAppContext = appContext;
		// mRegId = regId;
		mServerData = server_data;
		mServerData.mRegId = regId;
		mListener = new WeakReference<RegTaskCompleteListener>(listener);
	}

	@Override
	protected Void doInBackground(Void... params) {
		boolean registered = GCMServerUtilities.register(mAppContext,
				mServerData);
		// At this point all attempts to register with the AirBop
		// server failed, so we need to unregister the device
		// from GCM - the app will try to register again when
		// it is restarted. Note that GCM will send an
		// unregistered callback upon completion, but
		// GCMIntentService.onUnregistered() will ignore it.
		if (!registered) {
			GCMRegistrar.unregister(mAppContext);
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {

		if (mListener != null) {
			RegTaskCompleteListener listener = mListener.get();
			if (listener != null) {
				listener.onTaskComplete();
			}
		}
	}

	/**
	 * Defines an interface for a callback that will be called when the task is
	 * done
	 */
	public interface RegTaskCompleteListener {
		public void onTaskComplete();

	}
}
