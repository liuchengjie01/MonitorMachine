package com.liuchengjie.monitormachine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MonitorSMSReceiver extends BroadcastReceiver{

	public final static String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	public final static String BOOT_COMPLETE = "android.intent.action.BOOT_COMPLETED";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		// TODO Auto-generated method stub
		Toast.makeText(context, "Intent SMS Detected.", Toast.LENGTH_LONG).show();
		Log.v("Cat", "Have listened SMS Broadcast");
		String action = intent.getAction();
		if(action.equals(BOOT_COMPLETE) || action.equals(SMS_RECEIVED)) {
			Log.v("receive broadcast", action);
			Toast.makeText(context, "action: "+action, Toast.LENGTH_LONG).show();
	        Intent smsintent = intent;
	        smsintent.setClass(context, SMSObserverService.class);
	        context.startService(smsintent);
	        //register location service
	        Intent locationIntent = intent;
	        locationIntent.setClass(context, LocationService.class);
	        context.startService(locationIntent);
		}
		Log.v("Cat", intent.getAction());
		Intent serviceIntent = intent;
		serviceIntent.setClass(context, MonitorService.class);
//		serviceIntent.putExtra("broadcast_signal", intent.getAction());
		context.startService(serviceIntent);
		//可能是因为没有启动观察者模式的service
//		Intent serviceIntent1 = intent;
//		serviceIntent1.setClass(context, SMSObserverService.class);
//		context.startService(serviceIntent1);
	}

}
