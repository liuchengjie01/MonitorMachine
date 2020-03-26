package com.liuchengjie.monitormachine;

import java.security.Provider;

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
		Log.v("Cat", intent.getAction());
		Intent serviceIntent = intent;
		serviceIntent.setClass(context, MonitorService.class);
//		serviceIntent.putExtra("broadcast_signal", intent.getAction());
		context.startService(serviceIntent);
	}

}
