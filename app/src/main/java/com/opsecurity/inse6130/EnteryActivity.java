package com.opsecurity.inse6130;

import android.app.Activity;
import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;
import com.opsecurity.inse6130.ui.SectionsPagerAdapter;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class EnteryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entery);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this.getApplicationContext());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        tabs.getTabAt(0).setIcon(R.drawable.outline_account_circle_black_18dp);
        tabs.getTabAt(1).setIcon(R.drawable.outline_visibility_off_black_18dp);

    }
}