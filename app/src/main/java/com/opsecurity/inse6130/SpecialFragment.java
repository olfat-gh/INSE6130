package com.opsecurity.inse6130;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opsecurity.inse6130.adapter.SpecialAdapter;



public class SpecialFragment extends Fragment {
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vw=  inflater.inflate(R.layout.fragment_special, container, false);
        recyclerView =vw.findViewById(R.id.recyclerView_special);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String[] strArray = getResources().getStringArray(R.array.perm_items);
        TypedArray icArray=getResources().obtainTypedArray(R.array.ic2_items);


        recyclerView.setAdapter(new SpecialAdapter(strArray,icArray));
        return vw;
    }

    public static void openSpecialAccess(Context context, int i) {

        switch (i) {
            case 0:
                context.startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION"));
                return;
            case 1:
                context.startActivity(new Intent("android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS"));
                return;
            case 2:
                context.startActivity(new Intent("android.settings.action.MANAGE_WRITE_SETTINGS"));
                return;
            case 3:
                context.startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                return;
            case 4:
                context.startActivity(new Intent("android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS"));
                return;
            case 5:
                try {
                    context.startActivity(new Intent("android.settings.USAGE_ACCESS_SETTINGS"));
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            default:
                return;
        }
    }

}
