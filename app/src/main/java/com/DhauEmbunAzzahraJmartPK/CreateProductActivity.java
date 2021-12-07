package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class CreateProductActivity extends AppCompatActivity {
    EditText productName, productWeight, productPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        productName = findViewById(R.id.prodName);
        productWeight = findViewById(R.id.prodWeight);
        productPrice = findViewById(R.id.prodPrice);


    }
}