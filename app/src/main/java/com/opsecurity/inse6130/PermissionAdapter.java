package com.opsecurity.inse6130;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PermissionAdapter  extends RecyclerView.Adapter<PermissionAdapter.ViewHolder>{
    Context context1;
    List<PermissionModel> permList;

    public PermissionAdapter(Context context, List<PermissionModel> list){

        context1 = context;

        permList = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view2 = LayoutInflater.from(context1).inflate(R.layout.permission_list_item,parent,false);

        ViewHolder viewHolder = new ViewHolder(view2);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PermissionModel perItem =  permList.get(position);

        holder.textView_Perm_Name.setText(perItem.getPermName());



    }

    @Override
    public int getItemCount() {
        return permList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textView_Perm_Name;


        public ViewHolder (View view){

            super(view);

            imageView = view.findViewById(R.id.imageview);
            textView_Perm_Name =  view.findViewById(R.id.perm_name);
        }
    }

}
