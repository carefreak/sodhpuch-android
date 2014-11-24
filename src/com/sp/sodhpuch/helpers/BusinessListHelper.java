package com.sp.sodhpuch.helpers;

import java.net.URLEncoder;

import android.util.Log;

/**
 * Helper to interact with Sodhpuch private API.
 * 
 * @author Carefree
 * 
 */
public class BusinessListHelper {

	private static final String SodhpuchBusinessListUrl = "http://172.27.3.99:81/abc/search.php/?method=get&format=json";
	private static final int HTTP_STATUS_OK = 200;
	private static byte[] buff = new byte[1024];
	private static final String logTag = "BusinessListHelper";
	static int next;

	public static class ApiException extends Exception {
		private static final long serialVersionUID = 1L;

		public ApiException(String msg) {
			super(msg);
		}

		public ApiException(String msg, Throwable thr) {
			super(msg, thr);
		}
	}

	public static synchronized String downloadFromServer(String... params)
			throws ApiException {
		JSONHelper jParser = new JSONHelper();
		String retval = null;
		String keyword = URLEncoder.encode(params[0]);
		String location = URLEncoder.encode(params[1]);
		String id = URLEncoder.encode(params[2]);
		Log.d("first",URLEncoder.encode(params[3]));
		String first = URLEncoder.encode(params[3]);
		if(first.equals("first")){
			next = 0;
			Log.d("",""+next);
		}
		
		
		
		Log.d("next","next"+next);
//		if(URLEncoder.encode(params[3]) == "first"){
//			next = 0;
//		}
		
		String url = SodhpuchBusinessListUrl + "&name=" + keyword + "&address="
				+ location + "&next=" + next;
		Log.d("",url);
		retval = jParser.getJSONFromUrl(url);
		next += 10;
		return retval;
	}
}
