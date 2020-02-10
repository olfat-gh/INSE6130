package com.opsecurity.inse6130;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    List<AppModel> listApps=null;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    SpinKitView spinKitView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =  findViewById(R.id.recycler_view);
        spinKitView =findViewById(R.id.loading);
        // Passing the column number 1 to show online one column in each row.
        recyclerViewLayoutManager = new GridLayoutManager(MainActivity.this, 1);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        fillData();

        //spinKitView.setVisibility(View.GONE);
        // recyclerView.setVisibility(View.VISIBLE);

    }
    public void fillData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                listApps = new ApkInfoExtractor(MainActivity.this).GetAllInstalledApkInfo();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        adapter = new AppsAdapter(MainActivity.this,listApps);
                        recyclerView.setAdapter(adapter);
                        spinKitView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }


}
