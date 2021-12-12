package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.DhauEmbunAzzahraJmartPK.model.Account;
import com.DhauEmbunAzzahraJmartPK.model.Payment;
import com.DhauEmbunAzzahraJmartPK.model.Product;
import com.DhauEmbunAzzahraJmartPK.request.PaymentRequest;
import com.DhauEmbunAzzahraJmartPK.request.RequestFactory;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class StoreHistoryActivity extends AppCompatActivity {
    EditText cancelId, submitId, submitReceipt, acceptId;
    Button cancelBtn, submitBtn, acceptBtn;
    TextView mainContent;

    private static final Gson gson = new Gson();
    private static ArrayList<Payment> paymentList = new ArrayList<>();
    private static String status = "";
    private static String productId = "";
    Account account = LoginActivity.getLoggedAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_history);
        cancelId = findViewById(R.id.etCancelByStore);
        submitId = findViewById(R.id.etSubmitIdStore);
        submitReceipt = findViewById(R.id.etSubmitReceiptStore);
        acceptId = findViewById(R.id.etAcceptByStore);
        cancelBtn = findViewById(R.id.btnCancelByStore);
        submitBtn = findViewById(R.id.btnSubmitByStore);
        acceptBtn = findViewById(R.id.btnAcceptByStore);
        mainContent = findViewById(R.id.storeHist);



        Response.ErrorListener errorListener = error -> {
            Toast.makeText(StoreHistoryActivity.this, "System error.",Toast.LENGTH_SHORT).show();
        };


        Response.Listener<String> respListPayment = response -> {
            try {
                JSONArray array = new JSONArray(response);
                paymentList = gson.fromJson(array.toString(), new TypeToken<ArrayList<Payment>>() {
                }.getType());
                mainContent.setText(response);

            } catch (JSONException e) {
                Toast.makeText(StoreHistoryActivity.this, "find payments is failed",Toast.LENGTH_SHORT).show();
            }
        };

        //make the requests
        RequestQueue queue = Volley.newRequestQueue(StoreHistoryActivity.this);
        queue.add(PaymentRequest.getPaymentByProduct(AboutMeActivity.productIdList,respListPayment,errorListener));
    }
}