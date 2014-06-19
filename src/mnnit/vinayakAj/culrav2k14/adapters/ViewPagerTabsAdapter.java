package mnnit.vinayakAj.culrav2k14.adapters;

import java.util.Locale;

import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.fragments.FragmentExplore;
import mnnit.vinayakAj.culrav2k14.fragments.FragmentNites;
import mnnit.vinayakAj.culrav2k14.fragments.FragmentSchedule;
import mnnit.vinayakAj.culrav2k14.fragments.FragmentStream;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerTabsAdapter extends FragmentPagerAdapter {

	private int totalTabs = 4;
	private String tabTitles[];

	public ViewPagerTabsAdapter(Context context, FragmentManager fm) {
		super(fm);
		tabTitles = context.getResources().getStringArray(R.array.title_tabs);
	}

	public ViewPagerTabsAdapter(Context context, FragmentManager fm,
			int noOfTabs) {
		super(fm);
		tabTitles = context.getResources().getStringArray(R.array.title_tabs);
		totalTabs = noOfTabs;
	}

	@Override
	public int getCount() {
		return totalTabs;
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0: // Live stream
			return new FragmentStream();
		case 1: // Explore
			return new FragmentExplore();
		case 2: // Nite
			return new FragmentNites();
		case 3: // Schedule
			return new FragmentSchedule();
		}
		return null;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale local = Locale.getDefault();
		return tabTitles[position].toUpperCase(local);
	}

}
