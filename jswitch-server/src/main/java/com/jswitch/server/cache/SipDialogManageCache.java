package com.jswitch.server.cache;

import com.jswitch.sip.SipDialog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author danmo
 * @date 2024-06-28 13:47
 **/
public class SipDialogManageCache {

    private static final Map<String, SipDialog> sipDialaMap = new ConcurrentHashMap<>();

    public static void saveSipDialog(SipDialog sipDialog) {
        sipDialaMap.put(sipDialog.getDialogId(), sipDialog);
    }

    public static SipDialog getSipDialog(String dialogId) {
        return sipDialaMap.get(dialogId);
    }

    public static void removeSipDialog(String dialogId) {
        sipDialaMap.remove(dialogId);
    }

    public static Boolean isExist(String dialogId) {
        return sipDialaMap.containsKey(dialogId);
    }

    public static void clear() {
        sipDialaMap.clear();
    }
}
