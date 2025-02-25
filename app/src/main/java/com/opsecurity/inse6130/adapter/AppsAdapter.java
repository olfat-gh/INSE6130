package com.opsecurity.inse6130.adapter;


import android.app.Dialog;
import android.content.Context;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opsecurity.inse6130.R;
import com.opsecurity.inse6130.model.AppModel;

import java.util.List;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder>{

    Context context1;
    List<AppModel> appList;

    public AppsAdapter(Context context, List<AppModel> list){

        context1 = context;

        appList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public ImageView imageView;
        public TextView textView_App_Name;
        public TextView textView_App_Package_Name;
        public TextView textView_perm_score;
        public ImageView imageViewDanger;


        public ViewHolder (View view){

            super(view);

            cardView =  view.findViewById(R.id.card_view);
            imageView =  view.findViewById(R.id.imageview);
            imageViewDanger =  view.findViewById(R.id.imageviewdanger);
            textView_App_Name =  view.findViewById(R.id.Apk_Name);
            textView_App_Package_Name =  view.findViewById(R.id.Apk_Package_Name);
            textView_perm_score =  view.findViewById(R.id.perm_score);
        }
    }

    @Override
    public AppsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view2 = LayoutInflater.from(context1).inflate(R.layout.cardview_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(view2);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){

       // ApkInfoExtractor apkInfoExtractor = new ApkInfoExtractor(context1);

        final AppModel appItem =  appList.get(position);
        //String ApplicationLabelName = apkInfoExtractor.GetAppName(ApplicationPackageName);

       // Drawable drawable = apkInfoExtractor.getAppIconByPackageName(ApplicationPackageName);

        viewHolder.textView_App_Name.setText(appItem.getAppName());

        if(!appItem.isScoreType()){
            viewHolder.textView_App_Package_Name.setText(appItem.getPackageName());
        }else{
            if(appItem.isSystem())
             viewHolder.textView_App_Package_Name.setText("System");
            else
             viewHolder.textView_App_Package_Name.setText("User");


            viewHolder.textView_perm_score.setText(String.valueOf(appItem.getPermissionsScore()));

        }


        viewHolder.imageView.setImageDrawable(appItem.getIcon());
        viewHolder.imageViewDanger.setImageResource(R.drawable.ic_warning_black_24dp);
       if(appItem.hasGranted())
            viewHolder.imageViewDanger.setVisibility(View.VISIBLE);
        else
            viewHolder.imageViewDanger.setVisibility(View.INVISIBLE);

        //Adding click listener on CardView to open clicked application directly from here .
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(appItem.getPermissions().size()==0) {
                    Toast.makeText(context1,"This app didn't request critical Permission.", Toast.LENGTH_LONG).show();
                    return;
                }

                Dialog dialog = new Dialog(context1);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_permission_list);
                dialog.getWindow().setLayout(-1,-2);

                TextView textView = dialog.findViewById(R.id.app_name_title);
                ImageView imageView =  dialog.findViewById(R.id.app_ic);
                imageView.setImageDrawable(appItem.getIcon());
                textView.setText(appItem.getAppName());
                RecyclerView permListView =  dialog.findViewById(R.id.perm_recyclerView);

                permListView.setLayoutManager(new LinearLayoutManager(context1));
                permListView.setHasFixedSize(true);
                PermissionAdapter adapter=new PermissionAdapter(context1,appItem.getPermissions());


                permListView.setAdapter(adapter);

                dialog.show();
                /*Intent intent = context1.getPackageManager().getLaunchIntentForPackage(ApplicationPackageName);
                if(intent != null){

                    context1.startActivity(intent);

                }
                else {*/

                    //Toast.makeText(context1,appItem.getPermissions()[0], Toast.LENGTH_LONG).show();
              //  }
            }
        });
    }

    @Override
    public int getItemCount(){

        return appList.size();
    }
    public void clear() {

        appList.clear();

        notifyDataSetChanged();

    }
}
