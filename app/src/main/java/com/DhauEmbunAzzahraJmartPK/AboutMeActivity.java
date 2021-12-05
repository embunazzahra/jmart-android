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


        name.setText(account.name);
        email.setText(account.email);
        balance.setText(account.toString());

        btnStoreReg.setOnClickListener(e->{startActivity(new Intent(this,RegisterStore.class));});

        if(account.store!=null){
            tvCheckStore.setVisibility(View.GONE);
            btnStoreReg.setVisibility(View.GONE);
        }

    }
}