package mnnit.vinayakAj.culrav2k14;

import static mnnit.vinayakAj.culrav2k14.Config.DISPLAY_MESSAGE_ACTION;
import static mnnit.vinayakAj.culrav2k14.Config.EXTRA_MESSAGE;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mnnit.vinayakAj.culrav2k14.adapters.NavigationItemListAdapter;
import mnnit.vinayakAj.culrav2k14.adapters.ViewPagerTabsAdapter;
import mnnit.vinayakAj.culrav2k14.animations.DepthPageTransformer;
import mnnit.vinayakAj.culrav2k14.animations.ZoomOutPageTransformer;
import mnnit.vinayakAj.culrav2k14.util.NavigationDrawerItem;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.suredigit.inappfeedback.FeedbackDialog;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class HomeUI extends CulravBaseActivity {

	Context context = this;
	ActionBar actionBar;

	// Navigation Drawer fields
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private boolean drawerOpen;
	private ArrayList<NavigationDrawerItem> naviagtionDrawerItems;

	// ViewPager Tab fields
	ViewPager mViewPager;
	PagerTabStrip tabsRibbon;
	private ViewPagerTabsAdapter mTabsAdapter;

	// ViewPager ribbon colors fields
	private static Random rand = new Random();

	int tabColors[] = { R.color.holo_blue_light, R.color.holo_purple_light,
			R.color.holo_green_light, R.color.holo_orange_light,
			R.color.holo_red_light };
	int tabIndicatorColors[] = { R.color.holo_blue_dark,
			R.color.holo_purple_dark, R.color.holo_green_dark,
			R.color.holo_orange_dark, R.color.holo_red_dark };
	boolean tabColorsSet[] = { false, false, false, false, false };
	int totalColors = 5;
	int curCol;

	private FeedbackDialog feedBack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_ui);

		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);

		actionBar = getSupportActionBar();

		setupNavigationDrawer();

		setupViewPagerTabs();

		registerReceiver(mHandleMessageReceiver, new IntentFilter(
					DISPLAY_MESSAGE_ACTION));
		registerForGCM();		
		feedBack = new FeedbackDialog(this, "AF_EA6EF4AC032A-23", Config.getFeedBackSettings());		
	}

	private void registerForGCM() {
		if (checkPlayServices()) {			
			// Call the register function in the AirBopActivity
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					Crouton.makeText(getParent(), "Register attempt", Style.INFO).show();
					register();
					return null;
				}
			}.execute(null, null, null);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		checkPlayServices();
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		feedBack.dismiss();
	}

	private boolean checkPlayServices() {
		Crouton.makeText(getParent(), "Checking Play services", Style.INFO).show();
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						Config.PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Crouton.makeText(this, R.string.gcm_unsupported, Style.ALERT)
						.show();
				Crouton.makeText(this, R.string.gcm_unsupported_cntd,
						Style.ALERT).show();
			}
			return false;
		}
		return true;
	}

	private void setupNavigationDrawer() {
		// get menu titles
		navMenuTitles = getResources()
				.getStringArray(R.array.nav_drawer_titles);

		// get menu icons
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		// initially drawer is closed
		drawerOpen = false;

		// reference the drawer layout
		mDrawerLayout = (DrawerLayout) findViewById(R.id.home_drawer_layout);
		// reference the menu item format
		mDrawerList = (ListView) findViewById(R.id.home_left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		naviagtionDrawerItems = new ArrayList<NavigationDrawerItem>();
		// adding navigation menu items to array
		for (int i = 0; i < navMenuTitles.length; i++)
			naviagtionDrawerItems.add(new NavigationDrawerItem(
					navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));

		navMenuIcons.recycle();

		mDrawerList.setAdapter(new NavigationItemListAdapter(context,
				naviagtionDrawerItems));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_navigation_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			@Override
			public void onDrawerClosed(View view) {
				supportInvalidateOptionsMenu(); // creates call to
				drawerOpen = false; // onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely open state. */
			@Override
			public void onDrawerOpened(View drawerView) {
				supportInvalidateOptionsMenu(); // creates call to
				drawerOpen = true; // onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

	}

	// Navigation Drawer settings
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			mDrawerList.setItemChecked(position, true);
			mDrawerLayout.closeDrawers();
			selectAction(position);
		}
	}

	private void setupViewPagerTabs() {
		mViewPager = (ViewPager) findViewById(R.id.pager);

		mTabsAdapter = new ViewPagerTabsAdapter(context,
				getSupportFragmentManager());

		mViewPager.setAdapter(mTabsAdapter);

		tabsRibbon = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
		// initialize a view pager tab color
		nextColor();
		tabsRibbon.setBackgroundColor(getResources()
				.getColor(tabColors[curCol]));
		tabsRibbon.setTabIndicatorColor(getResources().getColor(
				tabIndicatorColors[curCol]));

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				nextColor();
				tabsRibbon.setBackgroundColor(getResources().getColor(
						tabColors[curCol]));
				tabsRibbon.setTabIndicatorColor(getResources().getColor(
						tabIndicatorColors[curCol]));
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		if (randInt(0, 2) == 0) {
			mViewPager.setPageTransformer(true, new DepthPageTransformer());
		} else {
			mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
		}
		SharedPreferences sharePref = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		int pos = sharePref.getInt(Config.PAGER_POSITION, 1);
		mViewPager.setCurrentItem(pos);
		SharedPreferences.Editor editor = sharePref.edit();
		editor.putInt(Config.PAGER_POSITION, 1);
		editor.commit();
	}

	private static int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min)) + min;
		return randomNum;
	}

	int colorId;

	void nextColor() {
		do {
			colorId = randInt(0, totalColors);
		} while (colorId == curCol);
		curCol = colorId;
	}

	public void selectAction(int position) {
		switch (position) {
		case 0:
			break;
		case 1:
			openWebsite();
			break;
		case 2:
			openFb();
			break;
		case 3:
			openMap();
			break;
		case 4:
			showContacts();
			break;
		case 5:
			showAbout();
			break;
		}
	}

	private void openWebsite() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(Config.WEB_PAGE));
		startActivity(Intent.createChooser(intent, "Load with"));
	}

	private void openFb() {
		final String urlFb = "fb://page/" + Config.FB_PAGE_CULARV_ID;
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(urlFb));

		// If Facebook application is installed, use that else launch a browser
		final PackageManager packageManager = getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		if (list.size() == 0) {
			intent.setData(Uri.parse(Config.FB_PAGE_CULRAV));
			startActivity(Intent.createChooser(intent, "Load with"));
		} else {
			startActivity(intent);
		}
	}

	private void openMap() {
		Intent intent = new Intent(context, MapActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private void showContacts() {
		Intent intent = new Intent(context, ContactsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private void showAbout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();
		View layoutView = inflater.inflate(R.layout.dialog_about, null);

		builder.setView(layoutView).setNeutralButton("Feedback",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						feedBack.show();
					}
				});
		final AlertDialog dialog = builder.create();

		dialog.show();

		TextView mohan = (TextView) layoutView
				.findViewById(R.id.dialog_person_mohan);
		mohan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				String url = "tel:+919935786440";
				Intent callIntent = new Intent(Intent.ACTION_CALL, Uri
						.parse(url));
				context.startActivity(callIntent);
			}
		});

		TextView jaiswal = (TextView) layoutView
				.findViewById(R.id.dialog_person_jaiswal);
		jaiswal.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				String url = "tel:+918765045935";
				Intent callIntent = new Intent(Intent.ACTION_CALL, Uri
						.parse(url));
				context.startActivity(callIntent);
			}
		});

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (drawerOpen) {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
				drawerOpen = false;
			} else {
				mDrawerLayout.openDrawer(Gravity.LEFT);
				drawerOpen = true;
			}
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBackPressed();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if navigation drawer is opened, hide the action items
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (mDrawerLayout.isDrawerOpen(mDrawerList))
				mDrawerLayout.closeDrawer(mDrawerList);
			else
				mDrawerLayout.openDrawer(mDrawerList);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		/*
		 * if (mDrawerToggle.onOptionsItemSelected(item)) { return true; }
		 */
		return true;
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mHandleMessageReceiver);
		super.onDestroy();
	}

	/** GCM Service **/	
	private final WakefulBroadcastReceiver mHandleMessageReceiver = new WakefulBroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			Crouton.makeText(getParent(), newMessage, Style.INFO).show();
		}
	};
}
