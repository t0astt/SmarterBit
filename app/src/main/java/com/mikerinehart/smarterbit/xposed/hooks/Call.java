package com.mikerinehart.smarterbit.xposed.hooks;

import android.content.Context;
import android.content.Intent;

import com.mikerinehart.smarterbit.generic.DeviceUtils;
import com.mikerinehart.smarterbit.xposed.SmarterBitXposed;
import com.mikerinehart.smarterbit.xposed.enums.FitBitClasses;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Call {

    public static void initHooks(XC_LoadPackage.LoadPackageParam lpparam) {

        // Hide Caller ID Hook
        XposedHelpers.findAndHookMethod(FitBitClasses.PHONE_CALL_OBSERVER.getInstance(lpparam).getCanonicalName(),
                    lpparam.classLoader,
                    "b", Context.class, Intent.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            //SmarterBitXposed.reloadPreferences();
                            if (isHideCallerIdEnabled()) {
                                XposedBridge.log("Hiding Caller ID");
                                ((Intent)param.args[1]).putExtra("incoming_number", "Incoming Call");
                            }
                        }
                    });

        XposedHelpers.findAndHookMethod(FitBitClasses.PHONE_CALL_OBSERVER.getInstance(lpparam).getCanonicalName(),
                lpparam.classLoader,
                "d", String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        //SmarterBitXposed.reloadPreferences();
                        if (isCallNotificationEnabled()) {
                            if (isNotifyOnlyIfScreenOffEnabled()) {
                                if (DeviceUtils.isScreenOn()) {
                                    XposedBridge.log("Screen is on, Killing call notification");
                                    param.setResult(null);
                                }
                            }
                        } else {
                            XposedBridge.log("Call notifications not enabled, Killing Call notification");
                            param.setResult(null);
                        }
                    }
                });
    }

    /*
    * Checks if user has call notifications enabled
    */
    private static boolean isCallNotificationEnabled() {
        XposedBridge.log("IsCallNotificationsEnabled method called");
        boolean status = SmarterBitXposed.getPreferences().getBoolean("callEnabled", false);
        if (status) {
            XposedBridge.log("Call Enabled: True");
        } else XposedBridge.log("Call Enabled: False");
        return SmarterBitXposed.getPreferences().getBoolean("callEnabled", false);
    }

    /*
    * Checks if user has screen off notifications enabled
    */
    private static boolean isNotifyOnlyIfScreenOffEnabled() {
        boolean status = SmarterBitXposed.getPreferences().getBoolean("callScreenOffOnly", false);
        if (status) {
            XposedBridge.log("Call Screen off only: Enabled");
        } else XposedBridge.log("Call Screen off only: False");
        return SmarterBitXposed.getPreferences().getBoolean("callScreenOffOnly", false);
    }

    /*
    * Check if user has hide caller ID enabled
    */
    private static boolean isHideCallerIdEnabled() {
        boolean status = SmarterBitXposed.getPreferences().getBoolean("callHideID", false);
        if (status) {
            XposedBridge.log("Hide Caller ID: Enabled");
        } else XposedBridge.log("Hide Caller ID: False");
        return SmarterBitXposed.getPreferences().getBoolean("callHideID", false);
    }
}
