package com.sp.sodhpuch.tasks;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.sp.sodhpuch.fragments.FragmentMain.FragmentCallback;
import com.sp.sodhpuch.helpers.JSONHelper;

public class BusinessInfoSuggestionTask extends
		AsyncTask<String, String, HashMap<String, String>> {
	public String acType;
	public String data;
	public String json;
	JSONObject respObj;
	JSONArray jArray;
	public List<HashMap<String, String>> autocompletedata = new ArrayList<HashMap<String, String>>();

	private FragmentCallback mfragmentCallback;

	public BusinessInfoSuggestionTask(FragmentCallback fragmentCallback) {
		// TODO Auto-generated constructor stub
		super();
		this.mfragmentCallback = fragmentCallback;
	}

	protected HashMap<String, String> doInBackground(String... params) {
		HashMap<String, String> resultList = new HashMap<String, String>();
		JSONHelper jParser = new JSONHelper();
		String acKey = params[0]; // string typed on EditText field
		String acType = params[1]; // Location suggestion or KeyWord suggestion?
		try {
			acKey = URLEncoder.encode(params[0], "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String url = null;
			if (acType == "location") {
				url = "http://172.27.3.99:81/abc/search.php/?method=get&format=json&address="
						+ acKey;
			} else {
				url = "http://172.27.3.99:81/abc/search.php/?method=get&format=json&key="
						+ acKey;
			}
			json = jParser.getJSONFromUrl(url);
			Log.d("json testing", json);

		} catch (Exception e) {
			json = new String();
		}
		resultList.put("acType", acType);
		resultList.put("result", json);
		return resultList;

	}

	@Override
	protected void onPostExecute(HashMap<String, String> resultList) {
		autocompletedata.clear();
		String acType = resultList.get("acType");
		String json = resultList.get("result");
		try {
			respObj = new JSONObject(json);
			jArray = respObj.getJSONArray("businesses");
			HashMap<String, String> hm = new HashMap<String, String>();
			Log.d("location", acType);
			for (int i = 0; i < jArray.length(); i++) {

				JSONObject filter = jArray.getJSONObject(0);
				String SuggestId = filter.getString("id");
				String SuggestLoc = filter.getString("address");
				String SuggestBiz = filter.getString("name");
				if (acType == "location") {
					hm.put("key", SuggestId);
					hm.put("loc", SuggestLoc);
					hm.put("biz", SuggestBiz);
				} else {
					hm.put("key", SuggestId);
					hm.put("loc", SuggestBiz);
					hm.put("biz", SuggestLoc);
				}

				autocompletedata.add(hm);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (acType == "location") {
			this.mfragmentCallback.onAsyncTaskComplete(autocompletedata);
		} else {

			this.mfragmentCallback.onAsyncTaskComplete(autocompletedata);
		}

	}
}
