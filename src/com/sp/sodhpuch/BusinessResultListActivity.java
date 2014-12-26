package com.sp.sodhpuch;

import java.util.ArrayList;

import com.sp.sodhpuch.adapters.BusinessListDataAdapter;
import com.sp.sodhpuch.data.BusinessListData;
import com.sp.sodhpuch.tasks.BusinessListApiTask;
import com.sp.sodhpuch.tasks.BusinessListIconTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class BusinessResultListActivity extends Activity {

	private ArrayList<BusinessListData> businesses; // BusinessListData object
	private ListView businessList; // ListView object
	private LayoutInflater layoutInflator; // Inflater Object
	private BusinessListIconTask imgFetcher; // Profile icon fetcher task object
	BusinessListDataAdapter adapter; // BusinessListDataAdapter object

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_result_list); // ListView to hold business list
												// data
		this.businessList = (ListView) findViewById(R.id.lvBusinesslist); // Initialize View Containing FieldsofListView
		this.adapter = new BusinessListDataAdapter(this, this.imgFetcher,
				this.layoutInflator, this.businesses); // initialize BusinessListDataAdapter
		this.businesses = null;
		getBusinesses(); // Get Businesses List from server
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		Object[] myStuff = new Object[2];
		myStuff[0] = this.businesses;
		myStuff[1] = this.imgFetcher;
		return myStuff;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	/**
	 * Bundle to hold refs to row items views.
	 * 
	 */

	/* view holder class to hold data from businessListDataAdapter */
	public static class BusinessListViewHolder {
		public TextView businessName, businessAddress, phoneNo;
		public ImageButton btnProfile;
		public ImageView icon;
		public BusinessListData business;
	}

	/* Set BusinessListData fetched from server to businessListDataAdapter */
	public void setBusinesses(ArrayList<BusinessListData> businesses) {
		imgFetcher = new BusinessListIconTask(this);
		layoutInflator = LayoutInflater.from(this);
		if (this.businesses == null || adapter == null) {
			this.businesses = new ArrayList<BusinessListData>();
			adapter = new BusinessListDataAdapter(this, imgFetcher,
					layoutInflator, this.businesses);
			businessList.setAdapter(adapter);
		}
		this.businesses.addAll(businesses);
		adapter.notifyDataSetChanged();

		businessList.setOnScrollListener(new OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (businessList.getLastVisiblePosition() == totalItemCount - 1) {
					getScrollData();
					// Log.d("test count", "abc" + totalItemCount);
				}

			}
		});

	}

	/* Get Businesses data from server */
	private void getBusinesses() {
		// TODO Auto-generated method stub
		Intent myIntent = getIntent();

		// gets the arguments from previously created intent
		String metroTxt = myIntent.getStringExtra("key");
		String metroLoc = myIntent.getStringExtra("loc");
		String metroId = myIntent.getStringExtra("qt");
		String first = "first";

		BusinessListApiTask spTask = new BusinessListApiTask(
				BusinessResultListActivity.this);

		try {
			spTask.execute(metroTxt, metroLoc, metroId, first);

		} catch (Exception e) {
			spTask.cancel(true);
		}
	}

	/* Fetch more data on scroll */
	private void getScrollData() {
		// TODO Auto-generated method stub
		Intent myIntent = getIntent();

		// gets the arguments from previously created intent
		String metroTxt = myIntent.getStringExtra("key");
		String metroLoc = myIntent.getStringExtra("loc");
		String metroId = myIntent.getStringExtra("qt");
		String first = "next";
		BusinessListApiTask spTask = new BusinessListApiTask(
				BusinessResultListActivity.this);

		try {
			spTask.execute(metroTxt, metroLoc, metroId, first);

		} catch (Exception e) {
			spTask.cancel(true);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_bar, menu);

		return super.onCreateOptionsMenu(menu);
	}
}