package mnnit.vinayakAj.culrav2k14.adapters;

import java.util.ArrayList;

import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.util.Contact;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class EventContactListAdapter extends BaseAdapter {

	Context context;
	ArrayList<Contact> contacts;
	private LayoutInflater mLayoutInflater;
	private String[] names;
	private String[] mobiles;
	private String[] emails;

	public EventContactListAdapter(Context context, ArrayList<Contact> contacts) {
		this.context = context;
		contacts = new ArrayList<Contact>();
		for (int i = 0; i < contacts.size(); i++)
			this.contacts.add(contacts.get(i));
	}

	public EventContactListAdapter(Context context, String[] names,
			String[] mobiles, String[] emails) {
		this.context = context;
		this.names = names;
		this.mobiles = mobiles;
		this.emails = emails;
	}

	@Override
	public int getCount() {
		return names.length;
	}

	@Override
	public Object getItem(int index) {
		return names[index];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView name;
		ImageButton mobile, email;

		if (convertView == null) {
			mLayoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mLayoutInflater.inflate(R.layout.frag_events_contact,
					parent, false);
		}

		name = (TextView) convertView
				.findViewById(R.id.frag_events_contact_name);
		mobile = (ImageButton) convertView
				.findViewById(R.id.frag_events_contact_mobile);
		email = (ImageButton) convertView
				.findViewById(R.id.frag_events_contact_email);

		name.setText(names[position]);

		if (mobiles[position].equals("")) {
			mobile.setVisibility(View.GONE);
		} else {
			final int pos = position;
			mobile.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String url = "tel:" + mobiles[pos];
					Intent callIntent = new Intent(Intent.ACTION_CALL, Uri
							.parse(url));
					context.startActivity(callIntent);
				}

			});
		}

		if (emails[position].equals("")) {
			email.setVisibility(View.GONE);
		} else {
			final int pos = position;
			email.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String url = "mailto:" + emails[pos];
					Intent mailIntent = new Intent(Intent.ACTION_SENDTO, Uri
							.parse(url));
					context.startActivity(Intent.createChooser(mailIntent,
							"Open email..."));
				}
			});
		}
		return convertView;
	}

}
