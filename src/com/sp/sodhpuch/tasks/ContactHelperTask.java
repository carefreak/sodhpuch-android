package com.sp.sodhpuch.tasks;

import java.util.ArrayList;

import com.sp.sodhpuch.BusinessProfileActivity;
import android.content.*;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.widget.Toast;

public class ContactHelperTask extends AsyncTask<String, Integer, String> {
	private BusinessProfileActivity activity;
	String result = null;

	public ContactHelperTask(BusinessProfileActivity activity) {
		// TODO Auto-generated constructor stub
		super();
		this.activity = activity;
		this.activity.getApplicationContext();
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String name = params[0];
		String phone = params[1];

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		int rawContactID = ops.size();

		// Adding insert operation to operations list
		// to insert a new raw contact in the table ContactsContract.RawContacts
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
				.withValue(RawContacts.ACCOUNT_NAME, null).build());

		// Adding insert operation to operations list
		// to insert display name in the table ContactsContract.Data
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
						rawContactID)
				.withValue(ContactsContract.Data.MIMETYPE,
						StructuredName.CONTENT_ITEM_TYPE)
				.withValue(StructuredName.DISPLAY_NAME, name).build());

		// Adding insert operation to operations list
		// to insert Mobile Number in the table ContactsContract.Data
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
						rawContactID)
				.withValue(ContactsContract.Data.MIMETYPE,
						Phone.CONTENT_ITEM_TYPE).withValue(Phone.NUMBER, phone)
				.withValue(Phone.TYPE, CommonDataKinds.Phone.TYPE_MOBILE)
				.build());
		//
		// // Adding insert operation to operations list
		// // to insert Home Phone Number in the table ContactsContract.Data
		// ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		// .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
		// rawContactID)
		// .withValue(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
		// .withValue(Phone.NUMBER, etHomePhone.getText().toString())
		// .withValue(Phone.TYPE, Phone.TYPE_HOME)
		// .build());
		//
		// // Adding insert operation to operations list
		// // to insert Home Email in the table ContactsContract.Data
		// ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		// .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
		// rawContactID)
		// .withValue(ContactsContract.Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
		// .withValue(Email.ADDRESS, etHomeEmail.getText().toString())
		// .withValue(Email.TYPE, Email.TYPE_HOME)
		// .build());
		//
		// // Adding insert operation to operations list
		// // to insert Work Email in the table ContactsContract.Data
		// ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		// .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
		// rawContactID)
		// .withValue(ContactsContract.Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
		// .withValue(Email.ADDRESS, etWorkEmail.getText().toString())
		// .withValue(Email.TYPE, Email.TYPE_WORK)
		// .build());

		try {
			// Executing all the insert operations as a single database
			// transaction
			activity.getContentResolver().applyBatch(
					ContactsContract.AUTHORITY, ops);
			Log.d("async", name);
			result = "1";
			// Toast.makeText(activity.getBaseContext(),
			// "Contact is successfully added", Toast.LENGTH_SHORT).show();
		} catch (RemoteException e) {
			e.printStackTrace();
			
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}

		return result;
	}

	protected void onPostExecute(String result) {
		// super.onPostExecute();
		if (result == "1") {
			Toast.makeText(this.activity.getBaseContext(),
					"Contact is successfully added", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this.activity.getBaseContext(),
					"Contact could not be added", Toast.LENGTH_SHORT).show();
		}
	}

}
