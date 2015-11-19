package com.ttu.spm.appsum.places;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ttu.spm.appsum.R;
import com.ttu.spm.appsum.Transport.TransportDetails;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Manohar on 17-Nov-15.
 */
public class AttractionPlaceDetails extends AppCompatActivity {
    protected TextView place_name;
    protected TextView place_adr;
    public String placeId;
public Place PlaceDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attractions_place_details);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            // Get Latitude and Longitude from parent activity
            String PlaceName = intent.getStringExtra("PlaceName");
            String city = intent.getStringExtra("City");
            String placeId = intent.getStringExtra("PlaceId");
            place_name = (TextView) findViewById(R.id.place_name);
            place_adr = (TextView) findViewById(R.id.place_addr);
            place_name.setText(PlaceName);
            place_adr.setText(city);
            new getPlaceDetails().execute(placeId);


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

    private class getPlaceDetails extends AsyncTask<String, Void, String> {
        protected String place;
        @Override
        protected String doInBackground(String... placeId) {
            HttpURLConnection connection = null;
            StringBuffer buffer = new StringBuffer();
            String data;
            String uri;
            Bitmap bm = null;
            Double Latitude, Longitude;
            String line;
            try {
////
                place = new String(placeId[0]);
                uri = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + place + "&key=AIzaSyDtS5nYWh9bMAdmdofOfKOkfkPe3SG_qxk";

                connection = (HttpURLConnection) (new URL(uri)).openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                line = null;
                while ((line = br.readLine()) != null) {
                    buffer.append(line + "\r\n");
                }
            } catch (Throwable t) {
                //Toast.makeText(context, "Unable to get cities. Please check internet connection",
                //      Toast.LENGTH_LONG).show();

            }
            data = buffer.toString();
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.length() > 0) {
                try {
                    parsePlaceDetails(result);
                } catch (JSONException J) {
                }
            }
        }

        protected void parsePlaceDetails(String result) throws JSONException {
            FindPlaces client = new FindPlaces("AIzaSyDtS5nYWh9bMAdmdofOfKOkfkPe3SG_qxk");
            Place selectedPlace = new Place();
            selectedPlace.setClient(client);
            selectedPlace.setPlaceId(place);
           PlaceDetails=selectedPlace.parseDetails(client, result);
            TextView fullAddr =(TextView) findViewById(R.id.fullAddress);
            fullAddr.setText(PlaceDetails.getAddress());
            List<AddressComponent> a=PlaceDetails.getAddressComponents();
            place_adr.setText(a.get(1).getLongName());
            TextView rating=(TextView) findViewById(R.id.rating);
            Double reviewRate =PlaceDetails.getRating();
            rating.setText(reviewRate.toString());
            TextView reviewCountView=(TextView) findViewById(R.id.reviewCount);
            Integer reviewCount =new Integer(PlaceDetails.getTotalReviews());
            reviewCountView.setText(reviewCount.toString()+" Reviews");

            Log.e("Check","parsing");
        }
    }
    public void onCall(View v) {
        String number = "tel:" + PlaceDetails.getInternationalPhoneNumber();
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void loadWebsite(View v) {
        Uri uriUrl = Uri.parse(PlaceDetails.getWebsite());
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
    public void loadMaps(View v) {
        Intent travel_intent = new Intent(AttractionPlaceDetails.this, TransportDetails.class);
        travel_intent.putExtra("PlaceId", PlaceDetails.getPlaceId());
        travel_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(travel_intent);

    }
}
