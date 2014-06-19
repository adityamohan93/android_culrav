package mnnit.vinayakAj.culrav2k14.adapters;

import java.util.ArrayList;

import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.util.Team;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class TeamListAdapter extends BaseAdapter {

	Context context;
	ArrayList<Team> contacts;
	private LayoutInflater mLayoutInflater;

	public TeamListAdapter(Context context, ArrayList<Team> contacts) {
		this.context = context;
		this.contacts = contacts;
	}

	@Override
	public int getCount() {
		return contacts.size();
	}

	@Override
	public Object getItem(int index) {
		return contacts.get(index);
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
			convertView = mLayoutInflater.inflate(R.layout.list_item_contact,
					parent, false);
			
			holder.contactName = (TextView) convertView.findViewById(R.id.contact_name);
			holder.contactPosition = (TextView) convertView
					.findViewById(R.id.contact_position);
			holder.contactMobile = (ImageButton) convertView
					.findViewById(R.id.contact_button_mobile);
			holder.contactEmail = (ImageButton) convertView
					.findViewById(R.id.contact_button_email);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}

		final Team contact = contacts.get(position);
		final String mobile = contact.getContact().getMobileNo();
		final String email = contact.getContact().getEmail();
		

		holder.contactName.setText(contact.getContact().getName());
		holder.contactPosition.setText(contact.getPosition());

		if (contact.getContact().getMobileNo().equals("")) {
			holder.contactMobile.setVisibility(View.GONE);
		} else {
			holder.contactMobile.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String url = "tel:" + mobile;
					Intent callIntent = new Intent(Intent.ACTION_CALL, Uri
							.parse(url));
					context.startActivity(callIntent);
				}

			});
		}

		if (contact.getContact().getEmail().equals("")) {
			holder.contactEmail.setVisibility(View.GONE);
		} else {
			holder.contactEmail.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String url = "mailto:" + email;
					Intent mailIntent = new Intent(Intent.ACTION_SENDTO, Uri
							.parse(url));
					context.startActivity(Intent.createChooser(mailIntent,
							"Open email..."));
				}
			});
		}
		return convertView;
	}
	
	static class ViewHolder{
		TextView contactName, contactPosition;
		ImageButton contactMobile, contactEmail;
	}

}
