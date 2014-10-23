package com.uapp.util;

public class CategoryFields 
{
	public String categoryImageLink;
	public String categoryID;
	public String categoryName;
	public String categoryArabicName;
	public String[] imageLink;
	public String[] voiceLink;
	 
	public CategoryFields(String CategoryStr)
	{
		try{
		String fields[] = Splitter.split(CategoryStr, "]");
		
		imageLink = new String[fields.length-3]; 
		voiceLink = new String[fields.length-3]; 
				
		this.categoryImageLink = fields[0];
		this.categoryID = fields[1];
		this.categoryName = fields[2];
		//this.categoryArabicName = fields[3];
		
		for(int i = 3; i < fields.length; i++)
		{
			String[] array;
			array = Splitter.split(fields[i], "**");
			
			imageLink[i-3] = array[0];
			voiceLink[i-3] = array[1];
			
		}
		}catch(Exception ex){}
	}
}
