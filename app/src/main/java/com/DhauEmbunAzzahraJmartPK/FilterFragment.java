package com.DhauEmbunAzzahraJmartPK;

import static com.DhauEmbunAzzahraJmartPK.ProductFragment.listViewAdapter;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.DhauEmbunAzzahraJmartPK.model.Account;
import com.DhauEmbunAzzahraJmartPK.model.ProductCategory;
import com.android.volley.Response;

import org.json.JSONArray;

/**
 * This is fragment for using product filter feature.
 * user need to fill all of the filter option
 * to get all list of product with filter.
 *
 * @author Dhau' Embun Azzahra
 * */
public class FilterFragment extends Fragment {
    EditText search;
    EditText lowestPrice_;
    EditText highestPrice_;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button aplyBtn;
    ProductCategory category;
    boolean condition = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    /**
     * In this onCreateOptionsMenu will check if the logged account
     * doesn't have store yet, the "add product" menu will be
     * hiden from the topbar.
     * If the search icon is clicked, this will do product search
     * based on the start of the words in product name.
     * @param menu the menu.
     * @param inflater the menu inflater.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu,inflater);
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

    /**
     * This will move the activity if one of the menu is clicked.
     * @param item menu item.
     * @return selected item.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(getActivity(),"search clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.addProduct:
                startActivity(new Intent(getActivity(),CreateProductActivity.class));
                break;
            case R.id.aboutme:
                startActivity(new Intent(getActivity(),AboutMeActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        search = (EditText) view.findViewById(R.id.etSearch);
        lowestPrice_ = (EditText) view.findViewById(R.id.etLowestPrice);
        highestPrice_ = (EditText) view.findViewById(R.id.etHIghestPrice);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup2);
        aplyBtn = (Button) view.findViewById(R.id.applyBtn);



        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        /**
         * Array adapter for product category.
         */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.product_category,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        /**
         * assigning 'condition' variable according to
         * the radio button selected.
         */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.newCondition2:
                        condition = false;
                        Toast.makeText(getActivity(),"New",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.usedCondition2:
                        condition = true;
                        Toast.makeText(getActivity(),"Used",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        /**
         * Converting the selected product category string in the spinner
         * to Product Category type.
         */
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = ProductCategory.valueOf(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /**
         * if apply button is clicked, it will move to product activity
         * with some filter information to be applied.
         */
        aplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedCondition = radioGroup.getCheckedRadioButtonId();
                radioButton = view.findViewById(selectedCondition);
                String name = search.getText().toString();
                String bottomPrice = lowestPrice_.getText().toString();
                String topPrice = highestPrice_.getText().toString();
                double lowestPrice = 0.0;
                double highestPrice = 0.0;
                try {
                    highestPrice = Double.valueOf(topPrice);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "highest price failed to parse.",Toast.LENGTH_SHORT).show();
                }
                try {
                    lowestPrice = Double.valueOf(bottomPrice);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "lowest price failed to parse.",Toast.LENGTH_SHORT).show();
                }


                Intent intent = new Intent(getActivity(),ProductActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("lowestPrice", lowestPrice);
                intent.putExtra("highestPrice", highestPrice);
                intent.putExtra("condition",condition);
                intent.putExtra("category", category);
                startActivity(intent);

            }
        });

        return view;
    }
}