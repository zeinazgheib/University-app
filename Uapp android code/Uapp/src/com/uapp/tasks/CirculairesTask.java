package com.uapp.tasks;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uapp.adapter.CirculaireListAdapter;
import com.uapp.adapter.FeedInfo;
import com.uapp.ui.R;
import com.uapp.util.HttpConnection;

public class CirculairesTask extends AsyncTask<Void, Void, Void> {
	
	//URL to be changed depending on the location of feeds.php file
	private final String myURL = "http://10.0.2.2/uapp/feeds.php";
	
	public static final String JSON_CIRCULAIRES_TITLE_ARRAY =  "circulars";

	public static final String JSON_STATUS =  "status";
	public static final String JSON_TITLE =  "title";
	public static final String JSON_DATE_TIME =  "datetime";
	public static final String JSON_SHORT_TEXT =  "shorttext";
	public static final String JSON_BODY_TEXT =  "bodytext";
	public static final String JSON_IMAGE_ICON =  "imageicon";

	private URL mURL;
	private HttpURLConnection mConnection;

	HttpURLConnection connection = null;
	URL url;
	private String rsp="";

	private Activity mContext;
	private ArrayList<FeedInfo> mList;
	private ProgressBar mProgressBar;
	private CirculaireListAdapter mAdapter;
	private TextView emptyView;
	private String[] bodyText;
	private ArrayList<String> bodyTextList = new ArrayList<String>();
	private String facId;
	private String isNewer;
	private String dateTime;
	private Button showMore;

	public CirculairesTask(Activity mContext, ArrayList<FeedInfo> mList, 
			ProgressBar mProgressBar, CirculaireListAdapter mAdapter, 
			TextView emptyView, ArrayList<String> bodyTextList, String facId,
			String isNewer, String dateTime, Button showMore){
		this.mContext = mContext;
		this.mList = mList;
		this.mProgressBar = mProgressBar;
		this.mAdapter = mAdapter;
		this.emptyView = emptyView;
		this.bodyTextList = bodyTextList;
		this.facId = facId;
		this.isNewer = isNewer;
		this.dateTime = dateTime;
		this.showMore = showMore;
	}

	@Override
	protected void onPreExecute() {
		if(mProgressBar != null){
			mProgressBar.setVisibility(View.VISIBLE);
		} 
		if(showMore != null){
			showMore.setVisibility(View.GONE);
		} 
		
		if(mList == null)
		{
			Toast.makeText(mContext, "There are no Feeds to be shown", Toast.LENGTH_SHORT).show();
		}
		super.onPreExecute();
	}

	@Override
	protected void onCancelled() {
		if(connection!=null){
			connection.disconnect();
			connection = null;
		}
		Log.d("onCanceled task", rsp);
		super.onCancelled();
	}


	@Override
	protected void onCancelled(Void result) {
		if(connection!=null){
			connection.disconnect();
			connection = null;
		}
		super.onCancelled(result);
	}

	@Override
	protected Void doInBackground(Void... params) {
		
		//showMore.setVisibility(View.GONE);
		
		/*ArrayList<Header_Param> headers = new ArrayList<Header_Param>();
		headers.add(new Header_Param("deviceid", "2"));
		headers.add(new Header_Param("branchid", "1"));
		headers.add(new Header_Param("facultyid", facId));
		headers.add(new Header_Param("isnewer", isNewer));
		headers.add(new Header_Param("datetime", dateTime)); */
		String parameters = "deviceid=2&branchid=1&facultyid="+facId+"&isnewer="+isNewer + "&datetime=" +dateTime;

		Log.i("here the doInBackground", "testing the do in background .... we are here");

		try {
			HttpConnection con = new HttpConnection();
			String rsp = con.httpPostString(myURL,parameters);
			// Create connection
			/*Log.i("testinggg", "--------------");
			mURL = new URL(myURL);
			mConnection = (HttpURLConnection) mURL.openConnection();
			mConnection.setRequestMethod("POST");
			// Add headers
			if(headers!=null){
				for (Header_Param header : headers) {
					mConnection.setRequestProperty(header.getKey(), header.getValue());
					Log.i("headers", "headers " + header.getKey() + " values " + header.getValue());
				}
			}
			// Get Response
			InputStream is = mConnection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;

			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
			}
			rd.close();
			rsp = response.toString();*/
			
			Log.i("response of circular", rsp);
		} catch (Exception e) {
			//rsp = "CONNECTION_ERROR";
			Log.i("response of circular error" , e.toString());
		} finally {
			if (mConnection != null) {
				mConnection.disconnect();
			}
		}
		return null;
	}


	@Override
	protected void onPostExecute(Void result) {
		parseResponse(rsp);
		if(mProgressBar != null){
			mProgressBar.setVisibility(View.GONE);
		}
		if(showMore != null){
			showMore.setVisibility(View.VISIBLE);
		}
		super.onPostExecute(result);
	}

	@SuppressWarnings("null")
	public void parseResponse(String rsp)
	{
		String status = "";
		String title = "";
		String datetime = "";
		String shorttext = "";
		String bodytext = "";
		String imageIcon = "";
		
		try{
			JSONObject jsonObject = new JSONObject(rsp);
			JSONArray jsonArray = jsonObject.getJSONArray(JSON_CIRCULAIRES_TITLE_ARRAY);
			status = jsonObject.getString(JSON_STATUS);

			//bodyText = new String[jsonArray.length()];
			int a  = jsonArray.length();
			
			for(int i=0; i<jsonArray.length(); i++)
			{
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				title = jsonObj.getString(JSON_TITLE);
				datetime = jsonObj.getString(JSON_DATE_TIME);
				shorttext = jsonObj.getString(JSON_SHORT_TEXT);
				bodytext = jsonObj.getString(JSON_BODY_TEXT);
				imageIcon = jsonObj.getString(JSON_IMAGE_ICON);

				bodyTextList.add(bodytext);
				
				String date = getDate(Long.parseLong(datetime+"000"), "dd/mm/yyyy HH:MM:SS aa");
				mList.add(new FeedInfo(BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.circular_logo), title, date, datetime));

			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		mAdapter.notifyDataSetChanged();
		if(mList.size() == 0){
			emptyView.setVisibility(View.VISIBLE);
		}else{
			emptyView.setVisibility(View.GONE);
		}
	}
	
	public static String getDate(long milliSeconds, String dateFormat)
	{
		DateFormat formatter = new SimpleDateFormat(dateFormat);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}

}