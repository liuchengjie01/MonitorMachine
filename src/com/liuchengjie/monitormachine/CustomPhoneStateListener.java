package com.liuchengjie.monitormachine;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CustomPhoneStateListener extends PhoneStateListener {

	// private String
	private Context mContext;
	private MediaRecorder mr;
	private SimpleDateFormat audioFormat;
	private int callKind;
	private static final String TAG = "CustomPhoneStateListener";
	private HashMap<String, Object> h = new HashMap<String, Object>();
	private File recordFile;
	private int isRecord = -1;

	public CustomPhoneStateListener(Context context) {
		this.setmContext(context);
		audioFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public CustomPhoneStateListener(int callKind) {
		// TODO Auto-generated constructor stub
		this.callKind = callKind;
		audioFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		super.onCallStateChanged(state, incomingNumber);
		Log.v(TAG, "callKind is " + String.valueOf(callKind));
		switch (state) {
		// ¹Ò¶Ï×´Ì¬
		case TelephonyManager.CALL_STATE_IDLE:
			// ////
			Log.v(TAG, "phone is IDLE");

			if (mr != null) {
				// 0 represent call out
				if (callKind == 0) {
					h.put("send_phone_number", "15580087385");
					h.put("receive_phone_number", incomingNumber);
					Log.v(TAG, incomingNumber);
				} else if (callKind == 1) {
					h.put("send_phone_number", incomingNumber);
					h.put("receive_phone_number", "15580087385");
					Log.v(TAG, incomingNumber);
				} else {
					Log.v(TAG, String.valueOf(callKind));
				}
				Log.v(TAG, String.valueOf(callKind));
				h.put("operate_kind", callKind);
				mr.stop();
				mr.reset();
				mr.release();
				mr = null;
				String stopTime = audioFormat.format(new Date(System
						.currentTimeMillis()));
				h.put("stop_time", stopTime);
				SendHttpRequest run = new SendHttpRequest(
						"http://192.168.0.101:8080/call/add", h);
				Thread t = new Thread(run);
				t.start();
				UploadAudioFile uaf = new UploadAudioFile(recordFile,
						"http://192.168.0.101:8080/call/upload");
				Thread t1 = new Thread(uaf);
				t1.start();
			}
			isRecord = -1;
			break;
		// ½ÓÌý×´Ì¬
		case TelephonyManager.CALL_STATE_OFFHOOK:

			// ///

			Log.v("PhoneStateListener", "phone is OFFHOOK and isRecord is "
					+ String.valueOf(isRecord));
			if (callKind == 0 && mr == null && isRecord == -1) {
				Log.v(TAG, "here callKind == 0 and mr = null");
				mr = new MediaRecorder();
				// set the input audio format
				mr.setAudioSource(MediaRecorder.AudioSource.MIC);
				// set the output audio format
				mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				// set the audio encode format
				mr.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
				// set the output FileName
				Date currentDate = new Date(System.currentTimeMillis());
				String dateTime = audioFormat.format(currentDate);
				h.put("start_time", dateTime);
				String filePath = "audio_" + dateTime + "_" + incomingNumber
						+ ".3gp";
				Log.v(TAG,
						"Environment Directory is "
								+ Environment.getExternalStorageDirectory());
				Log.v(TAG, "filePath is " + filePath);
				h.put("audio_file_name", filePath);
				recordFile = new File(
						Environment.getExternalStorageDirectory(), filePath);
				Log.v(TAG, "file path is " + recordFile.getAbsolutePath());
				mr.setOutputFile(recordFile.getAbsolutePath());
				try {
					mr.prepare();
					mr.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		// ÏìÁå×´Ì¬
		case TelephonyManager.CALL_STATE_RINGING:
			// //
			Log.v("PhoneStateListener", "phone is RINGING");
			if (callKind == 1 && mr == null) {
				mr = new MediaRecorder();
				// set the input audio format
				mr.setAudioSource(MediaRecorder.AudioSource.MIC);
				// set the output audio format
				mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				// set the audio encode format
				mr.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
				// set the output FileName
				Date currentDate = new Date(System.currentTimeMillis());
				String dateTime = audioFormat.format(currentDate);
				h.put("start_time", dateTime);
				String filePath = "audio_" + dateTime + "_" + incomingNumber
						+ ".3gp";
				Log.v(TAG,
						"Environment Directory is "
								+ Environment.getExternalStorageDirectory());
				Log.v(TAG, "filePath is " + filePath);
				h.put("audio_file_name", filePath);
				recordFile = new File(
						Environment.getExternalStorageDirectory(), filePath);
				Log.v(TAG, "file path is " + recordFile.getAbsolutePath());
				mr.setOutputFile(recordFile.getAbsolutePath());
				try {
					mr.prepare();
					mr.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (callKind == 0) {
				isRecord = 1;
			}
			break;
		default:
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
