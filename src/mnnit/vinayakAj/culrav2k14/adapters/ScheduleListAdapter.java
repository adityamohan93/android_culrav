package mnnit.vinayakAj.culrav2k14.adapters;

import java.util.ArrayList;

import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.util.Schedule;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScheduleListAdapter extends BaseAdapter {

	Context context;
	ArrayList<Schedule> items;
	private LayoutInflater mLayoutInflater;

	public ScheduleListAdapter(Context context, ArrayList<Schedule> schedules) {
		this.context = context;
		this.items = schedules;
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
			convertView = mLayoutInflater.inflate(R.layout.list_item_schedule,
					parent, false);

			holder.time = (TextView) convertView
					.findViewById(R.id.schedule_item_time);
			holder.title = (TextView) convertView
					.findViewById(R.id.schedule_item_title);
			holder.subtitle = (TextView) convertView
					.findViewById(R.id.schedule_item_subtitle);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Schedule schedule = items.get(position);
		holder.time.setText(schedule.getTime());
		holder.title.setText(schedule.getTitle());
		holder.subtitle.setText(schedule.getSubtitle());
		return convertView;
	}

	static class ViewHolder {
		TextView time;
		TextView title;
		TextView subtitle;
	}
}
