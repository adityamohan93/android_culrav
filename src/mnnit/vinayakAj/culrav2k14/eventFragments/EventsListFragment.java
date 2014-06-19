package mnnit.vinayakAj.culrav2k14.eventFragments;

import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.adapters.ExploreEventsTabsAdapter;
import mnnit.vinayakAj.culrav2k14.fragments.FragmentExplore;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class EventsListFragment extends Fragment {

	private String[] events;
	private View fragmentView;
	private ListView listView;
	private onEventSelectedListener mCallback;
	private int color;

	int[] colors = { R.color.holo_green_light, android.R.color.black,
			R.color.holo_blue_light, R.color.holo_purple_light,
			R.color.holo_blue_dark, R.color.holo_orange_dark,
			R.color.holo_pink_dark, R.color.holo_red_light,
			R.color.holo_red_dark, R.color.holo_yellow_dark };

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (onEventSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		events = bundle.getStringArray(ExploreEventsTabsAdapter.EVENTS_LIST);
		color = bundle.getInt(FragmentExplore.COLOR);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.frag_events_list, container,
				false);

		listView = (ListView) fragmentView
				.findViewById(R.id.frag_events_listview);

		return fragmentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		EventListAdapter mAdapter = new EventListAdapter(getActivity(), events,
				color);

		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCallback.onEventSelected(position);
			}

		});
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
	public void onSaveInstanceState(Bundle outState) {
		outState.putStringArray(ExploreEventsTabsAdapter.EVENTS_LIST, events);
		outState.putInt(FragmentExplore.COLOR, color);
		super.onSaveInstanceState(outState);
	}

	public interface onEventSelectedListener {
		public void onEventSelected(int position);
	}

	private class EventListAdapter extends BaseAdapter {

		private String[] events;
		private int color;
		private LayoutInflater mLayoutInflater;
		private Context context;

		public EventListAdapter(Context context, String[] events,
				int colorResource) {
			super();
			this.context = context;
			this.events = events;
			this.color = colorResource;
		}

		@Override
		public int getCount() {
			return events.length;
		}

		@Override
		public Object getItem(int position) {
			return events[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView;
			if (convertView == null) {
				mLayoutInflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = mLayoutInflater.inflate(
						R.layout.list_item_explore, parent, false);

				textView = (TextView) convertView
						.findViewById(R.id.explore_item_title);
				convertView.setTag(textView);
			} else {
				textView = (TextView) convertView.getTag();
			}
			textView.setText(events[position]);
			textView.setTextColor(context.getResources()
					.getColor(colors[color]));
			return convertView;
		}

	}
}
