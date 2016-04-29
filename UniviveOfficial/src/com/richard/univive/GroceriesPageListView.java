package com.richard.univive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.test25.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class GroceriesPageListView extends Activity{

    private ListView listview;
    private ArrayList<String> groceries;
    private ArrayAdapter<String> ArrayAdapter;
    private Button additemtolist, removeall, savechanges;
    private EditText whattoaddtolist;
    private SharedPreferences prefs;
    private Editor editor;
    private int tracker, colourtracker;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groceries_listview);
        colourtracker = 0;
        listview = (ListView) findViewById(R.id.listview1);
		//additemtolist = (Button) findViewById(R.id.addgroceries1);
		savechanges = (Button) findViewById(R.id.savechanges1);
		removeall = (Button) findViewById(R.id.deleteallgroceries1);
        groceries = new ArrayList<String>();
        whattoaddtolist = (EditText)findViewById(R.id.editText2);
        //ArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groceries);
        ArrayAdapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1, groceries){

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
        tracker = prefs.getInt("NumberRows", 0);
        for(int x = 0; x < tracker; x++){
        	groceries.add(prefs.getString(Integer.toString(x), " "));
        }
        ArrayAdapter.notifyDataSetChanged();
    }
    
    
    public void addGroceries(View v){
    	Toast.makeText(getApplicationContext(), "Hit onclick", Toast.LENGTH_SHORT).show();
		groceries.add(whattoaddtolist.getText().toString());
		ArrayAdapter.notifyDataSetChanged();
    }
    public void deleteAllGroceries(View v){
    	ArrayAdapter.clear();
		ArrayAdapter.notifyDataSetChanged();
    }
    public void saveChanges(View v){
    	editor = prefs.edit();
    	for(int y = 0; y < groceries.size(); y++){
			editor.putString(Integer.toString(y), groceries.get(y));
		}
		editor.putInt("NumberRows", groceries.size());
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
}

