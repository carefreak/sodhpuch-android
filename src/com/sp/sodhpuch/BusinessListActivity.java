package com.sp.sodhpuch;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.sp.sodhpuch.db.DBController;
import com.sp.sodhpuch.helpers.JSONHelper;
import com.sp.sodhpuch.tasks.BusinessListIconTask;

/**
 * Find/display given business in given area.
 * 
 * @author Carefree
 * 
 */
public class BusinessListActivity extends Activity implements OnClickListener {

	private AutoCompleteTextView acvKeyword;
	private AutoCompleteTextView acvLocation;
	private Button btnSearch;
	private EditText etLocationKey;
	private EditText etQueryType;
	private EditText etRequestType;
	private BusinessListIconTask imgFetcher;
	public String acType;
	public String data;
	public String json;
	public List<HashMap<String, String>> suggest = new ArrayList<HashMap<String, String>>();
	public List<HashMap<String, String>> CurrentString = new ArrayList<HashMap<String, String>>();
	public ArrayAdapter<String> aAdapter;

	DBController controller = new DBController(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		LayoutInflater.from(this);

		this.imgFetcher = new BusinessListIconTask(this);

		this.acvKeyword = (AutoCompleteTextView) findViewById(R.id.etKeyword);
		this.acvLocation = (AutoCompleteTextView) findViewById(R.id.etLocation);
		this.etLocationKey = (EditText) this.findViewById(R.id.etLocationKey);
		this.etQueryType = (EditText) this.findViewById(R.id.etQueryType);
		this.etRequestType = (EditText) this.findViewById(R.id.etRequestType);

		this.btnSearch = (Button) this.findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(this);

		etLocationKey.setVisibility(View.INVISIBLE);
		etQueryType.setVisibility(View.INVISIBLE);
		etRequestType.setVisibility(View.INVISIBLE);

		acvLocation.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable editable) {
				// TODO Auto-generated method stub

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String newText = s.toString();
				acType = "location";
				if (s.length() >= 2) {
					new getSuggestion().execute(newText, acType);

				}

			}

		});
		acvLocation.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				@SuppressWarnings("unchecked")
				Map<String, String> map = (Map<String, String>) arg0
						.getItemAtPosition(arg2);
				String id = map.get("key");
				String name = map.get("biz");
				etLocationKey.setText(id);
				etQueryType.setText(name);

			}
		});

		acvKeyword.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable editable) {
				// TODO Auto-generated method stub

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String newText = s.toString();
				acType = "key";
				if (s.length() >= 2) {
					new getSuggestion().execute(newText, acType);

				}

			}

		});
		acvKeyword.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				@SuppressWarnings("unchecked")
				Map<String, String> map = (Map<String, String>) arg0
						.getItemAtPosition(arg2);
				String id = map.get("key");
				String name = map.get("biz");
				etLocationKey.setText(id);
				etQueryType.setText(name);

			}
		});

	}

	/**
	 * Handy dandy alerter.
	 * 
	 * @param msg
	 *            the message to toast.
	 */
	public void alert(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * Save any fetched Business data for orientation changes.
	 */

	class getSuggestion extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... key) {
			JSONHelper jParser = new JSONHelper();
			String newText = key[0];
			String acType = key[1];
			try {
				newText = URLEncoder.encode(key[0], "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				String url = null;
				if (acType == "location") {
					url = "http://172.27.3.99:81/abc/search.php/?method=get&format=json&address="
							+ newText;
				} else {
					url = "http://172.27.3.99:81/abc/search.php/?method=get&format=json&key="
							+ newText;
				}

				json = jParser.getJSONFromUrl(url);
				Log.d("description", "" + url);
				suggest.clear();
				JSONObject respObj = new JSONObject(json);
				JSONArray jArray = respObj.getJSONArray("businesses");

				HashMap<String, String> hm = new HashMap<String, String>();

				for (int i = 0; i < jArray.length(); i++) {

					JSONObject filter = jArray.getJSONObject(0);
					String SuggestId = filter.getString("id");
					String SuggestLoc = filter.getString("address");
					String SuggestBiz = filter.getString("name");
					if (acType == "location") {
						hm.put("key", SuggestId);
						hm.put("loc", SuggestLoc);
						hm.put("biz", SuggestBiz);
					} else {
						hm.put("key", SuggestId);
						hm.put("loc", SuggestBiz);
						hm.put("biz", SuggestLoc);
					}
					suggest.add(hm);
				}

				Log.d("test", suggest.toString());

			} catch (Exception e) {
				Log.w("Error", e.getMessage());
			}
			if (acType == "location") {
				runOnUiThread(new Runnable() {
					public void run() {
						String[] from = { "key", "loc", "biz" };
						int[] to = { R.id.key, R.id.loc, R.id.biz };

						SimpleAdapter adapter = new SimpleAdapter(
								getBaseContext(), suggest,
								R.layout.autocomplete_layout, from, to);
						acvLocation.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}
				});
			} else {
				runOnUiThread(new Runnable() {
					public void run() {
						String[] from = { "key", "loc", "biz" };
						int[] to = { R.id.key, R.id.loc, R.id.biz };

						SimpleAdapter adapter = new SimpleAdapter(
								getBaseContext(), suggest,
								R.layout.autocomplete_layout, from, to);
						acvKeyword.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}
				});
			}

			return null;
		}

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnSearch) {
			Intent searchIntent = new Intent(BusinessListActivity.this,
					ListResultActivity.class);
			searchIntent.putExtra("key", acvKeyword.getText().toString());
			searchIntent.putExtra("loc", acvLocation.getText().toString());
			searchIntent.putExtra("qt", etQueryType.getText().toString());
			startActivity(searchIntent);
		}
	}

}