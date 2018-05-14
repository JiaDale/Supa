package com.jdy.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.jdy.base.utils.ResourceUtil;

/**
 * Created by Dale on 2017/8/30.
 * 项目名：Supa
 * 描述：所有Activity的基类
 * 统一属性、统一接口、统一方法
 */
public abstract class BaseActivity extends AppCompatActivity implements Action, OnClickListener {
    private Toast mToast;
    protected boolean hasTitle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInit();
        if (getRootLayoutID() != 0) {
            setContentView(getRootLayoutID());
            initData(savedInstanceState);
            initView();
            bendData();
            setListener();
        }
    }

    protected void beforeInit() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void bendData() {

    }

    @Override
    public void setListener() {

    }


    @Override
    public void showToast(String desc) {
        if (mToast == null) {
            mToast = Toast.makeText(this.getApplicationContext(), desc, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(desc);
        }
        mToast.show();
    }

    @Override
    public void showView(View view) {
        if (view.getVisibility() == View.INVISIBLE)
            view.setVisibility(View.VISIBLE);
    }

    @Override
    public void hiddenView(View view) {
        if (view.getVisibility() == View.VISIBLE)
            view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public <T extends View> T getView(int id) {
        if (id == View.NO_ID) {
            return null;
        }
        return findViewById(id);
    }


    public void setText(int id, int resID) {
        setText(id, ResourceUtil.getString(resID));
    }

    @Override
    public void setText(int id, String text) {
        TextView tv = getView(id);
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        } else {
            tv.setText("");
        }
    }

    protected void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    protected void setOnClickListener(@IdRes int id) {
        getView(id).setOnClickListener(this);
    }
}
