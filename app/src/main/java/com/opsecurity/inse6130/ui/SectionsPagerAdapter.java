package com.opsecurity.inse6130.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.opsecurity.inse6130.R;

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
                return new firstTabFrag();
            case 1:
                return new secondTabFrag();
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




