package com.uapp.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.uapp.tasks.FeedsTask;
import com.uapp.util.Globals;

public class FeedsActivity extends Activity {
	ListView listView;

	ImageView settingImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feeds);
		listView = (ListView) findViewById(R.id.feedsListView);
		
		FeedsTask feedsTask = new FeedsTask(FeedsActivity.this, Globals.branchId, Globals.depId);
		int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
		if(SDK_VERSION >= 11){
			feedsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}else{
			feedsTask.execute();
		}
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(FeedsActivity.this, Globals.feedList,android.R.layout.two_line_list_item,new String[] { "feedstitle","feedsText" }, new int[] { android.R.id.text1, android.R.id.text2 });
		listView.setAdapter(simpleAdapter);	
	
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feeds, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			
			Intent intent = new Intent(FeedsActivity.this, SettingsActivity.class);
			startActivity(intent); 
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void parseFeeds(String response) {
		List<Map<String, String>> feedList = new ArrayList<Map<String, String>>();

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
				feedList.add(createFeed("feedstitle",title,"feedsText",outPut));
			}
		} catch (JSONException e) {
			System.out.println(e.toString());
			Toast.makeText(FeedsActivity.this, "Error" + e.toString(),	Toast.LENGTH_SHORT).show();
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(FeedsActivity.this, feedList,android.R.layout.two_line_list_item,new String[] { "feedstitle","feedsText" }, new int[] { android.R.id.text1, android.R.id.text2 });
		listView.setAdapter(simpleAdapter);
	}

	private HashMap<String, String> createFeed(String name1, String data1,String name2, String data2) {
		HashMap<String, String> feedDetails = new HashMap<String, String>();
		feedDetails.put(name1, data1);
		feedDetails.put(name2, data2);
		return feedDetails;
	}
	
}
