package com.sp.sodhpuch.tasks;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sp.sodhpuch.db.DBController;
import com.sp.sodhpuch.db.UpdateSqliteDB;
import com.sp.sodhpuch.ListResultActivity;
import com.sp.sodhpuch.R;
import com.sp.sodhpuch.data.BusinessListData;
import com.sp.sodhpuch.helpers.BusinessListHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;

/**
 * AsyncTask for fetching from Sodhpuch API.
 * 
 * @author Carefree
 */
public class BusinessListApiTask extends AsyncTask<String, Integer, String> {
	private ProgressDialog progDialog;
	private Context context;
	private ListResultActivity activity;
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

		progDialog.getWindow().setGravity(Gravity.BOTTOM);
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			String result = BusinessListHelper.downloadFromServer(params);
			try {
				UpdateSqliteDB update = new UpdateSqliteDB();
				update.updateSQLite(result);

			} catch (Exception e) {
				return result;
			}
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
				DBController controller = new DBController(context);
				businessData = controller.getBusinessList();
				return;

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// }
		this.activity.setBusinesses(businessData);

	}

}