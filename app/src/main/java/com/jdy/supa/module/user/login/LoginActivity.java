package com.jdy.supa.module.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jdy.base.BaseActivity;
import com.jdy.base.utils.L;
import com.jdy.base.utils.SharedPreferencesUtil;
import com.jdy.supa.R;
import com.jdy.supa.module.main.MainActivity;
import com.jdy.supa.module.user.UserBean;
import com.jdy.supa.module.user.forget.ForgetActivity;
import com.jdy.supa.module.user.register.RegisterActivity;
import com.jdy.supa.utils.StaticClass;
import com.jdy.supa.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {
    private EditText mAccount, mPass;
    private Button mAction;
    private RadioButton remember;
    private TextView mTestView, registerView, forgetView;
    private CustomDialog dialog;

    @Override
    public int getRootLayoutID() {
        return R.layout.activity_login;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    public void initView() {
        mAccount = getView(R.id.login_account);
        mPass = getView(R.id.login_pass);
        mAction = getView(R.id.login_action);
        mTestView = getView(R.id.login_test_view);
        registerView = getView(R.id.login_register);
        forgetView = getView(R.id.login_forget);
        remember = getView(R.id.login_remember);
    }

    @Override
    public void bendData() {
        boolean isKeep = SharedPreferencesUtil.getBoolean(StaticClass.KEEP_PASS, false);
        remember.setChecked(isKeep);
        if (isKeep) {
            String name = SharedPreferencesUtil.getString(StaticClass.ACCOUNT, "");
            String password = SharedPreferencesUtil.getString(StaticClass.PASS, "");
            mAccount.setText(name);
            mPass.setText(password);
        }
        dialog = new CustomDialog(this, 500, 500, R.layout.dialog_loding, R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        dialog.setCancelable(false);
    }

    @Override
    public void setListener() {
        mAction.setOnClickListener(this);
        registerView.setOnClickListener(this);
        forgetView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_action:
                onLogin();
                break;
            case R.id.login_register:
                startActivity(RegisterActivity.class);
                break;

            case R.id.login_forget:
                startActivity(ForgetActivity.class);
                break;
        }
    }

    private void onLogin() {
        final String account = mAccount.getText().toString().trim();
        String password = mPass.getText().toString().trim();
        if (TextUtils.isEmpty(account))
            showToast("请输入账号!");
        else if (TextUtils.isEmpty(password))
            showToast("请输入密码!");
        else {
            dialog.show();
            UserBean user = new UserBean();
            user.setUsername(account);
            user.setPassword(password);
            user.login(new SaveListener<UserBean>() {
                @Override
                public void done(UserBean loginEntity, BmobException e) {
                    dialog.dismiss();
                    if (e == null){
                        if (loginEntity.getEmailVerified()){
                            //跳转
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            showToast("请前往邮箱验证");
                            return;
                        }
                    } else {
                        L.i(e.toString());
                        showToast(e.toString());
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存状态
        SharedPreferencesUtil.putBoolean(StaticClass.KEEP_PASS, remember.isChecked());
        //是否记住密码
        if (remember.isChecked()) {
            //记住用户名和密码
            SharedPreferencesUtil.putString(StaticClass.ACCOUNT, mAccount.getText().toString().trim());
            SharedPreferencesUtil.putString(StaticClass.PASS, mPass.getText().toString().trim());
        } else {
            SharedPreferencesUtil.deleShare(StaticClass.ACCOUNT);
            SharedPreferencesUtil.deleShare(StaticClass.PASS);
        }
    }
}
