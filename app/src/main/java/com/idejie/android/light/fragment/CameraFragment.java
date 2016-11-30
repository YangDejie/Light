package com.idejie.android.light.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idejie.android.light.MainActivity;
import com.idejie.android.light.R;


public class CameraFragment extends Fragment {


    MainActivity activity = (MainActivity) getActivity();
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_camera, container, false);
        return view;
    }


}
