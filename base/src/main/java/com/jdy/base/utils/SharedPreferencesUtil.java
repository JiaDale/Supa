package com.jdy.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    public static final String NAME = "SharedPreferences";
    private static SharedPreferences preferences;

    public static void  init(Context context){
        init(context, NAME);
    }

    public static void  init(Context context, String name){
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static boolean putString(String key, String value) {
        return preferences.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defvalue) {
        return preferences.getString(key, defvalue);
    }

    public static boolean putInt(String key, int value) {
        return preferences.edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defvalue) {
        return preferences.getInt(key, defvalue);
    }

    public static boolean putLong(String key, long value) {
        return preferences.edit().putLong(key, value).commit();
    }
    public static long getLong(String key, long defvalue) {
        return preferences.getLong(key, defvalue);
    }

    public static boolean putBoolean(String key, boolean value) {
        return preferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defvalue) {
        return preferences.getBoolean(key, defvalue);
    }

    public static boolean deleShare(String key) {
        return preferences.edit().remove(key).commit();
    }

    public static boolean deleAll() {
        return preferences.edit().clear().commit();
    }
}
