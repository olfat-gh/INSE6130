package com.opsecurity.inse6130;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.opsecurity.inse6130.adapter.MainPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Fragment> fragmentList = new ArrayList<>();
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView btmNav=findViewById(R.id.bottom_navigation);

        viewPager = findViewById(R.id.view_pager_main);

        fragmentList.add(new AllAppFragment());
        fragmentList.add(new SpecialFragment());
        MainPagerAdapter mpAdapter = new MainPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(mpAdapter);
        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.nav_allapps:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.nav_special:
                        viewPager.setCurrentItem(1);
                        return true;
                }

                return false;
            }
        });

     /*   getSupportFragmentManager().
                beginTransaction().replace(R.id.fragment_container,fragmentList.get(0)).commit();*/

    }


}
