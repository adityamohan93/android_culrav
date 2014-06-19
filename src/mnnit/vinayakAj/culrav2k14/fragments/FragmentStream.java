package mnnit.vinayakAj.culrav2k14.fragments;

import java.util.ArrayList;

import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.Loaders.StreamItemsLoader;
import mnnit.vinayakAj.culrav2k14.adapters.StreamListAdapter;
import mnnit.vinayakAj.culrav2k14.util.Stream;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentStream extends Fragment implements
		LoaderCallbacks<ArrayList<Stream>> {

	private View fragmentView;
	private ListView listView;
	private TextView emptyText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.fragment_stream, container,
				false);
		listView = (ListView) fragmentView
				.findViewById(R.id.fragment_stream_list);
		emptyText = (TextView) fragmentView
				.findViewById(R.id.fragment_stream_empty_text);
		getLoaderManager().initLoader(0, null, this).forceLoad();
		return fragmentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		if (getLoaderManager().getLoader(0) == null) {
			getLoaderManager().initLoader(0, null, this).forceLoad();
		}
		//listView.setTransitionEffect(JazzyHelper.ZIPPER);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (getLoaderManager().getLoader(0) == null) {
			getLoaderManager().initLoader(0, null, this).forceLoad();
		}
	}

	@Override
	public Loader<ArrayList<Stream>> onCreateLoader(int arg0, Bundle arg1) {
		return new StreamItemsLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Stream>> loader,
			ArrayList<Stream> items) {
		if (items != null) {
			emptyText.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			listView.setAdapter(new StreamListAdapter(getActivity(), items));
		}
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Stream>> arg0) {
	}
}
