package com.example.phonepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class EditPhoneActivity extends AppCompatActivity implements Serializable {
    Android phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone);

        phone = (Android) getIntent().getSerializableExtra("Phone"); //henter det tilføjet objekt fra intentet og sætter det ind i et Android objekt

        EditText et_model = findViewById(R.id.edit_model);
        EditText et_os = findViewById(R.id.edit_os);
        EditText et_released = findViewById(R.id.edit_year);

        // indsætter dataen fra android objektet i de forskellige EditTexts
        et_model.setText(phone.model);
        et_os.setText(phone.os);
        et_released.setText(Integer.toString(phone.released));


        // til rediger knappen
        FloatingActionButton btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if statement skal kun køres hvis der er ændret noget
                if (true) {
                    try {
                        Android phone = new Android();

                        phone.model = et_model.getText().toString();
                        phone.os = et_os.getText().toString();
                        phone.released = Integer.parseInt(et_released.getText().toString());

                        putData(phone);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                Intent intent = new Intent(EditPhoneActivity.this, AndroidActivity.class);
                startActivity(intent);
            }
        });


        // til slet knappen
        FloatingActionButton btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    delData(phone);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Intent intent = new Intent(EditPhoneActivity.this, AndroidActivity.class);
                startActivity(intent);
            }
        });
    }

    public void putData(Android data) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "http://192.168.1.8:8080/api/android";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", phone.id);
        jsonObject.put("model", data.model);
        jsonObject.put("os", data.os);
        jsonObject.put("released", data.released);

        final String requestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL,
                response -> {
                    Log.i("VOLLEY", response);
                },
                error -> Log.e("VOLLEY", error.toString())) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };

        requestQueue.add(stringRequest);
    }


    // sletter objektet med tilsvarende id
    public void delData(Android data) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "http://192.168.1.8:8080/api/android/" + phone.id;
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, URL,
                response -> {
                    Log.i("VOLLEY", response);
                },
                error -> Log.e("VOLLEY", error.toString())) {
        };
        requestQueue.add(stringRequest);
    }


}