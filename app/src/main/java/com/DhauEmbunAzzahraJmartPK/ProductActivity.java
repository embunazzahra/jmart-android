package com.DhauEmbunAzzahraJmartPK;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
    private ListView lv;
    String namey, price;
    private String JSON_URL = "http://10.0.2.2:8080/product/page?page=0&pageSize=10";

    ArrayList<HashMap<String,String>> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productList = new ArrayList<>();
        lv.findViewById(R.id.listview);

        GetData getData = new GetData();
        getData.execute();
    }

    public class GetData extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try{
                URL url;
                HttpURLConnection urlConnection = null;
                try{
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in);
                    int data = isr.read();

                    while(data!=-1){
                        current+=(char) data;
                        data = isr.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray jsonArray = new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    namey = object.getString("name");
                    price = String.valueOf(object.getDouble("price"));

                    HashMap<String,String> product = new HashMap<>();
                    product.put("name", namey);
                    product.put("price", price);

                    productList.add(product);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ListAdapter adapter = new SimpleAdapter(
                    ProductActivity.this,
                    productList,
                    R.layout.row_layout,
                    new String[]{"name", "price"},
                    new int[]{R.id.tvNameP, R.id.tvPriceP}
            );

            lv.setAdapter(adapter);
        }
    }
}