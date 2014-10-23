package com.uapp.util;

import java.util.Vector;

public class Splitter 
{

	public static  String[] split(String original, String separator) 
	{
		Vector<String> nodes = new Vector<String>();
		// Parse nodes into vector
		int index = original.indexOf(separator);
		while(index>=0) {
			nodes.addElement( original.substring(0, index) );
			original = original.substring(index+separator.length());
			index = original.indexOf(separator);
		}
		// Get the last node
		nodes.addElement( original );

		// Create splitted string array
		String[] result = new String[ nodes.size() ];
		if( nodes.size()>0 ) {
			for(int loop=0; loop<nodes.size(); loop++)
				result[loop] = (String)nodes.elementAt(loop);
		}
		return result; 
	}

	public static String replace(String source, String pattern, String replace) 
	{
		if (source != null) {
			final int len = pattern.length();
			StringBuffer sb = new StringBuffer();
			int found = -1;
			int start = 0;

			while ((found = source.indexOf(pattern, start)) != -1) {
				sb.append(source.substring(start, found));
				sb.append(replace);
				start = found + len;
			}

			sb.append(source.substring(start));

			return sb.toString();
		} else {
			return "";
		}
	}


}
