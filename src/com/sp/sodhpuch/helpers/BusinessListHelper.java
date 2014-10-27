package com.sp.sodhpuch.helpers;

import java.net.URLEncoder;

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
	static int next = 0;

	public static class ApiException extends Exception {
		private static final long serialVersionUID = 1L;

		public ApiException(String msg) {
			super(msg);
		}

		public ApiException(String msg, Throwable thr) {
			super(msg, thr);
		}
	}

	/**
	 * download most popular tracks in given metro.
	 * 
	 * @param params
	 *            search strings
	 * @return Array of json strings returned by the API.
	 * @throws ApiException
	 */
	public static synchronized String downloadFromServer(String... params)
			throws ApiException {
		JSONParser jParser = new JSONParser();
		String retval = null;
		String keyword = URLEncoder.encode(params[0]);
		String location = URLEncoder.encode(params[1]);
		String id = URLEncoder.encode(params[2]);
		String url = SodhpuchBusinessListUrl + "&name=" + keyword + "&address="
				+ location + "&next=" + next;
		// String url = SodhpuchBusinessListUrl
		// + "&name=" + keyword+"&address="+location+"&id="+id+"&next="+next;

		retval = jParser.getJSONFromUrl(url);
		next += 10;
		return retval;
	}
}
