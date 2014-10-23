package com.uapp.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class AboutUpaActivity extends SherlockActivity {

	TextView title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_upa);
		
		ActionBar mActionBar;
		mActionBar = getSupportActionBar();

		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		View actionBarView = getLayoutInflater().inflate(R.layout.about_action_bar_layout, null);

		getSupportActionBar().setCustomView(actionBarView);
		
		title = (TextView) findViewById(R.id.titletxt);
		Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/print_bold_tt.ttf");
		title.setTypeface(custom_font);
	}


}
