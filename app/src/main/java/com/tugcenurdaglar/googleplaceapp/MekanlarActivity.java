package com.tugcenurdaglar.googleplaceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MekanlarActivity extends AppCompatActivity {
    private RecyclerView rv;
    private String enlem;
    private String boylam;
    private ArrayList<Mekanlar> mekanlarArrayList;
    private  MekanRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yerler);

        rv = findViewById(R.id.rv);
        enlem = getIntent().getStringExtra("enlem");
        boylam = getIntent().getStringExtra("boylam");

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

     mekanGetir();

    }

    public void mekanGetir(){
        String key ="AIzaSyClmy-SUKjNY8gNuPnpfjcpVnux4O3b1wU";
        String aramaCapi = "500";
        String konum = enlem+","+boylam;

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+konum+"&radius="+aramaCapi+"&key="+key;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mekanlarArrayList = new ArrayList<>();

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray mekanlar = jsonObject.getJSONArray("results");

                    for (int i = 0; i<mekanlar.length();i++){
                        JSONObject m = mekanlar.getJSONObject(i);

                        String mekan_adi = m.getString("name");
                        String adres = m.getString("vicinity");

                        JSONObject geometry = m.getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");

                        String enlem = location.getString("lat");
                        String boylam = location.getString("lng");

                        Mekanlar mekan = new Mekanlar(mekan_adi, Double.parseDouble(enlem), Double.parseDouble(boylam),adres);

                        mekanlarArrayList.add(mekan);
                    }

                    adapter = new MekanRVAdapter(MekanlarActivity.this, mekanlarArrayList);
                    rv.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(MekanlarActivity.this).add(stringRequest);


    }


}