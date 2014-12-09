package com.sp.sodhpuch;

import java.util.ArrayList;

import com.sp.sodhpuch.adapters.BusinessListDataAdapter;
import com.sp.sodhpuch.data.BusinessListData;
import com.sp.sodhpuch.tasks.BusinessListApiTask;
import com.sp.sodhpuch.tasks.BusinessListIconTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListResultActivity extends Activity {

	private ArrayList<BusinessListData> businesses;
	private ListView businessList;
	private LayoutInflater layoutInflator;
	private BusinessListIconTask imgFetcher;
	BusinessListDataAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.businesslist);
		this.businessList = (ListView) findViewById(R.id.lvBusinesslist);
		this.adapter = new BusinessListDataAdapter(this, this.imgFetcher,
				this.layoutInflator, this.businesses);
		this.businesses = null;
		getData();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		Object[] myStuff = new Object[2];
		myStuff[0] = this.businesses;
		myStuff[1] = this.imgFetcher;
		return myStuff;
	}

	/**
	 * Bundle to hold refs to row items views.
	 * 
	 */
	public static class MyViewHolder {
		public TextView businessName, businessAddress, phoneNo;
		public Button btnProfile;
		public ImageView icon;
		public BusinessListData business;
	}

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

	private void getData() {
		// TODO Auto-generated method stub
		Intent myIntent = getIntent();

		// gets the arguments from previously created intent
		String metroTxt = myIntent.getStringExtra("key");
		String metroLoc = myIntent.getStringExtra("loc");
		String metroId = myIntent.getStringExtra("qt");
		String first = "first";

		BusinessListApiTask spTask = new BusinessListApiTask(
				ListResultActivity.this);

		try {
			spTask.execute(metroTxt, metroLoc, metroId, first);

		} catch (Exception e) {
			spTask.cancel(true);
		}
	}

	private void getScrollData() {
		// TODO Auto-generated method stub
		Intent myIntent = getIntent();

		// gets the arguments from previously created intent
		String metroTxt = myIntent.getStringExtra("key");
		String metroLoc = myIntent.getStringExtra("loc");
		String metroId = myIntent.getStringExtra("qt");
		String first = "next";
		BusinessListApiTask spTask = new BusinessListApiTask(
				ListResultActivity.this);

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