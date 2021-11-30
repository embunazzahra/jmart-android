package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.DhauEmbunAzzahraJmartPK.model.Account;
import com.DhauEmbunAzzahraJmartPK.request.LoginRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Account loggedAccount = null;

    public static Account getLoggedAccount(){
        return loggedAccount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText etPassword = findViewById(R.id.editTextTextPassword2);
        EditText etEmail = findViewById(R.id.editTextTextEmailAddress2);
        Button loginBtn = findViewById(R.id.button2);
        Button regBtn = findViewById(R.id.button3);

        regBtn.setOnClickListener(
            e->{
                startActivity(new Intent(this,RegisterActivity.class));
            }
        );

        loginBtn.setOnClickListener( new View.OnClickListener(){
                                         @Override
                                         public void onClick(View view) {
                                             String email = etEmail.getText().toString();
                                             String pass = etPassword.getText().toString();

                                             LoginRequest loginRequest = new LoginRequest(email,
                                                     pass,
                                                     new Response.Listener<String>() {
                                                         @Override
                                                         public void onResponse(String response) {
                                                             try{
                                                                 JSONObject object = new JSONObject(response);
                                                                 if(object!=null) {
                                                                     Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
                                                                     loggedAccount = gson.fromJson(object.toString(),Account.class);
                                                                     Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                                     startActivity(intent);
                                                                 }
                                                             }catch (JSONException e){
                                                                 Toast.makeText(LoginActivity.this, "login is failed.",Toast.LENGTH_SHORT).show();
                                                             }

                                                         }
                                                     },
                                                     new Response.ErrorListener(){
                                                         @Override
                                                         public void onErrorResponse(VolleyError error) {
                                                             Toast.makeText(LoginActivity.this, "System error.",Toast.LENGTH_SHORT).show();
                                                         }
                                                     });
                                             RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                                             queue.add(loginRequest);

                                         }
                                     }
        );


//        loginBtn.setOnClickListener(o->{
//            String email = etEmail.getText().toString();
//            String pass = etPassword.getText().toString();
//
//            Response.Listener<String> respList = response ->{
//                LoginRequest loginRequest = new LoginRequest(email,pass,this,this);
//                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//                queue.add(loginRequest);
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//            };
//
//        });

    }


}