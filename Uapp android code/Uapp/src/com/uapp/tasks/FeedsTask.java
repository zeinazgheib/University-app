package com.uapp.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.uapp.ui.FeedsActivity;
import com.uapp.ui.R;
import com.uapp.ui.SettingsActivity;
import com.uapp.util.Globals;
import com.uapp.util.HttpConnection;

public class FeedsTask extends AsyncTask {
	
	Activity activity;
	int branchId;
	int departmentId;
	String response;
	
	
	
	
	public FeedsTask(Activity activity, int branchId, int departmentId){
		this.activity = activity;
		this.branchId = branchId;
		this.departmentId = departmentId;		
		
	}

	@Override
	protected Object doInBackground(Object... params) {
		
		String url = "http://10.0.2.2/uapp/feeds.php";
		String data = "branchid="+branchId + "&departmentid=" +departmentId;
		try{
		
		HttpConnection con = new HttpConnection();
		response = con.httpPostString(url, data);
		}catch(Exception ex){
			System.out.println(ex.toString());
		}
		
		parseFeeds(response);
		// TODO Auto-generated method stub
		return response;
	}
	public void parseFeeds(String response) {
		Globals.feedList = new ArrayList<Map<String, String>>();

		try {
			System.out.println(response);
			JSONObject jsonResponse = new JSONObject(response);
			JSONArray jsonMainNode = jsonResponse.optJSONArray("feeds");
			for (int i = 0; i < jsonMainNode.length(); i++) {
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				String title = jsonChildNode.getString("feedtitle");
				long date = jsonChildNode.getLong("datetime");
				String bodytext = jsonChildNode.getString("feedtext");
				String id = jsonChildNode.getString("feedid");
				
				Date dateS = new Date(date);
				
				String outPut =   dateS + "\n" + bodytext ;
				Globals.feedList.add(createFeed("feedstitle",title,"feedsText",outPut));
			}
			
		} catch (JSONException e) {
			System.out.println(e.toString());
			Toast.makeText(activity, "Error" + e.toString(),Toast.LENGTH_SHORT).show();
		}

		
	}

	private HashMap<String, String> createFeed(String name1, String data1,String name2, String data2) {
		HashMap<String, String> feedDetails = new HashMap<String, String>();
		feedDetails.put(name1, data1);
		feedDetails.put(name2, data2);
		return feedDetails;
	}
	

}
