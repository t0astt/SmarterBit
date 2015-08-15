package com.mikerinehart.smarterbit.generic;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.PowerManager;

import com.mikerinehart.smarterbit.xposed.Common;

import de.robv.android.xposed.XposedBridge;

public class DeviceUtils {

    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    @TargetApi(20)
    @SuppressWarnings("deprecation")
    public static boolean isScreenOn() {
        if (getSDKVersion() >= 20) {
            XposedBridge.log("Power Service via Interactive");
            boolean status = ((PowerManager)Common.getContext().getSystemService(Context.POWER_SERVICE)).isInteractive();
            if (status) XposedBridge.log("Power service returning true"); else XposedBridge.log("Pwr return false");
            return status;
        } else {
            XposedBridge.log("Power service via ScreenOn (deprecated)");
            boolean status = ((PowerManager)Common.getContext().getSystemService(Context.POWER_SERVICE)).isScreenOn();
            if (status) XposedBridge.log("Power service returning true"); else XposedBridge.log("Pwr return false");
            return status;
        }
    }

    public static boolean isDeviceSilent() {
        AudioManager am =  ((AudioManager)Common.getContext().getSystemService(Context.AUDIO_SERVICE));
        return am.getRingerMode() == AudioManager.RINGER_MODE_SILENT;
    }

}
