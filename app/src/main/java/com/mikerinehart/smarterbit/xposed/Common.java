package com.mikerinehart.smarterbit.xposed;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.mikerinehart.smarterbit.xposed.enums.ClassUtils;
import com.mikerinehart.smarterbit.xposed.enums.PackageName;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Common {

    static XC_LoadPackage.LoadPackageParam lpparam;

    public static void init(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        lpparam = loadPackageParam;
    }

    /*
     * Returns an instance of FitBitApplication from the FitBit application
     */
    public static Application getFitBitApplication() {
        return (Application) XposedHelpers.callStaticMethod(ClassUtils.FITBIT_APPLICATION.getInstance(lpparam), "a");
    }

    /*
     * Returns a Context object from the FitBit application
     */
    public static Context getContext() {
        return getFitBitApplication().getApplicationContext();
    }

    /*
     * Returns the FitBit package name
     */
    public static String getFitBitPackageName() {
        return PackageName.FITBIT.getPackageName(); // TODO: Replace with enum value!
    }

    public static void addPackageName(Object notification, String packageName) {
        XposedHelpers.callMethod(notification, "a", packageName);
    }

    /*
     * Creates and returns a new Notification object
     */
    public static Object getNotification() {
        return XposedHelpers.newInstance(ClassUtils.NOTIFICATION.getInstance(lpparam));
    }

    /*
     * Returns an instance of NotificationManager from the FitBit application
     */
    public static Object getNotificationManager() {
        return XposedHelpers.callStaticMethod(ClassUtils.NOTIFICATION_MANAGER.getInstance(lpparam), "a");
    }

    /*
     * Returns an "incoming call" CategoryID object
     */
    public static Object getIncomingCallCategoryId() {
        return XposedHelpers.getStaticObjectField(ClassUtils.CATEGORY_ID.getInstance(lpparam), "b");
    }

    /*
     * Adds a CategoryID object to a Notification object
     */
    public static void addCategoryID(Object notification, Object categoryID) {
        XposedHelpers.callMethod(notification, "a", categoryID);
    }

    /*
     * Adds a DomainA to a Notification object
     */
    public static void addDomainA(Object notification, Object domainA) {
        XposedHelpers.callMethod(notification, "a", domainA);
    }

    /*
     * Returns a "title" NotificationAttributeID object
     */
    public static Object getTitleNotificationAttributeId() {
        return XposedHelpers.getStaticObjectField(ClassUtils.NOTIFICATION_ATTRIBUTE_ID.getInstance(lpparam), "b");
    }

    /*
     * Returns a "message" NotificationAttributeID object
     */
    public static Object getMessageNotificationAttributeId() {
        return XposedHelpers.getStaticObjectField(ClassUtils.NOTIFICATION_ATTRIBUTE_ID.getInstance(lpparam), "d");
    }

    /*
     * Creates and returns a DomainA object
     */
    public static Object getDomainA(Object notificationAttributeIdB, String notificationMessage) {
        return XposedHelpers.newInstance(ClassUtils.DOMAIN_A.getInstance(lpparam), notificationAttributeIdB, notificationMessage);
    }

    /*
     * Creates and returns a Temporary DncsNotificationDisplayType object
     */
    public static Object getTemporaryNotificationDisplayType() {
        return XposedHelpers.getStaticObjectField(ClassUtils.DNCS_NOTIFICATION_DISPLAY_TYPE.getInstance(lpparam), "a");
    }

    /*
     * Creates and returns a Permanent DncsNotificationDisplayType object
     */
    public static Object getPermanentNotificationDisplayType()  {
        return XposedHelpers.getStaticObjectField(ClassUtils.DNCS_NOTIFICATION_DISPLAY_TYPE.getInstance(lpparam), "b");
    }

    /*
     * Adds a Notification to the NotificationManager to be sent to the FitBit
     */
    public static void sendToFitBit(Object notification, Object displayDuration) {
        XposedHelpers.callMethod(getNotificationManager(), "a", notification, displayDuration);
    }

    /*
     * Matches a phone number with a phone contact
     */
    public static String matchContact(String sender) {
        Uri phoneUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(sender));
        Cursor phonesCursor = Common.getContext()
                .getApplicationContext()
                .getContentResolver()
                .query(phoneUri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

        if(phonesCursor != null && phonesCursor.moveToFirst()) {
            String matchedSender = phonesCursor.getString(0); // this is the contact name
            return matchedSender;
        } else {
            return sender;
        }
    }

}
