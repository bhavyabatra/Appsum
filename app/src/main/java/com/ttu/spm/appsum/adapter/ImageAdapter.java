package com.ttu.spm.appsum.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.Drawable;


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
		View menu_item;

		if (convertView == null) {
			menu_item = new View(context);
			// get layout reference from menu_item
			menu_item = inflater.inflate(R.layout.menu_item, null);
			// Get Textview from menu_item.xml and set the title to it
			TextView menu_item_textview = (TextView) menu_item
					.findViewById(R.id.grid_item_text);
			menu_item_textview.setText(MENU_ITEMS[position]);
			// Get image view to add the menu icon
			ImageView menu_Image_View = (ImageView) menu_item
					.findViewById(R.id.grid_item_image);
			String menu_type = MENU_ITEMS[position];
			int drawableResource=getDrawableResourceId(menu_type);
			menu_Image_View.setImageResource(drawableResource);
		} else {
			menu_item = (View) convertView;
		}
		menu_item.setPressed(true);
		return menu_item;
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
	private int getDrawableResourceId(String menu_Type) {
		Resources res = this.context.getResources();
		int id;
		//
		if (menu_Type.equals("FOOD")) {
			id = res.getIdentifier("food_menu", "drawable", this.context.getPackageName());
		} else if (menu_Type.equals("TOURISM")) {
			id = res.getIdentifier("tourism_menu", "drawable", this.context.getPackageName());
		} else if (menu_Type.equals("TRANSPORT")) {
			id = res.getIdentifier("taxi_menu", "drawable", this.context.getPackageName());
		}
		else if (menu_Type.equals("ACCOMMODATION")) {
			id = res.getIdentifier("accomodation_menu", "drawable", this.context.getPackageName());
		}
		else  {
			id = res.getIdentifier("accomodation_menu", "drawable", this.context.getPackageName());
		}

		// return resource identifier
		return id;
	}
}
