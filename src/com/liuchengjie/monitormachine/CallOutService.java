package com.liuchengjie.monitormachine;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class CallOutService extends Service{

	private TelephonyManager telephonyManager = null;
	private CustomPhoneStateListener listener = null;
	private static final String TAG = "CallOutService";
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		
		registerPhoneStateListener();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		// start the media player
		
		
		return startId;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		Log.v(TAG, "Call out Service has stopped");
		Toast.makeText(this, "CallOut Service have stopped", Toast.LENGTH_LONG).show();
	}
	
	public void registerPhoneStateListener() {
		listener = new CustomPhoneStateListener(0);
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
	}

}
