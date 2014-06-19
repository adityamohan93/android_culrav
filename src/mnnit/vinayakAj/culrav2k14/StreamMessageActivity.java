package mnnit.vinayakAj.culrav2k14;

import mnnit.vinayakAj.culrav2k14.adapters.StreamListAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class StreamMessageActivity extends ActionBarActivity {

	private Intent receiveIntent;
	private TextView title;
	private TextView message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stream_message);
		// Show the Up button in the action bar.
		title = (TextView) findViewById(R.id.activity_stream_title);
		message = (TextView) findViewById(R.id.activity_stream_message);

		setupActionBar();

		prepareFields();
	}

	private void prepareFields() {
		receiveIntent = getIntent();
		title.setText(receiveIntent
				.getStringExtra(StreamListAdapter.STREAM_TITLE));
		message.setText(receiveIntent
				.getStringExtra(StreamListAdapter.STREAM_MESSAGE));
		
		SharedPreferences sharePref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = sharePref.edit();
		editor.putInt(Config.PAGER_POSITION, 0);
		editor.commit();
	}

	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
