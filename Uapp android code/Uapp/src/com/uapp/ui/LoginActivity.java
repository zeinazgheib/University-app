package com.uapp.ui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.uapp.adapter.BranchInfo;
import com.uapp.adapter.CirculaireListAdapter;
import com.uapp.adapter.DepartmentInfo;
import com.uapp.adapter.FeedInfo;

import com.uapp.tasks.Header_Param;
import com.uapp.tasks.SigninTask;
import com.uapp.util.Globals;
import com.uapp.util.HttpConnection;

public class LoginActivity extends Activity {

	final String PREFS_NAME = "MyPrefsFile";

	private String[] facultyID ;
	private String[] facultyLabel;
	private String[] facultyBranchID;

	private String[] branchID;
	private String[] branchLabel;
	//	public Button okBtn;
	public Button cancelBtn;
	public Dialog dialog;
	public String facID;

	public EditText username_et;
	public EditText password_et;
	public Button signInBtn;
	public Button signUpBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		username_et = (EditText)findViewById(R.id.username);
		password_et = (EditText)findViewById(R.id.password);
		Button signInBtn = (Button)findViewById(R.id.sign_in);
		Button signUpBtn = (Button)findViewById(R.id.sign_up1);

		signUpBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
				startActivity(i);
			}
		});

		signInBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try{
					String username = username_et.getText().toString();
					String password = password_et.getText().toString();
					SigninTask signinTask = new SigninTask(LoginActivity.this,username, password);
					 String resp = "";
					 int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
					if(SDK_VERSION >= 11){
						signinTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					}else{
						signinTask.execute();
					}
					try {
						resp = (String) signinTask.get();
						System.out.println("RESPPPPPPPPP  " +resp);
					} catch (InterruptedException e) {
						System.out.println("RESPPPPPPPPP  exception " +e.toString());
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block

						e.printStackTrace();
					}

					
					if(resp.equals("error")){
						Toast.makeText(getApplicationContext(), " Authentication Failed",Toast.LENGTH_LONG).show();
						username_et.setText("");
						password_et.setText("");

					}else{
						parseLogin(resp);
						username_et.setText("");
						password_et.setText("");

					}
				}catch(Exception ex){
					Log.i("login exception", ex.toString());
				}

				//you run this task after u make sure that username and password are correct.

				//if the user is admin open admin page


				//if the user is not admin 

				//				int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
				//				LoginTask loginTask = new LoginTask(LoginActivity.this);
				//				if(SDK_VERSION >= 11){
				//
				//					loginTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				//				}else{
				//					loginTask.execute();
				//				}				
			}
		});


	}

	public class LoginTask extends AsyncTask<Void, Void, Void> {

		private final String myURL = "";

		public static final String JSON_STATUS =  "status";

		public static final String JSON_LOGIN_VERSION_OBJECT =  "version";
		public static final String JSON_LOGIN_CURRENT_VERSION =  "currentversion";
		public static final String JSON_LOGIN_UPDATE_TYPE =  "updatetype";
		public static final String JSON_LOGIN_UPDATE_LINK =  "updatelink";

		public static final String JSON_LOGIN_FACULTIES_ARRAY =  "faculties";
		public static final String JSON_LOGIN_FACULTY_ID =  "facultyid";
		public static final String JSON_LOGIN_FACULTY_LABEL =  "facultylabel";
		public static final String JSON_LOGIN_FACULTY_BRANCH_ID =  "branchid";

		public static final String JSON_LOGIN_BRANCHES_ARRAY =  "branches";
		public static final String JSON_LOGIN_BRANCH_ID =  "branchid";
		public static final String JSON_LOGIN_BRANCH_LABEL =  "branchlabel";


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

		private String[] facultyID ;
		private String[] facultyLabel;
		private String[] facultyBranchID;

		private String[] branchID;
		private String[] branchLabel;
		Button okBtn;

		public LoginTask(Activity mContext){

			this.mContext = mContext;
			//			this.facultyID = facultyID;
			//			this.facultyLabel = facultyLabel;
			//			this.facultyBranchID = facultyBranchID;
			//			this.branchID = branchID;
			//			this.branchLabel = branchLabel;
			//			this.rootView = rootView;
		}

		@Override
		protected void onPreExecute() {
			if(mProgressBar != null){
				mProgressBar.setVisibility(View.VISIBLE);
			} 
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			//parseResponse(rsp);
			if(mProgressBar != null){
				mProgressBar.setVisibility(View.GONE);
			}

			SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

			SharedPreferences sharedPref = mContext.getPreferences(Context.MODE_PRIVATE);
			final SharedPreferences.Editor editor = sharedPref.edit();

			//if (settings.getBoolean("my_first_time", true)) {
				Log.d("Comments", "First time");

				//--------show first time user dialog ---------//

				//--------done showing the dialog ------------//4
				//Toast.makeText(this, "first time user", Toast.LENGTH_LONG).show();
				settings.edit().putBoolean("my_first_time", false).commit(); 


				View progressLayout;

				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				progressLayout = inflater.inflate(R.layout.first_time_user_settings_dialog, null);

				dialog = new Dialog(mContext);
				dialog.setContentView(progressLayout);
				dialog.setTitle("Please select your settings");

				okBtn = (Button) progressLayout.findViewById(R.id.okBtn);
				cancelBtn = (Button) progressLayout.findViewById(R.id.cancelBtn);
				Log.d("Comments", "First time1");
/*				final RadioButton rb1 = (RadioButton) progressLayout.findViewById(R.id.radioFacultyOne);
				final RadioButton rb2 = (RadioButton) progressLayout.findViewById(R.id.radioFacultyTwo);
				final RadioButton rb3 = (RadioButton) progressLayout.findViewById(R.id.radioFacultyThree);
				final RadioButton rb4 = (RadioButton) progressLayout.findViewById(R.id.radioFacultyFour);
				final RadioButton rb5 = (RadioButton) progressLayout.findViewById(R.id.radioFacultyFive);
				final RadioButton rb6 = (RadioButton) progressLayout.findViewById(R.id.radioFacultySix);
				final RadioButton rb7 = (RadioButton) progressLayout.findViewById(R.id.radioFacultySeven);
				final RadioButton rb8 = (RadioButton) progressLayout.findViewById(R.id.radioFacultyEight);
				final RadioButton rb9 = (RadioButton) progressLayout.findViewById(R.id.radioFacultyNine);
				final RadioButton rb10 = (RadioButton) progressLayout.findViewById(R.id.radioFacultyTen);
				final RadioButton rb11 = (RadioButton) progressLayout.findViewById(R.id.radioFacultyEleven);
				final RadioButton rb12 = (RadioButton) progressLayout.findViewById(R.id.radioFacultyTwelve);

				okBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {

						if(rb1.isChecked()){
							Intent i = new Intent(LoginActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id", "323");
							facID = "323";
							editor.putString("facultyID", facID);
							editor.commit();
							startActivity(i);
						}
						if(rb2.isChecked()){
							Intent i = new Intent(LoginActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "324");
							facID = "324";
							editor.putString("facultyID", facID);
							editor.commit();
							startActivity(i);
						}
						if(rb3.isChecked()){
							Intent i = new Intent(LoginActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "410");
							facID = "410";
							editor.putString("facultyID", facID);
							editor.commit();
							startActivity(i);
						}
						if(rb4.isChecked()){
							Intent i = new Intent(LoginActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "411");
							facID = "411";
							editor.putString("facultyID", facID);
							editor.commit();
							startActivity(i);
						}
						if(rb5.isChecked()){
							Intent i = new Intent(LoginActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "412");
							facID = "412";
							editor.putString("facultyID", facID);
							editor.commit();
							startActivity(i);
						}
						if(rb6.isChecked()){
							Intent i = new Intent(LoginActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "413");
							facID = "413";
							editor.putString("facultyID", facID);
							editor.commit();
							startActivity(i);
						}
						if(rb7.isChecked()){
							Intent i = new Intent(LoginActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "414");
							facID = "414";
							editor.putString("facultyID", facID);
							editor.commit();
							startActivity(i);
						}
						if(rb8.isChecked()){
							Intent i = new Intent(LoginActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "415");
							facID = "415";
							editor.putString("facultyID", facID);
							editor.commit();
							startActivity(i);
						}
						if(rb9.isChecked()){
							Intent i = new Intent(LoginActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "416");
							facID = "416";
							editor.putString("facultyID", facID);
							editor.commit();
							startActivity(i);
						}
						if(rb10.isChecked()){
							Intent i = new Intent(LoginActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "434");
							facID = "434";
							editor.putString("facultyID", facID);
							editor.commit();
							startActivity(i);
						}
						if(rb11.isChecked()){
							Intent i = new Intent(LoginActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "452");
							facID = "452";
							editor.putString("facultyID", facID);
							editor.commit();
							startActivity(i);
						}
						if(rb12.isChecked()){
							Intent i = new Intent(LoginActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "451");
							facID = "451";
							editor.putString("facultyID", facID);
							editor.commit();
							startActivity(i);
						}

					}
				});*/

				cancelBtn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
						finish();
					}
				});

				dialog.show();
				Log.d("Comments", "First time finish");
		//	}

		/*	else
			{
				Log.d("Comments", "Not First time ");
				
				Intent i = new Intent(LoginActivity.this, UappCirculaires.class);
				String faculty_id = sharedPref.getString("facultyID", "");
				i.putExtra("faculty_id",  faculty_id);
				startActivity(i);
			}*/
			super.onPostExecute(result);
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
			
			return null;
		}

		public void parseResponse(String rsp)
		{
			String status = "";
			JSONObject version = null;
			String currentversion = "";
			String updatetype = "";
			String updatelink = "";

			String facultyid = "";
			String facultylabel = "";
			String branchid = "";

			String branchlabel = "";

			try{ 

				JSONObject jsonObject = new JSONObject(rsp);
				status = jsonObject.getString(JSON_STATUS);

				version = jsonObject.getJSONObject(JSON_LOGIN_VERSION_OBJECT);

				currentversion = version.getString(JSON_LOGIN_CURRENT_VERSION);
				updatetype = version.getString(JSON_LOGIN_UPDATE_TYPE);
				updatelink = version.getString(JSON_LOGIN_UPDATE_LINK);

				JSONArray jsonFacultiesArray = jsonObject.getJSONArray(JSON_LOGIN_FACULTIES_ARRAY);
				facultyID = new String[jsonFacultiesArray.length()];
				facultyLabel = new String[jsonFacultiesArray.length()];
				facultyBranchID = new String[jsonFacultiesArray.length()];

				for(int i=0; i<jsonFacultiesArray.length(); i++)
				{
					JSONObject jsonObjectFac = jsonFacultiesArray.getJSONObject(i);
					facultyid = jsonObjectFac.getString(JSON_LOGIN_FACULTY_ID);
					facultyID[i] = facultyid;

					facultylabel = jsonObjectFac.getString(JSON_LOGIN_FACULTY_LABEL);
					facultyLabel[i]=facultylabel;

					branchid = jsonObjectFac.getString(JSON_LOGIN_FACULTY_BRANCH_ID);
					facultyBranchID[i] = branchid;
				}

				JSONArray jsonBranchesArray = jsonObject.getJSONArray(JSON_LOGIN_BRANCHES_ARRAY);
				branchLabel = new String[jsonBranchesArray.length()];
				branchID = new String[jsonBranchesArray.length()];

				for(int i=0; i<jsonBranchesArray.length(); i++)
				{
					JSONObject jsonObjectBran = jsonBranchesArray.getJSONObject(i);
					branchlabel = jsonObjectBran.getString(JSON_LOGIN_BRANCH_LABEL);
					branchLabel[i] = branchlabel;

					branchid = jsonObjectBran.getString(JSON_LOGIN_BRANCH_ID);
					branchID[i] = branchid;
				}

			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	public void parseLogin(String resp){
		try {
			JSONObject jsonObject = new JSONObject(resp);
			JSONArray userArray = jsonObject.getJSONArray("user");
			for(int i = 0; i < userArray.length() ; i++){
				JSONObject obj = userArray.getJSONObject(i);
				int isAdmin = obj.getInt("isadmin");
				int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
				BranchTask branchTask = new BranchTask(LoginActivity.this,username_et.getText().toString(),password_et.getText().toString(), isAdmin);
				if(SDK_VERSION >= 11){

					branchTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}else{
					branchTask.execute();
				}

				
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onBackPressed()
	{
		finish();
		super.onBackPressed();  // optional depending on your needs
	}
	public class BranchTask extends AsyncTask<Void, Void, Void> { 

		String username;
		String password;
		Activity mContext; 
		int isAdmin;
		public BranchTask(Activity mContext, String username, String password, int isAdmin){
			this.mContext = mContext;
			this.username = username;
			this.password = password;
			this.isAdmin = isAdmin;
		}

		@Override
		protected Void doInBackground(Void... params) {
			String data  = "username=" +username +"&password=" + password;
			String url = "http://10.0.2.2/uapp/branchesNdepartment.php";
			HttpConnection con = new HttpConnection();
			String response = con.httpPostString(url,data);
			parseResponse(response);
			System.out.println(response);
			return null;
		}
		@SuppressWarnings("unchecked")
		public void parseResponse(String response){
			try {
				JSONObject object = new JSONObject(response);
				JSONArray branchArray = object.getJSONArray("branch");
				int length = branchArray.length();
				for(int i = 0 ; i < length ; i++){
					JSONObject obj = branchArray.getJSONObject(i);
					BranchInfo branch = new BranchInfo();
					branch.branchId = obj.getInt("branchid");
					branch.branchName = obj.getString("branchname");
					Globals.branches.addElement(branch);
				}
				JSONArray depArray = object.getJSONArray("dep");
				int len = depArray.length();
				for(int j = 0 ; j< len ; j++){
					JSONObject obj = depArray.getJSONObject(j);
					DepartmentInfo dep = new DepartmentInfo();
					dep.departmentId = obj.getInt("departmentid");
					dep.departmentName = obj.getString("departmentname");
					Globals.departments.addElement(dep);
				}
				
				
				if(isAdmin == 0){
					Intent intent = new Intent(LoginActivity.this, SettingsActivity.class);
					startActivity(intent);
					finish();
				
				}else if(isAdmin == 1){
					Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
					startActivity(intent);
					finish();
				
				}
				
			} catch (JSONException e) {
				Log.i("exception on parsing branches" , e.toString());
			}
		}
	}

}
