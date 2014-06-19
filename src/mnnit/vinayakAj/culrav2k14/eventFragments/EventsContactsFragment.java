package mnnit.vinayakAj.culrav2k14.eventFragments;

import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.adapters.EventContactListAdapter;
import mnnit.vinayakAj.culrav2k14.fragments.FragmentExplore;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class EventsContactsFragment extends Fragment {

	private View fragmentView;
	private String[] names;
	private ListView listView;
	private String[] mobiles;
	private String[] emails;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		names = bundle.getStringArray(FragmentExplore.CONTACTS_NAME);
		mobiles = bundle.getStringArray(FragmentExplore.CONTACTS_MOBILE);
		emails = bundle.getStringArray(FragmentExplore.CONTACTS_EMAIL);
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
		listView.setAdapter(new EventContactListAdapter(getActivity(), names,
				mobiles, emails));
	}
}
