package com.tugcenurdaglar.googleplaceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private EditText editTextEnlem, editTextBoylam;
    private Button buttonGit, buttonKonumAl;
    private String konumSaglayici = "gps";
    private int izinKontrol;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextBoylam = findViewById(R.id.editTextBoylam);
        editTextEnlem = findViewById(R.id.editTextEnlem);
        buttonGit = findViewById(R.id.buttonGit);
        buttonKonumAl = findViewById(R.id.buttonKonumAl);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        buttonGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enlem= editTextEnlem.getText().toString().trim();
                String boylam = editTextBoylam.getText().toString().trim();

                Intent intent = new Intent(MainActivity.this, MekanlarActivity.class);
                intent.putExtra("enlem", enlem);
                intent.putExtra("boylam", boylam);
                startActivity(intent);
            }
        });

        buttonKonumAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                izinKontrol = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

                if (izinKontrol != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);

                }else {
                    Location konum = locationManager.getLastKnownLocation(konumSaglayici);

                    if (konum != null){
                        onLocationChanged(konum);
                    }else{
                        Toast.makeText(getApplicationContext(),"hata", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        Double enlem = location.getLatitude();
        Double boylam = location.getLongitude();

        Intent intent = new Intent(MainActivity.this, MekanlarActivity.class);
        intent.putExtra("enlem", String.valueOf(enlem));
        intent.putExtra("boylam", String.valueOf(boylam));
        startActivity(intent);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100){

            izinKontrol = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                Toast.makeText(getApplicationContext(),"izin verildi", Toast.LENGTH_SHORT).show();

                Location konum = locationManager.getLastKnownLocation(konumSaglayici);

                if (konum != null){
                    onLocationChanged(konum);
                }else{
                    Toast.makeText(getApplicationContext(),"hata", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(getApplicationContext(),"izin verilmedi", Toast.LENGTH_SHORT).show();

            }
        }
    }
}