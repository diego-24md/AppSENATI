package com.example.myapplication.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.fragments.PrestamoFragment;
import com.example.myapplication.fragments.RecepcionFragment;
import com.example.myapplication.fragments.HistorialFragment;
import com.example.myapplication.fragments.ConfiguracionFragment;

public class DashboardPagerAdapter extends FragmentStateAdapter {

    public DashboardPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new PrestamoFragment();
            case 1: return new RecepcionFragment();
            case 2: return new HistorialFragment();
            case 3: return new ConfiguracionFragment();
            default: return new PrestamoFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}