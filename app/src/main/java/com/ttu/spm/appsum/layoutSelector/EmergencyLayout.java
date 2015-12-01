package com.ttu.spm.appsum.layoutSelector;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.ttu.spm.appsum.CitySelection.CitySelectionLayout;
import com.ttu.spm.appsum.CitySelection.SelectedCitySerializable;
import com.ttu.spm.appsum.R;
import com.ttu.spm.appsum.database.EmergencyDatasource;
import com.ttu.spm.appsum.models.Emergency;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EmergencyLayout extends AppCompatActivity implements LocationListener {

    // Varaibles to be used.
    private static final String LOGTAG = "EmergencyLayout";

    protected LocationManager locationManager;
    protected Context context;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    private boolean setLocation;
    GridView gridView;
    Menu actionbar_menu;
    CurrentCity currentCity = new CurrentCity();
    public static final int CITY_REQUEST_CODE = 1001;
    EmergencyDatasource datasource;


    Button volunteerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        // creating a new EmergencyDatasource object.
        datasource = new EmergencyDatasource(this);


        Intent intent = getIntent();
        setLocation = true;
        // getting country and cit data from MainActivity.java
        currentCity.city_name = intent.getStringExtra("CityName");
        currentCity.country = intent.getStringExtra("Country");


        // calling the getData method to display emergenc details according to the city.
        //datasource.getData(currentCity.country);
        Log.i(LOGTAG, "onCreate");
        List<Emergency> fetchedDetails = datasource.getData(currentCity.country);

        TextView editCountry = (TextView) findViewById(R.id.country_value);

        TextView editPolice = (TextView) findViewById(R.id.police_no_value);
        TextView editAmbulance = (TextView) findViewById(R.id.ambulance_no_value);
        TextView editFire = (TextView) findViewById(R.id.firecontrol_no_value);


        if (fetchedDetails.isEmpty()) {

            editCountry.setText(" No Country Found");
            editPolice.setText(" NA");
            editAmbulance.setText(" NA");
            editFire.setText(" NA");
        } else {

            editCountry.setText(" " + fetchedDetails.get(0).getCountryName().toString());
            editPolice.setText(" " + fetchedDetails.get(0).getPoliceNo().toString());
            editAmbulance.setText(" " + fetchedDetails.get(0).getAmbulanceNo().toString());
            editFire.setText(" " + fetchedDetails.get(0).getFireControlNo().toString());

        }


        // Functionality for button click
        volunteerButton = (Button) findViewById(R.id.volunteer_button);
        volunteerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent volunteer_intent = new Intent(EmergencyLayout.this, VolunteerLayout.class);
                volunteer_intent.putExtra("CityName", currentCity.city_name);
                volunteer_intent.putExtra("Country", currentCity.country);
                startActivity(volunteer_intent);


            }
        });

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
        if (setLocation == false) {
            Log.d("Location", "Service stopped");
            locationManager.removeUpdates(this);
        } else {
            double current_Latitude = location.getLatitude();
            double current_Longitude = location.getLongitude();
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(current_Latitude, current_Longitude, 1);
                // Get the city name from the address list and save to current city class
                String cityName = addresses.get(0).getLocality();
                currentCity.city_name = cityName;
                currentCity.country = addresses.get(0).getCountryName();
                currentCity.latitude = current_Latitude;
                currentCity.longitude = current_Longitude;
                //
                MenuItem location_item = actionbar_menu.findItem(R.id.city_textview);
                location_item.setTitle(cityName);
                datasource = new EmergencyDatasource(this);
                Log.i(LOGTAG, "onLocationChange");
                List<Emergency> fetchedDetails = datasource.getData(currentCity.country);

                TextView editCountry = (TextView) findViewById(R.id.country_value);
                TextView editPolice = (TextView) findViewById(R.id.police_no_value);
                TextView editAmbulance = (TextView) findViewById(R.id.ambulance_no_value);
                TextView editFire = (TextView) findViewById(R.id.firecontrol_no_value);


                if (fetchedDetails.isEmpty()) {

                    editCountry.setText(" No Country Found");
                    editPolice.setText(" NA");
                    editAmbulance.setText(" NA");
                    editFire.setText(" NA");
                } else {

                    editCountry.setText(" " + fetchedDetails.get(0).getCountryName().toString());
                    editPolice.setText(" " + fetchedDetails.get(0).getPoliceNo().toString());
                    editAmbulance.setText(" " + fetchedDetails.get(0).getAmbulanceNo().toString());
                    editFire.setText(" " + fetchedDetails.get(0).getFireControlNo().toString());

                }


                setLocation = false;
                Log.d("Location", "Found city");
            } catch (IOException e) {
                Log.e("Location", "Unable connect to Geocoder", e);
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
        actionbar_menu = menu;
        MenuItem location_item = actionbar_menu.findItem(R.id.city_textview);
        location_item.setTitle(currentCity.city_name);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       if (id == R.id.cityicon) {
            //Launch City selection activity
            Intent city_Selection_Intent = new Intent(EmergencyLayout.this, CitySelectionLayout.class);
            startActivityForResult(city_Selection_Intent, CITY_REQUEST_CODE);

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                SelectedCitySerializable selected_city = (SelectedCitySerializable) data.getSerializableExtra("Selected_City");
                // Save selected city details to currentCity class
                currentCity.city_name = selected_city.getCity();
                currentCity.country = selected_city.getCountry();
                currentCity.latitude = selected_city.getLatitude();
                currentCity.longitude = selected_city.getLongitude();
                MenuItem location_item = actionbar_menu.findItem(R.id.city_textview);
                location_item.setTitle(currentCity.city_name);
                datasource = new EmergencyDatasource(this);
                Log.i(LOGTAG, "onActivityResult");
                List<Emergency> fetchedDetails = datasource.getData(currentCity.country);
                TextView editCountry = (TextView) findViewById(R.id.country_value);
                TextView editPolice = (TextView) findViewById(R.id.police_no_value);
                TextView editAmbulance = (TextView) findViewById(R.id.ambulance_no_value);
                TextView editFire = (TextView) findViewById(R.id.firecontrol_no_value);


                if (fetchedDetails.isEmpty()) {

                    editCountry.setText(" No Country Found");
                    editPolice.setText(" NA");
                    editAmbulance.setText(" NA");
                    editFire.setText(" NA");
                } else {

                    editCountry.setText(" " + fetchedDetails.get(0).getCountryName().toString());
                    editPolice.setText(" " + fetchedDetails.get(0).getPoliceNo().toString());
                    editAmbulance.setText(" " + fetchedDetails.get(0).getAmbulanceNo().toString());
                    editFire.setText(" " + fetchedDetails.get(0).getFireControlNo().toString());

                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }
}// end of class
