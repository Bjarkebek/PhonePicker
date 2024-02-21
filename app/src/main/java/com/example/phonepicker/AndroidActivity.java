package com.example.phonepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AndroidActivity extends AppCompatActivity {

    List<Android> androidsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android);

        Button btn_back = findViewById(R.id.btn_back_android);
        androidsList = new ArrayList<>();

        btn_back.setOnClickListener(v -> {
            Intent intent = new Intent(AndroidActivity.this, MainActivity.class);
            startActivity(intent);
        });


        showItems();
    }


    void showItems() {
        ArrayAdapter<Android> adapter = new AndroidAdapter(this, androidsList);
        ((ListView)findViewById(R.id.lv_android)).setAdapter(adapter);
    }
}