package com.example.lab4;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.lab4.*;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;



public class MainActivity extends ActionBarActivity {
  
	public float[] magArray;
	
	public float[] gravity;
	float rotation[] = new float[16];
	float orientation[] = new float[3];
	public float azimuth;
	double north = 0;
	double east = 0;
	static MainActivity n = new MainActivity();
	static public float filteredazimuth = 0;
	private static MapView mapView;
	private static NavigationalMap map;
	public static List<PointF> pList = new ArrayList<PointF>();
	static boolean ezPath = false;
	private static  String dirVal = "";
	private final float WALKCONST = 1.65f;
	private final static float STEPCONST = 0.75f;
	
	static float xPos;
	static float yPos;
	

	
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

    @Override
    public void onCreateContextMenu ( ContextMenu menu , View v , ContextMenuInfo menuInfo ) {
    super.onCreateContextMenu ( menu , v , menuInfo );
    mapView.onCreateContextMenu ( menu , v , menuInfo );
    }
    
    @Override
    public boolean onContextItemSelected (MenuItem item) {
    return super.onContextItemSelected (item) 
    		|| mapView.onContextItemSelected (item);
    }
    
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

            mapView = new MapView(rootView
            		.getContext(), 800, 800, 30
            		, 30);
            
            
            
            registerForContextMenu(mapView);
            
             map = MapLoader.loadMap(rootView.getContext().
            		getExternalFilesDir(null),
            		"Lab-room-peninsula.svg");
            
            mapView.setMap(map);
            
            final TextView tv3 = new TextView(rootView.getContext());
            mapView.addListener(new PositionListener(){

				@Override
				public void originChanged(MapView source, PointF loc) {
					// TODO Auto-generated method stub
					source.setUserPoint(loc);
					xPos = loc.x;
					yPos = loc.y;
					checkNoIntersect();
					
				}

				@Override
				public void destinationChanged(MapView source, PointF dest) {
					// TODO Auto-generated method stub
					checkNoIntersect();
					
				}
				
				public void checkNoIntersect(){
					pList.clear();
					mapView.setUserPath(pList);
					ezPath = false;
					if (map.calculateIntersections(mapView.getUserPoint()
							, mapView.getDestinationPoint()).size() ==0){
						PointF dest = mapView.getDestinationPoint();
						PointF curr = mapView.getUserPoint();
						pList.clear();
						PointF currPoint = new PointF(dest.x, curr.y);
						pList.add(curr);
						pList.add(currPoint);
						pList.add(dest);
						mapView.setUserPath(pList);
						ezPath = true;
						return;
					}
					path();
					tv3.setText(dirVal);
					float xTemp = mapView.getUserPoint().x;
					float yTemp = mapView.getUserPoint().y;
					PointF testP = new PointF(xTemp,yTemp);
					float xDest = mapView.getDestinationPoint().x;
					float yDest = mapView.getDestinationPoint().y;
	                PointF dest = mapView.getDestinationPoint();
					if (Math.abs(dest.x - testP.x) <= 1 && Math
							.abs(dest.y -testP.y) <= 1){
						tv3.setText("you reached");
					}
					}
            	
            	
            });
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
            layout.addView(mapView);
            layout.addView(tv3);
            //Create sensorManager objects
            SensorManager asm = 
            		(SensorManager)
            		rootView.getContext().getSystemService(SENSOR_SERVICE);
            
            SensorManager msm = 
            		(SensorManager)
            		rootView.getContext().getSystemService(SENSOR_SERVICE);
            
            SensorManager lsm = 
            		(SensorManager)
            		rootView.getContext().getSystemService(SENSOR_SERVICE);

            Sensor accelSensor = asm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            SensorEventListener acc = n.new accelerometerEventListener(tv1);
            asm.registerListener(acc, accelSensor, SensorManager.SENSOR_DELAY_GAME);
            
