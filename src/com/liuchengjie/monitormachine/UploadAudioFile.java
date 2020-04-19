package com.liuchengjie.monitormachine;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import android.util.Log;

public class UploadAudioFile implements Runnable{

	private static final String TAG = "UploadAudioFile";
	private static final int TIME_OUT = 1000 * 1000; //timeout duration
	private static final String CHARSET = "utf-8"; //file encode
	
	private File file;
	private String url;
	
	public UploadAudioFile(File file, String url) {
		// TODO Auto-generated constructor stub
		this.file = file;
		this.url = url;
	}
	
	public int UploadFile() {
		int statusCode = 0;
		String BOUNDARY = UUID.randomUUID().toString();
		String PREFIX = "--";
		String LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data";
		try{
			URL url = new URL(this.url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10 * TIME_OUT);
			conn.setConnectTimeout(2 * TIME_OUT);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Charset", CHARSET);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
			if(file != null){
				DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				
				sb.append("Content-Disposition: form-data; name=\"audio_file\"; filename=\""+file.getName()+"\""+LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
				sb.append(LINE_END);
				Log.v(TAG, sb.toString());
				dos.write(sb.toString().getBytes());
				InputStream is  = new FileInputStream(file);
				byte[] bytes = new byte[2048];
				int len = 0;
				while((len = is.read(bytes)) != -1) {
					dos.write(bytes, 0, len);
				}
				is.close();
				dos.write(LINE_END.getBytes());
				dos.write((PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes());
				dos.flush();
				statusCode = conn.getResponseCode();
				
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return statusCode;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int status_code = UploadFile();
		Log.v(TAG, "status_code: " + String.valueOf(status_code));
	}

}
