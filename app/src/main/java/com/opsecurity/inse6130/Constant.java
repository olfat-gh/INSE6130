package com.opsecurity.inse6130;

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
}
