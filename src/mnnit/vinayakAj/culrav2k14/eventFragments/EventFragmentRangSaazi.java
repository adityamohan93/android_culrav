package mnnit.vinayakAj.culrav2k14.eventFragments;

import mnnit.vinayakAj.culrav2k14.ExploreEventsActivity;
import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.Loaders.EventRangSaaziLoader;
import mnnit.vinayakAj.culrav2k14.util.EventRangSaazi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EventFragmentRangSaazi extends Fragment implements
		LoaderCallbacks<EventRangSaazi> {

	private View fragmentView;
	private int position;
	private TextView eventHeader;
	private TextView description;
	private TextView format;
	private TextView rules;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		position = bundle.getInt(ExploreEventsActivity.EVENT_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.frag_event_rangsaazi,
				container, false);
		eventHeader = (TextView) fragmentView
				.findViewById(R.id.event_rangraaze_header);
		description = (TextView) fragmentView
				.findViewById(R.id.event_rangraaze_description);
		format = (TextView) fragmentView
				.findViewById(R.id.event_rangraaze_format_description);
		rules = (TextView) fragmentView
				.findViewById(R.id.event_rangraaze_rules_description);

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
	public Loader<EventRangSaazi> onCreateLoader(int arg0, Bundle arg1) {
		return new EventRangSaaziLoader(getActivity(), position);
	}

	@Override
	public void onLoadFinished(Loader<EventRangSaazi> arg0, EventRangSaazi event) {
		eventHeader.setText(event.getEvent());
		description.setText(event.getDescription());

		String[] rulesStr = event.getRules();
		int rulesLen = rulesStr.length;
		for (int i = 0; i < rulesLen; i++)
			rules.append(rulesStr[i] + "\n");

		format.setText(event.getFormat());

	}

	@Override
	public void onLoaderReset(Loader<EventRangSaazi> arg0) {
	}
}
