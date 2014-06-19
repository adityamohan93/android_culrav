package mnnit.vinayakAj.culrav2k14;

import mnnit.vinayakAj.culrav2k14.fragments.FragmentNites;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class NitesActivity extends ActionBarActivity {

	private ActionBar actionBar;
	private Intent intent;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		intent = getIntent();
		position = intent.getIntExtra(FragmentNites.POSITION, 0);
		if (position == 0) {
			setContentView(R.layout.activity_nites_kavya_sandhya);
		} else {
			setContentView(R.layout.activity_nites_pro_nite);
		}

		setupActionBar();

		SharedPreferences sharePref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = sharePref.edit();
		editor.putInt(Config.PAGER_POSITION, 2);
		editor.commit();		
	}

	private void setupActionBar() {
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.nites, menu);
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

}
