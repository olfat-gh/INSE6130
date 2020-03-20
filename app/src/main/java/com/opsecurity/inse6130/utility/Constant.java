package com.opsecurity.inse6130.utility;

import java.util.ArrayList;
import java.util.Arrays;

public final class Constant {

    private static final ArrayList<String> DANGEROUS_PERMISSIONS_GROUP = new ArrayList<String>( Arrays.asList(
            "android.permission-group.CALENDAR",
            "android.permission-group.CAMERA",
            "android.permission-group.CONTACTS",
            "android.permission-group.LOCATION",
            "android.permission-group.MICROPHONE",
            "android.permission-group.SMS",
            "android.permission-group.STORAGE",
            "android.permission-group.PHONE"));

    public static ArrayList<String> getDangerousPermissionsGroup() {
        return DANGEROUS_PERMISSIONS_GROUP;
    }
    private static final ArrayList<String> APP_DETAIL_ACTIVITY_VALUE = new ArrayList<String>( Arrays.asList(
            "com.android.settings.applications.InstalledAppDetailsTop",
            "com.miui.appmanager.ApplicationsDetailsActivity"));

    public static ArrayList<String> getAppDetailActivityValue() {
        return APP_DETAIL_ACTIVITY_VALUE;
    }

    private static final ArrayList<String> APP_PERMISSION_PACKAGE_VALUE = new ArrayList<String>( Arrays.asList(
            "com.miui.securitycenter",
            "com.miui.appmanager",
            "com.android.packageinstaller",
            "com.google.android.packageinstaller"));

    public static ArrayList<String> getAppPermissionPackageValue() {
        return APP_PERMISSION_PACKAGE_VALUE;
    }
}
