package com.jdy.supa;

import android.app.Application;

import com.jdy.base.utils.ResourceUtil;
import com.jdy.base.utils.SharedPreferencesUtil;
import com.jdy.base.utils.UtilTools;
import com.jdy.supa.utils.StaticClass;

import cn.bmob.v3.Bmob;

public class App extends Application{
    private static App INSTANCE;

    public static synchronized App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
        UtilTools.init(this);
        ResourceUtil.init(this);
        SharedPreferencesUtil.init(this);
    }
}
