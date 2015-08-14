package com.mikerinehart.smarterbit.xposed.enums;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findClass;

public enum FitBitClasses {

    CATEGORY_ID("com.fitbit.dncs.domain.CategoryID"),
    DNCS_NOTIFICATION_DISPLAY_TYPE("com.fitbit.dncs.NotificationManager$DncsNotificationDisplayType"),
    DOMAIN_A("com.fitbit.dncs.domain.a"),
    FITBIT_APPLICATION("com.fitbit.FitBitApplication"),
    NOTIFICATION_MANAGER("com.fitbit.dncs.NotificationManager"),
    NOTIFICATION("com.fitbit.dncs.Notification"),
    NOTIFICATION_ATTRIBUTE_ID("com.fitbit.dncs.domain.NotificationAttributeId"),
    OBSERVERS_A("com.fitbit.dncs.observers.a"),
    PHONE_CALL_OBSERVER("com.fitbit.dncs.observers.phone.PhoneCallObserver"),
    SMS_OBSERVER("com.fitbit.dncs.observers.sms.SmsObserver");

    private Class clazz;
    private String className;

    FitBitClasses(String className) {
        this.className = className;
    }

    public Class getInstance(XC_LoadPackage.LoadPackageParam lpparam) {
        if (this.clazz != null) {
            return this.clazz;
        } else {
            this.clazz = findClass(getClassName(), lpparam.classLoader);
            return this.clazz;
        }
    }

    public String getClassName() {
        return this.className;
    }

}
