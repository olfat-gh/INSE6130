package com.opsecurity.inse6130.model;

import android.graphics.drawable.Drawable;

import java.util.List;


public class AppModel {
    private String appName;
    private String packageName;
    private boolean isSystem;
    private boolean isScoreType;
    private int permissionsScore;
    private List<PermissionGroupModel> permissions;
    private Drawable icon;

    public AppModel(List<PermissionGroupModel> permissions, String name, String pkgName, boolean isSystem, Drawable drawable) {

        this.appName = name;
        this.packageName=pkgName;
        this.icon = drawable;
        this.isSystem = isSystem;
        this.permissions=permissions;
        this.isScoreType=false;
    }

    public AppModel(List<PermissionGroupModel> permissions, String name, String pkgName, boolean isSystem, Drawable drawable,int permissionsScore) {

        this.appName = name;
        this.packageName=pkgName;
        this.icon = drawable;
        this.isSystem = isSystem;
        this.permissions=permissions;
        this.permissionsScore=permissionsScore;
        this.isScoreType=true;
    }

    public boolean isScoreType() {
        return isScoreType;
    }

    public int getPermissionsScore() {
        return permissionsScore;
    }

    public void setPermissionsScore(int permissionsScore) {
        this.permissionsScore = permissionsScore;
    }

    public List<PermissionGroupModel> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionGroupModel> permissions) {
        this.permissions = permissions;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getAppName() {
        return appName;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public Drawable getIcon() {
        return icon;
    }


    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean hasGranted(){
        for(PermissionGroupModel tmpP: permissions){
            if(tmpP.isGranted())
                return  true;
        }
        return  false;
    }


}
