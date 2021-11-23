package com.example.cosmonotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.time.LocalDate;



public class homeActivity extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CalendarUtils.selectedDate = LocalDate.now();
        chipNavigationBar = findViewById(R.id.bottom_nav_bar);
        chipNavigationBar.setItemSelected(R.id.home, true);
        MenuNavegacion();
        setDayNight();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, new HomeFragment()).commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //getLastLocation();
        }

    }

    private void MenuNavegacion(){

        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        Fragment fragment = null;
                        switch (i){
                            case R.id.home:
                                fragment = new HomeFragment();
                                break;
                            case R.id.Calendario:
                                fragment = new CalendarFragment();
                                break; 
                            case R.id.Notas:
                                fragment = new NotesFragment();
                                break;
                            case R.id.Pendientes:
                                fragment = new ToDoFragment();
                                break;
                        }
                        assert fragment != null;
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_Container,
                                        fragment).commit();
                    }
                });
    }

    public void newEvent(View view) {
        //NewEventFragment.newInstance().show(getSupportFragmentManager(), NewEventFragment.TAG);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, new NewEventFragment()).commit();
    }

    public void newGroup(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, new NewGroupFragment()).commit();
    }

    public void CancelCreateGroup(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, new ToDoFragment()).commit();
    }

    public void CancelCreateEvent(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, new CalendarFragment()).commit();
    }

    public void configuracion(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, new ProfileThemeFragment()).commit();
    }

    public void setDayNight(){
        SharedPreferences pref= getSharedPreferences("PREF",this.MODE_PRIVATE);
        int theme= pref.getInt("Theme",1);
        if (theme ==0){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public void CloseConfiguration(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, new HomeFragment()).commit();
    }

}