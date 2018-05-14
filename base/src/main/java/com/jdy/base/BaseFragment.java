package com.jdy.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Dale on 2017/9/25.
 * 项目名：Supa
 * 描述：Fragment 的基类
 */
public abstract class BaseFragment extends Fragment implements Action, OnClickListener {
    protected View view;
    private Toast mToast;
    protected boolean hasTitle = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getRootLayoutID() != 0) {
            view = inflater.inflate(getRootLayoutID(), container, false);
            return view;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(savedInstanceState);
        initView();
        bendData();
        setListener();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void bendData() {

    }

    @Override
    public void setText(@IdRes int id, String text) {
        TextView tv = getView(id);
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        } else {
            tv.setText("");
        }
    }

    @Override
    public final <T extends View> T getView(@IdRes int id) {
        if (id == View.NO_ID) {
            return null;
        }
        return view.findViewById(id);
    }

    @Override
    public void showView(View view) {
        if (view.getVisibility() == View.INVISIBLE)
            view.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToast(String desc) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity().getApplicationContext(), desc, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(desc);
        }
        mToast.show();
    }

    @Override
    public void hiddenView(View view) {
        if (view.getVisibility() == View.VISIBLE)
            view.setVisibility(View.INVISIBLE);
    }

    protected void startActivity(Class<?> cls) {
        startActivity(new Intent(getActivity(), cls));
    }

    protected void setOnClickListener(@IdRes int id){
        getView(id).setOnClickListener(this);
    }
}
