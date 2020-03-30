package com.opsecurity.inse6130.utility;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.opsecurity.inse6130.R;
import com.opsecurity.inse6130.model.AppModel;
import com.opsecurity.inse6130.model.PermissionGroupModel;
import com.opsecurity.inse6130.utility.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class ApkInfoExtractor {

    Context context1;

    public ApkInfoExtractor(Context context2){

        context1 = context2;
    }
    public ArrayList<ApplicationInfo> GetAllInstalledApkByScan() {
        ArrayList arrayList = new ArrayList();
        String packageName = context1.getPackageName();
        PackageManager packageManager = context1.getPackageManager();
        for (ApplicationInfo applicationInfo : packageManager.getInstalledApplications(PackageManager.GET_META_DATA)) {
            if (!packageName.equals(applicationInfo.processName)) {
                arrayList.add(applicationInfo);
            }
        }
        return arrayList;
    }

     public AppModel GetAppModelByScan(ApplicationInfo applicationInfo){

         List<PermissionGroupModel> tmp=new ArrayList<>();

       return new AppModel(tmp,
                 GetAppName(applicationInfo.packageName),
                 applicationInfo.packageName,
                 isSystemPackage(applicationInfo),
                 getAppIconByPackageName(applicationInfo.packageName),GetAppPermissionsScore(applicationInfo));

    }


public int GetAppPermissionsScore(ApplicationInfo applicationInfo){
        int permissionsScore=0;
    PackageManager packageManager = context1.getPackageManager();
    try {
        PackageInfo pkgInfo = packageManager.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);
        String[] strArr =pkgInfo.requestedPermissions;
        if (strArr != null) {
            for (String permName : strArr) {


                if (Constant.getDangerousPermissionsScore().containsKey(permName.toLowerCase())) {
                    permissionsScore+= Constant.getDangerousPermissionsScore().get(permName.toLowerCase());

                }

            }
        }


    }catch (PackageManager.NameNotFoundException e) {

        // e.printStackTrace();
    }


return  permissionsScore;

  }



    public List<AppModel> GetAllInstalledApkInfo(int IsSystem){

        List<AppModel> AllAppModel = new ArrayList<>();


        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");

        List<ResolveInfo> resolveInfoList = context1.getPackageManager().queryIntentActivities(intent,PackageManager.GET_META_DATA);

        for(ResolveInfo resolveInfo : resolveInfoList){

            ActivityInfo activityInfo = resolveInfo.activityInfo;

            if(IsSystem==1 ){
                if(isSystemPackage(resolveInfo)){

                    List<PermissionGroupModel> tmp= GetAppReqPermission(activityInfo.applicationInfo.packageName);
                    if(tmp.size()>0){
                        AllAppModel.add(new AppModel(tmp
                                ,
                                GetAppName(activityInfo.applicationInfo.packageName),
                                activityInfo.applicationInfo.packageName,
                                isSystemPackage(resolveInfo),
                                getAppIconByPackageName(activityInfo.applicationInfo.packageName)));
                    }

                }
            }else {
                if(!isSystemPackage(resolveInfo)){
                    List<PermissionGroupModel> tmp= GetAppReqPermission(activityInfo.applicationInfo.packageName);
                    if(tmp.size()>0){
                        AllAppModel.add(new AppModel(tmp
                                ,
                                GetAppName(activityInfo.applicationInfo.packageName),
                                activityInfo.applicationInfo.packageName,
                                isSystemPackage(resolveInfo),
                                getAppIconByPackageName(activityInfo.applicationInfo.packageName)));
                    }
                }
            }



/*            if(IsSystem==1 ){
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
            }*/

        }
        Collections.sort(AllAppModel, new Comparator<AppModel>() {
            @Override
            public int compare(AppModel o1, AppModel o2) {
                return String.CASE_INSENSITIVE_ORDER.compare(o1.getAppName(), o2.getAppName());

            }
        });


        return AllAppModel;

    }

    public boolean isSystemPackage(ResolveInfo resolveInfo){

        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }
    public boolean isSystemPackage(ApplicationInfo applicationInfo){

        return ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
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


                       // Log.v("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx11",permName);
                        int indexOf = Constant.getDangerousPermissionsGroup().indexOf(groupPermission.name);
                        if (indexOf >= 0) {
                        CharSequence loadDescription = groupPermission.loadDescription(packageManager);
                        Drawable loadIcon = groupPermission.loadIcon(packageManager);
                       // String obj = groupPermission.loadLabel(packageManager).toString();

                         if(!permAdded.contains(groupPermission.name)) {
                                 boolean isGranted=false;
                             if((pkgInfo.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED )!=0){
                                 isGranted=true;

                             }
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

           // e.printStackTrace();
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
