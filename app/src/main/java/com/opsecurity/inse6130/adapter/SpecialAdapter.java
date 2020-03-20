package com.opsecurity.inse6130.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.opsecurity.inse6130.R;
import com.opsecurity.inse6130.SpecialFragment;


public class SpecialAdapter extends RecyclerView.Adapter<SpecialAdapter.ViewHolder>{
    private String[] list;
    private TypedArray iclist;

    public SpecialAdapter(String[] list, TypedArray iclist) {
        this.list = list;
        this.iclist = iclist;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.special_list_item,parent,false);

        SpecialAdapter.ViewHolder viewHolder = new SpecialAdapter.ViewHolder(view2);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.textView_Sp_Name.setText(this.list[position]);//this.iclist[position]

        viewHolder.imageView.setImageResource(this.iclist.getResourceId(position,-1));
        viewHolder.itemView.setTag(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpecialFragment.openSpecialAccess(v.getContext(),(int)v.getTag());

            }
        });
    }

    @Override
    public int getItemCount(){

        return list.length;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{


        public ImageView imageView;
        public TextView textView_Sp_Name;


        public ViewHolder (View view){

            super(view);

            imageView = (ImageView) view.findViewById(R.id.sp_imageView);
            textView_Sp_Name = (TextView) view.findViewById(R.id.sp_textView);

        }
    }
}
