package com.uapp.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.uapp.util.HttpConnection;

public class SigninTask extends  AsyncTask{
	Activity activity;
	String username;
	String password;
	String resp;
	
	public SigninTask(Activity activity,String username, String password){
		
		this.activity = activity;
		this.username = username;
		this.password = password;
		
	}
	@Override
	protected Object doInBackground(Object... params) {
		String url = "http://10.0.2.2/uapp/login.php";
		String data = "username=" + username+ "&password=" + password;
		Log.i("login ", data);
		HttpConnection con = new HttpConnection();
		Log.i("HTtpConn ", data);
		resp = con.httpPostString(url,data);
		Log.i("login response ", resp);
		
		
		return resp;
	}
	
	

}
