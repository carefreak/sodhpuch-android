package com.sp.sodhpuch.fragments;

import com.sp.sodhpuch.R;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragmentImageView extends Fragment {
    ImageView imageView;
    
    public FragmentImageView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imageview, null);
        
        imageView = (ImageView) view.findViewById(R.id.fragment_imageview_imageview);
        
        imageView.setImageResource(R.drawable.icon);
        
        return view;
    }
}
