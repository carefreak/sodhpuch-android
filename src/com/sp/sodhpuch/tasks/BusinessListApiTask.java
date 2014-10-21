package com.sp.sodhpuch.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sp.sodhpuch.db.DBController;
import com.sp.sodhpuch.BusinessListActivity;
import com.sp.sodhpuch.ListResultActivity;
import com.sp.sodhpuch.R;
import com.sp.sodhpuch.data.BusinessListData;
import com.sp.sodhpuch.helpers.BusinessListHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

/**
 * AsyncTask for fetching from Sodhpuch API.
 * 
 * @author Carefree
 */
public class BusinessListApiTask extends AsyncTask<String, Integer, String> {
	private ProgressDialog progDialog;
	private Context context;
	private ListResultActivity activity;
	private static final String debugTag = "sodhpuch";
	HashMap<String, String> queryValues;

	/**
	 * Construct a task
	 * 
	 * @param activity
	 */

	public BusinessListApiTask(ListResultActivity activity) {
		// TODO Auto-generated constructor stub
		super();
		this.activity = activity;
		this.context = this.activity.getApplicationContext();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progDialog = ProgressDialog.show(this.activity, "Search", this.context
				.getResources().getString(R.string.looking_for_business), true,
				false);
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			// Log.d(debugTag, "Background:" +
			// Thread.currentThread().getName());
			String result = BusinessListHelper.downloadFromServer(params);
			// try {
			//
			// updateSQLite(result);
			//
			// } catch (Exception e) {
			// return result;
			// }
			return result;

		} catch (Exception e) {
			return new String();
		}
	}

	@Override
	protected void onPostExecute(String result) {

		ArrayList<BusinessListData> businessData = new ArrayList<BusinessListData>();

		progDialog.dismiss();
		try {

			JSONObject respObj = new JSONObject(result);
			int success = respObj.getInt("success");
			Log.d("Success", "abc" + success);
			if (success == 1) {

				JSONArray businesses = respObj.getJSONArray("businesses");
				for (int i = 0; i < businesses.length(); i++) {
					JSONObject business = businesses.getJSONObject(i);
					String businessName = business.getString("name");
					String businessAddress = business.getString("address");
					String phone = business.getString("phone");
					String id = business.getString("id");
					String deals_in = business.getString("deals_in");
					businessData.add(new BusinessListData(businessName,
							businessAddress, id, phone, deals_in));
				}

			} else {

				Log.d("Success", "first" + success);
				// DBController controller = new DBController(context);
				// businessData = controller.getBusinessList();
				return;

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// }
		this.activity.setBusinesses(businessData);

	}

	public void updateSQLite(String result) {
		ArrayList<HashMap<String, String>> usersynclist;
		usersynclist = new ArrayList<HashMap<String, String>>();
		// Create GSON object
		// Gson gson = new GsonBuilder().create();
		try {

			JSONObject respObj = new JSONObject(result);
			JSONArray businesses = respObj.getJSONArray("businesses");
			for (int i = 0; i < businesses.length(); i++) {

				JSONObject business = businesses.getJSONObject(i);
				String businessName = business.getString("name");
				String phone = business.getString("phone");
				// String imageUrl = track.getString("id");
				String businessAddress = business.getString("address");
				String artistUrl = business.getString("id");
				queryValues = new HashMap<String, String>();

				// Add userID extracted from Object
				queryValues.put("name", businessName);
				// Add userName extracted from Object
				queryValues.put("address", businessAddress);
				queryValues.put("phone", phone);
				queryValues.put("id", artistUrl);
				// Insert User into SQLite DB
				DBController controller = new DBController(context);
				controller.insertUser(queryValues);

				HashMap<String, String> map = new HashMap<String, String>();
				// Add status for each User in Hashmap

				map.put("id", artistUrl);
				map.put("status", "1");

				usersynclist.add(map);

			}
			// Inform Remote MySQL DB about the completion of Sync activity
			// by passing Sync status of Users
			// updateMySQLSyncSts(gson.toJson(usersynclist));
			// // Reload the Main Activity
			// reloadActivity();
		} catch (JSONException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}