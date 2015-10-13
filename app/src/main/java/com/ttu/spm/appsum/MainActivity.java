package com.ttu.spm.appsum;

import android.content.Intent;
import android.os.Handler;
import android.location.Location;
import android.location.LocationManager;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.ttu.spm.appsum.adapter.ImageAdapter;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    // Array to hold the menu item details
    static final String[] MENU_ITEMS = new String[] { "ACCOMMODATION","FOOD", "TRANSPORT",
            "TOURISM" };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set grid view
        gridView = (GridView) findViewById(com.ttu.spm.appsum.R.id.gridViewHome);
        gridView.setAdapter(new ImageAdapter(this, MENU_ITEMS));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    if (position == 1){
                        //TODO: Showing food layout with sample screen shot. Main functionality is not yet implemented
                        Intent food_intent = new Intent(MainActivity.this, FoodLayout.class);
                    startActivity(food_intent);
                    }

                    // TODO: Displaying text for testing purpose. Main functionality yet to implement
                    Toast.makeText(
                            getApplicationContext(),
                            ((TextView) v.findViewById(R.id.grid_item_text))
                                    .getText(), Toast.LENGTH_SHORT).show();
                }
            });
       }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        //TODO: Check and remove code of adding menus to action bar if not required
       // MenuItem actionmenuitem1 = menu.add(Menu.NONE, Menu.NONE, 101, "Text");
        //MenuItem actionmenuitem2 = menu.add(Menu.NONE, Menu.NONE, 102, "Icon");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
