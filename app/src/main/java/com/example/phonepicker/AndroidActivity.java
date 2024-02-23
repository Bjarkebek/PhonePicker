package com.example.phonepicker;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AndroidActivity extends AppCompatActivity implements Serializable {

    List<Android> androidsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android);

        androidsList = new ArrayList<>();
        getAllPhones();


        // til tilbageknappen
        Button btn_back = findViewById(R.id.btn_back_android);
        btn_back.setOnClickListener(v -> {
            Intent intent = new Intent(AndroidActivity.this, MainActivity.class);
            startActivity(intent);
        });



        // til opret knappen
        FloatingActionButton btn_create = findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AndroidActivity.this, CreatePhoneActivity.class);
                startActivity(intent);
            }
        });



        // til når et objekt i listen bliver klikket på
        ListView lv_android = (ListView) findViewById(R.id.lv_android);
        lv_android.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                // tager objektet fra item klikket i listview, og sætter det ind i et android objekt
                Android obj = (Android) lv_android.getItemAtPosition(position);

                Intent intent = new Intent(AndroidActivity.this, EditPhoneActivity.class); // skaber nyt intent
                intent.putExtra("Phone", obj); //tilføjer android objektet til intentet, som føres med over til EditPhoneActivity
                startActivity(intent);
            }
        });
    }


    // metode til at hente alle objekter i databasen
    private void getAllPhones() {
        String url = "http://192.168.1.8:8080/api/android";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            androidsList = new Gson().fromJson(response, new TypeToken<List<Android>>() {
            }.getType());
            showItems();
        }, error -> Log.d("Volley", error.toString()));

        requestQueue.add(request);
    }


    // metode til at vise alle objekter i listviewet
    void showItems() {
        ArrayAdapter<Android> adapter = new AndroidAdapter(this, androidsList);
        ((ListView) findViewById(R.id.lv_android)).setAdapter(adapter);
    }
}