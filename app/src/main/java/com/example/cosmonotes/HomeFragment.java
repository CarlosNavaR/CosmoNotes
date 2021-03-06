package com.example.cosmonotes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cosmonotes.Utils.DataBaseHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
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
    private TextView mTextFecha;
    private TextView mUserName;
    private TextView eventsNumberTV,NotesNumberTV,ToDoNumberTV;

    private SimpleDateFormat dateFormat;
    private String date;

    private Context context;
    DataBaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = inflater.getContext();

        geocoder = new Geocoder(view.getContext(), Locale.getDefault());

        mWeatherTextView = view.findViewById(R.id.textviewWeather);
        mIconWeatherImgView = view.findViewById(R.id.IconImageWeather);
        mCityTextView = view.findViewById(R.id.textViewCity);
        mProfileUserImgView = view.findViewById(R.id.ProfileUserImgView);
        mTextFecha = view.findViewById(R.id.text_fecha);
        mUserName = view.findViewById(R.id.textUserName);
        eventsNumberTV = view.findViewById(R.id.eventsNumberTV);
        NotesNumberTV = view.findViewById(R.id.NotesNumberTV);
        ToDoNumberTV = view.findViewById(R.id.TodoNumberTV);

        db = new DataBaseHelper(getActivity());
        eventsNumberTV.setText(String.valueOf(db.getAllEvents().size()) + " Evento(s)");
        NotesNumberTV.setText(String.valueOf(db.leerNotas().size()) + " Nota(s)");
        ToDoNumberTV.setText(String.valueOf(db.getAllItemsList().size()) + " Pendiente(s)");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        Locale espanol = new Locale("es","ES");
        dateFormat = new SimpleDateFormat("EEEE, d MMM ", espanol);
        Date fecha = new Date();
        date = dateFormat.format(fecha);
        mTextFecha.setText(date);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(context);
        if(signInAccount != null){
            mProfileUserImgView.setImageURI(Uri.parse(String.valueOf(signInAccount.getPhotoUrl())));
            Picasso.get().load(signInAccount.getPhotoUrl()).placeholder(R.drawable.ic_launcher_background).into(mProfileUserImgView);
            mUserName.setText(upperCaseFirst(signInAccount.getDisplayName()));
        }

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getLastLocation();
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getLastLocation();
        }
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

    private void getLastLocation(){
        @SuppressLint("MissingPermission")
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
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
                Log.d(TAG, "On failure " + e.getLocalizedMessage());
            }
        });
    }

    private void getWeather(){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest string = new StringRequest(Request.Method.GET, ApiUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                //Toast.makeText(getView().getContext(), "Response" + response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonData = new JSONObject(response);
                    JSONArray jsonWeatherArray = jsonData.getJSONArray("weather"); // ESte se usa para accesar al elemento weather y traer icono
                    JSONObject jsonWeather = jsonWeatherArray.getJSONObject(0);
                    String icon = jsonWeather.getString("icon");

                    JSONObject jsonObjectMain = jsonData.getJSONObject("main"); // ESte sirve para obtener la temperatura
                    double temp = jsonObjectMain.getDouble("temp");

                    mWeatherTextView.setText(Integer.toString((int) Math.round(temp)));


                    Log.e(TAG, "Icono " + icon);

                    String IconDrawable = "w" + icon;
                    int id = context.getResources().getIdentifier(IconDrawable, "drawable", context.getPackageName());
                    Drawable drawable = context.getResources().getDrawable(id);
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