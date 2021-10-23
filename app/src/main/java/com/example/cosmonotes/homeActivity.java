package com.example.cosmonotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class homeActivity extends AppCompatActivity {
    private static final String TAG = "GoogleActivity";
    private static final int LOCATION_REQUEST_CODE = 100001;
    FusedLocationProviderClient fusedLocationProviderClient; // Para usar la ubicacion

    private static String ApiKey = "00422bf7e8d26b5adb0b769f1c0275dd";
    private static String longitud;
    private static String latitud;
    private static String ApiUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getLastLocation();
        }
    }

    private void getLastLocation(){
      @SuppressLint("MissingPermission") Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    Log.d(TAG, "On succes: " + location.toString());
                    Log.d(TAG, "On succes: " + location.getLongitude());
                    longitud = Double.toString(location.getLongitude());
                    Log.d(TAG, "On succes: " + location.getLatitude());
                    latitud = Double.toString(location.getLatitude());
                    ApiUrl = "https://api.openweathermap.org/data/2.5/weather?lat="+latitud+"&lon="+longitud+"&appid="+ApiKey;
                    getWeather();
                }
            }
        });

        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "On failure " + e.getLocalizedMessage());
            }
        });
    }

    private void getWeather(){
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest string = new StringRequest(Request.Method.GET, ApiUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Response" + response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "ERROR: " + error.toString());
            }
        });
        queue.add(string);
    }
}