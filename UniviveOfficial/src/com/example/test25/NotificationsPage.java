package com.example.test25;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
public class NotificationsPage extends Activity implements OnClickListener{
	private Button submit;
	private TimePicker time;
	private DatePicker date;
	private EditText eventname;
	private Calendar startTime, endTime; //used later on
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notifications_page);
		submit = (Button) findViewById(R.id.testbutton);
		submit.setOnClickListener(this);
		time = (TimePicker) findViewById(R.id.timeedit);
		date = (DatePicker) findViewById(R.id.dateedit);
		eventname = (EditText) findViewById(R.id.eventedit);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.testbutton:
			//we gonna need if statements for empty shit?
			Intent calIntent = new Intent(Intent.ACTION_INSERT); 
			calIntent.setType("vnd.android.cursor.item/event");    
			calIntent.putExtra(Events.TITLE, eventname.getText().toString()); 
			calIntent.putExtra(Events.EVENT_LOCATION, "My Beach House"); 
			calIntent.putExtra(Events.DESCRIPTION, "A Pig Roast on the Beach"); 
			calIntent.setData(CalendarContract.Events.CONTENT_URI);
			GregorianCalendar calDate = new GregorianCalendar(2015, 5, 31);
			calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true); 
			calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 
			     calDate.getTimeInMillis()); 
			calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 
			     calDate.getTimeInMillis());
			calIntent.putExtra(Events.RRULE, "FREQ=WEEKLY;COUNT=10;WKST=SU;BYDAY=TU,TH");
			startActivity(calIntent);
		}
		
	}
}
