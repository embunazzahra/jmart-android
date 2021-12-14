package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.DhauEmbunAzzahraJmartPK.request.RegisterRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is class for user register activity.
 *
 * @author Dhau' Embun Azzahra
 */
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText etName = findViewById(R.id.editTextTextPersonName);
        EditText etPassword = findViewById(R.id.editTextTextPassword);
        EditText etEmail = findViewById(R.id.editTextTextEmailAddress);
        Button registerBtn = findViewById(R.id.button);

        /**
         * This will process the register form
         * and move to login activity.
         */
        registerBtn.setOnClickListener(o->{
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String pass = etPassword.getText().toString();

            Response.ErrorListener respErrorList = volleyError->{

                if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "No Connection/Communication Error!", Toast.LENGTH_SHORT).show();
                } else if (volleyError instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Authentication/ Auth Error!", Toast.LENGTH_SHORT).show();
                } else if (volleyError instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_SHORT).show();
                } else if (volleyError instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                } else if (volleyError instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "System error.",Toast.LENGTH_SHORT).show();
                }
            };

            Response.Listener<String> respList = response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject!=null){
                        Toast.makeText(RegisterActivity.this, "success",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    Toast.makeText(RegisterActivity.this, "your registration is failed.",Toast.LENGTH_SHORT).show();

                }
            };
            RegisterRequest registerRequest = new RegisterRequest(name, email, pass, respList, respErrorList);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registerRequest);
        });

    }
}