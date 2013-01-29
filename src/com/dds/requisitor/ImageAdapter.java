package com.dds.requisitor;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;

	public ImageAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public String getItem(int position) {
		switch (position) {
		case 0:
			return "explore";
		case 1:
			return "search";
		case 2:
			return "list";
		case 3:
			return "up";
		case 4:
			return "help";
		case 5:
			return "about";
		}
		return null;
	}

	public long getItemId(int position) {
		return 0;

	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(mContext);
			int dips = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 130, parent.getResources().getDisplayMetrics());
			imageView.setLayoutParams(new GridView.LayoutParams(dips, dips));
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			imageView.setBackgroundResource(R.drawable.secondary_grey_grad);

			imageView.setPadding(8, 16, 8, 2);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(mThumbIds[position]);
		return imageView;
	}

	// references to our images
	private Integer[] mThumbIds = { R.drawable.explore_tr, R.drawable.search_tr,
			R.drawable.list_tr2, R.drawable.settings_tr, R.drawable.help_tr,R.drawable.about_tr

	};

}