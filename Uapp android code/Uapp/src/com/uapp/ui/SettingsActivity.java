package com.uapp.ui;

import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.uapp.adapter.BranchInfo;
import com.uapp.adapter.DepartmentInfo;
import com.uapp.tasks.FeedsTask;
import com.uapp.util.Globals;

public class SettingsActivity extends SherlockActivity{

	ImageView about_btn;
	RadioGroup branchesGroup;
	RadioButton branch_one_radio;
	RadioButton branch_two_radio;
	RadioButton branch_three_radio;
	TextView title;
	public Spinner branch_sp;
	public Spinner faculty_sp;
	public Button proceed;
	String[] branches;
	String[] departments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		branches = new String[Globals.branches.size()];
		for(int i = 0; i < Globals.branches.size(); i++){
			BranchInfo branchInfo = Globals.branches.elementAt(i);
			branches[i] = branchInfo.branchName;
		}
		departments = new String[Globals.departments.size()];
		for(int i = 0; i < Globals.departments.size(); i++){
			DepartmentInfo depInfo = Globals.departments.elementAt(i);
			departments[i] = depInfo.departmentName;
		}

		ActionBar mActionBar;
		mActionBar = getSupportActionBar();

		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		View actionBarView = getLayoutInflater().inflate(R.layout.settings_action_bar_layout, null);

		getSupportActionBar().setCustomView(actionBarView);
		
		title = (TextView) findViewById(R.id.titletxt);
		Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/print_bold_tt.ttf");
		title.setTypeface(custom_font);
		
		//mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		about_btn = (ImageView) actionBarView.findViewById(R.id.about);
		about_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(SettingsActivity.this, AboutUpaActivity.class);
				startActivity(intent); 
			}
		});
		
	


		ArrayAdapter<String> spinnerBranchesAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item,branches);
		spinnerBranchesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		branch_sp = (Spinner)findViewById(R.id.branch_spinner);
		branch_sp.setPrompt("Select a branch");
		branch_sp.setAdapter(spinnerBranchesAdapter);



		ArrayAdapter<String> spinnerDepartmentAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item,departments);
		spinnerDepartmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		faculty_sp = (Spinner)findViewById(R.id.faculty_spinner);
		faculty_sp.setAdapter(spinnerDepartmentAdapter);

		faculty_sp.setPrompt("Select a faculty");
		
	
		proceed = (Button) findViewById(R.id.proceed);
		proceed.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View v) {
				long branch = branch_sp.getSelectedItemId();
				long department = faculty_sp.getSelectedItemId();
				
				System.out.println("BRanch " +branch);
				BranchInfo branchInfo = Globals.branches.elementAt((int) branch);
				DepartmentInfo dep = Globals.departments.elementAt((int) department);
				
				Globals.branchId = branchInfo.branchId;
				Globals.depId = dep.departmentId;
				
				FeedsTask feedsTask = new FeedsTask(SettingsActivity.this, Globals.branchId, Globals.depId);
				int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
				if(SDK_VERSION >= 11){
					feedsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}else{
					feedsTask.execute();
				}
				
				try {
					String response = (String) feedsTask.get();
					if(!response.equals("")){
						Intent i =  new Intent(SettingsActivity.this, FeedsActivity.class);
						startActivity(i);
						finish();
					}else{
						Toast.makeText(SettingsActivity.this, "no Connection available",Toast.LENGTH_SHORT).show();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
				
			}
		});
		/*
		
		

		branchesGroup = (RadioGroup)findViewById(R.id.radioBranches);
		branchesGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {

				// custom dialog
				final Dialog dialog = new Dialog(SettingsActivity.this);
				//dialog.setContentView(R.layout.faculty_custom_dialog);
				dialog.setContentView(R.layout.first_time_user_settings_dialog);
				
				dialog.setTitle("Select your faculty :");

				Button ok_btn = (Button) dialog.findViewById(R.id.okBtn);
				Button cancel_btn = (Button) dialog.findViewById(R.id.cancelBtn);
				
				final RadioButton rb1 = (RadioButton) dialog.findViewById(R.id.radioFacultyOne);
				final RadioButton rb2 = (RadioButton) dialog.findViewById(R.id.radioFacultyTwo);
				final RadioButton rb3 = (RadioButton) dialog.findViewById(R.id.radioFacultyThree);
				final RadioButton rb4 = (RadioButton) dialog.findViewById(R.id.radioFacultyFour);
				final RadioButton rb5 = (RadioButton) dialog.findViewById(R.id.radioFacultyFive);
				final RadioButton rb6 = (RadioButton) dialog.findViewById(R.id.radioFacultySix);
				final RadioButton rb7 = (RadioButton) dialog.findViewById(R.id.radioFacultySeven);
				final RadioButton rb8 = (RadioButton) dialog.findViewById(R.id.radioFacultyEight);
				final RadioButton rb9 = (RadioButton) dialog.findViewById(R.id.radioFacultyNine);
				final RadioButton rb10 = (RadioButton) dialog.findViewById(R.id.radioFacultyTen);
				final RadioButton rb11 = (RadioButton) dialog.findViewById(R.id.radioFacultyEleven);
				final RadioButton rb12 = (RadioButton) dialog.findViewById(R.id.radioFacultyTwelve);

				cancel_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				// if button is clicked, close the custom dialog
				ok_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						if(rb1.isChecked()){
							Intent i = new Intent(SettingsActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id", "323");
							startActivity(i);
						}
						if(rb2.isChecked()){
							Intent i = new Intent(SettingsActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "324");
							startActivity(i);
						}
						if(rb3.isChecked()){
							Intent i = new Intent(SettingsActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "410");
							startActivity(i);
						}
						if(rb4.isChecked()){
							Intent i = new Intent(SettingsActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "411");
							startActivity(i);
						}
						if(rb5.isChecked()){
							Intent i = new Intent(SettingsActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "412");
							startActivity(i);
						}
						if(rb6.isChecked()){
							Intent i = new Intent(SettingsActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "413");
							startActivity(i);
						}
						if(rb7.isChecked()){
							Intent i = new Intent(SettingsActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "414");
							startActivity(i);
						}
						if(rb8.isChecked()){
							Intent i = new Intent(SettingsActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "415");
							startActivity(i);
						}
						if(rb9.isChecked()){
							Intent i = new Intent(SettingsActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "416");
							startActivity(i);
						}
						if(rb10.isChecked()){
							Intent i = new Intent(SettingsActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "434");
							startActivity(i);
						}
						if(rb11.isChecked()){
							Intent i = new Intent(SettingsActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "452");
							startActivity(i);
						}
						if(rb12.isChecked()){
							Intent i = new Intent(SettingsActivity.this, UappCirculaires.class);
							i.putExtra("faculty_id",  "451");
							startActivity(i);
						}

					}
				});

				dialog.show();

			}
		});*/
	}
}
