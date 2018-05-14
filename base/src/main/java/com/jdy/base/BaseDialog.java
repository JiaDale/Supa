package com.jdy.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public abstract class BaseDialog extends Dialog implements Action, OnClickListener {
    protected Context mContext;
    private Toast mToast;

    protected BaseDialog(Context context) {
        this(context, R.style.Base_Dialog);
    }

    protected BaseDialog(Context context, int styleId) {
        super(context, styleId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getRootLayoutID() != 0) {
            setContentView(LayoutInflater.from(mContext).inflate(getRootLayoutID(), null));
            initData(savedInstanceState);
            initView();
            bendData();
            setListener();
        }
    }

    @Override
    public void bendData() {

    }

    @Override
    public final <T extends View> T getView(@IdRes int id) {
        if (id == View.NO_ID) {
            return null;
        }
        return findViewById(id);
    }

    @Override
    public void showView(View view) {
        if (view.getVisibility() == View.INVISIBLE)
            view.setVisibility(View.VISIBLE);
    }


    protected void showView(@IdRes int id) {
        showView(getView(id));
    }

    @Override
    public void hiddenView(View view) {
        if (view.getVisibility() == View.VISIBLE)
            view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showToast(String desc) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, desc, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(desc);
        }
        mToast.show();
    }

    protected void setOnClickListener(@IdRes int id) {
        getView(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDialog.this.onClick(BaseDialog.this, v.getId());
            }
        });
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

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
}
