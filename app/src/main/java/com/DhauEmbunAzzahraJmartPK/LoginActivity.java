package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.DhauEmbunAzzahraJmartPK.model.Account;
import com.DhauEmbunAzzahraJmartPK.request.LoginRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<String> {
    private static final Gson gson = new Gson();
    private static Account loggedAccount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText etPassword = findViewById(R.id.editTextTextPassword2);
        EditText etEmail = findViewById(R.id.editTextTextEmailAddress2);
        Button loginBtn = findViewById(R.id.button2);


        loginBtn.setOnClickListener(o->{
            String email = etEmail.getText().toString();
            String pass = etPassword.getText().toString();

            Response.Listener<String> respList = response ->{
                LoginRequest loginRequest = new LoginRequest(email,pass,this,this);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            };

        });

    }

    public static Account getLoggedAccount(){
        return loggedAccount;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("something went wrong");
    }

    @Override
    public void onResponse(String response) {
        System.out.println("something went wrong");
    }
}