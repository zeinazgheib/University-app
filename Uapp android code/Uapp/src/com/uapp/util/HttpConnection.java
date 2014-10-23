package com.uapp.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import android.util.Log;


public class HttpConnection {

	public static final String EXCEPTION = "exception";
	public static final String EXCEPTION_IO = "IOEXCEPTION";
	public static final String EXCEPTION_ABORTED = "aborted";
	public static final String ERROR_SERVER = "10";

	private static HttpURLConnection mConnection;
	private static boolean mAborted;

	public static boolean isAborted() {
		return mAborted;
	}

	public static String executePostFile(String targetURL, byte[] _data, ArrayList<Header> Headers){
		String r = "";
		URL url;
		HttpURLConnection connection = null;
		try {
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			byte[] postDataBytes = _data;
			if(postDataBytes!=null)
				connection.setRequestMethod("POST");
			else
				connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent",
					"Profile/MIDP-2.0 Configuration/CLDC-1.0");
			connection.setRequestProperty("Content-Language", "en-US");
			connection
			.setRequestProperty("Content-Type",
					"multipart/form-data ; boundary = ----------V2ymHFg03ehbqgZCaKO6jy");
			connection.setRequestProperty("Connection", "close");
			if(postDataBytes!=null)
				connection.setRequestProperty("Content-Length",
						Integer.toString(postDataBytes.length));

			for(Header x : Headers){
				connection.setRequestProperty(x.getName(), x.getValue());
			}
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			if(postDataBytes!=null){
				DataOutputStream osFile = new DataOutputStream(
						connection.getOutputStream());
				osFile.write(postDataBytes);
				osFile.flush();
				osFile.close();
			}
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;

			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
			}
			rd.close();
			r = response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			r = "no";
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return r;
	}

	public static String executePostStringHeaders(String targetURL, String urlParameters, ArrayList<Header> Headers) {
		URL url;
		DataOutputStream wr = null ;
		HttpURLConnection connection = null; 
		try {
			url = new URL(targetURL); //http://zainapi.inmobiles.net

			connection = (HttpURLConnection)url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", 
					"application/x-www-form-urlencoded");
			//    String charset = "UTF-8";

			//    connection.setRequestProperty("Accept-Charset", charset);
			//    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
			byte[] b = urlParameters.getBytes("UTF-8");
			connection.setRequestProperty("Content-Length", "" + 
					Integer.toString(b.length));
			connection.setRequestProperty("Content-Language", "en-US");  
			for(Header x : Headers){
				connection.setRequestProperty(x.getName(), x.getValue());
			}

			connection.setUseCaches (false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			wr= new DataOutputStream (connection.getOutputStream ());

			wr.write (b);
			wr.flush ();            
			InputStream is = connection.getInputStream();  
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;

			StringBuffer response = new StringBuffer(); 
			while((line = rd.readLine()) != null) {
				response.append(line);
			}   
			rd.close();
			return response.toString(); 

		} catch (Exception e) {
			e.printStackTrace();
			return "no" ;
		} finally {

			if(connection != null) {
				connection.disconnect(); 
				try {
					wr.close ();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String httpGet(String targetURL, ArrayList<Header> headers) {
		mAborted = false;
		String r = "";
		URL url;
		try {
			// Create connection
			System.out.print("1");
			url = new URL(targetURL);
			System.out.print("2");
			mConnection = (HttpURLConnection) url.openConnection();
			System.out.print("3");
			mConnection.setRequestMethod("GET");
			System.out.print("4");
			// Add headers
			for (Header header : headers) {
				mConnection.setRequestProperty(header.getName(),
						header.getValue());
			}
			System.out.print("5");
			// Get Response
			InputStream is = mConnection.getInputStream();
			System.out.print("6");
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			System.out.print("7");
			String line;

			StringBuffer response = new StringBuffer();
			System.out.print("8");
			while ((line = rd.readLine()) != null) {
				response.append(line);
			}
			System.out.print("9");
			rd.close();
			System.out.print("10");
			r = response.toString();
			System.out.print("11");
		} catch (Exception e) {

			e.printStackTrace();
			r = EXCEPTION;
		} finally {

			if (mConnection != null) {
				mConnection.disconnect();
			}
		}
		return r;
	}

	public String httpPostByteArray(String targetURL, byte[] data,
			ArrayList<Header> headers) {
		mAborted = false;
		String r = "";
		URL url;
		try {
			// Create connection
			url = new URL(targetURL);
			mConnection = (HttpURLConnection) url.openConnection();
			mConnection.setRequestMethod("POST");
			mConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			mConnection.setRequestProperty("Content-Length",
					"" + Integer.toString(data.length));
			mConnection.setRequestProperty("Content-Language", "en-US");
			// Add headers
			for (Header header : headers) {
				mConnection.setRequestProperty(header.getName(),
						header.getValue());
			}

			mConnection.setUseCaches(false);
			mConnection.setDoInput(true);
			mConnection.setDoOutput(true);
			// Send request
			DataOutputStream wr = new DataOutputStream(
					mConnection.getOutputStream());
			wr.write(data);
			wr.flush();
			wr.close();
			// Get Response
			InputStream is = mConnection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;

			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
			}
			rd.close();
			r = response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			r = (isAborted()) ? EXCEPTION_ABORTED : EXCEPTION;
		} finally {
			if (mConnection != null) {
				mConnection.disconnect();
			}
		}
		return r;
	}

	public static String httpPostString(String targetURL, String params) {
		URL url;
		DataOutputStream wr = null ;
		HttpURLConnection connection = null;  
		try {
			// Create connection
			TrustManager[] trustAllCerts = new TrustManager[]{  
					new X509TrustManager() {  
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
							return null;  
						}  

						public void checkClientTrusted(  
								java.security.cert.X509Certificate[] certs, String authType) {  
						}  

						public void checkServerTrusted(  
								java.security.cert.X509Certificate[] certs, String authType) {  
						}  
					}  
			};  
			// Install the all-trusting trust manager  
			try {  
				SSLContext sc = SSLContext.getInstance("SSL");  
				sc.init(null, trustAllCerts, new java.security.SecureRandom());  
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());  
			} catch (Exception e) {  
				System.out.println("Error" + e);  
			}  
			// Now you can access an https URL without having the certificate in the truststore  
			try {  
				@SuppressWarnings("unused")
				HostnameVerifier hv = new HostnameVerifier() {  
					public boolean verify(String urlHostName, SSLSession session) {  
						System.out.println("Warning: URL Host: " + urlHostName + " vs. "  
								+ session.getPeerHost());  
						return true;  
					}  
				};  
			}catch(Exception ex){
				ex.printStackTrace();
			}

			url = new URL(targetURL); //http://zainapi.inmobiles.net

			connection = (HttpURLConnection)url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", 
					"application/x-www-form-urlencoded");
			//    String charset = "UTF-8";

			//    connection.setRequestProperty("Accept-Charset", charset);
			//    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
			byte[] b = params.getBytes("UTF-8");
			connection.setRequestProperty("Content-Length", "" + 
					Integer.toString(b.length));
			connection.setRequestProperty("Content-Language", "en-US");  

			connection.setUseCaches (false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			wr= new DataOutputStream (connection.getOutputStream ());

			wr.write (b);
			wr.flush ();            
			InputStream is = connection.getInputStream();  
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;

			StringBuffer response = new StringBuffer(); 
			while((line = rd.readLine()) != null) {
				response.append(line);
			}   
			rd.close();
			return response.toString(); 

		} catch (Exception e) {
			Log.i("HTTPConn function", e.toString());
			e.printStackTrace();
			return "no" ;
		} finally {

			if(connection != null) {
				connection.disconnect(); 
				try {
					wr.close ();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

//	public static String executePostDial(String targetURL, String urlParameters) {
//		URL url;
//		DataOutputStream wr = null ;
//		HttpURLConnection connection = null;  
//		try {
//
//			TrustManager[] trustAllCerts = new TrustManager[]{  
//					new X509TrustManager() {  
//						public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
//							return null;  
//						}  
//
//						public void checkClientTrusted(  
//								java.security.cert.X509Certificate[] certs, String authType) {  
//						}  
//
//						public void checkServerTrusted(  
//								java.security.cert.X509Certificate[] certs, String authType) {  
//						}  
//					}  
//			};  
//			// Install the all-trusting trust manager  
//			try {  
//				SSLContext sc = SSLContext.getInstance("SSL");  
//				sc.init(null, trustAllCerts, new java.security.SecureRandom());  
//				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());  
//			} catch (Exception e) {  
//				System.out.println("Error" + e);  
//			}  
//			// Now you can access an https URL without having the certificate in the truststore  
//			try {  
//				@SuppressWarnings("unused")
//				HostnameVerifier hv = new HostnameVerifier() {  
//					public boolean verify(String urlHostName, SSLSession session) {  
//						System.out.println("Warning: URL Host: " + urlHostName + " vs. "  
//								+ session.getPeerHost());  
//						return true;  
//					}  
//				};  
//			}catch(Exception ex){
//				ex.printStackTrace();
//			}
//
//			url = new URL(targetURL); //http://zainapi.inmobiles.net
//
//			connection = (HttpURLConnection)url.openConnection();
//
//			connection.setRequestMethod("POST");
//			connection.setRequestProperty("Content-Type", 
//					"application/x-www-form-urlencoded");
//			//    String charset = "UTF-8";
//
//			//    connection.setRequestProperty("Accept-Charset", charset);
//			//    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
//			byte[] b = urlParameters.getBytes("UTF-8");
//			connection.setRequestProperty("Content-Length", "" + 
//					Integer.toString(b.length));
//			connection.setRequestProperty("Content-Language", "en-US");  
//
//			connection.setUseCaches (false);
//			connection.setDoInput(true);
//			connection.setDoOutput(true);
//
//			wr= new DataOutputStream (connection.getOutputStream ());
//
//			wr.write (b);
//			wr.flush ();            
//			InputStream is = connection.getInputStream();  
//			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//			String line;
//
//			StringBuffer response = new StringBuffer(); 
//			while((line = rd.readLine()) != null) {
//				response.append(line);
//			}   
//			rd.close();
//			return response.toString(); 
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "no" ;
//		} finally {
//
//			if(connection != null) {
//				connection.disconnect(); 
//				try {
//					wr.close ();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

	public static String excutePost(String targetURL, String urlParameters) {
		String r = "";
		URL url;
		HttpURLConnection connection = null;
		try {
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.length()));
			connection.setRequestProperty("Content-Language", "en-US");


			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;

			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
			}
			rd.close();
			r = response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			r = "-1";
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return r;
	}


}