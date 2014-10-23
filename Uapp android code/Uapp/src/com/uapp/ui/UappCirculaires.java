/*package com.uapp.ui;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.uapp.circulaires.fragments.ViewPagerAdapter;

public class UappCirculaires extends SherlockFragmentActivity {

	ActionBar mActionBar;
	ViewPager mPager;
	Tab tab1,tab2;
	ImageView settingImage;
	TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("UAPP ACTIvityyyy");
		setContentView(R.layout.circulair_layout);
		System.out.println("UAPP ACTIvityyyy set layout");
		mActionBar = getSupportActionBar();
		
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		View actionBarView = getLayoutInflater().inflate(R.layout.main_action_bar_layout, null);
		
		getSupportActionBar().setCustomView(actionBarView);
		System.out.println("UAPP ACTIvityyyy set action bar");

		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mPager = (ViewPager) findViewById(R.id.pager);
		
		FragmentManager fm = getSupportFragmentManager();
		
		title = (TextView) findViewById(R.id.titletxt);
		Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/print_bold_tt.ttf");
		title.setTypeface(custom_font);
		
		ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);

				mActionBar.setSelectedNavigationItem(position);
			}
		};
		
		settingImage = (ImageView) actionBarView.findViewById(R.id.settings);
		settingImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(UappCirculaires.this, SettingsActivity.class);
				startActivity(intent); 
			}
		});

		mPager.setOnPageChangeListener(ViewPagerListener);
		ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(fm);
		mPager.setAdapter(viewpageradapter);
		System.out.println("UAPP ACTIvityyyy view pager adapter");
		/*ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {

				mPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		};

		tab1 = mActionBar.newTab().setText("Circulaire Admin").setTabListener(tabListener);
		mActionBar.addTab(tab1);
		
		tab2 = mActionBar.newTab().setText("Circulaire Alumni").setTabListener(tabListener);
		mActionBar.addTab(tab2);*/

/*		System.out.println("UAPP ACTIvityyyy end ofclass");
		
	}
	
	
}
*/