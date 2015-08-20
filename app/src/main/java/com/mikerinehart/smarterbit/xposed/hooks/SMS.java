package com.mikerinehart.smarterbit.xposed.hooks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.mikerinehart.smarterbit.SmarterBitXposed;
import com.mikerinehart.smarterbit.generic.DeviceUtils;
import com.mikerinehart.smarterbit.xposed.Common;
import com.mikerinehart.smarterbit.xposed.enums.FitBitClasses;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class SMS {

    public static void initHooks(XC_LoadPackage.LoadPackageParam lpparam) {

        //SMS Hook
        XposedHelpers.findAndHookMethod(FitBitClasses.SMS_OBSERVER.getInstance(lpparam).getCanonicalName(), lpparam.classLoader, "a", Context.class, Intent.class,
                new XC_MethodHook() {
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("SMS Received!");
                        SmarterBitXposed.prefs.reload();

                        SmsMessage sms = getReceivedSMS(param);
                        String sender = sms.getOriginatingAddress();
                        String body = sms.getMessageBody();

                        // SMS Enabled?
                        if (isSMSNotificationEnabled()) {
                            XposedBridge.log("SMS Notifications Enabled");
                            //Send Notification Only if the Screen is Off?
                            if (isNotifyOnlyIfScreenOffEnabled()) {
                                if (DeviceUtils.isScreenOn() == false) {
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
        byte[] smsPdu;
        try {
            smsPdu = (byte[]) pdus[0];
        } catch(NullPointerException e) {
            smsPdu = new byte[0];
        }

        return SmsMessage.createFromPdu(smsPdu);
    }

    /*
     * Checks if user has SMS notifications enabled
     */
    private static boolean isSMSNotificationEnabled() {
        boolean status = SmarterBitXposed.prefs.getBoolean("smsEnabled", false);
        if (status) {
            XposedBridge.log("SMS Status: Enabled");
        } else XposedBridge.log("SMS Status: False");
        return status;
    }

    /*
     * Checks if user has screen off notifications enabled
     */
    private static boolean isNotifyOnlyIfScreenOffEnabled() {
        boolean status = SmarterBitXposed.prefs.getBoolean("smsScreenOffOnly", false);
        if (status) {
            XposedBridge.log("SMS Screen off only: Enabled");
        } else XposedBridge.log("SMS Screen off only: False");
        return status;
    }

    /*
     * Reads a custom notification format set by the user
     */
    private static String formatMessage(String sender, String message) {
        String messageFormat =
                SmarterBitXposed.prefs.getString("smsNotificationFormat", "msg from %sender%: %message%");
            messageFormat = messageFormat
                    .replaceFirst("%sender%", sender)
                    .replaceFirst("%message%", message);
            return messageFormat;
    }
}
