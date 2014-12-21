package com.sp.sodhpuch.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import com.sp.sodhpuch.ListResultActivity;
import com.sp.sodhpuch.R;

public class FragmentMain extends Fragment implements OnClickListener {
	private AutoCompleteTextView acvKeyword;
	private AutoCompleteTextView acvLocation;
	private Button btnSearch;
	private EditText etLocationKey;
	private EditText etQueryType;
	private EditText etRequestType;
	// private BusinessListIconTask imgFetcher;
	public String acType;
	public String data;
	public String json;
	public List<HashMap<String, String>> suggest = new ArrayList<HashMap<String, String>>();
	public List<HashMap<String, String>> CurrentString = new ArrayList<HashMap<String, String>>();
	public ArrayAdapter<String> aAdapter;
    
    public FragmentMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main, null);
		this.acvKeyword = (AutoCompleteTextView) view.findViewById(R.id.etKeyword);
		this.acvLocation = (AutoCompleteTextView) view.findViewById(R.id.etLocation);
		this.etLocationKey = (EditText) view.findViewById(R.id.etLocationKey);
		this.etQueryType = (EditText) view.findViewById(R.id.etQueryType);
		this.etRequestType = (EditText) view.findViewById(R.id.etRequestType);
		this.btnSearch = (Button) view.findViewById(R.id.btnSearch);
//        textView = (TextView) view.findViewById(R.id.fragment_main_textview);
		btnSearch.setOnClickListener(this);
		etLocationKey.setVisibility(View.INVISIBLE);
		etQueryType.setVisibility(View.INVISIBLE);
		etRequestType.setVisibility(View.INVISIBLE);
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
}
