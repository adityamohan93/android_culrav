package mnnit.vinayakAj.culrav2k14.fragments;

import java.util.ArrayList;

import mnnit.vinayakAj.culrav2k14.ExploreEventsActivity;
import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.Loaders.CategoryListLoader;
import mnnit.vinayakAj.culrav2k14.adapters.CategoryListAdapter;
import mnnit.vinayakAj.culrav2k14.util.Category;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class FragmentExplore extends Fragment implements
		LoaderManager.LoaderCallbacks<ArrayList<Category>> {
	public final static String CATEGORY = "mnnit.vinayakAj.culrav2k14.EXPLORE";
	public final static String EVENTS = "mnnit.vinayakAj.culrav2k14.EXPLORE_EVENTS";
	public final static String COLOR = "mnnit.vinayakAj.culrav2k14.COLOR";
	public final static String CONTACTS_NAME = "mnnit.vinayakAj.culrav2k14.CONTACTS_NAME";
	public final static String CONTACTS_MOBILE = "mnnit.vinayakAj.culrav2k14.CONTACTS_MOBILE";
	public final static String CONTACTS_EMAIL = "mnnit.vinayakAj.culrav2k14.CONTACTS_EMAIL";
	public final static String CATEGORY_ICON = "mnnit.vinayakAj.culrav2k14.CATEGORY_ICON";
	private View fragmentView;
	private ListView listView;
	private ArrayList<Category> categories;
	private TypedArray exploreListIcons;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.fragment_explore, container,
				false);
		listView = (ListView) fragmentView.findViewById(R.id.frag_explore_list);

		return fragmentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getLoaderManager().initLoader(3110, null, this).forceLoad();
		exploreListIcons = getActivity().getResources().obtainTypedArray(
				R.array.explore_list_icons);
	}

	@Override
	public Loader<ArrayList<Category>> onCreateLoader(int i, Bundle bundle) {
		return new CategoryListLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Category>> loader,
			ArrayList<Category> exploreItems) {
		categories = exploreItems;
		listView.setAdapter(new CategoryListAdapter(getActivity(), exploreItems));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(getActivity(),
						ExploreEventsActivity.class);
				Category category = categories.get(position);

				intent.putExtra(CATEGORY, category.getTitle())
						.putExtra(COLOR, position)
						.putExtra(EVENTS, category.getEvents())
						.putExtra(CONTACTS_NAME, category.getContactsNames())
						.putExtra(CONTACTS_MOBILE,
								category.getContactsMobileNos())
						.putExtra(CONTACTS_EMAIL, category.getContactsEmails())
						.putExtra(CATEGORY_ICON,
								exploreListIcons.getResourceId(position, 0));
				exploreListIcons.recycle();
				startActivity(intent);
			}

		});
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Category>> arg0) {
	}

}
