package com.liuchengjie.monitormachine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MonitorPhoneReceiver extends BroadcastReceiver {
	
	private static final String TAG = "MonitorPhoneReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "Intent SMS Detected.", Toast.LENGTH_LONG).show();
		Log.v("Cat", "Have listened Phone Broadcast");
		Log.v("Cat", intent.getAction());
//		Intent phoneIntent = intent;
//		phoneIntent.setClass(context, cls)
		String action = intent.getAction();
		String phoneNumber = "";
//		String myNumber = "15580087385";
//		String startTime;
		//call out signal
		Log.v(TAG, "==========> " + action);
			if(action.equals(Intent.ACTION_NEW_OUTGOING_CALL)){
				// call out to others
				phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
				Intent outgoingService = new Intent(context, CallOutService.class);
				outgoingService.putExtra("outgoingNumber", phoneNumber);
				context.startService(outgoingService);
				//sTime = intent.getLongExtra(name, defaultValue)
			} else {
				Intent phoneService = new Intent(context, PhoneService.class);
				context.startService(phoneService);
			}

	}
	

}
