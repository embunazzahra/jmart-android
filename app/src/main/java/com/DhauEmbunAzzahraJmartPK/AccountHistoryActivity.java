package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.DhauEmbunAzzahraJmartPK.model.Account;
import com.DhauEmbunAzzahraJmartPK.model.Payment;
import com.DhauEmbunAzzahraJmartPK.model.Product;
import com.DhauEmbunAzzahraJmartPK.request.PaymentRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This is activity for giving information of
 * the user order history.
 *
 * @author Dhau' Embun Azzahra
 * */
public class AccountHistoryActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static ArrayList<Payment> paymentList = new ArrayList<>();
    private static String status = "";
    private static String productId = "";
    TextView lastHistory;
    Account account = LoginActivity.getLoggedAccount();
    Button cancelBtn;
    EditText etId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_history);
        lastHistory = findViewById(R.id.histAcc);
        cancelBtn = findViewById(R.id.cancelCustBtn);
        etId = findViewById(R.id.paymentIdCancel);

        /**
         * Cancel payment of the order by buyer.
         */
        cancelBtn.setOnClickListener(o->{
            String payId = etId.getText().toString();
            int id =-1; boolean idFound = false;
            try {
                id = Integer.parseInt(payId);
            } catch (NumberFormatException e) {
                Toast.makeText(AccountHistoryActivity.this, "fail to parse.",Toast.LENGTH_SHORT).show();
            }
            for(int i=0;i<paymentList.size();i++){
                if(paymentList.get(i).id == id){
                    idFound = true;
                }
            }
            if(!idFound)
                id = -1;

            Response.Listener<String> listener = response -> {
                boolean resp = Boolean.parseBoolean(response);
                if (resp){
                    Toast.makeText(AccountHistoryActivity.this, "success.",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AccountHistoryActivity.this, "failed.",Toast.LENGTH_SHORT).show();
                }
            };

            Response.ErrorListener errorListener = error -> {
                Toast.makeText(AccountHistoryActivity.this, "Something Error.",Toast.LENGTH_SHORT).show();
            };

            RequestQueue requestQueue = Volley.newRequestQueue(AccountHistoryActivity.this);
            requestQueue.add(PaymentRequest.cancelPayment(id, listener, errorListener));
        });

        Response.Listener<String> respList = response -> {
            try{
                JSONArray array = new JSONArray(response);
                paymentList = gson.fromJson(array.toString(), new TypeToken<ArrayList<Payment>>() {
                }.getType());
                if(paymentList.size()>0){
                    for (int i=paymentList.size()-1;i>=0;i--){
                        status += "\n\nPayment Id: "+String.valueOf(paymentList.get(i).id)+"\nProduct Id: "+String.valueOf(paymentList.get(i).productId)+"\nStatus:";
                        for(int j = 0; j < paymentList.get(i).history.size();j++){
                            status+="\n"+paymentList.get(i).history.get(j).status.toString();
                        }
                    }
                }else{
                    status = "You have no order history.";
                }
                lastHistory.setText(status);
            } catch (JSONException e) {
                Toast.makeText(AccountHistoryActivity.this, "something error.",Toast.LENGTH_SHORT).show();
            }
        };

        Response.ErrorListener errorListener = error -> {
            Toast.makeText(AccountHistoryActivity.this, "System error.",Toast.LENGTH_SHORT).show();
        };

        RequestQueue queue = Volley.newRequestQueue(AccountHistoryActivity.this);
        queue.add(PaymentRequest.getPaymentByUser(account.id,respList,errorListener));
    }
}