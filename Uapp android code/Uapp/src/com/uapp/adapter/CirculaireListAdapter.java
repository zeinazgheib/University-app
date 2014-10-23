package com.uapp.adapter;

import java.util.ArrayList;

import com.uapp.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CirculaireListAdapter extends ArrayAdapter<FeedInfo> {

	private int mLineLayout;
	private LayoutInflater mInflater;
	public Context pContext;

	public CirculaireListAdapter(Context pContext, int pLineLayout, ArrayList<FeedInfo> Dlist) {
		super(pContext, pLineLayout, Dlist);

		this.mLineLayout = pLineLayout;
		this.pContext = pContext;
		this.mInflater = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	static class ViewHolder {

		ImageView imgLogo;
		TextView name;
		TextView phone;
	}

	@Override
	public View getView(int pPosition, View pView, ViewGroup pParent) {

		ViewHolder holder;
		Animation animation = null;
		if (pView == null) {

			pView = this.mInflater.inflate(this.mLineLayout, null);

			holder = new ViewHolder();
			holder.imgLogo = (ImageView) pView.findViewById(R.id.logo_image);
			holder.name = (TextView) pView.findViewById(R.id.donater_name);
			holder.phone = (TextView) pView.findViewById(R.id.donater_phone);

			pView.setTag(holder);

		} else {
			holder = (ViewHolder) pView.getTag();
		}

		FeedInfo donInf = getItem(pPosition);
		if (donInf != null) {

			holder.phone.setText(donInf.phone);
			holder.name.setText(donInf.name);
			holder.imgLogo.setImageBitmap(donInf.img);
		}

		//animation
				animation = AnimationUtils.loadAnimation(pContext, R.anim.cycle);
				animation.setDuration(400);
				pView.startAnimation(animation);
				animation = null;
				
		return pView;
	}
}

