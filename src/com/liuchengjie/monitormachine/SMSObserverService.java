package com.liuchengjie.monitormachine;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class SMSObserverService extends Service{

	private static final String TAG = "SMSObserverService";
	private ContentResolver contentResolver;
	private SMSObserver smsObserver;
	
	public SMSObserverService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		Log.v(TAG, "start the SMSObserverService");
		contentResolver = getContentResolver();
		smsObserver = new SMSObserver(new Handler(), contentResolver);
		contentResolver.registerContentObserver(Uri.parse("content://sms"), true, smsObserver);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG, "here start the SMSObserverService");
		return startId;
	}
	@Override
	public void onDestroy(){
		contentResolver.unregisterContentObserver(smsObserver);
	}

}
