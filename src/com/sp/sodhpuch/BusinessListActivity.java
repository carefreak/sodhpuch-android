//package com.sp.sodhpuch;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.brickred.socialauth.Profile;
//import org.brickred.socialauth.android.DialogListener;
//import org.brickred.socialauth.android.SocialAuthAdapter;
//import org.brickred.socialauth.android.SocialAuthError;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.SimpleAdapter;
//
//import com.sp.sodhpuch.db.DBController;
//import com.sp.sodhpuch.tasks.BusinessInfoSuggestionTask;
//
////import com.sp.sodhpuch.tasks.BusinessListIconTask;
//
///**
// * Find/display given business in given area.
// * 
// * @author Carefree
// * 
// */
//// Main Activity that loads home page
//public class BusinessListActivity extends Activity implements OnClickListener {
//
//	/* Declare search field variables */
//	private AutoCompleteTextView acvKeyword;
//	private AutoCompleteTextView acvLocation;
//	private Button btnSearch;
//	private EditText etLocationKey;
//	private EditText etQueryType;
//	private EditText etRequestType;
//	// private BusinessListIconTask imgFetcher;
//	/* Data variables for Autocomplete result fetching */
//	public String acType;
//	public ArrayAdapter<String> aAdapter;
//	public BusinessInfoSuggestionTask autocomplete;
//	SocialAuthAdapter adapter;
//
//	/* SQLite database initialization */
//	DBController controller = new DBController(this);
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main); // Home page i.e search page
//		LayoutInflater.from(this);
//		/* Social Auth actions */
//
//		// Button facebook_button = (Button) findViewById(R.id.fb_btn);
//		// facebook_button.setBackgroundResource(R.drawable.facebook);
//		// adapter = new SocialAuthAdapter(new ResponseListener());
//		//
//		// // Add providers
//		// adapter.addProvider(Provider.LINKEDIN, R.drawable.facebook);
//		// adapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
//		// adapter.addProvider(Provider.TWITTER, R.drawable.facebook);
//		// adapter.enable(facebook_button);
//		// this.imgFetcher = new BusinessListIconTask(this);
//
//		/* Initializing search fields */
//		this.acvKeyword = (AutoCompleteTextView) findViewById(R.id.etKeyword);
//		this.acvLocation = (AutoCompleteTextView) findViewById(R.id.etLocation);
//		this.etLocationKey = (EditText) this.findViewById(R.id.etLocationKey);
//		this.etQueryType = (EditText) this.findViewById(R.id.etQueryType);
//		this.etRequestType = (EditText) this.findViewById(R.id.etRequestType);
//		this.btnSearch = (Button) this.findViewById(R.id.btnSearch);
//		/* fire search query */
//		btnSearch.setOnClickListener(this);
//		/* Visibility of fields */
//		etLocationKey.setVisibility(View.INVISIBLE);
//		etQueryType.setVisibility(View.INVISIBLE);
//		etRequestType.setVisibility(View.INVISIBLE);
//		/* Location field Auto COmplete Actions */
//		acvLocation.addTextChangedListener(new TextWatcher() {
//
//			public void afterTextChanged(Editable editable) {
//				// TODO Auto-generated method stub
//
//			}
//
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				// TODO Auto-generated method stub
//			}
//
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//				BusinessInfoSuggestionTask autocompleteTask = new BusinessInfoSuggestionTask(BusinessListActivity.this);
//				String newText = s.toString();
//				acType = "location";
//				if (s.length() >= 2) {
//					autocompleteTask.execute(newText, acType);
//
//				}
//
//			}
//
//		});
//		/* OnClick Location suggestion */
//		acvLocation.setOnItemClickListener(new OnItemClickListener() {
//
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//
//				@SuppressWarnings("unchecked")
//				Map<String, String> map = (Map<String, String>) arg0
//						.getItemAtPosition(arg2);
//				String id = map.get("key");
//				String name = map.get("biz");
//				etLocationKey.setText(id);
//				etQueryType.setText(name);
//
//			}
//		});
//		/* Keyword field Auto COmplete Actions */
//		acvKeyword.addTextChangedListener(new TextWatcher() {
//
//			public void afterTextChanged(Editable editable) {
//				// TODO Auto-generated method stub
//
//			}
//
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				// TODO Auto-generated method stub
//			}
//
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//				BusinessInfoSuggestionTask autocompleteTask = new BusinessInfoSuggestionTask(BusinessListActivity.this);
//				String newText = s.toString();
//				acType = "key";
//				if (s.length() >= 2) {
//					autocompleteTask.execute(newText, acType);
//
//				}
//
//			}
//
//		});
//		/* OnClick Keyword suggestion */
//		acvKeyword.setOnItemClickListener(new OnItemClickListener() {
//
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//
//				@SuppressWarnings("unchecked")
//				Map<String, String> map = (Map<String, String>) arg0
//						.getItemAtPosition(arg2);
//				String id = map.get("key");
//				String name = map.get("biz");
//				etLocationKey.setText(id);
//				etQueryType.setText(name);
//
//			}
//		});
//
//	}
//
//	/* Search button on click action */
//	@Override
//	public void onClick(View v) {
//		if (v.getId() == R.id.btnSearch) {
//			/* New ListView Activity Intent */
//			Intent searchIntent = new Intent(BusinessListActivity.this,
//					ListResultActivity.class);
//			searchIntent.putExtra("key", acvKeyword.getText().toString());
//			searchIntent.putExtra("loc", acvLocation.getText().toString());
//			searchIntent.putExtra("qt", etQueryType.getText().toString());
//			startActivity(searchIntent);
//		}
//	}
//
//	/* Action Bar Menu Options */
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.action_bar, menu);
//		return super.onCreateOptionsMenu(menu);
//	}
//
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Take appropriate action for each action item click
//		switch (item.getItemId()) {
//		case R.id.action_refresh:
//			// refresh
//			return true;
//		case R.id.action_help:
//			// help action
//			return true;
//		case R.id.action_check_updates:
//			// check for updates action
//			return true;
//		default:
//			return super.onOptionsItemSelected(item);
//		}
//	}
//
//	/* Response Listener for social auth adapter */
//	private final class ResponseListener implements DialogListener {
//		private Profile abc;
//
//		public void onComplete(Bundle values) {
//
//			// Variable to receive message status
//			boolean status;
//			String provider = values.getString(SocialAuthAdapter.PROVIDER);
//			Log.d("ShareButton", "Authentication Successful" + provider);
//
//			// Get name of provider after authentication
//
//			try {
//				// Please avoid sending duplicate message. Social Media
//				// Providers block duplicate messages.
//				abc = adapter.getCurrentProvider().getUserProfile();
//				status = true;
//			} catch (Exception e) {
//				status = false;
//				Log.d("", "" + e);
//			}
//			if (status) {
//				Intent intent = new Intent(BusinessListActivity.this,
//						Profile_Activity.class);
//				intent.putExtra("provider", provider);
//				intent.putExtra("profile", abc);
//				startActivity(intent);
//			} else {
//				Log.d("haai", "aalu khau" + status);
//			}
//
//		}
//
//		public void onError(SocialAuthError error) {
//			Log.d("ShareButton", "Authentication Error");
//		}
//
//		public void onCancel() {
//			Log.d("ShareButton", "Authentication Cancelled");
//		}
//
//		@Override
//		public void onBack() {
//			// TODO Auto-generated method stub
//
//		}
//
//	}
//
//	/* Check if Network is available before starting serach action */
//	public boolean isNetworkAvailable() {
//		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo activeNetworkInfo = connectivityManager
//				.getActiveNetworkInfo();
//		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//	}
////
////	/* Get suggestion from server */
////	class GetSuggestion extends AsyncTask<String, String, String> {
////
////		@Override
////		protected String doInBackground(String... key) {
////			if (isNetworkAvailable()) {
////				JSONHelper jParser = new JSONHelper();
////				String acKey = key[0]; // string typed on EditText field
////				String acType = key[1]; // Location suggestion or KeyWord
////										// suggestion?
////				try {
////					acKey = URLEncoder.encode(key[0], "UTF-8");
////				} catch (UnsupportedEncodingException e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
////
////				try {
////					String url = null;
////					if (acType == "location") {
////						url = "http://172.27.3.99:81/abc/search.php/?method=get&format=json&address="
////								+ acKey;
////					} else {
////						url = "http://172.27.3.99:81/abc/search.php/?method=get&format=json&key="
////								+ acKey;
////					}
////
////					json = jParser.getJSONFromUrl(url);
////					Log.d("description", "" + url);
////					suggest.clear();
////					JSONObject respObj = new JSONObject(json);
////					JSONArray jArray = respObj.getJSONArray("businesses");
////
////					HashMap<String, String> hm = new HashMap<String, String>();
////
////					for (int i = 0; i < jArray.length(); i++) {
////
////						JSONObject filter = jArray.getJSONObject(0);
////						String SuggestId = filter.getString("id");
////						String SuggestLoc = filter.getString("address");
////						String SuggestBiz = filter.getString("name");
////						if (acType == "location") {
////							hm.put("key", SuggestId);
////							hm.put("loc", SuggestLoc);
////							hm.put("biz", SuggestBiz);
////						} else {
////							hm.put("key", SuggestId);
////							hm.put("loc", SuggestBiz);
////							hm.put("biz", SuggestLoc);
////						}
////						suggest.add(hm);
////					}
////
////					Log.d("test", suggest.toString());
////
////				} catch (Exception e) {
////					Log.w("Error", e.getMessage());
////				}
////				if (acType == "location") {
////					runOnUiThread(new Runnable() {
////						public void run() {
////							String[] from = { "key", "loc", "biz" };
////							int[] to = { R.id.key, R.id.loc, R.id.biz };
////
////							SimpleAdapter adapter = new SimpleAdapter(
////									getBaseContext(), suggest,
////									R.layout.autocomplete_layout, from, to);
////							acvLocation.setAdapter(adapter);
////							adapter.notifyDataSetChanged();
////						}
////					});
////				} else {
////					runOnUiThread(new Runnable() {
////						public void run() {
////							String[] from = { "key", "loc", "biz" };
////							int[] to = { R.id.key, R.id.loc, R.id.biz };
////
////							SimpleAdapter adapter = new SimpleAdapter(
////									getBaseContext(), suggest,
////									R.layout.autocomplete_layout, from, to);
////							acvKeyword.setAdapter(adapter);
////							adapter.notifyDataSetChanged();
////						}
////					});
////				}
////			}
////			return null;
////		}
////
////	}
//
//	public void setLocationAutoComplete(List<HashMap<String, String>> suggestion) {
//		String[] from = { "key", "loc", "biz" };
//		int[] to = { R.id.key, R.id.loc, R.id.biz };
//		Log.d("string", suggestion.toString());
//		SimpleAdapter adapter = new SimpleAdapter(
//				getBaseContext(), suggestion,
//				R.layout.autocomplete_layout, from, to);
//		acvLocation.setAdapter(adapter);
//		adapter.notifyDataSetChanged();
//		
//	}
//	public void setKeywordAutoComplete(List<HashMap<String, String>> suggestion) {
//		String[] from = { "key", "loc", "biz" };
//		int[] to = { R.id.key, R.id.loc, R.id.biz };
//
//		SimpleAdapter adapter = new SimpleAdapter(
//				getBaseContext(), suggestion,
//				R.layout.autocomplete_layout, from, to);
//		acvKeyword.setAdapter(adapter);
//		adapter.notifyDataSetChanged();
//		
//	}
//}