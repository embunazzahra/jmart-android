package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.DhauEmbunAzzahraJmartPK.model.Product;
import com.DhauEmbunAzzahraJmartPK.request.RequestFactory;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView name,price,discount,condition,category,weight,shipment,store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);


        Product product = ProductFragment.getProductDetail();
        name = findViewById(R.id.productNameDetail);
        price = findViewById(R.id.productPriceDetail);
        discount = findViewById(R.id.productDiscountDetail);
        condition = findViewById(R.id.productConditionDetail);
        category = findViewById(R.id.productCategoryDetail);
        weight = findViewById(R.id.productWeightDetail);
        shipment = findViewById(R.id.productShipmentDetail);
        store = findViewById(R.id.productStoreDetail);

        name.setText(product.name);
        price.setText(String.valueOf(product.price));
        discount.setText(String.valueOf(product.discount));
        if(product.conditionUsed==false){
            condition.setText("New");
        }else{
            condition.setText("False");
        }
        category.setText(product.category.toString());
        weight.setText(String.valueOf(product.weight));
        switch (product.shipmentPlans){
            case (byte)(1<<0): shipment.setText("INSTANT"); break;
            case (byte)(1<<1): shipment.setText("SAME DAY"); break;
            case (byte)(1<<2): shipment.setText("NEXT DAY"); break;
            case (byte) (1<<3): shipment.setText("REGULER"); break;
            case (byte) (1<<4): shipment.setText("KARGO"); break;
            default: shipment.setText("shipment unknown");break;
        }

        Response.Listener<String> respList = response -> {
            try{
                JSONObject object = new JSONObject(response);
                if(object!=null){
                    JSONObject storeObject = object.getJSONObject("store");
                    store.setText(storeObject.getString("name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };

        Response.ErrorListener respErrorList = volleyError->{
            if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                Toast.makeText(getApplicationContext(), "No Connection/Communication Error!", Toast.LENGTH_SHORT).show();
            } else if (volleyError instanceof AuthFailureError) {
                Toast.makeText(getApplicationContext(), "Authentication/ Auth Error!", Toast.LENGTH_SHORT).show();
            } else if (volleyError instanceof ServerError) {
                Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_SHORT).show();
            } else if (volleyError instanceof NetworkError) {
                Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
            } else if (volleyError instanceof ParseError) {
                Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "System error.",Toast.LENGTH_SHORT).show();
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ProductDetailActivity.this);
        queue.add(RequestFactory.getById("account", product.accountId, respList, respErrorList));

    }
}