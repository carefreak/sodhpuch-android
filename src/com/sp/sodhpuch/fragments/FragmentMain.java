package com.sp.sodhpuch.fragments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.sp.sodhpuch.ListResultActivity;
import com.sp.sodhpuch.R;
import com.sp.sodhpuch.tasks.BusinessInfoSuggestionTask;

public class FragmentMain extends Fragment implements OnClickListener {
	private AutoCompleteTextView acvKeyword;
	private AutoCompleteTextView acvLocation;
	private Button btnSearch;
	private EditText etLocationKey;
	private EditText etQueryType;
	private EditText etRequestType;
	// private BusinessListIconTask imgFetcher;
	public ArrayAdapter<String> aAdapter;
	public BusinessInfoSuggestionTask autocomplete;
	public String acType;

	public FragmentMain() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main, null);
		this.acvKeyword = (AutoCompleteTextView) view
				.findViewById(R.id.etKeyword);
		this.acvLocation = (AutoCompleteTextView) view
				.findViewById(R.id.etLocation);
		this.etLocationKey = (EditText) view.findViewById(R.id.etLocationKey);
		this.etQueryType = (EditText) view.findViewById(R.id.etQueryType);
		this.etRequestType = (EditText) view.findViewById(R.id.etRequestType);
		this.btnSearch = (Button) view.findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(this);
		//Invisible form field to send extra data
		etLocationKey.setVisibility(View.INVISIBLE);
		etQueryType.setVisibility(View.INVISIBLE);
		etRequestType.setVisibility(View.INVISIBLE);

		// Calls async task to display suggestion on text change of location
		// field
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
				BusinessInfoSuggestionTask autocompleteTask = new BusinessInfoSuggestionTask(
						new FragmentCallback() {

							@Override
							public void onAsyncTaskComplete(
									List<HashMap<String, String>> autocompletedata) {
								setLocationAutoComplete(autocompletedata);
							}
						});
				String newText = s.toString();
				acType = "location";
				if (s.length() >= 2) {
					autocompleteTask.execute(newText, acType);

				}

			}

		});
		/* Set Selected location on EditText field */
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
		// Calls async task to display suggestion on text change of Keyword
		// field
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
				BusinessInfoSuggestionTask autocompleteTask = new BusinessInfoSuggestionTask(
						new FragmentCallback() {

							@Override
							public void onAsyncTaskComplete(
									List<HashMap<String, String>> autocompletedata) {
								setKeywordAutoComplete(autocompletedata);
							}
						});
				String newText = s.toString();
				acType = "key";
				if (s.length() >= 2) {
					autocompleteTask.execute(newText, acType);

				}

			}

		});
		/* Set selected keyword on EditText field */
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

		return view;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnSearch) {

			Intent searchIntent = new Intent(getActivity(),
					ListResultActivity.class);
			searchIntent.putExtra("key", acvKeyword.getText().toString());
			searchIntent.putExtra("loc", acvLocation.getText().toString());
			searchIntent.putExtra("qt", etQueryType.getText().toString());
			getActivity().startActivity(searchIntent);
		}
	}
	
	//Populates dropdown with suggestion of location
	public void setLocationAutoComplete(List<HashMap<String, String>> suggestion) {
		String[] from = { "key", "loc", "biz" };
		int[] to = { R.id.key, R.id.loc, R.id.biz };
		Log.d("string", suggestion.toString());
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), suggestion,
				R.layout.autocomplete_layout, from, to);
		acvLocation.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}
	//Populates dropdown with suggestion of keyword
	public void setKeywordAutoComplete(List<HashMap<String, String>> suggestion) {
		String[] from = { "key", "loc", "biz" };
		int[] to = { R.id.key, R.id.loc, R.id.biz };

		SimpleAdapter adapter = new SimpleAdapter(getActivity(), suggestion,
				R.layout.autocomplete_layout, from, to);
		acvKeyword.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}
	
	//Callback interface to be called by asynctask on completion of exectuion
	public interface FragmentCallback {
		public void onAsyncTaskComplete(
				List<HashMap<String, String>> autocompletedata);
	}
}
