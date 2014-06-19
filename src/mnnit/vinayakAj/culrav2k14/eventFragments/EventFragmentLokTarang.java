package mnnit.vinayakAj.culrav2k14.eventFragments;

import mnnit.vinayakAj.culrav2k14.ExploreEventsActivity;
import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.Loaders.EventLokTarangLoader;
import mnnit.vinayakAj.culrav2k14.util.EventLokTarang;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EventFragmentLokTarang extends Fragment implements
		LoaderCallbacks<EventLokTarang> {

	private View fragmentView;
	private int position;
	private TextView eventHeader;
	private TextView description;
	private TextView format;
	private TextView judging;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		position = bundle.getInt(ExploreEventsActivity.EVENT_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.frag_event_loktarang,
				container, false);
		eventHeader = (TextView) fragmentView
				.findViewById(R.id.event_loktarang_header);
		description = (TextView) fragmentView
				.findViewById(R.id.event_loktarang_description);
		format = (TextView) fragmentView
				.findViewById(R.id.event_loktarang_format_description);
		judging = (TextView) fragmentView
				.findViewById(R.id.event_loktarang_judging_description);

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
	public Loader<EventLokTarang> onCreateLoader(int arg0, Bundle arg1) {
		return new EventLokTarangLoader(getActivity(), position);
	}

	@Override
	public void onLoadFinished(Loader<EventLokTarang> arg0, EventLokTarang event) {
		eventHeader.setText(event.getEvent());
		description.setText(event.getDescription());

		String[] formatStr = event.getFormat();
		int formatLen = formatStr.length;
		for (int i = 0; i < formatLen; i++)
			format.append(formatStr[i]);

		String[] judgingStr = event.getJudging();
		int judgingLen = judgingStr.length;
		if (judgingLen == 1) {
			((TextView) fragmentView
					.findViewById(R.id.event_loktarang_header_judging))
					.setVisibility(View.GONE);
			((TextView) fragmentView
					.findViewById(R.id.event_loktarang_header_judging_underline))
					.setVisibility(View.GONE);
		} else {
			for (int i = 0; i < judgingLen; i++)
				judging.append(judgingStr[i]);
		}

	}

	@Override
	public void onLoaderReset(Loader<EventLokTarang> arg0) {
	}

}
