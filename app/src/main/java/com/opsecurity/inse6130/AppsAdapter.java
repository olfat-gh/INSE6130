package com.opsecurity.inse6130;


import android.app.Dialog;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        public ViewHolder (View view){

            super(view);

            cardView = (CardView) view.findViewById(R.id.card_view);
            imageView = (ImageView) view.findViewById(R.id.imageview);
            textView_App_Name = (TextView) view.findViewById(R.id.Apk_Name);
            textView_App_Package_Name = (TextView) view.findViewById(R.id.Apk_Package_Name);
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

        viewHolder.textView_App_Package_Name.setText(appItem.getPackageName());

        viewHolder.imageView.setImageDrawable(appItem.getIcon());

        //Adding click listener on CardView to open clicked application directly from here .
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

}
