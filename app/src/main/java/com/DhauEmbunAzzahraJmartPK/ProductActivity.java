package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.DhauEmbunAzzahraJmartPK.model.Account;
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

public class ProductActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    private static ArrayList<Product> productsList = new ArrayList<>();
    ListView listProd;
    private static Product selectedProduct = null;
    public static Product getProductDetail(){
        return selectedProduct;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        listProd = findViewById(R.id.listview);

        listProd.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProduct = productsList.get(i);
                Intent intent = new Intent(ProductActivity.this,ProductDetailActivity.class);
                startActivity(intent);
            }
        });

        final int pageSize = 10;
        int page = 0;
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

                        listProd.setAdapter(listViewAdapter);
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
                Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_SHORT).show();
            } else if (volleyError instanceof NetworkError) {
                Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
            } else if (volleyError instanceof ParseError) {
                Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "System error.",Toast.LENGTH_SHORT).show();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ProductActivity.this);
        requestQueue.add(RequestFactory.getPage("product", page, pageSize, listener, respErrorList));


    }
}