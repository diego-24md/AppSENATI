package com.example.myapplication;

import com.example.myapplication.adapters.DashboardPagerAdapter;
import androidx.annotation.NonNull;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class dashboard extends AppCompatActivity {

    private TabLayout tabLayout; //Selector de fragmentos
    private ViewPager2 viewPager; //Contenedor de fragmentos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        //Referenciamos
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Adaptador
        DashboardPagerAdapter adapter = new DashboardPagerAdapter(this);

        //El visor responderá a las órdenes del adaptador
        viewPager.setAdapter(adapter);


        //Configuración de las opciones y evento clic
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0: tab.setText("Registro"); break;
                            case 1: tab.setText("Buscador"); break;
                            case 2: tab.setText("Historial"); break;
                            case 3: tab.setText("Papelera"); break;
                        }
                    }
                }
        ).attach();
    }
}