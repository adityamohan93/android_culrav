package mnnit.vinayakAj.culrav2k14.adapters;

import java.util.ArrayList;
import java.util.Locale;

import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.util.ExploreItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExploreItemListAdapter extends BaseAdapter {

	Context context;
	ArrayList<ExploreItem> exploreItems;
	private LayoutInflater mLayoutInflater;

	public ExploreItemListAdapter(Context context, ArrayList<ExploreItem> items) {
		this.context = context;
		exploreItems = items;
	}

	@Override
	public int getCount() {
		return exploreItems.size();
	}

	@Override
	public Object getItem(int index) {
		return exploreItems.get(index);
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
			convertView = mLayoutInflater.inflate(R.layout.list_item_explore,
					parent, false);

			textView = (TextView) convertView
					.findViewById(R.id.explore_item_title);
			convertView.setTag(textView);
		} else {
			textView = (TextView) convertView.getTag();
		}
		textView.setText(exploreItems.get(position).getEvent()
				.toUpperCase(Locale.getDefault()));

		return convertView;
	}

}
