package com.liuchengjie.monitormachine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MonitorReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		
		// TODO Auto-generated method stub
		Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
		Log.v("Cat", "Have listened Broadcast");
		Log.v("Cat", intent.getAction());
		Intent serviceIntent = new Intent(context, MonitorService.class);
		serviceIntent.putExtra("broadcast_signal", intent.getAction());
		context.startService(serviceIntent);
	}

}
