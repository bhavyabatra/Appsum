package com.ttu.spm.appsum.layoutSelector;
import com.ttu.spm.appsum.CitySelection.*;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.Location;
import android.location.LocationManager;
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

import com.ttu.spm.appsum.Food.Restaurants;
import com.ttu.spm.appsum.Hotels.Accomadations;
import com.ttu.spm.appsum.R;
import com.ttu.spm.appsum.adapter.ImageAdapter;
import com.ttu.spm.appsum.places.Attractions;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {
    public static final int CITY_REQUEST_CODE = 1001;
    public byte[] buffer = new byte[1024];
    public CurrentCity current_city=new CurrentCity();
    private boolean current_city_first_try=true;
    GridView gridView;
    // Array to hold the menu item details
    static final String[] MENU_ITEMS = new String[] { "ACCOMMODATION","FOOD", "TRANSPORT",
            "ATTRACTIONS","ENTERTAINMENT","EMERGENCY" };
    Menu actionbar_menu;

    protected LocationManager locationManager;
    protected Context context;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    private boolean setLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState!=null) {
            //do nothing
        }
            else{
            setLocation = true;
            try {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            } catch (Exception e) {
            }
            //set grid view
            gridView = (GridView) findViewById(com.ttu.spm.appsum.R.id.gridViewHome);
            gridView.setAdapter(new ImageAdapter(this, MENU_ITEMS));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    launchNewActivity(v, position, id);

                }
            });
        }
       }
    @Override
    protected void onSaveInstanceState(Bundle state) {
        state.putString("Saving", "State");
        super.onSaveInstanceState(state);

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String test = savedInstanceState.getString("Saving");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    // location identifier
    @Override
    public void onLocationChanged(Location location) {
        // Turn off the location update requests
        if (setLocation == false){
            Log.d("Location","Service stopped");
            locationManager.removeUpdates(this);
        }
        else {
            double current_Latitude = location.getLatitude();
            double current_Longitude = location.getLongitude();
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(current_Latitude, current_Longitude, 1);
                // Get the city name from the address list and save to current city class
                String cityName = addresses.get(0).getLocality();
                current_city.city_name = cityName;
                Locale loc = new Locale("",addresses.get(0).getCountryName());
                current_city.country = loc.getDisplayCountry();
                current_city.latitude=current_Latitude;
                current_city.longitude=current_Longitude;
                //
                MenuItem location_item = actionbar_menu.findItem(R.id.city_textview);
                location_item.setTitle(cityName);
                setLocation = false;
                Log.d("Location", "Found city");
            } catch (IOException e) {
                Log.e("Location", "Unable connect to Geocoder", e);
                if (current_city_first_try) {
                    Toast.makeText(getApplicationContext(), "Unable to set current city. Please check internet connection",
                            Toast.LENGTH_LONG).show();
                    current_city_first_try=false;
                }
            }
        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Location", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Location", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Location", "status");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        actionbar_menu=menu;
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
        else if (id==R.id.cityicon){
            //Launch City selection activity
            Intent city_Selection_Intent = new Intent(MainActivity.this, CitySelectionLayout.class);
            startActivityForResult(city_Selection_Intent, CITY_REQUEST_CODE);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                SelectedCitySerializable selected_city = (SelectedCitySerializable)data.getSerializableExtra("Selected_City");
                // Save selected city details to current_city class
                current_city.city_name=selected_city.getCity();
                current_city.country=selected_city.getCountry();
                current_city.latitude=selected_city.getLatitude();
                current_city.longitude=selected_city.getLongitude();
                MenuItem location_item = actionbar_menu.findItem(R.id.city_textview);
                location_item.setTitle(current_city.city_name);
            }
        }
    }
  public void launchNewActivity(View view,int position,long id){
// Launch activity based on the selected menu item.
      TextView grid_item_text;
      String selected_menu;
      grid_item_text=(TextView)view.findViewById(R.id.grid_item_text);
      selected_menu=(grid_item_text.getText()).toString();
      switch (selected_menu) {
          case "FOOD":
              //TODO: Showing food layout with sample screen shot. Main functionality is not yet implemented
              Intent food_intent = new Intent(MainActivity.this, Restaurants.class);
              food_intent.putExtra("Latitude", current_city.latitude);
              food_intent.putExtra("Longitude", current_city.longitude);
              //
              startActivity(food_intent);
              break;

          case "ATTRACTIONS":
              if (current_city.latitude!=0 && current_city.longitude!=0) {
                  Intent attractions_intent = new Intent(MainActivity.this, Attractions.class);
                  attractions_intent.putExtra("Latitude", current_city.latitude);
                  attractions_intent.putExtra("Longitude", current_city.longitude);
                  startActivity(attractions_intent);
              }
              else{
                  Toast.makeText(getApplicationContext(), R.string.selectCityError,
                          Toast.LENGTH_LONG).show();
              }

              break;
          case "TRANSPORT":
              if (current_city.latitude!=0 && current_city.longitude!=0) {
                  Intent transport_intent = new Intent(MainActivity.this, TransportLayer.class);
                  // attractions_intent.putExtra("Latitude", current_city.latitude);
                  //attractions_intent.putExtra("Longitude", current_city.longitude);
                  startActivity(transport_intent);
              }
              else{
                  Toast.makeText(getApplicationContext(), R.string.selectCityError,
                          Toast.LENGTH_LONG).show();
              }
              break;
          case "EMERGENCY":

              // Creating new intent for EmergencyLayout
              Intent emergency_intent = new Intent(MainActivity.this, EmergencyLayout.class);
              emergency_intent.putExtra("CityName",current_city.city_name);
              emergency_intent.putExtra("Country", current_city.country);

              if(current_city.city_name != null){

                  //Getting the CSV file for use
                  InputStream inputStream = getResources().openRawResource(R.raw.ememrgencydata);

                  //Start the activity
                  startActivity(emergency_intent);
              }
              else{
                  Toast.makeText(
                          getApplicationContext(),
                          R.string.selectCityError ,
                          Toast.LENGTH_LONG).show();
              }

              //TextView editCountry = (TextView)findViewById(R.id.country_value);
              //Log.i("temp",editCountry.toString());
              //editCountry.setText(R.string.noCountryFound, TextView.BufferType.EDITABLE);


            break;
          case "ACCOMMODATION":
              if (current_city.latitude != 0 && current_city.longitude != 0) {
                  Intent accomodation_intent = new Intent(MainActivity.this, Accomadations.class);
                  accomodation_intent.putExtra("Latitude", current_city.latitude);
                  accomodation_intent.putExtra("Longitude", current_city.longitude);
                  startActivity(accomodation_intent);
              } else {
                  Toast.makeText(getApplicationContext(), R.string.selectCityError,
                          Toast.LENGTH_LONG).show();
              }

              break;

      }
      // TODO: Displaying text for testing purpose. Main functionality yet to implement
      Toast.makeText(
              getApplicationContext(),
              ((TextView) view.findViewById(R.id.grid_item_text))
                      .getText(), Toast.LENGTH_SHORT).show();

     }

}
