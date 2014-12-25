package com.sp.sodhpuch.adapters;

import java.util.ArrayList;

//import com.sp.sodhpuch.BusinessListActivity.MyViewHolder;
import com.sp.sodhpuch.BusinessResultListActivity;
import com.sp.sodhpuch.BusinessResultListActivity.BusinessListViewHolder;
import com.sp.sodhpuch.BusinessProfileActivity;
import com.sp.sodhpuch.R;
import com.sp.sodhpuch.data.BusinessListData;
import com.sp.sodhpuch.tasks.BusinessListIconTask;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A custom adapter to fill the Track list.
 * 
 * @author Carefree
 * 
 */
public class BusinessListDataAdapter extends BaseAdapter implements
		OnClickListener {

	private static final String debugTag = "BusinessListDataAdapter";
	private BusinessResultListActivity activity;
	private BusinessListIconTask imgFetcher;
	private LayoutInflater layoutInflater;
	private ArrayList<BusinessListData> businesses;
	BusinessListData business;
	
	public BusinessListDataAdapter(BusinessResultListActivity a,
			BusinessListIconTask i, LayoutInflater l,
			ArrayList<BusinessListData> data) {
		this.activity = a;
		this.imgFetcher = i;
		this.layoutInflater = l;
		this.businesses = data;
		
	}
	

	@Override
	public int getCount() {
		return this.businesses.size();
	}
	public void clear()
	{
	    businesses.clear();
	    notifyDataSetChanged();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		BusinessListViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.list_item_businesses, parent,
					false);
			holder = new BusinessListViewHolder();
			holder.businessName = (TextView) convertView
					.findViewById(R.id.tvBusinessName);
			holder.businessAddress = (TextView) convertView
					.findViewById(R.id.tvAddress);
			holder.phoneNo = (TextView) convertView.findViewById(R.id.tvPhone);
			holder.icon = (ImageView) convertView.findViewById(R.id.album_icon);
			holder.btnProfile = (ImageButton) convertView
					.findViewById(R.id.btnProfile);
			holder.btnProfile.setTag(holder);
			convertView.setTag(holder);
		} else {
			holder = (BusinessListViewHolder) convertView.getTag();
		}

		convertView.setOnClickListener(this);

		business= businesses.get(pos);
		holder.business = business;
		holder.businessName.setText(business.getName());
		holder.businessAddress.setText(business.getAddress());
		holder.phoneNo.setText(business.getPhone());
		holder.btnProfile.setOnClickListener(this);


		// if(track.getImageUrl() != null) {
		// holder.icon.setTag(track.getImageUrl());
		// Drawable dr = imgFetcher.loadImage(this, holder.icon);
		// if(dr != null) {
		// holder.icon.setImageDrawable(dr);
		// }
		// } else {
		holder.icon.setImageResource(R.drawable.filler_icon);
		// }

		return convertView;
	}
	@Override
	public void onClick(View v) {
		String deals_in = business.getDeals().toString();
		Log.d("name", deals_in);
		BusinessListViewHolder holder = (BusinessListViewHolder) v.getTag();
		Log.d(debugTag, "OnClick pressed.");
		
		switch(v.getId())
		{
		case R.id.tvPhone:
		// handle phone number click;
			Intent intent = new Intent(Intent.ACTION_CALL);
			   intent.setData(Uri.parse("tel:" +holder.business.getPhone()));
			   this.activity.startActivity(intent);
			
		break;
		case R.id.btnProfile:
		// handle profile button click;
			Intent profile = new Intent(activity,
					BusinessProfileActivity.class);
			profile.putExtra("deals_in", deals_in);
			profile.putExtra("phone", holder.business.getPhone());
			profile.putExtra("address", holder.business.getAddress());
			profile.putExtra("name", holder.business.getName());
			this.activity.startActivity(profile);
		break;
		default:
		Log.d("","ss");
		}
	}

}