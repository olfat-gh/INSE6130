package com.opsecurity.inse6130.model;

import android.graphics.drawable.Drawable;

public class PermissionGroupModel {
    private String permName;
    private String lableName;
    private String desc;
    private String pkgName;
    private boolean isGranted;
    private Drawable icon;
    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public PermissionGroupModel(String permName, String desc, Drawable icon, boolean isGranted){
            this.permName=permName;
            this.desc=desc;
            this.icon=icon;
            this.isGranted=isGranted;
   }
    public Drawable getIcon() {
        return icon;
    }
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isGranted() {
        return isGranted;
    }

    public void setGranted(boolean granted) {
        isGranted = granted;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLableName() {
        return lableName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName;
    }
}
