package com.ttu.spm.appsum.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttu.spm.appsum.R;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	private final String[] MENU_ITEMS;

	public ImageAdapter(Context context, String[] MENU_ITEMS) {
		this.context = context;
		this.MENU_ITEMS = MENU_ITEMS;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View gridView;

		if (convertView == null) {

			gridView = new View(context);

			// get layout from activity_main.xml
			gridView = inflater.inflate(R.layout.menu_item, null);

			// Get textview from menu_item.xml and set the title to it
			TextView textView = (TextView) gridView
					.findViewById(R.id.grid_item_text);
			textView.setText(MENU_ITEMS[position]);

			// set image based on selected text
			ImageView imageView = (ImageView) gridView
					.findViewById(R.id.grid_item_image);
			String menutype = MENU_ITEMS[position];
			if (menutype.equals("FOOD")) {
				imageView.setImageResource(R.drawable.food_menu);
			} else if (menutype.equals("TOURISM")) {
				imageView.setImageResource(R.drawable.tourism_menu);
			} else if (menutype.equals("TRANSPORT")) {
				imageView.setImageResource(R.drawable.taxi_menu);
			}
				else if (menutype.equals("ACCOMMODATION")) {
					imageView.setImageResource(R.drawable.accomodation_menu);

				} else {

			}

		} else {
			gridView = (View) convertView;
		}
		gridView.setPressed(true);
		return gridView;
	}

	@Override
	public int getCount() {
		return MENU_ITEMS.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
