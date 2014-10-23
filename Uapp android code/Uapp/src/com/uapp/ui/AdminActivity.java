package com.uapp.ui;

import java.util.concurrent.ExecutionException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.uapp.adapter.BranchInfo;
import com.uapp.adapter.DepartmentInfo;
import com.uapp.tasks.PostFeedTask;
import com.uapp.util.Globals;

public class AdminActivity extends Activity {

	public Spinner branch_sp;
	public Spinner faculty_sp;
	public Button add_field;
	public Button add_image;
	public Button postFeed;
	public EditText title_et;
	public EditText feed_et;
	public ImageView img;
	String[] branches;
	String[] departments;


	public static final int RESULT_LOAD_IMAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
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
		add_field = (Button)findViewById(R.id.report_field);
		add_image = (Button)findViewById(R.id.add_image);
		title_et = (EditText)findViewById(R.id.title);
		feed_et = (EditText)findViewById(R.id.feed);
		img = (ImageView)findViewById(R.id.img);
		postFeed = (Button) findViewById(R.id.post_feed);





		add_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});

		add_field.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				Intent i = new Intent(AdminActivity.this,AdminReportActivity.class);
				startActivity(i);

			}
		});

		postFeed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//how to get spinner value
				long branch = branch_sp.getSelectedItemId();
				long department = faculty_sp.getSelectedItemId();
				String feed = feed_et.getText().toString();
				String title = title_et.getText().toString();

				System.out.println("BRanch " +branch);
				BranchInfo branchInfo = Globals.branches.elementAt((int) branch);
				DepartmentInfo dep = Globals.departments.elementAt((int) department);


				String resp = "";
				int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
				PostFeedTask postTask = new PostFeedTask(AdminActivity.this,branchInfo.branchId,dep.departmentId,feed,title);
				if(SDK_VERSION >= 11){
					postTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}else{
					postTask.execute();
				}
				try {
					resp = (String) postTask.get();
					System.out.println("RESPPPPPPPPP  " +resp);
				} catch (InterruptedException e) {
					System.out.println("RESPPPPPPPPP  exception " +e.toString());
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block

					e.printStackTrace();
				}

				if(resp.equals("1")){
					title_et.setText("");
					feed_et.setText("");
				}

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();


			img.setImageBitmap(BitmapFactory.decodeFile(picturePath));

		}


	}


}
