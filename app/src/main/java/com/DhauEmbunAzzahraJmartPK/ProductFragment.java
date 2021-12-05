package com.DhauEmbunAzzahraJmartPK;

import static com.DhauEmbunAzzahraJmartPK.R.id.lvProduct;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ProductFragment extends Fragment {
    ListView lv;
    SearchView searchView;
    ArrayAdapter<String> adapter;
    List<String> items=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(getActivity(),"search clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.addProduct:
                Toast.makeText(getActivity(),"add product clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.aboutme:
                startActivity(new Intent(getActivity(),AboutMeActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Response.Listener<String> respList = response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                items = new ArrayList<>();
                if(jsonArray!=null){
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        items.add(jsonObject.getString("name"));
                    }
                }
                else {
                    items.add("no data");
                }
                JSONObject jsonObject = new JSONObject(response);
                items = new ArrayList<>();
                if(jsonObject!=null){
                    for(int i=0;i<jsonObject.length();i++){
                        items.add(jsonObject.getString("name"));
                    }
                }
                else {
                    items.add("no data");
                }
            } catch (JSONException e) {
                //Toast.makeText(getActivity(), "not found.",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        };

        Response.ErrorListener respErrorList = volleyError->{
            Toast.makeText(getActivity(), "data error.",Toast.LENGTH_SHORT).show();
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(RequestFactory.getPage("product",0,2,respList,respErrorList));
        items.add("coba");
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        lv = (ListView) view.findViewById(lvProduct);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,items);
        lv.setAdapter(adapter);
        return view;
    }
}