package com.jdy.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;

public class ResourceUtil {
    // R文件的对象
    private static Resources resources;
    private static String pkgName;
    private static Theme mTheme;


    public static void init(Context context) {
        mTheme = context.getTheme();
        pkgName = context.getPackageName();
        resources = context.getResources();
    }

    public static int checkExistID(int id) {
        return checkExistID(id, "Couldn't find the ID that for the View!");
    }

    public static int checkExistID(int id, String message) {
        if (id == View.NO_ID) {
            throw new NullPointerException(message);
        }
        return id;
    }

    public static String getString(int resId) {
        return checkResourceNotNull(resources).getString(resId);
    }

    public static int getColor(int resId) {
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            return checkResourceNotNull(resources).getColor(resId, mTheme);
        } else
            return checkResourceNotNull(resources).getColor(resId);
    }

    public static float getDimen(int resId) {
        return checkResourceNotNull(resources).getDimension(resId);
    }

    public static Drawable getDrawable(int resId) {
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            return checkResourceNotNull(resources).getDrawable(resId, mTheme);
        } else {
            return checkResourceNotNull(resources).getDrawable(resId);
        }
    }

    public static Theme getTheme() {
        return mTheme;
    }

    private static Resources checkResourceNotNull(Resources resources) {
        if (resources == null)
            throw new NullPointerException("You should init the util before!");
        return resources;
    }
}
