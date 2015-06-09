package com.example.test25;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private ImageButton shoppingList, notifications, funthings, finances;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		shoppingList = (ImageButton)findViewById(R.id.groceries);
		notifications = (ImageButton)findViewById(R.id.notifications);
		funthings = (ImageButton)findViewById(R.id.funthings);
		finances = (ImageButton)findViewById(R.id.finances);
		funthings.setOnClickListener(this);
		finances.setOnClickListener(this);
		shoppingList.setOnClickListener(this);
		notifications.setOnClickListener(this);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.groceries:
			Intent i = new Intent(this, GroceriesPage.class);
			startActivity(i);
			break;
		case R.id.notifications:
			Intent x = new Intent(this, NotificationsPage.class);
			startActivity(x);
			break;
		case R.id.funthings:
			Intent y = new Intent(this, FunThingsPage.class);
			startActivity(y);
			break;
		case R.id.finances:
			Intent z = new Intent(this, FinancesPage.class);
			startActivity(z);
			break;
		}
		
	}
	
}
