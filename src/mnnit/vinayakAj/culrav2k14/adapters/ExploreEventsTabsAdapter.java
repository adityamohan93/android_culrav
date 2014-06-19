package mnnit.vinayakAj.culrav2k14.adapters;

import mnnit.vinayakAj.culrav2k14.eventFragments.EventsContactsFragment;
import mnnit.vinayakAj.culrav2k14.eventFragments.EventsListFragment;
import mnnit.vinayakAj.culrav2k14.fragments.FragmentExplore;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ExploreEventsTabsAdapter extends FragmentPagerAdapter {

	private final int totalTabs = 2;
	private String[] eventsList;
	private String[] contactNames;
	private String[] contactMobiles;
	private String[] contactEmails;
	private int color;
	public final static String EVENTS_LIST = "exploreEventsAdapter.EVENTS_LIST";

	public ExploreEventsTabsAdapter(Context context, FragmentManager fm,
			int color, String[] events, String[] contactNames,
			String[] contactMobiles, String[] contactEmails) {
		super(fm);
		this.color = color;
		eventsList = events;
		this.contactNames = contactNames;
		this.contactMobiles = contactMobiles;
		this.contactEmails = contactEmails;
	}

	@Override
	public Fragment getItem(int index) {
		Fragment fragment;
		Bundle args = new Bundle();
		if (index == 0) {
			args.putStringArray(EVENTS_LIST, eventsList);
			args.putInt(FragmentExplore.COLOR, color);
			fragment = new EventsListFragment();
			fragment.setArguments(args);
		} else {
			args.putStringArray(FragmentExplore.CONTACTS_NAME, contactNames);
			args.putStringArray(FragmentExplore.CONTACTS_MOBILE, contactMobiles);
			args.putStringArray(FragmentExplore.CONTACTS_EMAIL, contactEmails);
			fragment = new EventsContactsFragment();
			fragment.setArguments(args);
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return totalTabs;
	}

}
