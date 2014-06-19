package mnnit.vinayakAj.culrav2k14.fragments;

import java.util.ArrayList;

import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.Loaders.ScheduleLoader;
import mnnit.vinayakAj.culrav2k14.adapters.ScheduleListAdapter;
import mnnit.vinayakAj.culrav2k14.animations.JazzyHelper;
import mnnit.vinayakAj.culrav2k14.animations.JazzyListView;
import mnnit.vinayakAj.culrav2k14.util.Schedule;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentSchedule extends Fragment implements
		LoaderManager.LoaderCallbacks<ArrayList<Schedule>> {

	private View fragmentView;
	private JazzyListView listView;
	private TextView emptyText;
	private ArrayList<Schedule> scheduleItems;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		getLoaderManager().initLoader(0, null, this).forceLoad();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.fragment_schedule, container,
				false);
		listView = (JazzyListView) fragmentView
				.findViewById(R.id.fragment_schedule_list);
		emptyText = (TextView) fragmentView
				.findViewById(R.id.fragment_schedule_empty);
		if (getLoaderManager().getLoader(0) == null) {
			getLoaderManager().initLoader(0, null, this).forceLoad();
		}
		return fragmentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (getLoaderManager().getLoader(0) == null) {
			getLoaderManager().initLoader(0, null, this).forceLoad();
		}
		listView.setTransitionEffect(JazzyHelper.GROW);
	}

	@Override
	public Loader<ArrayList<Schedule>> onCreateLoader(int i, Bundle bundle) {
		return new ScheduleLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Schedule>> loader,
			ArrayList<Schedule> items) {
		scheduleItems = items;
		if (scheduleItems != null && listView.getVisibility() == View.GONE) {
			emptyText.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			listView.setAdapter(new ScheduleListAdapter(getActivity(),
					scheduleItems));
		}
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Schedule>> arg0) {
	}

}