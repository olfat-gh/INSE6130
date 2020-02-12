package com.opsecurity.inse6130;

import android.graphics.drawable.Drawable;

import java.util.List;


public class AppModel {
    private String appName;
    private String packageName;
    private boolean isSystem;
    private List<PermissionModel> permissions;
    private Drawable icon;
    public AppModel(List<PermissionModel> permissions,String name, String pkgName, boolean isSystem, Drawable drawable) {

        this.appName = name;
        this.packageName=pkgName;
        this.icon = drawable;
        this.isSystem = isSystem;
        this.permissions=permissions;
    }

    public List<PermissionModel> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionModel> permissions) {
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
}
