package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.DhauEmbunAzzahraJmartPK.model.Account;
import com.DhauEmbunAzzahraJmartPK.request.RequestFactory;
import com.DhauEmbunAzzahraJmartPK.request.TopUpRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

public class AboutMeActivity extends AppCompatActivity {
    TextView name;
    TextView email;
    TextView balance;
    Button btnTopUp;
    Button btnStoreReg;
    TextView tvCheckStore;
    TextView tvStore, tvSname, tvSaddress, tvSnumber;
    TextView storeName, storeAddress, storeNumber;
    EditText topUpBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        Account account = LoginActivity.getLoggedAccount();

        name = findViewById(R.id.txName);
        email = findViewById(R.id.txEmail);
        balance = findViewById(R.id.txBalance);
        btnTopUp = findViewById(R.id.button5);
        btnStoreReg = findViewById(R.id.registBtn);
        tvCheckStore = findViewById(R.id.storeCheck);
        tvStore = findViewById(R.id.tvRegStore);
        tvSname = findViewById(R.id.textView11);
        tvSaddress = findViewById(R.id.textView12);
        tvSnumber = findViewById(R.id.textView13);
        storeName = findViewById(R.id.namaToko);
        storeAddress = findViewById(R.id.txName3);
        storeNumber = findViewById(R.id.txName4);
        topUpBalance = findViewById(R.id.etTopUp);

        btnTopUp.setOnClickListener(e->{
            double balance_ = 0.0;
            try {
                balance_ = Double.valueOf(topUpBalance.getText().toString());
            } catch (NumberFormatException numberFormatException) {
                Toast.makeText(AboutMeActivity.this, "parsing balance is failed.",Toast.LENGTH_SHORT).show();
            }
            Response.Listener<String> respList = response -> {
                boolean resp = Boolean.parseBoolean(response);
              if (resp){
                  Toast.makeText(AboutMeActivity.this, "top up success.",Toast.LENGTH_SHORT).show();
                  balance.setText(String.valueOf(account.balance));
              }else {
                  Toast.makeText(AboutMeActivity.this, "top up failed.",Toast.LENGTH_SHORT).show();
              }
            };

            Response.ErrorListener errorListener = error -> {
                Toast.makeText(AboutMeActivity.this, "Something Error.",Toast.LENGTH_SHORT).show();
            };

            TopUpRequest request = new TopUpRequest(account.id, balance_,respList,errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(AboutMeActivity.this);
            requestQueue.add(request);
        });

        name.setText(account.name);
        email.setText(account.email);
        balance.setText(account.toString());

        btnStoreReg.setOnClickListener(e->{startActivity(new Intent(this, StoreRegistActivity.class));});

        if(account.store!=null){
            tvCheckStore.setVisibility(View.GONE);
            btnStoreReg.setVisibility(View.GONE);
            storeName.setText(account.store.name);
            storeAddress.setText(account.store.address);
            storeNumber.setText(account.store.phoneNumber);
        }else {
            tvStore.setVisibility(View.GONE);
            tvSname.setVisibility(View.GONE);
            tvSaddress.setVisibility(View.GONE);
            tvSnumber.setVisibility(View.GONE);
            storeName.setVisibility(View.GONE);
            storeAddress.setVisibility(View.GONE);
            storeNumber.setVisibility(View.GONE);
        }

    }
}