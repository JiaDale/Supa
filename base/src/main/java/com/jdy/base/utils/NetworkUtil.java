package com.jdy.base.utils;

import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetworkUtil {
    private static ConnectivityManager connManager = null;

    /**
     * 网络是否已经连接
     * @return true : 可用; false : 不可;
     */
    public static boolean isNetworkConnected(Context context) {
        if (isNetworkAvailable(context)) {
            int type = getConnectionType(context);
            if (type == 0 || type == 1) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 获取当前网络连接的类型信
     *
     * @return one of TYPE_MOBILE, TYPE_WIFI, TYPE_WIMAX, TYPE_ETHERNET,
     *         TYPE_BLUETOOTH, or other types defined by ConnectivityManager
     *         int值分别为
     */
    public static int getConnectionType(Context context) {
        if (context != null) {
            NetworkInfo networkInfo = getAcitiveNetworkType(context);
            if (networkInfo != null && networkInfo.isAvailable()) {
                return networkInfo.getType();
            }
        }
        return -1;
    }


    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }


    /**
     * 对网络连接是否可?
     * @return true : 可用; false : 不可;
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            NetworkInfo networkInfo =  getAcitiveNetworkType(context);
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 对wifi连接状进行判断
     * @return true : 可用; false : 不可;
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            NetworkInfo wifiInfo = getAcitiveNetworkType(context, ConnectivityManager.TYPE_WIFI);
            if (wifiInfo != null) {
                return wifiInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 对MOBILE网络连接状进行判断
     * @return true : 可用; false : 不可;
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            NetworkInfo mobileInfo = getAcitiveNetworkType(context, ConnectivityManager.TYPE_MOBILE);
            if (mobileInfo != null) {
                return mobileInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 提示设置网络连接
     */
    public static void alertSetNetwork(final Context context) {
        Builder builder = new Builder(context);
        builder.setTitle("提示：网络异常").setMessage("是否对网络进行设置？");
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                try {
                    int sdkVersion = android.os.Build.VERSION.SDK_INT;
                    if (sdkVersion > 10) {
                        intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    } else {
                        intent = new Intent();
                        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                        intent.setComponent(comp);
                        intent.setAction("android.intent.action.VIEW");
                    }
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    public static void destroy() {
        if (connManager != null) {
            connManager = null;
        }
    }

    private static NetworkInfo getAcitiveNetworkType(Context context) {
        return getAcitiveNetworkType(context, -1);
    }

    private static NetworkInfo getAcitiveNetworkType(Context context, int networkType) {
        connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return networkType == -1 ? connManager.getActiveNetworkInfo() :  connManager.getNetworkInfo(networkType);
    }
}
