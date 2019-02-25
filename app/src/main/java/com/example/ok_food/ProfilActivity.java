package com.example.ok_food;

import android.Manifest;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProfilActivity extends AppCompatActivity {
    private TextView tempat;
    private TextView nama;
    private FusedLocationProviderClient fusedLocationClient;
    private String Nama_alamat="unknown location";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        tempat = findViewById(R.id.location);
        nama = findViewById(R.id.ini_nama);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Log.d("location", tempat.getText().toString() + " 1");
        SharedPreferences prefs = getSharedPreferences("nama_lokasi", MODE_PRIVATE);
        Nama_alamat = prefs.getString("alamat", Nama_alamat); //unknown location is the default value.
        tempat.setText(Nama_alamat);
    }

    public void setLocation(View view) {
        tempat.setText("please allow location permission for this app on your android, thanks :)");
        Log.d("kepencet","kepencet");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("aneh","ga dapet izin");
            return;
        }else {
            Log.d("ga aneh","dapet izin");
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        tempat.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
                        Log.d("location", tempat.getText().toString());
                        Geocoder geocoder;
                        List<Address> addresses = null;
                        geocoder = new Geocoder( ProfilActivity.this,Locale.getDefault());

                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        //String city = addresses.get(0).getLocality();
                        //String state = addresses.get(0).getAdminArea();
                        //String country = addresses.get(0).getCountryName();
                        //String postalCode = addresses.get(0).getPostalCode();
                        //String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                        tempat.setText(address);
                        SharedPreferences.Editor editor = getSharedPreferences("nama_lokasi", MODE_PRIVATE).edit();
                        editor.putString("alamat", address);
                        editor.apply();

                        Nama_alamat = address;



                    } else {
                        Log.d("location", tempat.getText().toString());
                    }


                }
            });
        }
    }
}
