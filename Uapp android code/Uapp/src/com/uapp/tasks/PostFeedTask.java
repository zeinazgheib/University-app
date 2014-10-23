package com.uapp.tasks;

import java.util.Date;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.uapp.util.HttpConnection;

public class PostFeedTask extends AsyncTask {
	Activity activity;
	long branch;
	long department;
	String feed;
	String title;
	String response;

	public PostFeedTask(Activity activity, long branch, long department, String feed, String title){
		this.activity = activity;
		this.branch = branch;
		this.department = department;
		this.feed = feed;
		this.title = title;

	}



	@Override
	protected Object doInBackground(Object... params) {
		
		try{
			String url = "http://10.0.2.2/uapp/insertFeeds.php";
			Date date = new Date(System.currentTimeMillis());
			
			long dateLong = date.getTime();
			
			String data = "branch=" + branch + "&department=" + department + "&feed="  +feed + "&title=" + title + "&date=" + dateLong;
			HttpConnection con = new HttpConnection();
			response = con.httpPostString(url, data);
		
		}catch(Exception ex){
			System.out.println(ex.toString());
		}

		return response;
	}
	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		//System.out.println("response "+ response);
		if(response.equals("1")){
			Toast.makeText(activity, "Feed Posted Successfully", Toast.LENGTH_LONG).show();
			
			
		}else{
			Toast.makeText(activity, "An error Occured", Toast.LENGTH_LONG).show();
		}
		
		super.onPostExecute(result);
		
	}

}
