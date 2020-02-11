package com.opsecurity.inse6130;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

public class AppFragment extends Fragment {
    private int appTypes=0;
    private Handler handler = new Handler();
    List<AppModel> listApps=null;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    SpinKitView spinKitView;
    public static AppFragment newInstance(int appTypes){
        AppFragment fragment=new AppFragment();
        Bundle args=new Bundle();
        args.putInt("appTypes",appTypes);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args=getArguments();
        if(args==null)
            return;
        appTypes=args.getInt("appTypes");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View vw= inflater.inflate(R.layout.fragment_apps, container, false);
        recyclerView =vw.findViewById(R.id.recycler_view);
        spinKitView =vw.findViewById(R.id.loading);
        recyclerViewLayoutManager = new GridLayoutManager(getActivity(), 1);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        fillData();

        return vw;

    }

    public void fillData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                listApps = new ApkInfoExtractor(getActivity()).GetAllInstalledApkInfo(appTypes);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        adapter = new AppsAdapter(getActivity(),listApps);
                        recyclerView.setAdapter(adapter);
                        spinKitView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }



}
