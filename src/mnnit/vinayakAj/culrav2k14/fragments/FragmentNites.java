package mnnit.vinayakAj.culrav2k14.fragments;

import mnnit.vinayakAj.culrav2k14.NitesActivity;
import mnnit.vinayakAj.culrav2k14.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragmentNites extends Fragment {

	public final static String POSITION = "mnnit.vinayakAj.culrav2k14.fragmentNites.POSITION";
	private View fragmentView;
	private ImageView kavyaSandhya;
	private ImageView farhanLive;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.fragment_nites, container,
				false);

		kavyaSandhya = (ImageView) fragmentView
				.findViewById(R.id.nite_pic_kavya_sandhya);
		farhanLive = (ImageView) fragmentView
				.findViewById(R.id.nite_pic_farhan);

		View.OnClickListener listener = new View.OnClickListener() {

			Intent intent = new Intent(getActivity(), NitesActivity.class);

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.nite_pic_kavya_sandhya:
					intent.putExtra(POSITION, 0);
					break;
				default:
					intent.putExtra(POSITION, 1);
					break;
				}
				startActivity(intent);
			}
		};

		kavyaSandhya.setOnClickListener(listener);
		farhanLive.setOnClickListener(listener);

		return fragmentView;
	}

}
