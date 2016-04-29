package com.richard.univive;

import com.richard.univive.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity implements OnClickListener, ViewFactory {
	private ImageButton shoppingList, notifications, funthings, finances;
	private ImageSwitcher imgswitch;
	int imgs[]={R.drawable.uwaterloo1,R.drawable.uwaterloo};
	private Handler customHandler;
	private int imgslength, currindex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LayoutInflater in = getLayoutInflater() ;
	   // View layout = in.inflate(R.layout.activity_main, (ViewGroup) findViewById ( R.id.imgswitchlayout ) ) ;
		shoppingList = (ImageButton)findViewById(R.id.groceries);
		notifications = (ImageButton)findViewById(R.id.notifications);
		funthings = (ImageButton)findViewById(R.id.funthings);
		finances = (ImageButton)findViewById(R.id.finances);
		funthings.setOnClickListener(this);
		finances.setOnClickListener(this);
		shoppingList.setOnClickListener(this);
		notifications.setOnClickListener(this);
		currindex = 0;
		imgslength=imgs.length;
		imgswitch = (ImageSwitcher) findViewById(R.id.imageSwitcher1);
		imgswitch.setFactory(this);
		if(imgswitch!= null){
			imgswitch.setImageResource(R.drawable.uwaterloo1);
		}
		imgswitch.setInAnimation(this,android.R.anim.slide_in_left);
		imgswitch.setOutAnimation(this,android.R.anim.slide_out_right);  
		Gallery gallery = (Gallery) findViewById(R.id.gallery1);
		gallery.setAdapter(new ImageAdapter(this));
		
		gallery.setOnItemClickListener(new OnItemClickListener() {
 
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				imgswitch.setImageResource(imgs[arg2]);
			}
		});
		   customHandler = new android.os.Handler();
           customHandler.postDelayed(updateTimerThread, 0);
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
			Intent i = new Intent(this, GroceriesPageListView.class);
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
	@Override
	public View makeView() {
	    ImageView imageView = new ImageView(this);
	    imageView.setBackgroundColor(0xFF000000);
	    imageView.setScaleType(ImageView.ScaleType.CENTER);
	    imageView.setLayoutParams(
	        new ImageSwitcher.LayoutParams(
	            ImageSwitcher.LayoutParams.MATCH_PARENT,
	            ImageSwitcher.LayoutParams.WRAP_CONTENT));
	    return imageView;
	}
	public class ImageAdapter extends BaseAdapter {
		 
		private Context ctx;
 
		public ImageAdapter(Context c) {
			ctx = c; 
		}
 
		public int getCount() {
 
			return imgs.length;
		}
 
		public Object getItem(int arg0) {
 
			return arg0;
		}
 
		public long getItemId(int arg0) {
 
			return arg0;
		}
 
		public View getView(int arg0, View arg1, ViewGroup arg2) {
 
			ImageView iView = new ImageView(ctx);
			iView.setImageResource(imgs[arg0]);
			iView.setScaleType(ImageView.ScaleType.FIT_XY);
			iView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.MATCH_PARENT,
		           LayoutParams.WRAP_CONTENT));
			return iView;
		}
	}
	private Runnable updateTimerThread = new Runnable()
	{
	        public void run()
	        {
	        	if(currindex<imgslength-1){
	        		currindex++;
	        		imgswitch.setImageResource(imgs[currindex]);
	        	}
	        	else{
	        		currindex=0;
	        		imgswitch.setImageResource(currindex);
	        	}
	            customHandler.postDelayed(this, 5000);
	        }
	};
 
}
