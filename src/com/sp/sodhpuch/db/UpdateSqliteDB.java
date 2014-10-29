package com.sp.sodhpuch.db;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class UpdateSqliteDB {
	HashMap<String, String> queryValues;
	private Context context;

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
