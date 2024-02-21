package com.example.phonepicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class IphoneActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    iPhone iphone;
    List<iPhone> iphonesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iphone);

        Button btn_back = findViewById(R.id.btn_back_iPhone);
        iphonesList = new ArrayList<>();

        btn_back.setOnClickListener(v -> {
            Intent intent = new Intent(IphoneActivity.this, MainActivity.class);
            startActivity(intent);
        });

        getData();
        Toast.makeText(this, "Size: " + iphonesList.size(), Toast.LENGTH_LONG).show();
        showItems();
    }

    void getData() {
        String url = "http://localhost:8080/api/iPhone";
        requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            iphonesList = new Gson().fromJson(response, new TypeToken<List<iPhone>>() {
            }.getType());
        }, error -> Log.d("Volley", error.toString()));
        requestQueue.add(request);
    }

    void MockData() {
        iPhone p1 = new iPhone();
        p1.model = "iPhone";
        p1.ios = "first";
        p1.releaseyear = 2099;
        iphonesList.add(p1);

        iPhone p2 = new iPhone();
        p2.model = "better iPhone";
        p2.ios = "better";
        p2.releaseyear = 2199;
        iphonesList.add(p2);
    }

    void showItems() {
        ArrayAdapter<iPhone> adapter = new IphoneAdapter(this, iphonesList);
        ((ListView) findViewById(R.id.lv_iphone)).setAdapter(adapter);
    }

}