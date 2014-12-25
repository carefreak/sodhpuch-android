package com.sp.sodhpuch.helpers;

import java.io.UnsupportedEncodingException;
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
		String keyword = params[0];
		String location = (params[1]);
//		String qt = params[2];
		String first = params[3];
		try {
			keyword = URLEncoder.encode(params[0], "UTF-8");
			location = URLEncoder.encode(params[1], "UTF-8");
//			qt = URLEncoder.encode(params[2], "UTF-8");
			first = URLEncoder.encode(params[3], "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (first.equals("first")) {
			next = 0;
		}
		String url = SodhpuchBusinessListUrl + "&name=" + keyword + "&address="
				+ location + "&next=" + next;
		Log.d("", url);
		retval = jParser.getJSONFromUrl(url);
		next += 10;
		return retval;
	}
}
