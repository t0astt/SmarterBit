package com.mikerinehart.smarterbit.xposed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.mikerinehart.smarterbit.generic.Utils;

import de.robv.android.xposed.XC_MethodHook;
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

    public static void initHooks(XC_LoadPackage.LoadPackageParam lpparam) {

        SmsObserver = findClass(ClassName.SMS_OBSERVER.getClassName(), lpparam.classLoader);
        CategoryID = findClass(ClassName.CATEGORY_ID.getClassName(), lpparam.classLoader);
        FitBitApplication = findClass(ClassName.FITBIT_APPLICATION.getClassName(), lpparam.classLoader);
        ObserversA = findClass(ClassName.OBSERVERS_A.getClassName(), lpparam.classLoader);
        DomainA = findClass(ClassName.DOMAIN_A.getClassName(), lpparam.classLoader);
        NotificationAttributeID = findClass(ClassName.NOTIFICATION_ATTRIBUTE_ID.getClassName(), lpparam.classLoader);

        //SMS Hook
        XposedHelpers.findAndHookMethod(SmsObserver.getCanonicalName(), lpparam.classLoader, "a", Context.class, Intent.class,
                new XC_MethodHook() {
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("SMS Received!");
                        SmsMessage sms = getReceivedSMS(param);
                        String sender = sms.getOriginatingAddress();
                        String body = sms.getMessageBody();

                        // SMS Enabled?
                        if (SMS.isSMSNotificationsEnabled()) {
                            XposedBridge.log("SMS Notifications Enabled");
                            //Send Notification Only if the Screen is Off?
                            if (SMS.isNotifyOnlyIfScreenOffEnabled()) {
                                if (Utils.isScreenOn() == false) {
                                    XposedBridge.log("Screen is off and setting is enabled");
                                    //Option is enabled and screen is off, send
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
        sender = Common.matchContact(sender);
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
     * Checks if user has SMS notifications enabled
     */
    private static boolean isSMSNotificationsEnabled() {
        boolean status = SmarterBitXposed.getPreferences().getBoolean("smsEnabled", false);
        if (status) {
            XposedBridge.log("SMS Status: Enabled");
        } else XposedBridge.log("SMS Status: False");
        return SmarterBitXposed.getPreferences().getBoolean("smsEnabled", false);
    }

    /*
     * Checks if user has screen off notifications enabled
     */
    private static boolean isNotifyOnlyIfScreenOffEnabled() {
        boolean status = SmarterBitXposed.getPreferences().getBoolean("smsScreenOffOnly", false);
        if (status) {
            XposedBridge.log("SMS Screen off only: Enabled");
        } else XposedBridge.log("SMS Screen off only: False");
        return SmarterBitXposed.getPreferences().getBoolean("smsScreenOffOnly", false);
    }

    /*
     * Reads a custom notification format set by the user
     */
    private static String formatMessage(String sender, String message) {
            String messageFormat = SmarterBitXposed
                    .getPreferences()
                    .getString("smsNotificationFormat", "msg from %sender%: %message%");
            messageFormat = messageFormat
                    .replaceFirst("%sender%", sender)
                    .replaceFirst("%message%", message);
            return messageFormat;
    }

//    public static void sendTestNotification() {
//        String fitbitPackageName = Common.getFitBitPackageName();
//        Object notification = Common.getNotification();
//        Object categoryID = Common.getIncomingCallCategoryId();
//
//        Common.addCategoryID(notification, categoryID);
//
//        Common.addPackageName(notification, fitbitPackageName);
//
//        // Create Notification Message
//        Object notificationAttributeIdB = Common.getTitleNotificationAttributeId();
//        Object titleDomain = Common.getDomainA(notificationAttributeIdB, "I am a test!");
//        Common.addDomainA(notification, titleDomain);
//        // End Create Notification Message
//
//        Object displayTemporary = Common.getTemporaryNotificationDisplayType();
//
//        Common.sendToFitBit(notification, displayTemporary);
//    }

}
