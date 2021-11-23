package com.example.cosmonotes;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

public class themeFragment extends Fragment {

    public themeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_theme, container, false);

        //Tema oscuro
        final homeActivity ha = (homeActivity) getActivity();
        SharedPreferences pref = ha.getSharedPreferences("PREF",ha.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        final Switch switchT = root.findViewById(R.id.switchTheme);

        int theme = pref.getInt("Theme",1);
        if (theme==1){
            switchT.setChecked(false);
        }
        else{
            switchT.setChecked(true);
        }
        switchT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchT.isChecked()){
                    editor.putInt("Theme",0);
                }
                else{
                    editor.putInt("Theme",1);
                }
                editor.commit();
                ha.setDayNight();
            }
        });

        return root;

    }
    
}