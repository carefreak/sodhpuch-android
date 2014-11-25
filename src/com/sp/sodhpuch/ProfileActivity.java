package com.sp.sodhpuch;

import com.sp.sodhpuch.contacts.SaveContacts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends Activity implements OnClickListener {
	// public TextView profile_name;
	public Button btnAddContacts;
	public String name;
	public String phone;
	public String address;
	public String deals_in;
	private Object activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business_profile);
		TextView profile_name = (TextView) findViewById(R.id.profile_name);
		TextView profile_address = (TextView) findViewById(R.id.profile_address);
		TextView profile_phone = (TextView) findViewById(R.id.profile_phone);
		TextView profile_deals = (TextView) findViewById(R.id.profile_deals);
		Button add_contacts = (Button) findViewById(R.id.add_contacts);
		add_contacts.setOnClickListener(this);

		Intent myIntent = getIntent();
		name = myIntent.getStringExtra("name");
		phone = myIntent.getStringExtra("phone");
		address = myIntent.getStringExtra("address");
		deals_in = myIntent.getStringExtra("deals_in");
		// Log.d("abc", metroTxt);
		profile_name.setText(name);
		profile_phone.setText(phone);
		profile_address.setText(address);
		profile_deals.setText(deals_in);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.add_contacts) {
			SaveContacts save = new SaveContacts(ProfileActivity.this);
			save.execute(name, phone);
//			Log.d("contact", name);
		}
		else if(v.getId() == R.id.profile_phone){
			Intent intent = new Intent(Intent.ACTION_CALL);
			   intent.setData(Uri.parse("tel:" +phone));
			   ((Activity) this.activity).startActivity(intent);
		}

	}

}
