package com.jdy.supa.module.user.forget;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdy.base.BaseActivity;
import com.jdy.base.utils.CheckUtil;
import com.jdy.base.utils.ImageUtil;
import com.jdy.supa.R;
import com.jdy.supa.module.user.UserBean;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetActivity extends BaseActivity {
    @Override
    public int getRootLayoutID() {
        return R.layout.activity_forget;
    }

    @Override
    public void initView() {

    }

    @Override
    public void bendData() {
        ImageView cancel = getView(R.id.toolbar_cancel);
        ImageUtil.loadImage(this, R.drawable.back, cancel);
        showView(cancel);
        setText(R.id.toolbar_title, R.string.forget_password);
    }

    @Override
    public void setListener() {
        setOnClickListener(R.id.forget_action);
        setOnClickListener(R.id.toolbar_cancel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_action:
                final String email = ((TextView) getView(R.id.forget_email)).getText().toString().trim();
                if (!TextUtils.isEmpty(email) && CheckUtil.isEmail(email)) {
                    UserBean.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                showToast("信息已发送至您的邮箱 请前往验证" + email);
                                finish();
                            } else {
                                showToast("密码修改失败！");
                            }
                        }
                    });
                } else {
                    showToast("email 为空或格式不对");
                }
                break;
            case R.id.toolbar_cancel:
                finish();
                break;
        }
    }
}
