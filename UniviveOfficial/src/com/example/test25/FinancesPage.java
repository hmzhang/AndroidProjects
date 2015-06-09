package com.example.test25;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class FinancesPage extends Activity implements OnClickListener {
	private TableLayout financetable;
	private TextView additemtv;
	private Button additemtolist, removeall, savechanges;
	private EditText whatitemtoadd, itemprice;
	private int colourtracker = 0;
	private int itemtracker = 1;
	private SharedPreferences shared;
	private int prefstracker;
	private Editor editor, editor1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finances_page);
		Log.d("test1", "hits onCreate");
		shared = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
		financetable = (TableLayout) findViewById(R.id.finance_table);
		additemtolist = (Button) findViewById(R.id.additems);
		additemtolist.setOnClickListener(this);
		whatitemtoadd = (EditText)findViewById(R.id.editText1);
		itemprice = (EditText)findViewById(R.id.editText2);
		removeall = (Button) findViewById(R.id.deleteallitems);
		removeall.setOnClickListener(this);
		savechanges = (Button) findViewById(R.id.savechanges);
		savechanges.setOnClickListener(this);
		Toast.makeText(getApplicationContext(), String.valueOf(shared.getInt("NumberRowsFinance", 2342352)), Toast.LENGTH_LONG).show();
		prefstracker = shared.getInt("NumberRowsFinance", 0);
		if(prefstracker!= 0){
			itemtracker = shared.getInt("NumberRowsFinance", 1);
			Log.d("test1", "hits if statement");
			for(int x = 0; x < prefstracker; x++){
				TableRow tr1 = new TableRow(this);
				TextView n = new TextView(this);
				n.setGravity(Gravity.CENTER | Gravity.BOTTOM);
				n.setTextSize(32);
				n.setText((shared.getString("finance" + String.valueOf(x), "")));
				n.setTextColor(getTextColour(colourtracker));
				tr1.addView(n);
				tr1.setPaddingRelative(250, 20, 0, 20); //setPaddingRelative(left, top, right, bottom) 
				tr1.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL));
				financetable.addView(tr1, new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL));
				
			}
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.additems:
			Log.d("test1", "hits case");
			TableRow tr1 = new TableRow(this);
			String i = itemtracker + ". " + whatitemtoadd.getText().toString() +"   " + itemprice.getText().toString();
			if(!i.matches("")){
			additemtv = new TextView(this);
			additemtv.setText(i);
			additemtv.setTextSize(32);
			additemtv.setGravity(Gravity.CENTER | Gravity.BOTTOM);
    		additemtv.setTextColor(getTextColour(colourtracker));
			tr1.addView(additemtv);
			tr1.setPaddingRelative(250, 20, 0, 20); //setPaddingRelative(left, top, right, bottom) 
			tr1.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL));
			financetable.addView(tr1, new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL));
			itemtracker ++;
			
		}
			else{
				Toast.makeText(getApplicationContext(), "Please enter a valid item.", Toast.LENGTH_SHORT).show();
			}
			break;
			//issue after pressing save then delete
		case R.id.deleteallitems:
			int count = financetable.getChildCount();
			itemtracker = 1;
			for (int x = 0; x < count; x++) {
			   /* View child = grocerieslist.getChildAt(x);
			    if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();*/
				financetable.removeViewAt(x);
			}
			if(shared.getInt("NumberRowsFinance", -1) != -1){
				editor = shared.edit();
			editor.remove("NumberRowsFinance");
			editor.commit();
			Toast.makeText(getApplicationContext(), String.valueOf(financetable.getChildCount()), Toast.LENGTH_SHORT).show();
			break;
			}
			else{
				Toast.makeText(getApplicationContext(), "No items to delete", Toast.LENGTH_SHORT).show();
				break;
			}
		
		case R.id.savechanges:
			editor1 = shared.edit();
			for(int y = 0; y < financetable.getChildCount(); y++){
				TableRow r = (TableRow) financetable.getChildAt(y);
				TextView m = (TextView) r.getChildAt(0);
				String t = m.getText().toString();
				editor1.putString("finance" + String.valueOf(y), t);
			}
			editor1.putInt("NumberRowsFinance", financetable.getChildCount());
			editor1.commit();
			Toast.makeText(getApplicationContext(), String.valueOf(financetable.getChildCount()), Toast.LENGTH_SHORT).show();
			break;
			
	}
}
	public int getTextColour(int colourtracker){
		colourtracker ++;
		if(colourtracker ==0){
			return Color.RED;
		}
		else if(colourtracker ==1){
			return Color.argb(255, 255, 191, 0);
		}
		else if(colourtracker ==2){
			return Color.YELLOW;
		}
		else if(colourtracker ==3){
			return Color.GREEN;
			}
		else if(colourtracker ==4){
			return Color.BLUE;
		}
		else if(colourtracker == 5){
			colourtracker = 0;
			return Color.MAGENTA;
		}
		return Color.BLACK;
	}
	
}