package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.DhauEmbunAzzahraJmartPK.model.Account;
import com.DhauEmbunAzzahraJmartPK.model.Product;
import com.DhauEmbunAzzahraJmartPK.request.RequestFactory;
import com.DhauEmbunAzzahraJmartPK.request.TopUpRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class AboutMeActivity extends AppCompatActivity {
    TextView name;
    TextView email;
    TextView balance;
    Button btnTopUp;
    Button btnStoreReg;
    Button accHist, storeHist;
    TextView tvCheckStore;
    TextView tvStore, tvSname, tvSaddress, tvSnumber;
    TextView storeName, storeAddress, storeNumber;
    EditText topUpBalance;
    private static final Gson gson = new Gson();
    public static ArrayList<Product> productList = new ArrayList<>();
    public static ArrayList<Integer> productIdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        Account account = LoginActivity.getLoggedAccount();

        name = findViewById(R.id.txName);
        email = findViewById(R.id.txEmail);
        balance = findViewById(R.id.txBalance);
        btnTopUp = findViewById(R.id.button5);
        btnStoreReg = findViewById(R.id.registBtn);
        tvCheckStore = findViewById(R.id.storeCheck);
        tvStore = findViewById(R.id.tvRegStore);
        tvSname = findViewById(R.id.textView11);
        tvSaddress = findViewById(R.id.textView12);
        tvSnumber = findViewById(R.id.textView13);
        storeName = findViewById(R.id.namaToko);
        storeAddress = findViewById(R.id.txName3);
        storeNumber = findViewById(R.id.txName4);
        topUpBalance = findViewById(R.id.etTopUp);
        accHist = findViewById(R.id.accHistory);
        storeHist = findViewById(R.id.storeHistory);

        accHist.setOnClickListener(e->{
            startActivity(new Intent(AboutMeActivity.this, AccountHistoryActivity.class));
        });

        storeHist.setOnClickListener(e->{
            //fetching data of all product in this store
            Response.Listener<String> respListProduct = response ->{
                try {
                    JSONArray array = new JSONArray(response);
                    productList = gson.fromJson(array.toString(), new TypeToken<ArrayList<Product>>() {
                    }.getType());
                    //fetching data of all payment of the product
                    if(!productList.isEmpty()){
                        for (int i=0;i<productList.size();i++){
                            productIdList.add(productList.get(i).id);
                        }
                        startActivity(new Intent(AboutMeActivity.this,StoreHistoryActivity.class));
                    }else{
                        Toast.makeText(AboutMeActivity.this, "You have no product.",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException v) {
                    Toast.makeText(AboutMeActivity.this, "find product is failed",Toast.LENGTH_SHORT).show();
                }
            };
            Response.ErrorListener errorListener = error -> {
                Toast.makeText(AboutMeActivity.this, "System error.",Toast.LENGTH_SHORT).show();
            };
            RequestQueue queue = Volley.newRequestQueue(AboutMeActivity.this);
            queue.add(RequestFactory.getProductByStore(
                    account.id,
                    0,
                    50,
                    respListProduct,
                    errorListener
            ));
        });

        btnTopUp.setOnClickListener(e->{
            double balance_ = 0.0;
            try {
                balance_ = Double.valueOf(topUpBalance.getText().toString());
            } catch (NumberFormatException numberFormatException) {
                Toast.makeText(AboutMeActivity.this, "parsing balance is failed.",Toast.LENGTH_SHORT).show();
            }
            Response.Listener<String> respList = response -> {
                boolean resp = Boolean.parseBoolean(response);
              if (resp){
                  Toast.makeText(AboutMeActivity.this, "top up success.",Toast.LENGTH_SHORT).show();
                  balance.setText(String.valueOf(account.balance));
              }else {
                  Toast.makeText(AboutMeActivity.this, "top up failed.",Toast.LENGTH_SHORT).show();
              }
            };

            Response.ErrorListener errorListener = error -> {
                Toast.makeText(AboutMeActivity.this, "Something Error.",Toast.LENGTH_SHORT).show();
            };

            TopUpRequest request = new TopUpRequest(account.id, balance_,respList,errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(AboutMeActivity.this);
            requestQueue.add(request);
        });

        name.setText(account.name);
        email.setText(account.email);
        balance.setText(account.toString());

        btnStoreReg.setOnClickListener(e->{startActivity(new Intent(this, StoreRegistActivity.class));});

        if(account.store!=null){
            tvCheckStore.setVisibility(View.GONE);
            btnStoreReg.setVisibility(View.GONE);
            storeName.setText(account.store.name);
            storeAddress.setText(account.store.address);
            storeNumber.setText(account.store.phoneNumber);
        }else {
            tvStore.setVisibility(View.GONE);
            tvSname.setVisibility(View.GONE);
            tvSaddress.setVisibility(View.GONE);
            tvSnumber.setVisibility(View.GONE);
            storeName.setVisibility(View.GONE);
            storeAddress.setVisibility(View.GONE);
            storeNumber.setVisibility(View.GONE);
            storeHist.setVisibility(View.GONE);
        }

    }
}