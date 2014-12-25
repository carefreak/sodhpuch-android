package com.sp.sodhpuch.db;

import java.util.ArrayList;
import java.util.HashMap;
import com.sp.sodhpuch.data.BusinessListData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBController extends SQLiteOpenHelper {

	public DBController(Context applicationcontext) {
		super(applicationcontext, "restaurant.db", null, 1);
	}

	// Creates Table
	@Override
	public void onCreate(SQLiteDatabase database) {

		String query;
		query = "CREATE TABLE restaurant ( id INTEGER, address TEXT, phone TEXT, name TEXT )";
		database.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old,
			int current_version) {
		String query;
		query = "DROP TABLE IF EXISTS restaurant";
		database.execSQL(query);
		onCreate(database);
	}

	/**
	 * Inserts User into SQLite DB
	 * 
	 * @param queryValues
	 */
	public void insertUser(HashMap<String, String> queryValues) {

		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", queryValues.get("id"));
		values.put("address", queryValues.get("address"));
		values.put("phone", queryValues.get("phone"));
		values.put("name", queryValues.get("name"));
		database.insert("restaurant", null, values);
		database.close();
	}

	/**
	 * Get list of Users from SQLite DB as Array List
	 * 
	 * @return
	 */

	public ArrayList<BusinessListData> getBusinessList() {
		ArrayList<BusinessListData> businessListData = new ArrayList<BusinessListData>();
		SQLiteDatabase sql = this.getReadableDatabase();
		String name = null;
		String address = null;
		String phone = null;
		String id = null;
		String deals_in = null;

		String query = "SELECT  * FROM restaurant";

		Cursor c = sql.rawQuery(query, null);

		if (c.moveToFirst()) {
			do {
				BusinessListData businessData = new BusinessListData(name,
						address, phone, id, deals_in);
				businessData.setName(c.getString(3));
//				Log.d("test1", c.getString(0)); //id
				
				businessData.setAddress(c.getString(1));
//				Log.d("test2", c.getString(1)); //address
				
				businessData.setPhone(c.getString(2));
//				Log.d("test3", c.getString(2)); //phone
				
				businessData.setArtistUrl(c.getString(0));
				
//				Log.d("test4", c.getString(3)); //name
				
				businessListData.add(businessData);
			} while (c.moveToNext());
		}
		c.close();
		sql.close();
		return businessListData;

	}

}
