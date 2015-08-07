package com.mikerinehart.smarterbit.xposed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findClass;

public class SMS {

    static Class SmsObserver;
    static Class CategoryID;
    static Class FitBitApplication;
    static Class ObserversA;
    static Class DomainA;
    static Class NotificationAttributeID;

    private static boolean log = true;

    public static void initHooks(XC_LoadPackage.LoadPackageParam lpparam) {

        SmsObserver = findClass("com.fitbit.dncs.observers.sms.SmsObserver", lpparam.classLoader);
        CategoryID = findClass("com.fitbit.dncs.domain.CategoryID", lpparam.classLoader);
        FitBitApplication = findClass("com.fitbit.FitBitApplication", lpparam.classLoader);
        ObserversA = findClass("com.fitbit.dncs.observers.a", lpparam.classLoader);
        DomainA = findClass("com.fitbit.dncs.domain.a", lpparam.classLoader);
        NotificationAttributeID = findClass("com.fitbit.dncs.domain.NotificationAttributeId", lpparam.classLoader);

        //SMS Hook
        XposedHelpers.findAndHookMethod(SmsObserver.getCanonicalName(), lpparam.classLoader, "a", Context.class, Intent.class,
                new XC_MethodHook() {
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        SmsMessage sms = getReceivedSMS(param);
                        String sender = getSmsSender(sms);
                        String body = getSmsBody(sms);

                        // SMS Enabled?
                        if (SMS.isSMSNotificationsEnabled()) {
                            sendSMSNotification(sender, body);

                            //Send Notification Only if the Screen is Off?
                            if (SMS.isNotifyOnlyIfScreenOffEnabled()) {
                                if (Common.isScreenOff()) {
                                    //Option is enabled and screen is off, send
                                    sendSMSNotification(sender, body);
                                } else {
                                    // Option is enabled but screen is not off, don't send notification
                                    sendSMSNotification(sender, body);
                                }
                            } else {
                                //Option is not enabled, send Notification regardless
                                sendSMSNotification(sender, body);
                            }
                        }
                    }
                });
    }

    public static void sendSMSNotification(String sender, String message) {
        String notificationMessage;

        String fitbitPackageName = Common.getFitBitPackageName();
        Object notification = Common.getNotification();
        Object categoryID = Common.getIncomingCallCategoryId();

        Common.addCategoryID(notification, categoryID);

        Common.addPackageName(notification, fitbitPackageName);

        // Create Notification Message
        sender = matchContact(sender);
        notificationMessage = formatMessage(sender, message);
        Object notificationAttributeIdB = Common.getTitleNotificationAttributeId();
        Object titleDomain = Common.getDomainA(notificationAttributeIdB, notificationMessage);
        Common.addDomainA(notification, titleDomain);
        // End Create Notification Message

        Object displayTemporary = Common.getTemporaryNotificationDisplayType();

        Common.sendToFitBit(notification, displayTemporary);
    }

    /*
     * Receives an intent carrying a PDU payload and creates an SMS from it
     */
    private static SmsMessage getReceivedSMS(XC_MethodHook.MethodHookParam param) {
        Intent intent = (Intent) param.args[1];

        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");
        byte[] smsByte = (byte[]) pdus[0];

        return SmsMessage.createFromPdu(smsByte);
    }

    /*
     * Receives an SMS and returns the sender
     */
    private static String getSmsSender(SmsMessage sms) {
        return sms.getOriginatingAddress();
    }

    /*
     * Receives an SMS and returns the body
     */
    private static String getSmsBody(SmsMessage sms) {
        return sms.getMessageBody();
    }

    public static boolean isSMSNotificationsEnabled() {

        return true;
    }

    public static boolean isNotifyOnlyIfScreenOffEnabled() {

        return true;
    }

    public static String formatMessage(String sender, String message) {

        if (true) { // If custom notification format exists, replace placeholders
            String placeHolderUntilNotLazy = "%sender% : %message%";
            placeHolderUntilNotLazy = placeHolderUntilNotLazy
                    .replaceFirst("%sender%", sender)
                    .replaceFirst("%message%", message);
            return placeHolderUntilNotLazy;
        } else {
            return "msg from " + sender + ": " + message;
        }
    }

    public static void sendTestNotification() {
        String fitbitPackageName = Common.getFitBitPackageName();
        Object notification = Common.getNotification();
        Object categoryID = Common.getIncomingCallCategoryId();

        Common.addCategoryID(notification, categoryID);

        Common.addPackageName(notification, fitbitPackageName);

        // Create Notification Message
        Object notificationAttributeIdB = Common.getTitleNotificationAttributeId();
        Object titleDomain = Common.getDomainA(notificationAttributeIdB, "I am a test!");
        Common.addDomainA(notification, titleDomain);
        // End Create Notification Message

        Object displayTemporary = Common.getTemporaryNotificationDisplayType();

        Common.sendToFitBit(notification, displayTemporary);
    }

    private static String matchContact(String sender) {
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
