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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class IphoneActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    iPhone iphone;
    private List<iPhone> iphonesList;


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

        getAllPhones();

    }

    private void getAllPhones() {
        String url = "http://192.168.1.8:8080/api/iphone";
        requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            iphonesList = new Gson().fromJson(response, new TypeToken<List<iPhone>>() {
            }.getType());
            showItems();
        },error -> Log.d("Volley", error.toString()));

        requestQueue.add(request);
    }


    void showItems() {
        ArrayAdapter<iPhone> adapter = new IphoneAdapter(this, iphonesList);
        ((ListView) findViewById(R.id.lv_iphone)).setAdapter(adapter);
    }

}