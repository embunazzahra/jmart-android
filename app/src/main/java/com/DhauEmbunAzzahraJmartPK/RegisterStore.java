package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.DhauEmbunAzzahraJmartPK.model.Account;
import com.DhauEmbunAzzahraJmartPK.request.StoreRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterStore extends AppCompatActivity {
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
                                Toast.makeText(RegisterStore.this, "success",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterStore.this, AboutMeActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(RegisterStore.this, "your registration is failed.",Toast.LENGTH_SHORT).show();

                        }
                    };
                    //StringRequest stringRequest = StoreRequest.getStore(id,name,address,phone,respList,errorListener);
                    StoreRequest request = new StoreRequest(id,name,address,phone,respList,errorListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterStore.this);
                    queue.add(request);
                }
        );

    }
}