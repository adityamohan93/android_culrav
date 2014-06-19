package mnnit.vinayakAj.culrav2k14.adapters;

import java.util.ArrayList;

import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.StreamMessageActivity;
import mnnit.vinayakAj.culrav2k14.util.Stream;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StreamListAdapter extends BaseAdapter {

	final public static String STREAM_TITLE = "mnnit.vinayakAj.culrav2k14.stream_title";
	final public static String STREAM_MESSAGE = "mnnit.vinayakAj.culrav2k14.stream_message";

	Context context;
	ArrayList<Stream> items;
	private LayoutInflater mLayoutInflater;

	public StreamListAdapter(Context context, ArrayList<Stream> streamItems) {
		this.context = context;
		this.items = streamItems;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
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
			convertView = mLayoutInflater.inflate(R.layout.list_item_stream,
					parent, false);

			holder.title = (TextView) convertView
					.findViewById(R.id.stream_item_title);
			holder.subtitle = (TextView) convertView
					.findViewById(R.id.stream_item_subtitle);
			holder.arrow = (ImageView) convertView
					.findViewById(R.id.stream_detail);
			holder.clicker = (LinearLayout) convertView
					.findViewById(R.id.stream_item_clickable);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Stream item = items.get(position);

		holder.title.setText(item.getTitle());
		holder.subtitle.setText(item.getSubtitle());
		if (item.getMessage().equals("")) {
			holder.arrow.setVisibility(View.GONE);
		} else {
			holder.clicker.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context,
							StreamMessageActivity.class);
					intent.putExtra(STREAM_TITLE, item.getTitle());
					intent.putExtra(STREAM_MESSAGE, item.getMessage());

					context.startActivity(intent);
				}
			});
		}
		return convertView;
	}

	static class ViewHolder {
		LinearLayout clicker;
		TextView title;
		TextView subtitle;
		ImageView arrow;
	}

}
