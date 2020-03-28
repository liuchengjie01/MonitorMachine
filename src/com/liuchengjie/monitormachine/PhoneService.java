package com.liuchengjie.monitormachine;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneService extends Service{

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
		
		
		return startId;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "Phone Service have stopped", Toast.LENGTH_LONG).show();
	}
	
	public void registerPhoneStateListener() {
		CustomPhoneStateListener listener = new CustomPhoneStateListener();
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
	}

}
