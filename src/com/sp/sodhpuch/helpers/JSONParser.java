package com.sp.sodhpuch.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.sp.sodhpuch.helpers.BusinessListHelper.ApiException;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	String retval = null;
	private static byte[] buff = new byte[1024];
	private static final int HTTP_STATUS_OK = 200;
	// constructor
	public JSONParser() {

	}

	public String getJSONFromUrl(String url)throws ApiException {

		
		// Making HTTP request
		try {
			// defaultHttpClient
			HttpClient client = new DefaultHttpClient();
//			HttpGet request = new HttpGet(url);
			
//			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost request = new HttpPost(url);
			
			HttpResponse response = client.execute(request);
			StatusLine status = response.getStatusLine();
			
			if (status.getStatusCode() != HTTP_STATUS_OK) {
				// handle error here
				throw new ApiException("Invalid response from Sodhpuch" + 
						status.toString());
			}			
			
			HttpEntity entity = response.getEntity();
			InputStream ist = entity.getContent();

			ByteArrayOutputStream content = new ByteArrayOutputStream();

			int readCount = 0;
			while ((readCount = ist.read(buff)) != -1) {
				content.write(buff, 0, readCount);
			}
			ist.close();
			retval = new String (content.toByteArray());

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			throw new ApiException("Problem connecting to the server " + 
					e.getMessage(), e);
		}
		return retval;
	}
}

