package com.opsecurity.inse6130;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PermissionAdapter  extends RecyclerView.Adapter<PermissionAdapter.ViewHolder>{
    Context context1;
    List<PermissionGroupModel> permList;

    public PermissionAdapter(Context context, List<PermissionGroupModel> list){

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
        final PermissionGroupModel perItem =  permList.get(position);

        holder.textView_Perm_Name.setText(perItem.getPermName());
        holder.textView_Desc_Name.setText(perItem.getDesc());
        holder.img_group_ic.setImageDrawable(perItem.getIcon());
        holder.Switch_switchGrant.setChecked(perItem.isGranted());

    }

    @Override
    public int getItemCount() {
        return permList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img_group_ic;
        public TextView textView_Perm_Name;
        public TextView textView_Desc_Name;
        public Switch Switch_switchGrant;
        public ViewHolder (View view){

            super(view);

            img_group_ic = view.findViewById(R.id.group_ic);
            textView_Perm_Name =  view.findViewById(R.id.perm_name);
            textView_Desc_Name= view.findViewById(R.id.perm_desc);
            Switch_switchGrant=view.findViewById(R.id.switchGrant);
        }
    }

}
