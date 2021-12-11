package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.DhauEmbunAzzahraJmartPK.model.Product;

public class CheckoutActivity extends AppCompatActivity {
    TextView category, name, discPrice, count, quantity, originalPrice,
    shipCost, discount, total;
    Button increment, decrement;
    private int prodCount = 1;
    Product product = ProductFragment.getProductDetail();
    private double discountedPrice = product.price*(100.0-product.discount)/100.0;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.incButton:
                    prodCount++;
                    count.setText(prodCount+"");
                    quantity.setText(prodCount+" Items");
                    discPrice.setText(String.valueOf(discountedPrice*prodCount));
                    originalPrice.setText(String.valueOf(product.price*prodCount));
                    total.setText(String.valueOf(10000.0+discountedPrice*prodCount));
                    break;
                case R.id.dcrButton:
                    if(prodCount>0){
                        prodCount--;
                        count.setText(prodCount+"");
                        quantity.setText(prodCount+" Items");
                        discPrice.setText(String.valueOf(discountedPrice*prodCount));
                        originalPrice.setText(String.valueOf(product.price*prodCount));
                        total.setText(String.valueOf(10000.0+discountedPrice*prodCount));
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        category = findViewById(R.id.coCategory);
        name = findViewById(R.id.coName);
        discPrice = findViewById(R.id.coDiscPrice);
        count = findViewById(R.id.coCount);
        increment = findViewById(R.id.incButton);
        decrement = findViewById(R.id.dcrButton);
        quantity = findViewById(R.id.coQuanitity);
        originalPrice = findViewById(R.id.coRealPrice);
        shipCost = findViewById(R.id.coShipCost);
        discount = findViewById(R.id.coDiscount);
        total = findViewById(R.id.coTotal);

        increment.setOnClickListener(clickListener);
        decrement.setOnClickListener(clickListener);

        category.setText(product.category.toString());
        name.setText(product.name);
        discPrice.setText(String.valueOf(discountedPrice));
        originalPrice.setText(String.valueOf(product.price*prodCount));
        discount.setText(String.valueOf(product.discount)+"%");
        total.setText(String.valueOf(10000.0+discountedPrice*prodCount));


    }
}