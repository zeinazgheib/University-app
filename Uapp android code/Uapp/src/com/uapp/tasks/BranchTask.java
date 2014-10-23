package com.uapp.tasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.uapp.adapter.BranchInfo;
import com.uapp.adapter.DepartmentInfo;
import com.uapp.util.Globals;
import com.uapp.util.HttpConnection;

public class BranchTask extends AsyncTask<Void, Void, Void> { 

	String username;
	String password;
	Activity mContext;
	public BranchTask(Activity mContext, String username, String password){
		this.mContext = mContext;
		this.username = username;
		this.password = password;
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
			
			
			
		} catch (JSONException e) {
			Log.i("exception on parsing branches" , e.toString());
		}
	}
	

	
}