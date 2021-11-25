package com.example.cosmonotes;

import android.annotation.SuppressLint;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileThemeFragment extends Fragment {
    private CircleImageView mProfileUserImgView;
    private TextView userNAme, userMail;
    private LinearLayout logOut;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View View = inflater.inflate(R.layout.fragment_profile_theme, container, false);
        context = inflater.getContext();

        mProfileUserImgView = View.findViewById(R.id.ProfileUserImgView);
        userNAme = View.findViewById(R.id.NombreTV);
        userMail = View.findViewById(R.id.CorreoTV);
        logOut = View.findViewById(R.id.LogOut);

        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch switchTheme = View.findViewById(R.id.switchTheme);

        final homeActivity ha = (homeActivity) getActivity();
        assert ha != null;
        SharedPreferences pref = ha.getSharedPreferences("PREF",ha.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        int theme = pref.getInt("Theme",1);

        switchTheme.setChecked(theme != 1);

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


        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(context);
        if(signInAccount != null){
            mProfileUserImgView.setImageURI(Uri.parse(String.valueOf(signInAccount.getPhotoUrl())));
            Picasso.get().load(signInAccount.getPhotoUrl()).placeholder(R.drawable.ic_launcher_background).into(mProfileUserImgView);
            userNAme.setText(upperCaseFirst(signInAccount.getDisplayName()));
            userMail.setText(signInAccount.getEmail());
        }

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(signInAccount != null){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getContext(), MainActivity.class));
                }
            }
        });
        return View;
    }


    public static String upperCaseFirst(String val) {
        StringBuffer strbf = new StringBuffer();
        Matcher match = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(val);
        while(match.find())
        {
            match.appendReplacement(strbf, match.group(1).toUpperCase() + match.group(2).toLowerCase());
        }
        return match.appendTail(strbf).toString();
    }

}