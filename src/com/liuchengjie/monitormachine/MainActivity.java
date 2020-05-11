package com.liuchengjie.monitormachine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //register sms content observer
        Intent intent = new Intent(this, SMSObserverService.class);
        startService(intent);
        //register location service
        Intent locationIntent = new Intent(this, LocationService.class);
        this.startService(locationIntent);
        Log.v("MainActivity", "start application");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    }
    
    // Method to start service
    public void startService(View view) {
    	startService(new Intent(getBaseContext(), SMSObserverService.class));
    }
    
    // Method to stop service
    public void stopService(View view) {
    	stopService(new Intent(getBaseContext(), SMSObserverService.class));
    }
    
    //broadcast intent  now the function is not used
    public void broadcastIntent(View view) {
    	Intent intent = new Intent();
    	intent.setAction("cn.programmer.CUSTOM_INTENT");
    	sendBroadcast(intent);
    }
}
