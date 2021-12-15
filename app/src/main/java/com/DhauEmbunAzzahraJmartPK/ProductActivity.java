package com.DhauEmbunAzzahraJmartPK;

import static com.DhauEmbunAzzahraJmartPK.model.ProductCategory.FNB;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.DhauEmbunAzzahraJmartPK.model.Account;
import com.DhauEmbunAzzahraJmartPK.model.Product;
import com.DhauEmbunAzzahraJmartPK.model.ProductCategory;
import com.DhauEmbunAzzahraJmartPK.request.RequestFactory;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is class for showing product after the filter applied.
 *
 * @author Dhau' Embun Azzahra
 */
public class ProductActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    private static ArrayList<Product> productsList = new ArrayList<>();
    ListView listProd;
    EditText etPage;
    Button prevBtn, nextBtn, goBtn;
    final int pageSize = 10;
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        double lowestPrice = intent.getDoubleExtra("lowestPrice",0.0);
        double highestPrice = intent.getDoubleExtra("highestPrice", 0.0);
        ProductCategory category = (ProductCategory) intent.getSerializableExtra("category");
        Boolean condition = intent.getBooleanExtra("condition",false);

        setContentView(R.layout.activity_product);
        listProd = findViewById(R.id.listview);
        etPage = findViewById(R.id.pageFiltered);
        prevBtn = findViewById(R.id.preevButton);
        nextBtn = findViewById(R.id.neextButton);
        goBtn = findViewById(R.id.gooBtn);


        /**
         * If the listview is clicked,
         * it will move to product detail activity
         * in ProductDetailActivity.class
         */
        listProd.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProductFragment.selectedProduct = productsList.get(i);
                Intent intent = new Intent(ProductActivity.this,ProductDetailActivity.class);
                startActivity(intent);
            }
        });


        /**
         * Listener to convert the response to the Product array list
         * using gson and set the listview.
         */
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray object = new JSONArray(response);
                    if (object != null) {
                        productsList = gson.fromJson(object.toString(), new TypeToken<ArrayList<Product>>() {
                        }.getType());

                        ArrayAdapter<Product> listViewAdapter = new ArrayAdapter<Product>(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                productsList
                        );
                        if(!productsList.isEmpty())
                            listProd.setAdapter(listViewAdapter);
                        else
                            if(page>0)
                                page--;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        Response.ErrorListener respErrorList = volleyError->{

            if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                Toast.makeText(getApplicationContext(), "No Connection/Communication Error!", Toast.LENGTH_SHORT).show();
            } else if (volleyError instanceof AuthFailureError) {
                Toast.makeText(getApplicationContext(), "Authentication/ Auth Error!", Toast.LENGTH_SHORT).show();
            } else if (volleyError instanceof ServerError) {
                Toast.makeText(getApplicationContext(), "No data in this page.", Toast.LENGTH_SHORT).show();
            } else if (volleyError instanceof NetworkError) {
                Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
            } else if (volleyError instanceof ParseError) {
                Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "No data in this page.",Toast.LENGTH_SHORT).show();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ProductActivity.this);
        requestQueue.add(RequestFactory.getProductFiltered(page,pageSize,-1, name,lowestPrice,
                highestPrice,category,listener,respErrorList
                ));

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page++;
                RequestQueue requestQueue = Volley.newRequestQueue(ProductActivity.this);
                requestQueue.add(RequestFactory.getProductFiltered(page,pageSize,-1, name,lowestPrice,
                        highestPrice,category,listener,respErrorList
                ));
            }
        });

        /**
         * if the prev button is clicked,
         * the page will be decrement by one.
         */
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page>0){
                    page--;
                    RequestQueue requestQueue = Volley.newRequestQueue(ProductActivity.this);
                    requestQueue.add(RequestFactory.getProductFiltered(page,pageSize,-1, name,lowestPrice,
                            highestPrice,category,listener,respErrorList
                    ));
                }
            }
        });

        Response.Listener<String> secondListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray object = new JSONArray(response);
                    if (object != null) {
                        productsList = gson.fromJson(object.toString(), new TypeToken<ArrayList<Product>>() {
                        }.getType());

                        ArrayAdapter<Product> listViewAdapter = new ArrayAdapter<Product>(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                productsList
                        );
                        if(!productsList.isEmpty())
                            listProd.setAdapter(listViewAdapter);
                        else{
                            Toast.makeText(ProductActivity.this, "No data in this page.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ProductActivity.this,ProductActivity.class);
                            startActivity(intent);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        /**
         * if the go button is clicked,
         * the page will go to the page
         * of user input.
         */
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    page = Integer.parseInt(etPage.getText().toString());
                    if(page>=1){
                        RequestQueue requestQueue = Volley.newRequestQueue(ProductActivity.this);
                        requestQueue.add(RequestFactory.getProductFiltered(page-1,pageSize,-1, name,lowestPrice,
                                highestPrice,category,secondListener,respErrorList
                        ));
                    }else{
                        Toast.makeText(ProductActivity.this, "page start with 1.",Toast.LENGTH_SHORT).show();
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(ProductActivity.this, "failed to parse.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}