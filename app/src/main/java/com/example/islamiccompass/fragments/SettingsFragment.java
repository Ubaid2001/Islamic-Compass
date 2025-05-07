package com.example.islamiccompass.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.islamiccompass.helper.DataPassListener;
import com.example.islamiccompass.R;

public class SettingsFragment extends Fragment {

    View view;
    private TextView darkmode;
    private Button lightSwitch;

    DataPassListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (DataPassListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ " must implement DataPassListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        darkmode = (TextView) view.findViewById(R.id.darkmode);
        lightSwitch = (Button) view.findViewById(R.id.lightSwitch);
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if(Configuration.UI_MODE_NIGHT_NO == currentNightMode) darkmode.setText("Press Button To Turn on Dark Mode!!!!");
         else darkmode.setText("Press Button To Turn on Light Mode!!!!");


        lightSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = preferences.edit();
                if(Configuration.UI_MODE_NIGHT_NO == currentNightMode) {
                    System.out.println("Onclick Set Boolean To isMorningMode - False");
                    editor.putBoolean("isMorningMode", false);
                    editor.apply();

                } else {
                    System.out.println("Onclick Set Boolean To isMorningMode - True");
                    editor.putBoolean("isMorningMode", true);
                    editor.apply();
                }

                mCallback.passPref();
            }
        });

        return view;
    }
}