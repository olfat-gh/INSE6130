package com.opsecurity.inse6130;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.opsecurity.inse6130.service.GlobalPermissionService;

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
        holder.Switch_switchGrant.setTag(perItem);
        holder.Switch_switchGrant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PermissionGroupModel permSelected = (PermissionGroupModel) buttonView.getTag();
                Toast.makeText(context1, permSelected.getPermName(), Toast.LENGTH_SHORT).show();

                   if(!isServiceRunning()){
                       AlertDialog.Builder builder = new AlertDialog.Builder(context1);
                       builder.setMessage(R.string.dialog_service_message)
                               .setTitle(R.string.dialog_service_title);
                       builder.setPositiveButton(R.string.dialog_service_ok, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.cancel();
                               context1.startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
                           }
                       });
                       builder.setNegativeButton(R.string.dialog_service_cancel, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.cancel();
                           }
                       });
                       AlertDialog create = builder.create();
                       if (create != null) {
                           create.setCancelable(false);
                           create.show();
                       }
                   return;
                   }
                GlobalPermissionService.lblGroup=permSelected.getLableName();
                GlobalPermissionService.doPermissionOpene=true;
                GlobalPermissionService.doSecondBack=false;
                GlobalPermissionService.isPermissionSeted=false;
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                StringBuilder sb = new StringBuilder();
                sb.append("package:");
                sb.append(permSelected.getPkgName());
                intent.setData(Uri.parse(sb.toString()));
                context1.startActivity(intent);

            }
        });
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

    public boolean isServiceRunning(){
        String myServiceName=context1.getPackageName()+
                "/.service."+
                com.opsecurity.inse6130.service.GlobalPermissionService.class.getSimpleName();
        AccessibilityManager systemService =(AccessibilityManager) context1.getSystemService(context1.ACCESSIBILITY_SERVICE);
        if (systemService != null) {
            List<AccessibilityServiceInfo> enabledServices =
                    systemService.getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
            for (AccessibilityServiceInfo accessibilityServiceInfo : enabledServices) {
                if(accessibilityServiceInfo!=null){
                   // Log.v("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",accessibilityServiceInfo.getId());
                   // Log.v("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",myServiceName);
                    if(myServiceName.equals(accessibilityServiceInfo.getId()))
                        return true;
                }

            }
        }
        return false;
    }

}
