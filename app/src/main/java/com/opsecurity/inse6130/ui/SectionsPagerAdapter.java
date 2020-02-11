package com.opsecurity.inse6130.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.opsecurity.inse6130.R;
import com.opsecurity.inse6130.AppFragment;

public class SectionsPagerAdapter  extends FragmentStatePagerAdapter {
    Context m_contexct=null;

    public SectionsPagerAdapter(@NonNull FragmentManager fm,  Context nContext) {
        super(fm);
        this.m_contexct=nContext;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return AppFragment.newInstance(0);
            case 1:
                return AppFragment.newInstance(1);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return m_contexct.getResources().getString(R.string.tab_text_1);
            case 1: return m_contexct.getResources().getString(R.string.tab_text_2);
            default:return "";
        }
    }


}




