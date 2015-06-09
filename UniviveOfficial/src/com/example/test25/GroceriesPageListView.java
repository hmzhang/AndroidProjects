package com.example.test25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

public class GroceriesPageListView extends Activity {

    ListView listview;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groceries_listview);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(new yourAdapter(this, new String[] { "data1",
                "data2" }));
    }
}

class yourAdapter extends BaseAdapter {

    Context context;
    String[] data;
    private static LayoutInflater inflater = null;

    public yourAdapter(Context context, String[] data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);
        TextView text = (TextView) vi.findViewById(R.id.text);
        text.setText(data[position]);
        return vi;
    }
}