            Sensor mSensor = msm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            SensorEventListener mag = n.new MagneticSensorEventListener(tv1);
            msm.registerListener(mag, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

            Sensor lSensor = lsm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            final SensorEventListener lin = n.new acceleratorEventListener(tv1, tv2, tv3);
            lsm.registerListener(lin, lSensor, SensorManager.SENSOR_DELAY_GAME);

            
            //Create onClickListener for clear button and run methods inside
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	((acceleratorEventListener) lin).resetMax();
                	
                }
            });

            //Make graph visible before returning view
            //graph.setVisibility(View.VISIBLE);
            
            return rootView;
        }
    }

class accelerometerEventListener implements SensorEventListener{
	TextView output;
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	public accelerometerEventListener(TextView t){
		output = t;
	}
	
	@Override
	public void onSensorChanged(SensorEvent acc) {
		// TODO Auto-generated method stub
		if(acc.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			gravity = acc.values;
		}
	}
	
}


class MagneticSensorEventListener implements SensorEventListener{
	TextView output;

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public MagneticSensorEventListener(TextView t){
		output = t;
	}

	@Override
	public void onSensorChanged(SensorEvent mag) {
		// TODO Auto-generated method stub
		if (mag.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
			magArray = mag.values;
		}
		
	}
	
	
}

public static void ezPath(){
	dirVal = "";
	PointF dest = mapView.getDestinationPoint();
	PointF curr = mapView.getUserPoint();
	pList.clear();
	
	PointF currPoint = new PointF(dest.x, curr.y);
	pList.add(curr);
	pList.add(currPoint);
	int dist = (int) (VectorUtils.distance(curr,currPoint)*STEPCONST);
	if (curr.x >= dest.x){
		dirVal += "Walk West " + String.valueOf(dist) + " steps\n";
	}
	else{
		dirVal += "Walk East " + String.valueOf(dist) + " steps\n";
	}
	pList.add(dest);
	dist = (int) (VectorUtils.distance(dest, currPoint)*STEPCONST);
	if (curr.y >= dest.y){
		dirVal += "Walk North " + String.valueOf(dist) + " steps\n";
	}
	else
	{
		dirVal += "Walk South " + String.valueOf(dist) + " steps\n";
	}
	mapView.setUserPath(pList);
}

public static void path(){
	
	dirVal = "";
	PointF dest = mapView.getDestinationPoint();
	PointF curr = mapView.getUserPoint();
	pList.clear();

	PointF currPoint = new PointF(curr.x, (float)18.5);
	PointF destPoint = new PointF(dest.x, (float)18.5);
	pList.add(curr);
	pList.add(currPoint);
	int dist = (int) (VectorUtils.distance(curr,currPoint)*STEPCONST);
	if (curr.y  > 18.5){
		dirVal += "Walk North " + String.valueOf(dist) + " steps\n";
	}
	else{
		dirVal += "Walk South " + String.valueOf(dist) + " steps\n";
	}
	pList.add(destPoint);
	dist = (int) (VectorUtils.distance(currPoint, destPoint)*STEPCONST);
	if (dest.x > curr.x){
		dirVal += "Walk East " + String.valueOf(dist) + " steps\n";
	}
	else{
		dirVal += "Walk West " + String.valueOf(dist) + "steps\n";
	}
	pList.add(dest);
	dist = (int) (VectorUtils.distance(destPoint, dest)*STEPCONST);
	if (dest.y > 18.5){
		dirVal += "Walk North " + String.valueOf(dist) + " steps\n";
	}
	else{
		dirVal += "Walk South " + String.valueOf(dist) + " steps\n";
	}
	mapView.setUserPath(pList);
	
}


