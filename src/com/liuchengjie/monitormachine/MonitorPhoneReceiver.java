package com.liuchengjie.monitormachine;

import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MonitorPhoneReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "Intent SMS Detected.", Toast.LENGTH_LONG).show();
		Log.v("Cat", "Have listened Phone Broadcast");
		Log.v("Cat", intent.getAction());
//		Intent phoneIntent = intent;
//		phoneIntent.setClass(context, cls)
		String action = intent.getAction();
		String phoneNumber;
		String myNumber = "15580087385";
		String startTime;
		if(action.equals(Intent.ACTION_NEW_OUTGOING_CALL)){
			// call out to others
			phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			//sTime = intent.getLongExtra(name, defaultValue)
		} else {
			
		}
		HashMap<String, Object> h = new HashMap<String, Object>();
		
	}

}
