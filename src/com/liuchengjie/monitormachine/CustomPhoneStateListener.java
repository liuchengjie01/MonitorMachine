package com.liuchengjie.monitormachine;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CustomPhoneStateListener extends PhoneStateListener {

//	private String 
	private Context mContext;
	
	public CustomPhoneStateListener(Context context) {
		this.setmContext(context);
	}
	
	public CustomPhoneStateListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		super.onCallStateChanged(state, incomingNumber);
		switch(state) {
		//¹Ò¶Ï×´Ì¬
		case TelephonyManager.CALL_STATE_IDLE:
			//////
			Log.v("PhoneStateListener", "phone is IDLE");
			break;
		//½ÓÌý×´Ì¬
		case TelephonyManager.CALL_STATE_OFFHOOK:
			/////
			Log.v("PhoneStateListener", "phone is OFFHOOK");
			break;
		//ÏìÁå×´Ì¬
		case TelephonyManager.CALL_STATE_RINGING:
			////
			Log.v("PhoneStateListener", "phone is RINGING");
			break;
		}
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}
}
