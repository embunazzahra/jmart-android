package com.DhauEmbunAzzahraJmartPK;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.DhauEmbunAzzahraJmartPK.model.Account;
import com.DhauEmbunAzzahraJmartPK.model.Product;
import com.DhauEmbunAzzahraJmartPK.request.RegisterRequest;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ProductFragment extends Fragment {
    final int pageSize = 10;
    int page = 0;

    private static final Gson gson = new Gson();
    private static ArrayList<Product> productsList = new ArrayList<>();
    ListView listProd;
    EditText etPage;
    Button prevBtn, nextBtn, goBtn;
    public static Product selectedProduct = null;
    public static Product getProductDetail(){
        return selectedProduct;
    }
    ArrayAdapter<Product> listViewAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
        Account account = LoginActivity.getLoggedAccount();
        if(account.store==null)
            menu.findItem(R.id.addProduct).setVisible(false);
        else menu.findItem(R.id.addProduct).setVisible(true);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search product here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listViewAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(getActivity(),CheckoutActivity.class));
                break;
            case R.id.addProduct:
                startActivity(new Intent(getActivity(), CreateProductActivity.class));
                break;
            case R.id.aboutme:
                startActivity(new Intent(getActivity(), AboutMeActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View productView = inflater.inflate(R.layout.fragment_product, container, false);
        etPage = (EditText) productView.findViewById(R.id.etPage);
        prevBtn = (Button) productView.findViewById(R.id.button4);
        nextBtn = (Button) productView.findViewById(R.id.button5);
        goBtn = (Button) productView.findViewById(R.id.button6);
        listProd = (ListView) productView.findViewById(R.id.lvProduct);

        listProd.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProduct = productsList.get(i);
                Intent intent = new Intent(getActivity(),ProductDetailActivity.class);
                startActivity(intent);
            }
        });


        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray object = new JSONArray(response);
                    if (object != null) {
                        productsList = gson.fromJson(object.toString(), new TypeToken<ArrayList<Product>>() {
                        }.getType());
                        listViewAdapter = new ArrayAdapter<Product>(
                                getActivity(),
                                android.R.layout.simple_list_item_1,
                                productsList
                        );
                        if(!productsList.isEmpty())
                            listProd.setAdapter(listViewAdapter);
                        else
                            if(page>0)
                                page--;
                        Toast.makeText(getActivity(), String.valueOf(page),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.Listener<String> secondListener = response -> {
            try {
                JSONArray object = new JSONArray(response);
                if (object != null) {
                    productsList = gson.fromJson(object.toString(), new TypeToken<ArrayList<Product>>() {
                    }.getType());
                    ArrayAdapter<Product> listViewAdapter = new ArrayAdapter<Product>(
                            getActivity(),
                            android.R.layout.simple_list_item_1,
                            productsList
                    );
                    listProd.setAdapter(listViewAdapter);
                    Toast.makeText(getActivity(), String.valueOf(page),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page++;
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                requestQueue.add(RequestFactory.getPage("product", page, pageSize, listener, null));

            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page>0){
                    page--;
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    requestQueue.add(RequestFactory.getPage("product", page, pageSize, listener, null));
                }
            }
        });

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    page = Integer.parseInt(etPage.getText().toString());
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    requestQueue.add(RequestFactory.getPage("product", page, pageSize, secondListener, null));
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "failed to parse.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(RequestFactory.getPage("product", page, pageSize, listener, null));
        return productView;
    }
}