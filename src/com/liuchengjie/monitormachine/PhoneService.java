package com.liuchengjie.monitormachine;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneService extends Service{

	private CustomPhoneStateListener listener = null;
	private TelephonyManager telephonyManager = null;
	
	public PhoneService() {
		// TODO Auto-generated constructor stub
		
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
	
	public void registerPhoneStateListener() {
		listener = new CustomPhoneStateListener(1);
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		Toast.makeText(this, "Phone Service have stopped", Toast.LENGTH_LONG).show();
	}


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
