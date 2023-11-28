package com.example.indoorairqualitymonitoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.indoorairqualitymonitoringapp.dashboardelements.GraphFragment;
import com.example.indoorairqualitymonitoringapp.dashboardelements.MapFragment;
import com.example.indoorairqualitymonitoringapp.dashboardelements.SettingFragment;
import com.example.indoorairqualitymonitoringapp.dashboardelements.WeatherFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class DashBoardactivity extends AppCompatActivity {
    ViewPager2 viewPager2Main;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        viewPager2Main = findViewById(R.id.viewPager2Main);
        bottomNavigationView = findViewById(R.id.bottomNavView);

        fragmentArrayList.add(new MapFragment());
        fragmentArrayList.add(new WeatherFragment());
        fragmentArrayList.add(new GraphFragment());
        fragmentArrayList.add(new SettingFragment());

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, fragmentArrayList);
        viewPager2Main.setAdapter(viewPagerAdapter);
        viewPager2Main.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.it_Weather);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.it_Graph);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.it_Setting);
                        break;
                    default:
                        bottomNavigationView.setSelectedItemId(R.id.it_Home);
                        break;
                }
                super.onPageSelected(position);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final int id = item.getItemId();
                if (id == R.id.it_Home) {
                    viewPager2Main.setCurrentItem(0);
                } else if (id == R.id.it_Weather) {
                    viewPager2Main.setCurrentItem(1);
                } else if (id == R.id.it_Graph) {
                    viewPager2Main.setCurrentItem(2);
                } else if (id == R.id.it_Setting) {
                    viewPager2Main.setCurrentItem(3);
                }
                return true;
            }
        });
    }

//    private void signOut() {
//        Intent intent = new Intent(dashboard.this, Loginactivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);
//        finish();
//    }
} 