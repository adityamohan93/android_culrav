package mnnit.vinayakAj.culrav2k14.adapters;

import java.util.ArrayList;

import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.util.NavigationDrawerItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationItemListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<NavigationDrawerItem> navigationDrawerItems;

	public NavigationItemListAdapter(Context context,
			ArrayList<NavigationDrawerItem> navigationDrawerItems) {
		this.context = context;
		this.navigationDrawerItems = navigationDrawerItems;
	}

	@Override
	public int getCount() {
		return navigationDrawerItems.size();
	}

	@Override
	public Object getItem(int item) {
		return navigationDrawerItems.get(item);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.list_item_drawer, null);
		}

		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
		TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

		imgIcon.setImageResource(navigationDrawerItems.get(position).getIcon());
		txtTitle.setText(navigationDrawerItems.get(position).getTitle());

		return convertView;
	}

}
