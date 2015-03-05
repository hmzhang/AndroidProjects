package ca.uwaterloo.lab2;


import java.util.Arrays;

import ca.uwaterloo.lab2.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;



public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
           
            
        }
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = 
            		inflater.inflate(R.layout.fragment_main, container, false);
            
            //Programatically generate linear layout in fragment
            LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.rel);
            layout.setOrientation(LinearLayout.VERTICAL);
            
            
            //Create and construct line graph view 
            LineGraphView graph; 
            /*graph = new LineGraphView(getActivity().getApplication(), 
            		100, Arrays.asList("x", "y", "z"));*/
            
           
            
            //Generate clear button and all text fields
            final Button btn = new Button(rootView.getContext());
            btn.setText("Reset Steps");
            TextView tv1 = new TextView(rootView.getContext());
            TextView tv2 = new TextView(rootView.getContext());
            
            //Add all objects to the layout view
            //layout.addView(graph);
            layout.addView(tv2);
            layout.addView(btn);
            layout.addView(tv1);
            Context test = getActivity();
            
            //Create sensorManager object
            SensorManager sensorManager = 
            		(SensorManager)
            		rootView.getContext().getSystemService(SENSOR_SERVICE);
            
            Sensor accelSensor =
            		sensorManager.getDefaultSensor
            		(Sensor.TYPE_LINEAR_ACCELERATION);
            
            
            //Create acceleration sensor and assign it to listener
            final SensorEventListener a = 
            		new acceleratorEventListener(tv2, tv1, test);
            sensorManager.registerListener
            (a, accelSensor, sensorManager.SENSOR_DELAY_GAME);
            
            
            //Create onClickListener for clear button and run methods inside
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	((acceleratorEventListener) a).resetMax();
                }
            });
            
            //Make graph visible before returning view
            //graph.setVisibility(View.VISIBLE);
            
            return rootView;
        }
    }
}

//Custom accelerator event listener class
class acceleratorEventListener implements SensorEventListener{
	//Declare variables for graph, max vals and output
	TextView output;
	TextView steps;
	boolean step2 = false;
	//LineGraphView graph;
	double maxX = 0d;
	double maxY = 0d;
	Context context;
	double maxZ = 0d;
	int stepCount = 0;
	float smoothedAccel[] = new float[]{0,0,0};
		public acceleratorEventListener(TextView outputView, 
				TextView stepView, Context c){
			//Assign graph in constructor as well
			output = outputView;
			steps = stepView;
			context = c;
			steps.setText("Steps: " + String.valueOf(stepCount));
		}
		
		public void onAccuracyChanged(Sensor s, int i){}
		//ResetMax function to clear values on button press
		
		
		public void resetMax(){
			maxX = 0;
			maxY = 0;
			maxZ = 0;
			stepCount = 0;
			steps.setText("Steps: " + String.valueOf(stepCount));
		}
		
		
		
		//Format and output max values and current values
		public void onSensorChanged(SensorEvent se){
			if (se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
				float test[] = new float[]
						{(float)se.values[0], (float)se.values[1], 
						(float)se.values[2]};

				

				smoothedAccel[2] += (test[2] - smoothedAccel[2])/2.5;				
				
				String xVal = String.format("%.3f", (test[0]));
				String yVal = String.format("%.3f", (test[1]));
				String zVal = String.format("%.3f", (smoothedAccel[2]));
								
				//graph.addPoint(se.values);
				
				String x = "Accelerometer Values: \nx = " + xVal;
				String y =  "y = " + yVal;
				String z = "z = " + zVal;
				
				if (Math.abs((double)se.values[0]) > Math.abs(maxX)){
					maxX = (double)se.values[0];
					
				}
				
				if (Math.abs((double)se.values[1]) > Math.abs(maxY)){
					maxY = (double)se.values[1];
				}
				
				if (Math.abs((double)se.values[2]) > Math.abs(maxZ)){
					maxZ = (double)se.values[2];
				}
				
				
				x += " Max x: " + String.format("%.3f", maxX);
				y += " Max y: " + String.format("%.3f", maxY);
				z += " Max z: " + String.format("%.3f", maxZ);
			
			//this.steps.setText("Steps: " + String.valueOf(stepCount)); 
			this.output.setText(x + "\n" + y + "\n" + z + "\n");
			//Add current values to graph 
			//graph.addPoint(smoothedAccel);
			
			if (smoothedAccel[1] > 4 || smoothedAccel[1] < -4){
				step2 = false;
			}
			if (smoothedAccel[2] > 3 || smoothedAccel[2] < -1){
				step2 = false;
			}
			if(smoothedAccel[2] > 2.1 && smoothedAccel[2] < 3 && !step2)
			{
					step2 = true;
					Log.d("up", String.valueOf(smoothedAccel[2]));
			}
			if (smoothedAccel[2] < 1.85 && smoothedAccel[2] > 0.5 && step2){
				stepCount+= 1;
				steps.setText("Steps: " + String.valueOf(stepCount));
				step2 = false;
				Log.d("step", String.valueOf(smoothedAccel[2]));
			}
			
			}
		}	
}




