package com.opsecurity.inse6130;

public class PermissionModel {
    private String permName;

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public  PermissionModel(String permName){
            this.permName=permName;
   }
}
