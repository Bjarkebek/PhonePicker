package com.example.phonepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AndroidActivity extends AppCompatActivity {

    List<Android> androidsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android);

        Button btn_back = findViewById(R.id.btn_back_android);
        FloatingActionButton btn_create = findViewById(R.id.btn_create);
        androidsList = new ArrayList<>();

        btn_back.setOnClickListener(v -> {
            Intent intent = new Intent(AndroidActivity.this, MainActivity.class);
            startActivity(intent);
        });
        getAllPhones();


        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AndroidActivity.this, CreatePhoneActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getAllPhones() {
        String url = "http://10.131.210.133:8080/api/android";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            androidsList = new Gson().fromJson(response, new TypeToken<List<Android>>() {
            }.getType());
            showItems();
        },error -> Log.d("Volley", error.toString()));

        requestQueue.add(request);
    }


    void showItems() {
        ArrayAdapter<Android> adapter = new AndroidAdapter(this, androidsList);
        ((ListView)findViewById(R.id.lv_android)).setAdapter(adapter);
    }
}