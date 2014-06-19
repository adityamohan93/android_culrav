package mnnit.vinayakAj.culrav2k14.eventFragments;

import mnnit.vinayakAj.culrav2k14.ExploreEventsActivity;
import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.Loaders.EventAnunaadLoader;
import mnnit.vinayakAj.culrav2k14.util.EventAnunaad;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EventFragmentAnunaad extends Fragment implements
		LoaderCallbacks<EventAnunaad> {

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
		fragmentView = inflater.inflate(R.layout.frag_event_anunaad, container,
				false);
		eventHeader = (TextView) fragmentView
				.findViewById(R.id.event_anunaad_header);
		description = (TextView) fragmentView
				.findViewById(R.id.event_anunaad_description);
		format = (TextView) fragmentView
				.findViewById(R.id.event_anunaad_format_description);
		rules = (TextView) fragmentView
				.findViewById(R.id.event_anunaad_rules_description);

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
	public Loader<EventAnunaad> onCreateLoader(int arg0, Bundle arg1) {
		return new EventAnunaadLoader(getActivity(), position);
	}

	@Override
	public void onLoadFinished(Loader<EventAnunaad> arg0, EventAnunaad event) {
		eventHeader.setText(event.getEvent());
		description.setText(event.getDescription() + "\n");

		String[] rulesStr = event.getRules();
		int rulesLen = rulesStr.length;
		for (int i = 0; i < rulesLen; i++)
			rules.append(rulesStr[i] + "\n");

		String[] formatStr = event.getFormat();
		int formatLen = formatStr.length;
		for (int i = 0; i < formatLen; i++)
			format.append(formatStr[i] + "\n");
	}

	@Override
	public void onLoaderReset(Loader<EventAnunaad> arg0) {
	}
}
