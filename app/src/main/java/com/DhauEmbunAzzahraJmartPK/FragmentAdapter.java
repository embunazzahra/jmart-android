package com.DhauEmbunAzzahraJmartPK;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.DhauEmbunAzzahraJmartPK.FilterFragment;
import com.DhauEmbunAzzahraJmartPK.ProductFragment;

/**
 * This is class for fragment adapter.
 *
 * @author Dhau' Embun Azzahra
 * */
public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1 :
                return new FilterFragment();
        }
        return new ProductFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
