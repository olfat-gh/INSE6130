package com.opsecurity.inse6130;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opsecurity.inse6130.adapter.AppsAdapter;
import com.opsecurity.inse6130.model.AppModel;
import com.opsecurity.inse6130.utility.ApkInfoExtractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScanFragment extends Fragment {
    ObjectAnimator textColorAnim;
    Button startScanButton;
    float sumValue = 0;
    ArrayList<AppModel> listApps=new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
   TextView textView_help_scan;
    private Handler handler = new Handler();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vw=  inflater.inflate(R.layout.fragment_scan, container, false);
        startScanButton = vw.findViewById(R.id.start_scan);
        startScanButton.setBackgroundColor(Color.TRANSPARENT);
        textView_help_scan=vw.findViewById(R.id.textView_help_scan);
        Drawable drawable = vw.getContext().getDrawable(R.drawable.circular);
        final ProgressBar crProgress = vw.findViewById(R.id.circularProgressbar);

        crProgress.setProgress(0);
        crProgress.setProgressDrawable(drawable);
        crProgress.setSecondaryProgress(100);
        crProgress.setMax(100);

        recyclerView =vw.findViewById(R.id.recycler_view_scan);
        recyclerViewLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        final ApkInfoExtractor apkUtility=new ApkInfoExtractor(getActivity());
        AnimateStartButton(startScanButton);
        startScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        ArrayList<ApplicationInfo> arrayListApps = apkUtility.GetAllInstalledApkByScan();
                       // arrayListApps.size();
                        float max=100;
                        float incValue= max/ arrayListApps.size();








                            for (ApplicationInfo applicationInfo : arrayListApps) {
                                sumValue+=incValue;
                                AppModel tmpApp= apkUtility.GetAppModelByScan(applicationInfo);
                                if(tmpApp.getPermissionsScore()>0)
                                listApps.add(tmpApp);


                            handler.post(new Runnable() {


                                public void run() {

                                    crProgress.setProgress((int)sumValue);
                                    startScanButton.setText((int)sumValue + "%");

                                }
                            });

                        }


                        handler.post(new Runnable() {


                            public void run() {
                                sortComparator(listApps);
                                adapter = new AppsAdapter(getActivity(),listApps);
                                recyclerView.setAdapter(adapter);

                                crProgress.setVisibility(View.INVISIBLE);
                                startScanButton.setVisibility(View.INVISIBLE);
                                textView_help_scan.setVisibility(View.INVISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }
                        });

                    }
                }).start();
            }
        });

        return vw;
    }
    public void sortComparator(ArrayList<AppModel> arrayList) {
        Collections.sort(arrayList, new Comparator<AppModel>() {
            public int compare(AppModel appItem, AppModel appItem2) {
                return appItem2.getPermissionsScore() - appItem.getPermissionsScore();
            }
        });
    }
    public void AnimateStartButton(Button v) {
        textColorAnim = ObjectAnimator.ofInt(v, "textColor", Color.BLACK, Color.TRANSPARENT);
        textColorAnim.setDuration(1000);
        textColorAnim.setEvaluator(new ArgbEvaluator());
        textColorAnim.setRepeatCount(ValueAnimator.INFINITE);
        textColorAnim.setRepeatMode(ValueAnimator.REVERSE);
        textColorAnim.start();
    }
}
