package com.idejie.android.light.fragment;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idejie.android.light.MainActivity;
import com.idejie.android.light.R;
import com.idejie.android.light.widget.LightDashboardView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;


public class LightFragment extends Fragment {
    View lightFragment;
    private LightDashboardView lightDashboardView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        lightFragment = inflater.inflate(R.layout.fragment_light, container, false);
        initView();
        return lightFragment;
    }

    private void initView() {
        lightDashboardView= (LightDashboardView) lightFragment.findViewById(R.id.lightDashboard);
        MainActivity mainActivity= (MainActivity) getActivity();
        SensorManager sensorManager= (SensorManager) mainActivity.getSystemService(SENSOR_SERVICE);
        Sensor lightSensor =sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float light= event.values[0];
                int l =(int)light;
                lightDashboardView.setRealTimeValue((int)light);
//                mDashboardView2.setCreditValueWithAnim((int)light/10+350);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        },lightSensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

}
