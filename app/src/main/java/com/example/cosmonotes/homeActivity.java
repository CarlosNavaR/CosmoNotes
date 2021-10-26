package com.example.cosmonotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class homeActivity extends AppCompatActivity {


    private static Geocoder geocoder;
    private static List<Address> addresses;
    private static final String TAG = "GoogleActivity";
    private static final int LOCATION_REQUEST_CODE = 100001;
    FusedLocationProviderClient fusedLocationProviderClient; // Para usar la ubicacion

    private static String ApiKey = "00422bf7e8d26b5adb0b769f1c0275dd";
    private static String Ciudad;
    private static String longitud;
    private static String latitud;
    private static String ApiUrl;

    private TextView mWeatherTextView;
    private ImageView mIconWeatherImgView;
    private TextView mCityTextView;
    private CircleImageView mProfileUserImgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        geocoder = new Geocoder(this, Locale.getDefault());

        mWeatherTextView = findViewById(R.id.textviewWeather);
        mIconWeatherImgView = findViewById(R.id.IconImageWeather);
        mCityTextView = findViewById(R.id.textViewCity);
        mProfileUserImgView = findViewById(R.id.ProfileUserImgView);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            mProfileUserImgView.setImageURI(Uri.parse(String.valueOf(signInAccount.getPhotoUrl())));
            Picasso.get().load(signInAccount.getPhotoUrl()).placeholder(R.drawable.ic_launcher_background).into(mProfileUserImgView);
        }
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

                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        Ciudad = addresses.get(0).getLocality();
                        Log.d(TAG, "Ciudad: " +  addresses.get(0).getLocality());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ApiUrl = "https://api.openweathermap.org/data/2.5/weather?lat="+latitud+"&lon="+longitud+"&appid="+ApiKey+"&units=metric";
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
                try {
                    JSONObject jsonData = new JSONObject(response);
                    JSONArray jsonWeatherArray = jsonData.getJSONArray("weather"); // ESte se usa para accesar al elemento weather y traer icono
                    JSONObject jsonWeather = jsonWeatherArray.getJSONObject(0);
                    String icon = jsonWeather.getString("icon");

                    JSONObject jsonObjectMain = jsonData.getJSONObject("main"); // ESte sirve para obtener la temperatura
                    double temp = jsonObjectMain.getDouble("temp");
                    mWeatherTextView.setText(Double.toString(temp) + " °C");


                    Log.e(TAG, "Icono " + icon);

                    String IconDrawable = "w" + icon;
                    int id = getResources().getIdentifier(IconDrawable, "drawable", getPackageName());
                    Drawable drawable = getResources().getDrawable(id);
                    mIconWeatherImgView.setBackground(drawable);

                    mCityTextView.setText(Ciudad);
                    Log.i(TAG, "Temperatura: " + temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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