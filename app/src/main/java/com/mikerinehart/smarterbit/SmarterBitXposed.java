package com.mikerinehart.smarterbit;

import com.mikerinehart.smarterbit.xposed.Common;
import com.mikerinehart.smarterbit.xposed.enums.PackageName;
import com.mikerinehart.smarterbit.xposed.hooks.Call;
import com.mikerinehart.smarterbit.xposed.hooks.SMS;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class SmarterBitXposed implements IXposedHookLoadPackage,
        IXposedHookZygoteInit,
        IXposedHookInitPackageResources {

    public static XSharedPreferences prefs;

    public void initZygote(StartupParam sparam) throws Throwable {
        //makeNewPreferences();
    }

    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals("com.fitbit.FitbitMobile")) {
            return;
        }

        final String MODULE_PACKAGE = SmarterBitXposed.class.getPackage().getName();
        XposedBridge.log("SmarterBit test is: " + MODULE_PACKAGE);
        prefs = new XSharedPreferences(MODULE_PACKAGE);
        prefs.makeWorldReadable();

        Common.init(lpparam);
        SMS.initHooks(lpparam);
        Call.initHooks(lpparam);
    }

    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) {
        if (resparam.packageName.equals(PackageName.FITBIT.getPackageName())) {
            resparam.res.setReplacement(PackageName.FITBIT.getPackageName(), "string", "call_notifications", "Enable SmarterBit");
        }
    }
}
