package com.opsecurity.inse6130.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


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

    private static final HashMap<String,Integer> DANGEROUS_PERMISSIONS_SCORE = new HashMap<String,Integer>() {{
        put("android.permission.brick",100);
        put("android.permission.bind_device_admin",10);
        put("android.permission.get_accounts",10);
        put("android.permission.manage_accounts",10);
        put("android.permission.read_profile",10);
        put("android.permission.read_sms",10);
        put("android.permission.receive_mms",10);
        put("android.permission.receive_sms",10);
        put("android.permission.read_calendar",10);
        put("android.permission.read_social_stream",10);
        put("android.permission.write_secure_settings",9);
        put("android.permission.write_settings",9);
        put("android.permission.read_call_log",9);
        put("android.permission.modify_phone_state",9);
        put("android.permission.access_fine_location",9);
        put("android.permission.access_wifi_state",9);
        put("android.permission.change_network_state",9);
        put("android.permission.access_location_extra_commands",9);
        put("android.permission.read_history_bookmarks",8);
        put("android.permission.send_sms",8);
        put("android.permission.write_sms",8);
        put("android.permission.use_credentials",8);
        put("android.permission.location_hardware",8);
        put("android.permission.camera",8);
        put("android.permission.record_audio",8);
        put("android.permission.bluetooth_admin",8);
        put("android.permission.change_wifi_state",8);
        put("android.permission.read_logs",7);
        put("android.permission.write_social_stream",7);
        put("android.permission.call_phone",7);
        put("android.permission.call_privileged",7);
        put("android.permission.access_coarse_location",7);
        put("android.permission.delete_packages",6);
        put("android.permission.internet",6);
        put("android.permission.read_phone_state",4);
        put("android.permission.modify_audio_settings",4);
        put("android.permission.kill_background_processes",3);
        put("android.permission.send_respond_via_message",3);
        put("android.permission.read_external_storage",3);
        put("android.permission.get_tasks",3);
        put("android.permission.bluetooth",2);
        put("android.permission.receive_boot_completed",1);
          }};

    public static HashMap<String, Integer> getDangerousPermissionsScore() {
        return DANGEROUS_PERMISSIONS_SCORE;
    }

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
