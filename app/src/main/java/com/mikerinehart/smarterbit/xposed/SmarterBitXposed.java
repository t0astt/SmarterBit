package com.mikerinehart.smarterbit.xposed;

import android.content.SharedPreferences;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class SmarterBitXposed implements IXposedHookLoadPackage, IXposedHookZygoteInit, SharedPreferences.OnSharedPreferenceChangeListener{

    private static XSharedPreferences prefs;

    public void initZygote(StartupParam sparam) throws Throwable {
        makeNewPreferences();
    }

    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if(lpparam.packageName.equals(PackageName.FITBIT.getPackageName())) {
            Common.init(lpparam);
            SMS.initHooks(lpparam);
            Call.initHooks(lpparam);
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences xsp, String key) {
        XposedBridge.log("SP Changed, reloading");
        reloadPreferences();
    }


    public static XSharedPreferences getPreferences() {
        XposedBridge.log("Attempting to return prefs");
        if (prefs != null) {
            return prefs;
        } else {
            XposedBridge.log("Preferences were null, wtf lol");
            makeNewPreferences();
            return prefs;
        }
    }

    public static void reloadPreferences() {
        //TODO: Requires a reboot until the garbage collection issue is resolved
        XposedBridge.log("Reloading preferences");
        getPreferences().reload();
        XposedBridge.log("PREFERENCES RELOADED");
    }

    private static void makeNewPreferences() {
        prefs = new XSharedPreferences(PackageName.SMARTERBIT.getPackageName());
        prefs.reload();
        prefs.makeWorldReadable();
    }
}
