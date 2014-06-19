package mnnit.vinayakAj.culrav2k14;

import mnnit.vinayakAj.culrav2k14.eventFragments.EventFragmentAnunaad;
import mnnit.vinayakAj.culrav2k14.eventFragments.EventFragmentDarkRoom;
import mnnit.vinayakAj.culrav2k14.eventFragments.EventFragmentGlamStreet;
import mnnit.vinayakAj.culrav2k14.eventFragments.EventFragmentInformals;
import mnnit.vinayakAj.culrav2k14.eventFragments.EventFragmentLitMuse;
import mnnit.vinayakAj.culrav2k14.eventFragments.EventFragmentLokTarang;
import mnnit.vinayakAj.culrav2k14.eventFragments.EventFragmentNatyaManch;
import mnnit.vinayakAj.culrav2k14.eventFragments.EventFragmentRangSaazi;
import mnnit.vinayakAj.culrav2k14.eventFragments.EventFragmentRazzmatazz;
import mnnit.vinayakAj.culrav2k14.eventFragments.EventFragmentSrishti;
import mnnit.vinayakAj.culrav2k14.fragments.FragmentExplore;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class EventDetailsActivity extends ActionBarActivity {

	private ActionBar actionBar;
	private Intent receivedIntent;
	private String category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);

		prepareFields();

		setupActionBar();
	}

	private void prepareFields() {
		receivedIntent = getIntent();
		category = receivedIntent.getStringExtra(FragmentExplore.CATEGORY);
		int position = receivedIntent.getIntExtra(
				ExploreEventsActivity.EVENT_POSITION, -1);
		Bundle args = new Bundle();
		args.putInt(ExploreEventsActivity.EVENT_POSITION, position);

		FragmentTransaction transition = getSupportFragmentManager()
				.beginTransaction();

		if (category.equalsIgnoreCase("Loktarang")) {
			EventFragmentLokTarang fragment = new EventFragmentLokTarang();
			fragment.setArguments(args);
			transition.add(R.id.event_details_fragment_container, fragment)
					.commit();
		} else if (category.equalsIgnoreCase("Razzmatazz")) {
			EventFragmentRazzmatazz fragment = new EventFragmentRazzmatazz();
			fragment.setArguments(args);
			transition.add(R.id.event_details_fragment_container, fragment)
					.commit();
		} else if (category.equalsIgnoreCase("LitMuse")) {
			EventFragmentLitMuse fragment = new EventFragmentLitMuse();
			fragment.setArguments(args);
			transition.add(R.id.event_details_fragment_container, fragment)
					.commit();
		} else if (category.equalsIgnoreCase("NatyaManch")) {
			EventFragmentNatyaManch fragment = new EventFragmentNatyaManch();
			fragment.setArguments(args);
			transition.add(R.id.event_details_fragment_container, fragment)
					.commit();
		} else if (category.equalsIgnoreCase("RangSaazi")) {
			EventFragmentRangSaazi fragment = new EventFragmentRangSaazi();
			fragment.setArguments(args);
			transition.add(R.id.event_details_fragment_container, fragment)
					.commit();
		} else if (category.equalsIgnoreCase("Anunaad")) {
			EventFragmentAnunaad fragment = new EventFragmentAnunaad();
			fragment.setArguments(args);
			transition.add(R.id.event_details_fragment_container, fragment)
					.commit();
		} else if (category.equalsIgnoreCase("GlamStreet")) {
			EventFragmentGlamStreet fragment = new EventFragmentGlamStreet();
			fragment.setArguments(args);
			transition.add(R.id.event_details_fragment_container, fragment)
					.commit();
		} else if (category.equalsIgnoreCase("DarkRoom")) {
			EventFragmentDarkRoom fragment = new EventFragmentDarkRoom();
			fragment.setArguments(args);
			transition.add(R.id.event_details_fragment_container, fragment)
					.commit();
		} else if(category.equalsIgnoreCase("Srishti")){
			EventFragmentSrishti fragment = new EventFragmentSrishti();
			fragment.setArguments(args);
			transition.add(R.id.event_details_fragment_container, fragment)
					.commit();
		}else{
			EventFragmentInformals fragment = new EventFragmentInformals();
			fragment.setArguments(args);
			transition.add(R.id.event_details_fragment_container, fragment)
					.commit();
		}
	}

	private void setupActionBar() {
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(category);
		actionBar.setIcon(receivedIntent.getIntExtra(
				FragmentExplore.CATEGORY_ICON, R.drawable.ic_launcher));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.event_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			// NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
