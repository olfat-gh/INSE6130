package com.opsecurity.inse6130;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;



public class ApkInfoExtractor {

    Context context1;

    public ApkInfoExtractor(Context context2){

        context1 = context2;
    }

    public List<AppModel> GetAllInstalledApkInfo(int IsSystem){

        List<AppModel> AllAppModel = new ArrayList<>();

        Intent intent = new Intent(Intent.ACTION_MAIN,null);

        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED );

        List<ResolveInfo> resolveInfoList = context1.getPackageManager().queryIntentActivities(intent,0);

        for(ResolveInfo resolveInfo : resolveInfoList){

            ActivityInfo activityInfo = resolveInfo.activityInfo;

            if(IsSystem==1 ){
                if(isSystemPackage(resolveInfo)){
                    AllAppModel.add(new AppModel(
                            GetAppReqPermission(activityInfo.applicationInfo.packageName),
                            GetAppName(activityInfo.applicationInfo.packageName),
                            activityInfo.applicationInfo.packageName,
                            isSystemPackage(resolveInfo),
                            getAppIconByPackageName(activityInfo.applicationInfo.packageName)));
                }
            }else {
                if(!isSystemPackage(resolveInfo)){
                    AllAppModel.add(new AppModel(
                            GetAppReqPermission(activityInfo.applicationInfo.packageName),
                            GetAppName(activityInfo.applicationInfo.packageName),
                            activityInfo.applicationInfo.packageName,
                            isSystemPackage(resolveInfo),
                            getAppIconByPackageName(activityInfo.applicationInfo.packageName)));
                }
            }

        }

        return AllAppModel;

    }

    public boolean isSystemPackage(ResolveInfo resolveInfo){

        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public Drawable getAppIconByPackageName(String ApkTempPackageName){

        Drawable drawable;

        try{
            drawable = context1.getPackageManager().getApplicationIcon(ApkTempPackageName);

        }
        catch (PackageManager.NameNotFoundException e){

            e.printStackTrace();

            drawable = ContextCompat.getDrawable(context1, R.mipmap.ic_launcher);
        }
        return drawable;
    }


    public  List<PermissionModel> GetAppReqPermission(String ApkPackageName){

        List<PermissionModel> permReq=new ArrayList<>();


        PackageManager packageManager = context1.getPackageManager();

        try {

            PackageInfo pkgInfo =packageManager.getPackageInfo(ApkPackageName,PackageManager.GET_PERMISSIONS);

            if(pkgInfo!=null && pkgInfo.requestedPermissions!=null){
                for(String permName:pkgInfo.requestedPermissions)
                        permReq.add(new PermissionModel(permName));


               // return pkgInfo.requestedPermissions;
            }

        }catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return permReq;
    }

    public String GetAppName(String ApkPackageName){

        String Name = "";

        ApplicationInfo applicationInfo;

        PackageManager packageManager = context1.getPackageManager();

        try {

            applicationInfo = packageManager.getApplicationInfo(ApkPackageName, 0);

            if(applicationInfo!=null){

                Name = (String)packageManager.getApplicationLabel(applicationInfo);
            }

        }catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return Name;
    }
}
