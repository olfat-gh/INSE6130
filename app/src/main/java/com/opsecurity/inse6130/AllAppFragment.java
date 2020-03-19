package com.opsecurity.inse6130;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.opsecurity.inse6130.ui.SectionsPagerAdapter;

public class AllAppFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vw= inflater.inflate(R.layout.fragment_all_apps, container, false);
        viewPager = vw.findViewById(R.id.view_pager);
        tabLayout = vw.findViewById(R.id.tabs);

        return vw;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.outline_account_circle_black_18dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.outline_visibility_off_black_18dp);


    }


    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        sectionsPagerAdapter.addFragment(AppFragment.newInstance(0), "Installed");
        sectionsPagerAdapter.addFragment(AppFragment.newInstance(1), "System");
        viewPager.setAdapter(sectionsPagerAdapter);
    }




}
