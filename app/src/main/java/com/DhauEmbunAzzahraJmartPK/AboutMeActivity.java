package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.DhauEmbunAzzahraJmartPK.model.Account;

public class AboutMeActivity extends AppCompatActivity {
    TextView name;
    TextView email;
    TextView balance;
    Button btnTopUp;
    Button btnStoreReg;
    TextView tvCheckStore;
    TextView tvStore, tvSname, tvSaddress, tvSnumber;
    TextView storeName, storeAddress, storeNumber;

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