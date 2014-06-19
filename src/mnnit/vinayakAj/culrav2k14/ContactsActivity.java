package mnnit.vinayakAj.culrav2k14;

import java.util.ArrayList;
import java.util.List;

import mnnit.vinayakAj.culrav2k14.Loaders.TeamListLoader;
import mnnit.vinayakAj.culrav2k14.adapters.NavigationItemListAdapter;
import mnnit.vinayakAj.culrav2k14.adapters.TeamListAdapter;
import mnnit.vinayakAj.culrav2k14.util.ContactsDynamics;
import mnnit.vinayakAj.culrav2k14.util.ContactsListView;
import mnnit.vinayakAj.culrav2k14.util.NavigationDrawerItem;
import mnnit.vinayakAj.culrav2k14.util.Team;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ContactsActivity extends ActionBarActivity implements
		LoaderManager.LoaderCallbacks<ArrayList<Team>> {
	Context context = this;
	ActionBar actionBar;

	private FeedbackDialog feedBack;
	
	// Navigation Drawer fields
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private boolean drawerOpen;
	private ArrayList<NavigationDrawerItem> naviagtionDrawerItems;

	// Contacts fields
	/** Id for the toggle rotation menu item */
	// private static final int TOGGLE_ROTATION_MENU_ITEM = 0;

	/** Id for the toggle lighting menu item */
	// private static final int TOGGLE_LIGHTING_MENU_ITEM = 1;

	private ContactsListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);

		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);

		actionBar = getSupportActionBar();

		setupNavigationDrawer();

		setupContactsList();
		
		feedBack = new FeedbackDialog(this, "AF_EA6EF4AC032A-23", Config.getFeedBackSettings());
	}

	private void setupContactsList() {
		listView = (ContactsListView) findViewById(R.id.contacts_list);

		getSupportLoaderManager().initLoader(1992, null, this).forceLoad();
	}

	@Override
	public Loader<ArrayList<Team>> onCreateLoader(int i, Bundle bundle) {
		return new TeamListLoader(context);
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Team>> loader,
			ArrayList<Team> contacts) {
		listView.setAdapter(new TeamListAdapter(context, contacts));
		listView.setDynamics(new SimpleDynamics(0.9f, 0.6f));
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Team>> arg0) {
	}

	@Override
	protected void onResume() {
		super.onResume();
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

	/**
	 * A very simple dynamics implementation with spring-like behavior
	 */
	class SimpleDynamics extends ContactsDynamics {

		/** The friction factor */
		private float mFrictionFactor;

		/** The snap to factor */
		private float mSnapToFactor;

		/**
		 * Creates a SimpleDynamics object
		 * 
		 * @param frictionFactor
		 *            The friction factor. Should be between 0 and 1. A higher
		 *            number means a slower dissipating speed.
		 * @param snapToFactor
		 *            The snap to factor. Should be between 0 and 1. A higher
		 *            number means a stronger snap.
		 */
		public SimpleDynamics(final float frictionFactor,
				final float snapToFactor) {
			mFrictionFactor = frictionFactor;
			mSnapToFactor = snapToFactor;
		}

		@Override
		protected void onUpdate(final int dt) {
			// update the velocity based on how far we are from the snap point
			mVelocity += getDistanceToLimit() * mSnapToFactor;

			// then update the position based on the current velocity
			mPosition += mVelocity * dt / 1000;

			// and finally, apply some friction to slow it down
			mVelocity *= mFrictionFactor;
		}
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
		mDrawerLayout = (DrawerLayout) findViewById(R.id.contacts_drawer_layout);
		// reference the menu item format
		mDrawerList = (ListView) findViewById(R.id.contacts_left_drawer);

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

	public void selectAction(int position) {
		switch (position) {
		case 0:
			openHome();
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
			// showContacts();
			break;
		case 5:
			showAbout();
			break;
		}
	}

	private void openHome() {
		Intent intent = new Intent(context, HomeUI.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
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

	private void showAbout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();
		View layoutView = inflater.inflate(R.layout.dialog_about, null);
		
		builder.setView(layoutView)
		.setNeutralButton("Feedback", new DialogInterface.OnClickListener() {
			
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getSupportMenuInflater().inflate(R.menu.home_ui, menu);
		return super.onCreateOptionsMenu(menu);
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
		// menu.findItem(R.id.action_finish).setVisible(!drawerOpen);
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
		return true;
	}

}