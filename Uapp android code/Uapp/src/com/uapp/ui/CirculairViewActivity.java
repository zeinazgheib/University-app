package com.uapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class CirculairViewActivity extends SherlockFragmentActivity {

	public WebView webView;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circulair_view);
		
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		View actionBarView = getLayoutInflater().inflate(R.layout.main_action_bar_layout, null);
		
		getSupportActionBar().setCustomView(actionBarView);
		
		Intent intent = getIntent();
		String text = intent.getExtras().getString("circularContent");
		String finalBodyText = "<html><body>" + text + "</body></html>";
		
		webView = (WebView) findViewById(R.id.bodyText);
		
		WebSettings settings = webView.getSettings();
		settings.setDefaultTextEncodingName("utf-8");
		
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.loadData(finalBodyText, "text/html","utf-8");
		
	}

}
