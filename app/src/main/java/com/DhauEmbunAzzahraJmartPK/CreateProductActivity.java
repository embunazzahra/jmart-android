package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.DhauEmbunAzzahraJmartPK.model.Account;
import com.DhauEmbunAzzahraJmartPK.model.ProductCategory;
import com.DhauEmbunAzzahraJmartPK.request.CreateProductRequest;
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

public class CreateProductActivity extends AppCompatActivity  {
    EditText productName, productWeight, productPrice, productDiscount;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button createButton;
    Spinner productCategory, productShipment;
    ProductCategory category;
    byte shipmentPlans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        Account account = LoginActivity.getLoggedAccount();

        productName = findViewById(R.id.prodName);
        productWeight = findViewById(R.id.prodWeight);
        productPrice = findViewById(R.id.prodPrice);
        productDiscount = findViewById(R.id.prodDiscount);
        radioGroup = findViewById(R.id.radioGroup);
        createButton = findViewById(R.id.prodCreate);
        productCategory = findViewById(R.id.spinner1);
        productShipment = findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter
                .createFromResource(this, R.array.product_category, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> shipmentAdapter = ArrayAdapter
                .createFromResource(this, R.array.shipment_plan, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productCategory.setAdapter(categoryAdapter);
        productShipment.setAdapter(shipmentAdapter);
        productCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = ProductCategory.valueOf(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        productShipment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sp = adapterView.getItemAtPosition(i).toString().toUpperCase();
                switch (sp){
                    case "INSTANT": shipmentPlans = (byte)(1<<0); break;
                    case "SAME DAY": shipmentPlans = (byte)(1<<1); break;
                    case "NEXT DAY": shipmentPlans = (byte)(1<<2); break;
                    case "REGULER": shipmentPlans = (byte) (1<<3); break;
                    case "KARGO": shipmentPlans = (byte) (1<<4); break;
                    default: shipmentPlans = (byte) (1<<3); break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedCondition = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedCondition);
                String name = productName.getText().toString();
                String weight_ = productWeight.getText().toString();
                String price_ = productPrice.getText().toString();
                String discount_ = productDiscount.getText().toString();
                int weight = 0;
                double price = 0.0;
                double discount = 0.0;
                boolean condition;
                try{
                    weight = Integer.parseInt(weight_);
                    price = Double.valueOf(price_);
                    discount = Double.valueOf(discount_);
                    if(weight<0)
                        Toast.makeText(CreateProductActivity.this, "weight must be positive double numbers.",Toast.LENGTH_SHORT).show();
                    if(price<0.0)
                        Toast.makeText(CreateProductActivity.this, "price must be positive double numbers.",Toast.LENGTH_SHORT).show();
                    if(discount<0.0)
                        Toast.makeText(CreateProductActivity.this, "discount must be positive double numbers.",Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(CreateProductActivity.this, "weight, price, and discount must be positive numbers.",Toast.LENGTH_SHORT).show();
                }
                if(radioButton.getText().toString().equalsIgnoreCase("Used")){
                    condition = true;
                }else {
                    condition = false;
                }

                Response.Listener<String> respList = response -> {
                  try {
                      JSONObject object = new JSONObject(response);
                      if(object!=null){
                          Toast.makeText(CreateProductActivity.this, "success creating product",Toast.LENGTH_SHORT).show();
                          Intent intent = new Intent(CreateProductActivity.this, MainActivity.class);
                          startActivity(intent);
                      }
                  } catch (JSONException e) {
                      Toast.makeText(CreateProductActivity.this, "fail to add the product",Toast.LENGTH_SHORT).show();
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
                CreateProductRequest request = new CreateProductRequest(account.id, name,
                        weight,condition,price,discount,category, shipmentPlans, respList,respErrorList);
                RequestQueue queue = Volley.newRequestQueue(CreateProductActivity.this);
                queue.add(request);
            }
        });


    }

}