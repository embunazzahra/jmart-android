package com.DhauEmbunAzzahraJmartPK.request;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PaymentRequest extends StringRequest {
    private static final String URL_PAYMENT_REQUEST = "http://10.0.2.2:8080/payment/create";
    private static final String URL_PAYMENT_BY_USER = "http://10.0.2.2:8080/payment/byAccount?buyerId=%s";
    private static final String URL_CANCEL_PAYMENT = "http://10.0.2.2:8080/payment/%d/cancel";
    private final Map<String ,String> params;

    public PaymentRequest(int buyerId, int productId,int productCount,String shipmentAddress,byte shipmentPlan, Response.Listener<String> listener,
                        Response.ErrorListener errorListener)
    {
        super(Request.Method.POST, URL_PAYMENT_REQUEST, listener, errorListener);
        params = new HashMap<>();
        params.put("buyerId", String.valueOf(buyerId));
        params.put("productId", String.valueOf(productId));
        params.put("productCount", String.valueOf(productCount));
        params.put("shipmentAddress", shipmentAddress);
        params.put("shipmentPlan", String.valueOf(shipmentPlan));
    }

    @Nullable
    @Override
    public Map<String, String> getParams() {
        return params;
    }

    public static StringRequest getPaymentByUser(int userId,Response.Listener<String> listener, Response.ErrorListener errorListener){
        String url = String.format(URL_PAYMENT_BY_USER,userId);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }

    public static StringRequest cancelPayment(int id, Response.Listener<String> listener, Response.ErrorListener errorListener){
        String url = String.format(URL_CANCEL_PAYMENT,id);
        return new StringRequest(Method.POST, url, listener, errorListener);
    }
}
