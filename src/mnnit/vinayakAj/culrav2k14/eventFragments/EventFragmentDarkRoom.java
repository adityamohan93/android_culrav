package mnnit.vinayakAj.culrav2k14.eventFragments;

import java.util.List;

import mnnit.vinayakAj.culrav2k14.Config;
import mnnit.vinayakAj.culrav2k14.ExploreEventsActivity;
import mnnit.vinayakAj.culrav2k14.R;
import mnnit.vinayakAj.culrav2k14.Loaders.EventDarkRoomLoader;
import mnnit.vinayakAj.culrav2k14.util.EventDarkRoom;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class EventFragmentDarkRoom extends Fragment implements
		LoaderCallbacks<EventDarkRoom> {
	private View fragmentView;
	private int position;
	private TextView eventHeader;
	private TextView description;
	private TextView format;
	private TextView rules;
	private Button fbPage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		position = bundle.getInt(ExploreEventsActivity.EVENT_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.frag_event_darkroom,
				container, false);
		eventHeader = (TextView) fragmentView
				.findViewById(R.id.event_darkroom_header);
		description = (TextView) fragmentView
				.findViewById(R.id.event_darkroom_description);
		format = (TextView) fragmentView
				.findViewById(R.id.event_darkroom_format_description);
		rules = (TextView) fragmentView
				.findViewById(R.id.event_darkroom_rules_description);
		fbPage = (Button)fragmentView.findViewById(R.id.event_darkroom_button_fb);
		
		fbPage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final String urlFb = "fb://page/" + Config.FB_PAGE_DARKROOM_ID;
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(urlFb));

				// If Facebook application is installed, use that else launch a
				// browser
				final PackageManager packageManager = getActivity()
						.getPackageManager();
				List<ResolveInfo> list = packageManager.queryIntentActivities(
						intent, PackageManager.MATCH_DEFAULT_ONLY);
				if (list.size() == 0) {
					intent.setData(Uri
							.parse(Config.FB_PAGE_DARKROOM));
					startActivity(Intent.createChooser(intent, "Load with"));
				} else {
					startActivity(intent);
				}
			}

		});
		
		return fragmentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getLoaderManager().initLoader(0101, null, this).forceLoad();
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	@Override
	public void onPause() {
		super.onPause();
		getActivity().overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	@Override
	public Loader<EventDarkRoom> onCreateLoader(int arg0, Bundle arg1) {
		return new EventDarkRoomLoader(getActivity(), position);
	}

	@Override
	public void onLoadFinished(Loader<EventDarkRoom> arg0, EventDarkRoom event) {
		eventHeader.setText(event.getEvent());
		description.setText(event.getDescription());

		String[] rulesStr = event.getRules();
		int rulesLen = rulesStr.length;
		if (rulesLen == 1) {
			((TextView) fragmentView
					.findViewById(R.id.event_darkroom_rules_judging))
					.setVisibility(View.GONE);
			((TextView) fragmentView
					.findViewById(R.id.event_darkroom_header_rules_underline))
					.setVisibility(View.GONE);
		} else {
			for (int i = 0; i < rulesLen; i++)
				rules.append(rulesStr[i] + "\n");
		}

		if (event.getFormat().equals("")) {
			((TextView) fragmentView
					.findViewById(R.id.event_darkroom_header_format))
					.setVisibility(View.GONE);
			((TextView) fragmentView
					.findViewById(R.id.event_darkroom_header_format_underline))
					.setVisibility(View.GONE);
		} else {
			format.setText(event.getFormat());
		}

	}

	@Override
	public void onLoaderReset(Loader<EventDarkRoom> arg0) {
	}
}
