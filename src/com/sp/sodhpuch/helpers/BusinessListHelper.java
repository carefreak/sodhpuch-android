package com.sp.sodhpuch.helpers;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.sp.sodhpuch.ListResultActivity;
import com.sp.sodhpuch.tasks.BusinessListApiTask;

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
	static int next = 0;

	public static class ApiException extends Exception {
		private static final long serialVersionUID = 1L;

		public ApiException (String msg)
		{
			super (msg);
		}

		public ApiException (String msg, Throwable thr)
		{
			super (msg, thr);
		}
	}

	/**
	 * download most popular tracks in given metro.
	 * @param params search strings
	 * @return Array of json strings returned by the API. 
	 * @throws ApiException
	 */
	public static synchronized String downloadFromServer (String... params)
	throws ApiException
	{
		String retval = null;
		String keyword = URLEncoder.encode(params[0]); 
		String location = URLEncoder.encode(params[1]);
		String id = URLEncoder.encode(params[2]);
		String url = SodhpuchBusinessListUrl 
		+ "&name=" + keyword+"&address="+location+"&next="+next; 
//		String url = SodhpuchBusinessListUrl 
//		+ "&name=" + keyword+"&address="+location+"&id="+id+"&next="+next; 
		Log.d(logTag,"Fetching " + url);
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		try {

			// execute the request
			HttpResponse response = client.execute(request);
			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() != HTTP_STATUS_OK) {
				// handle error here
				throw new ApiException("Invalid response from Sodhpuch" + 
						status.toString());
			}

			// process the content. 
			HttpEntity entity = response.getEntity();
			InputStream ist = entity.getContent();
			ByteArrayOutputStream content = new ByteArrayOutputStream();

			int readCount = 0;
			while ((readCount = ist.read(buff)) != -1) {
				content.write(buff, 0, readCount);
			}
			retval = new String (content.toByteArray());

		} catch (Exception e) {
			throw new ApiException("Problem connecting to the server " + 
					e.getMessage(), e);
		}
		next+=10;
		return retval;
	}
}
