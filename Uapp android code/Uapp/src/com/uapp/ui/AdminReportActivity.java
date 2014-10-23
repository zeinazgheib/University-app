package com.uapp.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.uapp.util.HttpConnection;

public class AdminReportActivity extends Activity {
	private String jsonResult;
	private String url = "http://10.0.2.2/uapp/feedreport.php";
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_report);
		listView = (ListView) findViewById(R.id.feedlist);
		accessWebService();
	}

	// Async Task to access the web
	private class JsonReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			String data = "isadmin=1";
			HttpConnection con = new HttpConnection();
			
			try {
				
				jsonResult = con.httpPostString(url, data);
			}catch(Exception ex){
				System.out.println( ex.toString());
			}
			return null;
		}


		@Override
		protected void onPostExecute(String result) {
			ListDrwaer();
		}
	}// end async task

	public void accessWebService() {
		JsonReadTask task = new JsonReadTask();
		// passes values for the urls string array
		task.execute(new String[] { url });
	}

	// build hash set for list view
	public void ListDrwaer() {
		List<Map<String, String>> feedList = new ArrayList<Map<String, String>>();

		try {
			System.out.println(jsonResult);
			JSONObject jsonResponse = new JSONObject(jsonResult);
			JSONArray jsonMainNode = jsonResponse.optJSONArray("feeds");
			for (int i = 0; i < jsonMainNode.length(); i++) {
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				String title = jsonChildNode.getString("feedtitle");
				long date = jsonChildNode.getLong("datetime");
				String bodytext = jsonChildNode.getString("feedtext");
				String id = jsonChildNode.getString("feedid");
				
				Date dateS = new Date(date);
				
				String outPut =   dateS + "\n" + bodytext ;
				feedList.add(createFeed("feedstitle",title,"feedsText",outPut));
			}
		} catch (JSONException e) {
			System.out.println(e.toString());
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, feedList,android.R.layout.two_line_list_item,new String[] { "feedstitle","feedsText" }, new int[] { android.R.id.text1, android.R.id.text2 });
		listView.setAdapter(simpleAdapter);
	}

	private HashMap<String, String> createFeed(String name1, String data1,String name2, String data2) {
		HashMap<String, String> feedDetails = new HashMap<String, String>();
		feedDetails.put(name1, data1);
		feedDetails.put(name2, data2);
		return feedDetails;
	}
}




