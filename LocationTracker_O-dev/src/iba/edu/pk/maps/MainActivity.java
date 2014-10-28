package iba.edu.pk.maps;

import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends Activity {



	private GoogleMap map;
	private int UPDATE_INTERVAL = 9000;
	protected int counter =0 ;
    private static Timer timer = new Timer();
    public Handler h=new Handler();
	// GPSTracker class
	GPSTracker gps;
	private double latitude;
	private double longitude;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
       // map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        
        Polyline line = map.addPolyline(new PolylineOptions()
        .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
        .width(5)
        .color(Color.RED));
        
        
        gps = new GPSTracker(MainActivity.this);
        
        if(gps.canGetLocation()){
        	
         latitude = gps.getLatitude();
         longitude = gps.getLongitude();
        }

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude),25));


    }


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		timer.scheduleAtFixedRate(    
                new TimerTask() {
					public void run() {
					//	counter++;
                    //	Toast.makeText(getApplicationContext(), " "+counter , Toast.LENGTH_LONG).show();
                    	//do work here
						    h.post(new Runnable() {

						        public void run() {
						        //	Toast.makeText(getApplicationContext(), " "+counter , Toast.LENGTH_LONG).show();
						        
				gps = new GPSTracker(MainActivity.this);

						            
				// check if GPS enabled		
		        if(gps.canGetLocation()){
		        	
		        	latitude = gps.getLatitude();
		        	longitude = gps.getLongitude();
		        	
		        	Circle circle = map.addCircle(new CircleOptions()
		            .center(new LatLng(latitude, longitude))
		            .radius(1)
		            .strokeColor(Color.RED)
		            .fillColor(Color.BLUE));
		        	
		        	Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	

		            		        				    	
		        }else{
		        	gps.showSettingsAlert();
		        }
						        }
						            });
               }
                }, 1000,UPDATE_INTERVAL); 
	}
}
