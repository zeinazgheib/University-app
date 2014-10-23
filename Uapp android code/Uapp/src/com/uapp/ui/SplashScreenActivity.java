package com.uapp.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.widget.Button;

public class SplashScreenActivity extends Activity {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.activity_splash_screen);

	
		 new CountDownTimer(5000,1000){
		        @Override
		        public void onTick(long millisUntilFinished){} 

		        @Override
		        public void onFinish(){
		        	Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
		    		startActivity(i);
		    		finish();
		        }
		   }.start();
		
		
	}

}
