package com.liuchengjie.monitormachine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MonitorService extends Service{
	

	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressLint("NewApi") @Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Let it continue running until it is stopped
		Toast.makeText(this, "service is start running", Toast.LENGTH_LONG).show();
		//here start the service
		Log.v("Cat", "Here start the service and come into onStartCommand function");
		Log.v("Cat", "Action ====> " + intent.getAction());
		Log.v("Cat", "===============");
//		//Log.v("Cat", intent.getStringExtra("broadcast_signal"));
//		//if(intent.getStringExtra("broadcast_signal").equals(SMS_RECEIVED)){
			Object[] pdus = (Object[])intent.getExtras().get("pdus");
//			Toast.makeText(this, pdus.toString(), Toast.LENGTH_LONG).show();
			String format = intent.getStringExtra("format");
			if(format==null){
				Log.v("Error", "format is null and set format to 3gpp");
				format = "3gpp";
			}
			int pdusLength = pdus.length;
			
			Log.v("Cat", String.valueOf(pdusLength));
			if(pdus != null && pdusLength != 0){
				SmsMessage[] messages = new SmsMessage[pdusLength];
				for(int i=0; i<pdusLength; i++){
					byte[] pdu = (byte[])pdus[i];
					messages[i] = SmsMessage.createFromPdu(pdu);
				}
				for(SmsMessage message : messages){
					final String messageBody = message.getMessageBody();
					final String sender = message.getOriginatingAddress();
					Date date = new Date(message.getTimestampMillis());
					SimpleDateFormat MyFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					final String datetime = MyFormat.format(date);
					Log.v("Cat", "body ====> " + messageBody + ";  sender: " + sender + ";   date: " + datetime);
//					Toast.makeText(this,  "body ====> " + messageBody + ";  sender: " + sender , Toast.LENGTH_LONG).show();
					/*
					 * 得到message body 接下来打开socket，将结果发送给服务器
					 * */
					
					Thread t = new Thread(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							try{
								URL url = new URL("http://192.168.0.101:8080/sms/add");
								HttpURLConnection connection = (HttpURLConnection) url.openConnection();
								connection.setRequestMethod("POST");
								connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
								connection.setDoOutput(true);
								connection.setUseCaches(false);
								connection.setRequestProperty("Charset", "UTF-8");
								//set timeout duration
								connection.setConnectTimeout(8000);
								//set read timeout
								connection.setReadTimeout(8000);
								//open output stream
								OutputStream os = connection.getOutputStream();
								Log.i("messageBody", messageBody);
								System.out.println("============");
								System.out.println(messageBody);
								Log.v("Cat", sender);
								//make json params
								JSONObject json = new JSONObject();
								json.put("send_phone_number", sender);
								//here should change
								json.put("receive_phone_number", "15580087385");
								json.put("operate_time", datetime);
								json.put("sms_content", messageBody);
								json.put("operate_kind", 1);
								Log.v("Cat", json.toString());
								//send this request
								os.write(json.toString().getBytes());
								os.flush();
								InputStream is = connection.getInputStream();
								BufferedReader br = new BufferedReader(new InputStreamReader(is));
								//get message from server
								String result = br.readLine();
								Log.v("status_code", String.valueOf(connection.getResponseCode()));
								Log.v("return_data", result);
								is.close();
								os.close();
								br.close();
								connection.disconnect();
							} catch(Exception e) {
								e.printStackTrace();
							}
						}
						
					});
					t.start();
				}
			}
//		} else if(intent.getStringExtra("broadcast_signal").equals(BOOT_COMPLETE)){
//			Log.v("Cat", "BOOT_COMPLETE signal detected! ");
//			
//		}
		
		return startId;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "service is stop", Toast.LENGTH_LONG).show();
	}

}
