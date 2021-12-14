package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.DhauEmbunAzzahraJmartPK.model.Account;
import com.DhauEmbunAzzahraJmartPK.model.Product;
import com.DhauEmbunAzzahraJmartPK.request.PaymentRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is activity for giving information of
 * the product selected and can make a payment request
 * to buy the product.
 *
 * @author Dhau' Embun Azzahra
 * */
public class CheckoutActivity extends AppCompatActivity {
    TextView category, name, discPrice, count, quantity, originalPrice,
    shipCost, discount, total;
    EditText address;
    Button increment, decrement, checkout;
    private int prodCount = 1;
    Product product = ProductFragment.getProductDetail();
    private double discountedPrice = product.price*(100.0-product.discount)/100.0;
    Account account = LoginActivity.getLoggedAccount();

    /**
     * product counter feature.
     * this will increment and decrement product counter
     * and also dunamically change the price after that.
     */
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
        checkout = findViewById(R.id.checkoutButton);
        address = findViewById(R.id.delivAddress);

        increment.setOnClickListener(clickListener);
        decrement.setOnClickListener(clickListener);

        category.setText(product.category.toString());
        name.setText(product.name);
        discPrice.setText(String.valueOf(discountedPrice));
        originalPrice.setText(String.valueOf(product.price*prodCount));
        discount.setText(String.valueOf(product.discount)+"%");
        total.setText(String.valueOf(10000.0+discountedPrice*prodCount));

        /**
         * This will make a payment request.
         */
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deliveryAddress = address.getText().toString();
                Response.Listener<String> respList = response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        if(object!=null){
                            Toast.makeText(CheckoutActivity.this, "success.",Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(CheckoutActivity.this, "failed.",Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(CheckoutActivity.this, "JSON exception.",Toast.LENGTH_SHORT).show();
                    }
                };

                Response.ErrorListener errorListener = error -> {
                    Toast.makeText(CheckoutActivity.this, "something error.",Toast.LENGTH_SHORT).show();
                };

                PaymentRequest request = new PaymentRequest(account.id, product.id,prodCount,deliveryAddress, product.shipmentPlans, respList,errorListener);
                RequestQueue queue = Volley.newRequestQueue(CheckoutActivity.this);
                queue.add(request);
            }
        });

    }
}