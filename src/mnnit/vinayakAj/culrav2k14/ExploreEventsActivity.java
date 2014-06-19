package mnnit.vinayakAj.culrav2k14;

import mnnit.vinayakAj.culrav2k14.adapters.ExploreEventsTabsAdapter;
import mnnit.vinayakAj.culrav2k14.eventFragments.EventsListFragment.onEventSelectedListener;
import mnnit.vinayakAj.culrav2k14.fragments.FragmentExplore;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class ExploreEventsActivity extends ActionBarActivity implements
		onEventSelectedListener {

	final public static String EVENT_POSITION = "mnnit.vinayakAj.culrav2k14.exploreEventsActivity.EVENT_POSITION";

	Intent receivedIntent;
	ListView listView;
	String[] events;
	String jsonFilePath;
	String category;
	private ViewPager mViewPager;
	private ExploreEventsTabsAdapter mTabsAdapter;
	private ActionBar actionBar;
	private int color;
	private String[] contactsNames;
	private String[] contactsMobiles;
	private String[] contactsEmails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explore_events);

		prepareFields();
		setupTabs();
		setupActionBar();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void prepareFields() {
		receivedIntent = getIntent();
		category = receivedIntent.getStringExtra(FragmentExplore.CATEGORY);
		color = receivedIntent.getIntExtra(FragmentExplore.COLOR, 0);
		events = receivedIntent.getStringArrayExtra(FragmentExplore.EVENTS);
		contactsNames = receivedIntent
				.getStringArrayExtra(FragmentExplore.CONTACTS_NAME);
		contactsMobiles = receivedIntent
				.getStringArrayExtra(FragmentExplore.CONTACTS_MOBILE);
		contactsEmails = receivedIntent
				.getStringArrayExtra(FragmentExplore.CONTACTS_EMAIL);

		SharedPreferences sharePref = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = sharePref.edit();
		editor.putInt(Config.PAGER_POSITION, 1);
		editor.commit();
	}

	private void setupTabs() {
		actionBar = getSupportActionBar();
		mViewPager = (ViewPager) findViewById(R.id.act_explore_events_pager);

		mTabsAdapter = new ExploreEventsTabsAdapter(this,
				getSupportFragmentManager(), color, events, contactsNames,
				contactsMobiles, contactsEmails);

		mViewPager.setAdapter(mTabsAdapter);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
				mViewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(ActionBar.Tab tab,
					FragmentTransaction ft) {
			}

			@Override
			public void onTabReselected(ActionBar.Tab tab,
					FragmentTransaction ft) {
			}

		};

		actionBar.addTab(actionBar.newTab().setText("Events")
				.setTabListener(tabListener));

		actionBar.addTab(actionBar.newTab().setText("Contacts")
				.setTabListener(tabListener));

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

	}

	private void setupActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(category);
		actionBar.setIcon(receivedIntent.getIntExtra(
				FragmentExplore.CATEGORY_ICON, R.drawable.ic_launcher));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.explore_events, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onEventSelected(int position) {

		Intent intent = new Intent(this, EventDetailsActivity.class);
		intent.putExtra(FragmentExplore.CATEGORY, category)
				.putExtra(
						FragmentExplore.CATEGORY_ICON,
						receivedIntent.getIntExtra(
								FragmentExplore.CATEGORY_ICON,
								R.drawable.ic_launcher))
				.putExtra(EVENT_POSITION, position);
		startActivity(intent);
	}

}