//Custom accelerator event listener class
class acceleratorEventListener implements SensorEventListener{
	//Declare variables for graph, max vals and output
	TextView output;
	TextView directions;
	TextView steps;
	boolean step2 = false;
	//LineGraphView graph;
	double maxX = 0d;
	double maxY = 0d;
	double maxZ = 0d;
	int stepCount = 0;
	float smoothedAccel[] = new float[]{0,0,0};
		public acceleratorEventListener(TextView outputView, 
				TextView stepView, TextView dir){
			//Assign graph in constructor as well
			output = outputView;
			steps = stepView;
			steps.setText("Steps: " + String.valueOf(stepCount));
			directions = dir;
		}
		
		public void onAccuracyChanged(Sensor s, int i){}
		//ResetMax function to clear values on button press
		
		
		public void resetMax(){
			maxX = 0;
			maxY = 0;
			maxZ = 0;
			stepCount = 0;
			north = 0;
			east = 0;
			steps.setText("Steps: " + String.valueOf(stepCount));
			directions.setText("");

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
			
			
			//rotation vector thing
			if (gravity != null && magArray != null){
				SensorManager.getRotationMatrix(rotation, null, gravity , magArray);
				SensorManager.getOrientation(rotation, orientation);
				azimuth = (float) orientation[0];
			}
			
			filteredazimuth = calculateFilteredAngle(azimuth, filteredazimuth);
			if (smoothedAccel[2] > 3 || smoothedAccel[2] < -1){
				step2 = false;
			}
			if(smoothedAccel[2] > 1.9 && smoothedAccel[2] < 3 && !step2)
			{
					step2 = true;
					//Log.d("up", String.valueOf(smoothedAccel[2]));
			}
			if (smoothedAccel[2] < 1.8 && smoothedAccel[2] > 0.5 && step2){
					
				north += Math.cos(filteredazimuth);
				east += Math.sin(filteredazimuth);
				
				step2 = false;
				String s = "North: " + north + "East: " + east + "Azimuth: " + filteredazimuth 
						+ "magArr: " + magArray[0] + "gravity: " + gravity[0];
				Log.d("step", s);
				
				
				
				
				
                yPos += (Math.cos((filteredazimuth)))*WALKCONST;
                xPos -= (Math.sin((filteredazimuth)))*WALKCONST;

                PointF testP = new PointF(xPos, yPos);
                PointF dest = mapView.getDestinationPoint();
                
                if (map.calculateIntersections(testP, 
                		mapView.getUserPoint()).size() == 0){
                	mapView.setUserPoint(xPos, yPos);	
    				if (!ezPath){
    					path();
    				}
    				else{
    					ezPath();
    				}
                	stepCount+= 1;
                }else{
                	yPos -= (Math.cos((filteredazimuth)))*WALKCONST;
                    xPos += (Math.sin((filteredazimuth)))*WALKCONST;
                    north -= Math.cos(filteredazimuth);
    				east -= Math.sin(filteredazimuth);
                }
                
                directions.setText(dirVal);
				
				if (Math.abs(dest.x - testP.x) <= 1 && Math
						.abs(dest.y -testP.y) <= 1){
					directions.setText("you reached");
				}
				
				
				Log.d("VALS", testP.x + " " + dest.x);
				
				
			}
			steps.setText("Steps: " + String.valueOf(stepCount));
			output.setText(String.format("North:" + (north) + "\nEast:" + 
					(east) + "\n Azimuth:" + filteredazimuth)); 
			}
		}}
private float restrict(float Angle){
    while(Angle>=Math.PI) Angle-= 2*Math.PI;
    while(Angle<-Math.PI) Angle+= 2*Math.PI;
    return Angle;
}

//x is a raw angle value from getOrientation(...)
//y is the current filtered angle value
private float calculateFilteredAngle(float x, float y){ 
    final float alpha = 2.5f;
    float diff = x-y;

    //here, we ensure that abs(diff)<=180
    diff = restrict(diff);

    y += alpha*diff;
    //ensure that y stays within [-180, 180[ bounds
    y = restrict(y);
    return y;
}

}	



