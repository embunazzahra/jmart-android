package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.DhauEmbunAzzahraJmartPK.model.Account;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView hello_user = findViewById(R.id.textView);

        Account account = LoginActivity.getLoggedAccount();

        hello_user.setText("Hello, "+account.name);
    }
}