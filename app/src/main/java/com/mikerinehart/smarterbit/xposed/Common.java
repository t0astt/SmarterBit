package com.mikerinehart.smarterbit.xposed;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.ContactsContract;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findClass;

public class Common {

    static Class FitBitApplication;
    static Class NotificationManager;
    static Class Notification;
    static Class CategoryID;
    static Class NotificationAttributeID;
    static Class DomainA;
    static Class DncsNotificationDisplayType;

    public static void init(XC_LoadPackage.LoadPackageParam lpparam) {
        FitBitApplication = findClass("com.fitbit.FitBitApplication", lpparam.classLoader);
        NotificationManager = findClass("com.fitbit.dncs.NotificationManager", lpparam.classLoader);
        Notification = findClass("com.fitbit.dncs.Notification", lpparam.classLoader);
        CategoryID = findClass("com.fitbit.dncs.domain.CategoryID", lpparam.classLoader);
        NotificationAttributeID = findClass("com.fitbit.dncs.domain.NotificationAttributeId", lpparam.classLoader);
        DomainA = findClass("com.fitbit.dncs.domain.a", lpparam.classLoader);
        DncsNotificationDisplayType = findClass("com.fitbit.dncs.NotificationManager$DncsNotificationDisplayType", lpparam.classLoader);
    }

    /*
     * Returns an instance of FitBitApplication from the FitBit application
     */
    public static Application getFitBitApplication() {
        return (Application) XposedHelpers.callStaticMethod(FitBitApplication, "a");
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
        return "com.fitbit"; // TODO: Replace with enum value!
    }

    public static void addPackageName(Object notification, String packageName) {
        XposedHelpers.callMethod(notification, "a", packageName);
    }

    /*
     * Creates and returns a new Notification object
     */
    public static Object getNotification() {
        return XposedHelpers.newInstance(Notification);
    }

    /*
     * Returns an instance of NotificationManager from the FitBit application
     */
    public static Object getNotificationManager() {
        return XposedHelpers.callStaticMethod(NotificationManager, "a");
    }

    /*
     * Returns an "incoming call" CategoryID object
     */
    public static Object getIncomingCallCategoryId() {
        return XposedHelpers.getStaticObjectField(CategoryID, "b");
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
        return XposedHelpers.getStaticObjectField(NotificationAttributeID, "b");
    }

    /*
     * Returns a "message" NotificationAttributeID object
     */
    public static Object getMessageNotificationAttributeId() {
        return XposedHelpers.getStaticObjectField(NotificationAttributeID, "d");
    }

    /*
     * Creates and returns a DomainA object
     */
    public static Object getDomainA(Object notificationAttributeIdB, String notificationMessage) {
        return XposedHelpers.newInstance(DomainA, notificationAttributeIdB, notificationMessage);
    }

    /*
     * Creates and returns a Temporary DncsNotificationDisplayType object
     */
    public static Object getTemporaryNotificationDisplayType() {
        return XposedHelpers.getStaticObjectField(DncsNotificationDisplayType, "a");
    }

    /*
     * Creates and returns a Permanent DncsNotificationDisplayType object
     */
    public static Object getPermanentNotificationDisplayType()  {
        return XposedHelpers.getStaticObjectField(DncsNotificationDisplayType, "b");
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
