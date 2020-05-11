package com.liuchengjie.monitormachine;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class LocationService extends Service{

	private LocationManager manager;
	private Location location;
	private String provider;
	private final String url = "http://192.168.0.101:8080/location/update";
	private static final String TAG = "LocationService";
	private double al = 0.0;
	private double lng = 0.0;
	private double lat = 0.0;
	
	public LocationService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.v(TAG, "Start location service");
		getApplicationContext();
		manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if(manager == null) {
			Log.v(TAG, "get LocationManager failed");
			return;
		}
		//create a new criteria
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		
		provider = manager.getBestProvider(criteria, true);
		Log.v(TAG, "provider: "+provider);
		location = manager.getLastKnownLocation(provider);
		if(location != null) {
			Log.v(TAG, "first location: " + location.getLatitude() + ", " + location.getLongitude());
		}
		
		manager.requestLocationUpdates(provider, 6 * 1000, 100, listener);
		
	}
	
	private LocationListener listener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			updateLocation(location);
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Log.v(TAG, "provider is enabled, It's " + provider);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Log.v(TAG, "provider is disabled, It's " + provider);
		}
		
	};
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		return startId;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
	
	private void updateLocation(Location location) {
		String latLong = "can not access your location";
		if(location == null) {
			Log.v("LocationListener", latLong);
			return;
		}
		this.location = location;
		lat = location.getLatitude();
		lng = location.getLongitude();
		al  = location.getAltitude();
		sendLocation();
	}
	
	public void sendLocation() {
		HashMap<String, Object> h = new HashMap<String, Object>();
		h.put("al", al);
		h.put("lat", lat);
		h.put("lng", lng);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = sdf.format(new Date(System.currentTimeMillis()));
		h.put("update_time", datetime);
		h.put("provider", provider);
		
		SendHttpRequest run = new SendHttpRequest(url, h);
		Thread t = new Thread(run);
		t.start();
	}

	public LocationManager getManager() {
		return manager;
	}

	public void setManager(LocationManager manager) {
		this.manager = manager;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
