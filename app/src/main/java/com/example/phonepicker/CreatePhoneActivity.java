package com.example.phonepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

public class CreatePhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_phone);

        EditText model = findViewById(R.id.et_model);
        EditText os = findViewById(R.id.et_os);
        EditText year = findViewById(R.id.et_releaseyear);

        Button btn_add = findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Android phone = new Android();

                    phone.model = model.getText().toString();
                    phone.os = os.getText().toString();
                    phone.released = Integer.parseInt(year.getText().toString());

                    addData(phone);


                    // routes back to android page
                    Intent intent = new Intent(CreatePhoneActivity.this, AndroidActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    System.out.println("Could not add " + e);
                }


            }
        });

    }

    public void addData(Android data) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "http://10.131.210.133:8080/api/android";

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("model",data.model);
        jsonBody.put("os",data.os);
        jsonBody.put("released",data.released);

        final String requestBody = jsonBody.toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
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
}
