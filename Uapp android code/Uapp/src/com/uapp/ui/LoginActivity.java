package com.uapp.ui;

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
import com.uapp.adapter.DepartmentInfo;
import com.uapp.adapter.FeedInfo;
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
