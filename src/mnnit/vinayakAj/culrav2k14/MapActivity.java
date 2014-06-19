package mnnit.vinayakAj.culrav2k14;

import java.util.ArrayList;
import java.util.List;

import mnnit.vinayakAj.culrav2k14.adapters.NavigationItemListAdapter;
import mnnit.vinayakAj.culrav2k14.util.NavigationDrawerItem;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suredigit.inappfeedback.FeedbackDialog;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MapActivity extends ActionBarActivity {

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

	private FeedbackDialog feedBack;
	
	// Map fields
	GoogleMap map;
	Location myLocation;
	private LocationManager manager;
	private LatLng library;

	private LatLng sms, mpHall, basketCourt, skateCourt, tennisCourt,
			gangaGate, yamunaGate, NLH, seminarHall, lectureHallComplex,
			GSLectureRooms, footballGround, gymkhanaGround, fnLectureRooms,
			bikanerOutlet, saraswatiGate, newAdminBuilding, amulOutlet,
			vivekanadHostel, computerCentre, deanAcadBuilding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);

		actionBar = getSupportActionBar();

		setupNavigationDrawer();

		setupMap();
		
		feedBack = new FeedbackDialog(this, "AF_EA6EF4AC032A-23", Config.getFeedBackSettings());

	}

	private void setupMap() {
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		if (map == null) {
			Crouton.makeText(this, "Google Maps not available", Style.ALERT)
					.show();
			return;
		}
		/*
		 * CameraPosition camPos = new CameraPosition.Builder().target(MNNIT)
		 * .zoom(16) .tilt(30) .build();
		 * map.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
		 */
		mpHall = new LatLng(25.491917, 81.865844);
		NLH = new LatLng(25.493031, 81.863092);
		GSLectureRooms = new LatLng(25.492781, 81.863234);
		seminarHall = new LatLng(25.492254, 81.862350);
		lectureHallComplex = new LatLng(25.491748, 81.862325);
		footballGround = new LatLng(25.4947416, 81.864632);
		gymkhanaGround = new LatLng(25.493398, 81.865975);
		fnLectureRooms = new LatLng(25.493924, 81.864346);
		basketCourt = new LatLng(25.492301, 81.866066);

		gangaGate = new LatLng(25.492648, 81.861216);
		yamunaGate = new LatLng(25.494221, 81.861488);
		saraswatiGate = new LatLng(25.491343, 81.866341);
		newAdminBuilding = new LatLng(25.493417, 81.862270);
		deanAcadBuilding = new LatLng(25.492369, 81.863044);
		skateCourt = new LatLng(25.492311, 81.865462);
		tennisCourt = new LatLng(25.493946, 81.865875);
		computerCentre = new LatLng(25.491689, 81.863150);
		vivekanadHostel = new LatLng(25.490506, 81.863147);
		library = new LatLng(25.493924, 81.864346);
		sms = new LatLng(25.490487, 81.864245);
		bikanerOutlet = new LatLng(25.491540, 81.866122);
		amulOutlet = new LatLng(25.4939459, 81.8623689);

		map.addMarker(new MarkerOptions().position(mpHall).title("MP Hall"));
		map.addMarker(new MarkerOptions().position(NLH).title(
				"New Lecuture Halls (NLH)"));
		map.addMarker(new MarkerOptions().position(GSLectureRooms).title(
				"GS Lecture Rooms"));
		map.addMarker(new MarkerOptions().position(seminarHall).title(
				"Seminar Hall"));
		map.addMarker(new MarkerOptions().position(lectureHallComplex).title(
				"Lecture Hall Complex"));
		map.addMarker(new MarkerOptions().position(footballGround).title(
				"Football Ground"));
		map.addMarker(new MarkerOptions().position(gymkhanaGround).title(
				"Gymkhana Ground"));
		map.addMarker(new MarkerOptions().position(fnLectureRooms).title(
				"FN Lecture Rooms"));
		map.addMarker(new MarkerOptions().position(basketCourt).title(
				"Basketball Court"));

		map.addMarker(new MarkerOptions().position(gangaGate).title(
				"Ganga Gate"));
		map.addMarker(new MarkerOptions().position(yamunaGate).title(
				"Yamuna Gate"));
		map.addMarker(new MarkerOptions().position(saraswatiGate).title(
				"Saraswati Gate"));
		map.addMarker(new MarkerOptions().position(newAdminBuilding).title(
				"MNNIT Administrative Building"));
		map.addMarker(new MarkerOptions().position(deanAcadBuilding).title(
				"Dean Academics Office"));
		map.addMarker(new MarkerOptions().position(skateCourt).title(
				"Skating Court"));
		map.addMarker(new MarkerOptions().position(tennisCourt).title(
				"Tennis Court"));
		map.addMarker(new MarkerOptions().position(computerCentre).title(
				"Computer Centre"));
		map.addMarker(new MarkerOptions().position(vivekanadHostel).title(
				"Swami Vivekanand Hostel"));
		map.addMarker(new MarkerOptions().position(library).title(
				"MNNIT Central Library"));
		map.addMarker(new MarkerOptions().position(sms).title(
				"School of Management Studies"));
		map.addMarker(new MarkerOptions().position(bikanerOutlet).title(
				"Bikaner Outlet"));
		map.addMarker(new MarkerOptions().position(amulOutlet).title(
				"Amul Outlet"));

		manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		setupGPS();

	}

	private void setupGPS() {
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ){
			setupGPSCompat();
			return;
		}
		manager.addGpsStatusListener(new Listener() {
			
			@Override
			public void onGpsStatusChanged(int event) {
				map.setMyLocationEnabled(true);
			}
		});
				
		if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			map.setMyLocationEnabled(true);
			myLocation = map.getMyLocation();
			Crouton.makeText(this, "GPS is enabled", Style.INFO).show();
		} else {
			Crouton.makeText(this, "GPS is not enabled. Can not locate you",
					Style.ALERT).show();
		}
	}

	void setupGPSCompat(){
		map.setMyLocationEnabled(true);
		myLocation = map.getMyLocation();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Crouton.cancelAllCroutons();
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
		mDrawerLayout = (DrawerLayout) findViewById(R.id.map_drawer_layout);
		// reference the menu item format
		mDrawerList = (ListView) findViewById(R.id.map_left_drawer);

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
			// openMap();
			break;
		case 4:
			showContacts();
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
		getMenuInflater().inflate(R.menu.map, menu);
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
		menu.findItem(R.id.map_action_normal).setVisible(!drawerOpen);
		menu.findItem(R.id.map_action_hybrid).setVisible(!drawerOpen);
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
		case R.id.map_action_hybrid:
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		case R.id.map_action_normal:
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
}
