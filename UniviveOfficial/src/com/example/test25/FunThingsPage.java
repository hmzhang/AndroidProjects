package com.example.test25;

//later on, add function to select what kind of things you want to do?
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FunThingsPage extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
	public TextView latitude, longitude, whereami;
	public Location lastLocation;
	Button test, locationName;
	public GoogleApiClient mGoogleApiClient;
	Geocoder geocoder;
	String cityName, provinceName, countryName;
	double latitudenum, longitudenum;
	LinearLayout linear;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.funthings_page);
		linear = (LinearLayout) findViewById(R.layout.funthings_page);
		buildGoogleApiClient();
		test = (Button) findViewById(R.id.button1);
		test.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				mGoogleApiClient.connect();
			}
        	
        });
		latitude = (TextView) findViewById(R.id.textView1);
		longitude = (TextView) findViewById(R.id.textView2);
		geocoder = new Geocoder(this, Locale.getDefault());
		locationName = (Button)findViewById(R.id.locationname);
		whereami = (TextView) findViewById(R.id.textView3);
		locationName.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(latitudenum != Double.NaN && longitudenum != Double.NaN){
				List<Address> addresses = new ArrayList<Address>();
				try {
					addresses = geocoder.getFromLocation(latitudenum, longitudenum, 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cityName = addresses.get(0).getAddressLine(0);
				provinceName = addresses.get(0).getAddressLine(1);
				countryName = addresses.get(0).getAddressLine(2);
				}
				whereami.setText("You are currently at " + provinceName + " " + countryName + ". Would you like to see what"
						+ "there is to do around your area currently?");
				
				Button letsgo = (Button) findViewById(R.id.letsgo);
				letsgo.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
						String term = "fun things to do in " + provinceName + " " + countryName;
						intent.putExtra(SearchManager.QUERY, term);
						startActivity(intent);
					}
		        	
		        });
				letsgo.setVisibility(0);
        	
        }});
	}
	protected synchronized void buildGoogleApiClient() {
		Log.d("test1", "Hit buildGoogleApiClient() method");
	    mGoogleApiClient = new GoogleApiClient.Builder(this)
	        .addConnectionCallbacks(this)
	        .addOnConnectionFailedListener(this)
	        .addApi(LocationServices.API)
	        .build();
	}
	@Override 
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnected(Bundle arg0) {
		Toast.makeText(getApplicationContext(), "Connected to Google API services",  Toast.LENGTH_SHORT).show();
		Log.d("test1", "Hit On Connected method");
		lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (lastLocation != null) {
        	latitudenum = lastLocation.getLatitude();
        	longitudenum = lastLocation.getLongitude();
            latitude.setText("latitude:" + String.valueOf(latitudenum));
            longitude.setText("longitude:" + String.valueOf(longitudenum));
        }
		
	}
	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
}
