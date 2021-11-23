package com.example.cosmonotes;

import android.annotation.SuppressLint;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;


public class ProfileThemeFragment extends Fragment {
    Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View View = inflater.inflate(R.layout.fragment_profile_theme, container, false);
        context = inflater.getContext();
        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch switchTheme = View.findViewById(R.id.switchTheme);

        final homeActivity ha = (homeActivity) getActivity();
        assert ha != null;
        SharedPreferences pref = ha.getSharedPreferences("PREF",ha.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        int theme = pref.getInt("Theme",1);
        if (theme==1){
            switchTheme.setChecked(false);
        }
        else{
            switchTheme.setChecked(true);
        }
        switchTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchTheme.isChecked()){
                    editor.putInt("Theme",0);

                }
                else{
                    editor.putInt("Theme",1);
                }
                editor.commit();
                ha.setDayNight();
            }
        });

        return View;

    }

}