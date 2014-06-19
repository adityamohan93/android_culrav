package mnnit.vinayakAj.culrav2k14.adapters;

import java.util.ArrayList;

import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.util.Category;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryListAdapter extends BaseAdapter {

	Context context;
	ArrayList<Category> categories;
	private LayoutInflater mLayoutInflater;
	private TypedArray exploreListIcons;

	public CategoryListAdapter(Context context, ArrayList<Category> items) {
		this.context = context;
		categories = items;

		exploreListIcons = context.getResources().obtainTypedArray(
				R.array.explore_list_icons);
	}

	@Override
	public int getCount() {
		return categories.size();
	}

	@Override
	public Object getItem(int index) {
		return categories.get(index);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			mLayoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mLayoutInflater.inflate(R.layout.list_item_category,
					parent, false);
			holder.categoryView = (TextView) convertView
					.findViewById(R.id.category_title);
			holder.exploreIcon = (ImageView) convertView
					.findViewById(R.id.category_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Category category = categories.get(position);
		holder.categoryView.setText(category.getTitle());
		holder.exploreIcon.setImageResource(exploreListIcons.getResourceId(
				position, 0));

		exploreListIcons.recycle();
		return convertView;
	}

	static class ViewHolder {
		TextView categoryView;
		ImageView exploreIcon;
	}

}
