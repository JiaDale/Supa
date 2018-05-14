package com.jdy.base.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class UtilTools {
    private static Context context;

    private UtilTools() {
        throw new UnsupportedOperationException("You can't instantiate me...");
    }

    public static void init(Context context) {
        UtilTools.context = context.getApplicationContext();
    }

    public static Context getContext() {
        if (context == null)
            throw new NullPointerException("You should init first");
        return context;
    }


    public static void setFont(TextView textview) {
        setFont(getContext(), textview);
    }

    public static void setFont(Context context, TextView textview) {
        Typeface fontType = Typeface.createFromAsset(context.getAssets(), "fonts/SIMLI.TTF");
        textview.setTypeface(fontType);
    }

    public static void setFont(Context context, TextView textview, String path) {
        Typeface fontType = Typeface.createFromAsset(context.getAssets(), path);
        textview.setTypeface(fontType);
    }
}
