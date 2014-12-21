package com.sp.sodhpuch.fragments;

import com.sp.sodhpuch.R;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentButton extends Fragment implements OnClickListener {
    Button button;
    
    public FragmentButton() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, null);
    
        button = (Button) view.findViewById(R.id.fragment_button_button);
        
        button.setOnClickListener(this);
        return view;
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
//		Intent i = new Intent(getActivity(), BookmarkActivity.class);
//		getActivity().startActivity(i);
//		button.setText("Button after clicked");
	}
}
