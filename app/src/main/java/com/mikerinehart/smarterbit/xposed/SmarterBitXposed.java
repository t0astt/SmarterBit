package com.mikerinehart.smarterbit.xposed;

import android.content.Context;
import android.content.Intent;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XC_MethodHook;

import static de.robv.android.xposed.XposedHelpers.findClass;

public class SmarterBitXposed implements IXposedHookLoadPackage {

    Class SmsObserver;
    Class PhoneCallObserver;

    private static XC_LoadPackage.LoadPackageParam lpparam;

    final private String FITBIT = "com.fitbit.FitbitMobile";

    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        this.lpparam = lpparam;
        if(lpparam.packageName.equals(FITBIT)) {
            Common.init(lpparam);
            SMS.initHooks(lpparam);
        }
    }
}
