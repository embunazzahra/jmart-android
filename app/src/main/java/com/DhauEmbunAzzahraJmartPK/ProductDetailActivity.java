package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.DhauEmbunAzzahraJmartPK.model.Product;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        name = findViewById(R.id.textView9);
        Product product = ProductFragment.getProductDetail();
        if(product!=null){
            name.setText(product.name);
        }

    }
}