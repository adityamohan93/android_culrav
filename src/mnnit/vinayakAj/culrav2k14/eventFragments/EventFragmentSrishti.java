package mnnit.vinayakAj.culrav2k14.eventFragments;

import mnnit.vinayakAj.culrav2k14.ExploreEventsActivity;
import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.Loaders.EventSrishtiLoader;
import mnnit.vinayakAj.culrav2k14.util.EventSrishti;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EventFragmentSrishti extends Fragment implements
		LoaderCallbacks<EventSrishti> {

	private View fragmentView;
	private int position;
	private TextView eventHeader;
	private TextView description;
	private TextView rounds;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		position = bundle.getInt(ExploreEventsActivity.EVENT_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.frag_event_srishti, container,
				false);
		eventHeader = (TextView) fragmentView
				.findViewById(R.id.event_srishti_header);
		description = (TextView) fragmentView
				.findViewById(R.id.event_srishti_description);
		rounds = (TextView) fragmentView
				.findViewById(R.id.event_srishti_rounds_description);

		return fragmentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getLoaderManager().initLoader(0101, null, this).forceLoad();
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
	public Loader<EventSrishti> onCreateLoader(int arg0, Bundle arg1) {
		return new EventSrishtiLoader(getActivity(), position);
	}

	@Override
	public void onLoadFinished(Loader<EventSrishti> arg0, EventSrishti event) {
		eventHeader.setText(event.getEvent());
		description.setText(event.getDescription());

		String[] roundsStr = event.getRounds();
		int roundsLen = roundsStr.length;
		for (int i = 0; i < roundsLen; i++)
			rounds.append(roundsStr[i] + "\n");
	}

	@Override
	public void onLoaderReset(Loader<EventSrishti> arg0) {
	}
}
