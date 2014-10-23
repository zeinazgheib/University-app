package com.uapp.adapter;

import android.graphics.Bitmap;

public class FeedInfo {
	Bitmap img;
	String name;
	String phone;
	String millisDate;

	public FeedInfo(Bitmap img, String name, String phone, String millisDate)
	{
		this.img = img;
		this.name = name;
		this.phone = phone;
		this.millisDate = millisDate;
	}
	
	public void setImage(Bitmap b)
	{
		img = b;
	}
	
	public Bitmap getImage()
	{
		return img;
	}
	
	public void setName(String nom)
	{
		name = nom;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setPhone(String tel)
	{
		phone = tel;
	}
	
	public String getPhone()
	{
		return phone;
	}

	public void setMilliDate(String m)
	{
		millisDate = m;
	}
	
	public String getMilliDate()
	{
		return millisDate;
	}
}
