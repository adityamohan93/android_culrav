package mnnit.vinayakAj.culrav2k14.eventFragments;

import mnnit.vinayakAj.culrav2k14.ExploreEventsActivity;
import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.Loaders.EventInformalsLoader;
import mnnit.vinayakAj.culrav2k14.util.EventInformals;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EventFragmentInformals extends Fragment implements LoaderCallbacks<EventInformals>{

	private View fragmentView;
	private int position;
	private TextView eventHeader;
	private TextView description;
	private TextView format;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		position = bundle.getInt(ExploreEventsActivity.EVENT_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.frag_event_informals, container,
				false);
		eventHeader = (TextView) fragmentView
				.findViewById(R.id.event_informals_header);
		description = (TextView) fragmentView
				.findViewById(R.id.event_informals_description);
		format = (TextView)fragmentView.findViewById(R.id.event_informals_format_description);
		return fragmentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getLoaderManager().initLoader(1001, null, this).forceLoad();
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	@Override
	public void onPause() {
		super.onPause();
		getActivity().overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}
	
	@Override
	public Loader<EventInformals> onCreateLoader(int arg0, Bundle arg1) {
		return new EventInformalsLoader(getActivity(), position);
	}

	@Override
	public void onLoadFinished(Loader<EventInformals> arg0, EventInformals event) { 
		eventHeader.setText(event.getEvent());
		description.setText(event.getDescription());
		format.setText(event.getFormat());
	}

	@Override
	public void onLoaderReset(Loader<EventInformals> arg0) {		
	}

}
