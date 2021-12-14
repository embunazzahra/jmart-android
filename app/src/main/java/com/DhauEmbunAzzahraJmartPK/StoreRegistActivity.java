package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.DhauEmbunAzzahraJmartPK.model.Account;
import com.DhauEmbunAzzahraJmartPK.request.StoreRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is class for store registration
 * @author Dhau' Embun Azzahra.
 */
public class StoreRegistActivity extends AppCompatActivity {

    EditText storeName;
    EditText storeAddress;
    EditText phoneNumber;
    Button registButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_store);

        Account account = LoginActivity.getLoggedAccount();
        
        storeName = findViewById(R.id.storeName);
        storeAddress = findViewById(R.id.storeAddress);
        phoneNumber = findViewById(R.id.phoneNumber);
        registButton = findViewById(R.id.button9);

        /**
         * This is the listener for the store registration.
         * will inform whether the registration is success or not.
         */
        registButton.setOnClickListener(
                o->{
                    int id = account.id;
                    String name = storeName.getText().toString();
                    String phone = phoneNumber.getText().toString();
                    String address = storeAddress.getText().toString();

                    Response.ErrorListener errorListener = volleyError->{
                        Toast.makeText(getApplicationContext(), "Something error.", Toast.LENGTH_SHORT).show();
                    };

                    Response.Listener<String> respList = response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject!=null){
                                Toast.makeText(StoreRegistActivity.this, "success\nplease, re-login",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(StoreRegistActivity.this, AboutMeActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(StoreRegistActivity.this, "your registration is failed.",Toast.LENGTH_SHORT).show();

                        }
                    };

                    StoreRequest request = new StoreRequest(id,name,address,phone,respList,errorListener);
                    RequestQueue queue = Volley.newRequestQueue(StoreRegistActivity.this);
                    queue.add(request);
                }
        );

    }
}