package com.richard.univive;

import java.text.NumberFormat;
import java.util.ArrayList;

import com.example.test25.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class FinancesPage extends Activity {
	 private ListView listview;
	    private ArrayList<String> finances;
	    private ArrayAdapter<String> ArrayAdapter;
	    private Button additemtolist, removeall, savechanges;
	    private EditText amountspent, itemspenton;
	    private SharedPreferences prefs;
	    private Editor editor;
	    private int tracker, colourtracker;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.finances_page);
	        colourtracker = 0;
	        listview = (ListView) findViewById(R.id.listview2);
	        finances = new ArrayList<String>();
	        itemspenton = (EditText)findViewById(R.id.itemspenton);
	        amountspent = (EditText)findViewById(R.id.amountspent);
	        amountspent.addTextChangedListener(new CurrencyTextWatcher());
	        //ArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, finances);
	        ArrayAdapter=new ArrayAdapter<String>(
	                this,android.R.layout.simple_list_item_1, finances){

	            @Override
	            public View getView(int position, View convertView,
	                    ViewGroup parent) {
	                View view =super.getView(position, convertView, parent);

	                TextView textView=(TextView) view.findViewById(android.R.id.text1);

	                textView.setTextColor(getTextColour());

	                return view;
	            }
	        };
	        listview.setAdapter(ArrayAdapter);
	        prefs = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
	        tracker = prefs.getInt("FinanceNumberRows", 0);
	        for(int x = 0; x < tracker; x++){
	        	finances.add(prefs.getString("FinanceItem" + Integer.toString(x), " "));
	        }
	        ArrayAdapter.notifyDataSetChanged();
	    }
	    
	    
	    public void addItem(View v){
	    	String dollarsSpent = amountspent.getText().toString();
			finances.add(itemspenton.getText().toString() + "    " + amountspent.getText().toString());
			ArrayAdapter.notifyDataSetChanged();
	    }
	    public void deleteAllItems(View v){
	    	ArrayAdapter.clear();
			ArrayAdapter.notifyDataSetChanged();
	    }
	    public void saveItemChanges(View v){
	    	editor = prefs.edit();
	    	for(int y = 0; y < finances.size(); y++){
				editor.putString("FinanceItem" + Integer.toString(y), finances.get(y));
			}
			editor.putInt("FinanceNumberRows", finances.size());
			editor.commit();
			Toast.makeText(getApplicationContext(), "Changes Saved!", Toast.LENGTH_SHORT).show();
	    }
	    public int getTextColour(){
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
	    class CurrencyTextWatcher implements TextWatcher {

	        boolean mEditing;

	        public CurrencyTextWatcher() {
	            mEditing = false;
	        }

	        public synchronized void afterTextChanged(Editable s) {
	            if(!mEditing) {
	                mEditing = true;

	                String digits = s.toString().replaceAll("\\D", "");
	                NumberFormat nf = NumberFormat.getCurrencyInstance();
	                try{
	                    String formatted = nf.format(Double.parseDouble(digits)/100);
	                    s.replace(0, s.length(), formatted);
	                } catch (NumberFormatException nfe) {
	                    s.clear();
	                }

	                mEditing = false;
	            }
	        }

	        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

	        public void onTextChanged(CharSequence s, int start, int before, int count) { }

	    }
}