package com.opsecurity.inse6130;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
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


        Intent intent = new Intent("android.intent.action.MAIN");
       intent.addCategory("android.intent.category.LAUNCHER");
       // Intent intent = new Intent(Intent.ACTION_MAIN,null);

        //intent.addCategory(Intent.CATEGORY_LAUNCHER);

       // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED );

        List<ResolveInfo> resolveInfoList = context1.getPackageManager().queryIntentActivities(intent,PackageManager.GET_META_DATA);

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


    public  List<PermissionGroupModel> GetAppReqPermission(String ApkPackageName){

        List<PermissionGroupModel> permReq=new ArrayList<>();
        List<String> permAdded=new ArrayList<>();

        PackageManager packageManager = context1.getPackageManager();

        try {

            PackageInfo pkgInfo =packageManager.getPackageInfo(ApkPackageName,PackageManager.GET_PERMISSIONS);
           // Log.v("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",ApkPackageName);
            if(pkgInfo!=null && pkgInfo.requestedPermissions!=null){
                    for (int i = 0; i < pkgInfo.requestedPermissions.length; i++) {
                        String permName=pkgInfo.requestedPermissions[i];

                   String gpName= packageManager.getPermissionInfo(permName,0).group;

                    if(gpName!=null) {
                        PermissionGroupInfo groupPermission = packageManager.getPermissionGroupInfo(
                                gpName, 0);

                        int indexOf = Constant.getDangerousPermissionsGroup().indexOf(groupPermission.name);
                        if (indexOf >= 0) {
                        CharSequence loadDescription = groupPermission.loadDescription(packageManager);
                        Drawable loadIcon = groupPermission.loadIcon(packageManager);
                       // String obj = groupPermission.loadLabel(packageManager).toString();

                         if(!permAdded.contains(groupPermission.name)) {
                                 boolean isGranted=false;
                             if((pkgInfo.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED )!=0)
                                 isGranted=true;
                             permAdded.add(groupPermission.name);

                             PermissionGroupModel itemTemp=new PermissionGroupModel(groupPermission.name, loadDescription.toString(),loadIcon,isGranted);
                             itemTemp.setPkgName(ApkPackageName);
                             itemTemp.setLableName(groupPermission.loadLabel(packageManager).toString());
                             permReq.add(itemTemp);
                            }
                         }

                    }



                }



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
