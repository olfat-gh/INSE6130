package com.opsecurity.inse6130.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;


import com.opsecurity.inse6130.utility.Constant;

import java.util.List;

public class GlobalPermissionService extends AccessibilityService {
    static final String TAG = "RecorderService";
    static Handler handler=null;
    public static boolean doPermissionOpene =false;
    public static boolean isPermissionSeted =false;
    public static boolean doSecondBack =false;
    public static String lblGroup="";
    private static GlobalPermissionService myAccessibilityServiceInstance;
    private AccessibilityNodeInfo findNodeByTitleResult;

    public  static GlobalPermissionService getInstance(){
        return  myAccessibilityServiceInstance;
    }

    public static void returnBackApp() {
        handler= new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message message) {
                try {
                    GlobalPermissionService.getInstance().performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                } catch (Exception e) {
                }
                return true;
            }
        });
        handler.sendEmptyMessageDelayed(0, 200);
    }

    private String getEventType(AccessibilityEvent event) {
        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                return "TYPE_NOTIFICATION_STATE_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                return "TYPE_VIEW_CLICKED";
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                return "TYPE_VIEW_FOCUSED";
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                return "TYPE_VIEW_LONG_CLICKED";
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                return "TYPE_VIEW_SELECTED";
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                return "TYPE_WINDOW_STATE_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                return "TYPE_VIEW_TEXT_CHANGED";
        }
        return "default";
    }

    private String getEventText(AccessibilityEvent event) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence s : event.getText()) {
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
       // if (event != null) {
       //   event.getClassName();
       // }

        CharSequence className=event.getClassName();
        AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();





        if (className!=null && rootInActiveWindow != null) {

/*            Log.v(TAG, String.format(
                    "onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [text] %s",
                    getEventType(event), event.getClassName(), event.getPackageName(),
                    event.getEventTime(), getEventText(event)));*/

            if(GlobalPermissionService.doSecondBack){
                if (Constant.getAppDetailActivityValue().contains(className)) {
                    GlobalPermissionService.doSecondBack = false;
                    returnBackApp();
                }
            }

            if(GlobalPermissionService.doPermissionOpene) {

                if (Constant.getAppDetailActivityValue().contains(className)) {
                    if( GlobalPermissionService.isPermissionSeted){

                        GlobalPermissionService.doPermissionOpene = false;
                        GlobalPermissionService.doSecondBack = true;
                        returnBackApp();
                    }else {
                        //if (isPermissionScreenActivity(this.firstClassName)) {

                        //}else {
                        AccessibilityNodeInfo findNodeByTitle = findNodeByTitle("Permissions");
                        if (findNodeByTitle == null || findNodeByTitle.getParent() == null) {
                            GlobalPermissionService.doPermissionOpene = false;
                            toastNotSupport("please change Permission manually.");
                        } else {
                            findNodeByTitle.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }

                    //}
                } else if (Constant.getAppPermissionPackageValue().contains(event.getPackageName().toString())) {


                    if (event.getClassName().equals("android.app.AlertDialog")) {
                        handler.removeMessages(0);
                        List findAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/button1");

                        ((AccessibilityNodeInfo) findAccessibilityNodeInfosByViewId.get(0)).performAction(16);
                        GlobalPermissionService.doPermissionOpene = false;
                        GlobalPermissionService.doSecondBack = true;
                        returnBackApp();
                    }else {
                        if( GlobalPermissionService.isPermissionSeted){

                            GlobalPermissionService.doPermissionOpene = false;
                            GlobalPermissionService.doSecondBack = true;
                            returnBackApp();
                        }



                        AccessibilityNodeInfo findParentNodeByTitle0 = findParentNodeByTitle(GlobalPermissionService.lblGroup);

                        if (findParentNodeByTitle0 != null) {

                            AccessibilityNodeInfo parent = findParentNodeByTitle0.getParent();
                            if (parent != null) {
                                GlobalPermissionService.isPermissionSeted=true;
                                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                returnBackApp();


                            }

                        } else {
                            GlobalPermissionService.doPermissionOpene = false;
                            toastNotSupport("please change Permission manually.");
                        }
                    }

                }
            }
            }


    }

    @Override
    public boolean onUnbind(Intent intent) {
        myAccessibilityServiceInstance = null;
        return super.onUnbind(intent);
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        myAccessibilityServiceInstance = this;
        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
        accessibilityServiceInfo.eventTypes =  AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        accessibilityServiceInfo.flags = 80;
        accessibilityServiceInfo.notificationTimeout = 0;
        setServiceInfo(accessibilityServiceInfo);
    }

    private final void toastNotSupport(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    private final void doFindNodeByTitle(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        if (this.findNodeByTitleResult == null) {
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null) {
                    CharSequence text = child.getText();
                    if (text != null && text.toString().contains((CharSequence) str)) {
                        AccessibilityNodeInfo parent = child.getParent();

                        if (parent!=null && parent.isClickable()) {
                            this.findNodeByTitleResult = child;
                        }
                    }
                    doFindNodeByTitle(child, str);
                }
            }
        }
    }

    private final AccessibilityNodeInfo findParentNodeByTitle(String str) {
        if (getRootInActiveWindow() != null) {
            List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = getRootInActiveWindow().findAccessibilityNodeInfosByText(str);
            if (!findAccessibilityNodeInfosByText.isEmpty()) {
                for (AccessibilityNodeInfo accessibilityNodeInfo : findAccessibilityNodeInfosByText) {

                    if (accessibilityNodeInfo!=null &&accessibilityNodeInfo.getText().equals(str) && accessibilityNodeInfo.getParent() != null) {
                        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();

                        if (parent!=null && parent.isClickable()) {
                            return accessibilityNodeInfo;
                        }
                    }
                }
            }
        }
        return null;
    }

    private final AccessibilityNodeInfo findNodeByTitle(String str) {
        if (getRootInActiveWindow() != null) {
            List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = getRootInActiveWindow().findAccessibilityNodeInfosByText(str);
            if (!findAccessibilityNodeInfosByText.isEmpty()) {
                for (AccessibilityNodeInfo accessibilityNodeInfo : findAccessibilityNodeInfosByText) {

                    if (accessibilityNodeInfo !=null && accessibilityNodeInfo.getParent() != null) {
                        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();

                        if (parent !=null && parent.isClickable()) {
                            return accessibilityNodeInfo;
                        }
                    }
                }
            }
            AccessibilityNodeInfo accessibilityNodeInfo2 = null;
            this.findNodeByTitleResult = accessibilityNodeInfo2;
            AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
           if(rootInActiveWindow!=null);
               doFindNodeByTitle(rootInActiveWindow, str);
            AccessibilityNodeInfo accessibilityNodeInfo3 = this.findNodeByTitleResult;
            if (accessibilityNodeInfo3 != null) {
                return accessibilityNodeInfo3;
            }
            List findAccessibilityNodeInfosByViewId = getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.android.settings:id/list");
            if (!findAccessibilityNodeInfosByViewId.isEmpty()) {
                ((AccessibilityNodeInfo) findAccessibilityNodeInfosByViewId.get(0)).performAction(4096);
                SystemClock.sleep(100);
                List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText2 = getRootInActiveWindow().findAccessibilityNodeInfosByText(str);
                if (!findAccessibilityNodeInfosByText2.isEmpty()) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo4 : findAccessibilityNodeInfosByText2) {

                        if (accessibilityNodeInfo4!=null && accessibilityNodeInfo4.getParent() != null) {
                            AccessibilityNodeInfo parent2 = accessibilityNodeInfo4.getParent();

                            if (parent2!=null && parent2.isClickable()) {
                                return accessibilityNodeInfo4;
                            }
                        }
                    }
                }
                this.findNodeByTitleResult = accessibilityNodeInfo2;
                AccessibilityNodeInfo rootInActiveWindow2 = getRootInActiveWindow();
                if(rootInActiveWindow2!=null)
                    doFindNodeByTitle(rootInActiveWindow2, str);
                return this.findNodeByTitleResult;
            }
        }
        return null;
    }
}
