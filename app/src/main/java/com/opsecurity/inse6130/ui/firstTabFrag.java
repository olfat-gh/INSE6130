package com.opsecurity.inse6130.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opsecurity.inse6130.ApkInfoExtractor;
import com.opsecurity.inse6130.AppsAdapter;
import com.opsecurity.inse6130.MainActivity;
import com.opsecurity.inse6130.R;

public class firstTabFrag extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View vw= inflater.inflate(R.layout.activity_main, container, false);
        recyclerView =  vw.findViewById(R.id.recycler_view);

        // Passing the column number 1 to show online one column in each row.
        recyclerViewLayoutManager = new GridLayoutManager(getActivity(), 1);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        adapter = new AppsAdapter(getActivity() , new ApkInfoExtractor(getActivity()).GetAllInstalledApkInfo());

        recyclerView.setAdapter(adapter);


        return vw;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}
