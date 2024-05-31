package com.example.indoorairqualitymonitoringapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class SettingFragment extends Fragment {
    private String accessToken;
    private DashBoardactivity activity;
    private String username;
    private AppCompatButton btnchangeunit, btnlogout, btnAbout;
    private TextView tvuser;

    public static SettingFragment newInstance(String accessToken,String username, DashBoardactivity activity) {
        SettingFragment fragment = new SettingFragment();
        fragment.accessToken = accessToken;
        fragment.username = username;
        fragment.activity = activity;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        tvuser = view.findViewById(R.id.nameuser);
        tvuser.setText(username);
        btnAbout = view.findViewById(R.id.btnAbout);
        btnchangeunit = view.findViewById(R.id.btnChangeUnit);
        btnchangeunit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.viewPager2Main.setCurrentItem(1, false);
                activity.getWeatherFragment().convertTemperatureToFahrenheit();
            }
        });
        btnlogout = view.findViewById(R.id.btnLogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AboutActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DashBoardactivity) {
            activity = (DashBoardactivity) context;
        } else {
            Log.e("SettingFragment", "Activity must be of type DashBoardactivity");
        }
    }
}
