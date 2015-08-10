package com.mikerinehart.smarterbit.xposed.enums;

public enum ClassName {

    CATEGORY_ID("com.fitbit.dncs.domain.CategoryID"),
    DNCS_NOTIFICATION_DISPLAY_TYPE("com.fitbit.dncs.NotificationManager$DncsNotificationDisplayType"),
    DOMAIN_A("com.fitbit.dncs.domain.a"),
    FITBIT_APPLICATION("com.fitbit.FitBitApplication"),
    NOTIFICATION_MANAGER("com.fitbit.dncs.NotificationManager"),
    NOTIFICATION("com.fitbit.dncs.Notification"),
    NOTIFICATION_ATTRIBUTE_ID("com.fitbit.dncs.domain.NotificationAttributeId"),
    OBSERVERS_A("com.fitbit.dncs.observers.a"),
    SMS_OBSERVER("com.fitbit.dncs.observers.sms.SmsObserver");

    private String className;

    ClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return this.className;
    }

}
