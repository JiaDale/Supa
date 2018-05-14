package com.jdy.supa.module.user.register;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jdy.base.BaseActivity;
import com.jdy.base.utils.CheckUtil;
import com.jdy.base.utils.DateUtil;
import com.jdy.base.utils.ImageUtil;
import com.jdy.base.utils.L;
import com.jdy.datepicker.CustomDatePicker;
import com.jdy.supa.R;
import com.jdy.supa.module.main.MainActivity;
import com.jdy.supa.module.user.UserBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class RegisterActivity extends BaseActivity {
    private ViewPager mViewPager;
    private List<View> mList = new ArrayList<>();
    private int currentPosition = 0, sex;
    private View userRegister, loginRegister;
    private String account, password, name, phone, email, num, address;
    private Date birth;
    private CustomDatePicker customDatePicker;

    @Override
    public int getRootLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        userRegister = View.inflate(this, R.layout.view_user, null);
        loginRegister = View.inflate(this, R.layout.view_login, null);
        mList.add(userRegister);
        mList.add(loginRegister);
    }

    @Override
    public void initView() {
        mViewPager = getView(R.id.register_viewpager);
        //预加载
        mViewPager.setOffscreenPageLimit(mList.size());
        mViewPager.setAdapter(new RegisterAdapter());
    }

    @Override
    public void bendData() {
        mViewPager.setCurrentItem(currentPosition);
        ImageView cancel = getView(R.id.toolbar_cancel);
        ImageUtil.loadImage(this, R.drawable.back, cancel);
        showView(cancel);
        if (currentPosition == 0) {
            setText(R.id.toolbar_title, "注册");
        }
        ((TextView) userRegister.findViewById(R.id.register_user_birth)).setText(DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                ((TextView) userRegister.findViewById(R.id.register_user_birth)).setText(time.split(" ")[0]);
            }
        }, "1980-01-01 00:00", DateUtil.dateFormat(new Date(),"yyyy-MM-dd HH:mm"));  // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(false); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }

    @Override
    public void setListener() {
        setOnClickListener(R.id.register_action);
        setOnClickListener(R.id.toolbar_cancel);
        userRegister.findViewById(R.id.register_user_birth).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_action:
                if (checkEdit())
                    if (currentPosition == mList.size() - 1) {
                        onRegister();
                    } else {
                        mViewPager.setCurrentItem(++currentPosition);
                    }
                break;
            case R.id.toolbar_cancel:
                if (currentPosition == 0)
                    finish();
                else {
                    mViewPager.setCurrentItem(--currentPosition);
                }
                break;

            case R.id.register_user_birth:
                customDatePicker.show();
                break;
        }
    }

    private boolean checkEdit() {
        switch (currentPosition) {
            case 0:
                return checkUser();
            case 1:
                return checkLogin();
        }
        return false;
    }

    private boolean checkLogin() {
        account = ((TextView) loginRegister.findViewById(R.id.register_login_name)).getText().toString().trim();
        password = ((TextView) loginRegister.findViewById(R.id.register_login_pass)).getText().toString().trim();
        String confirm = ((TextView) loginRegister.findViewById(R.id.register_login_confirm)).getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            showToast("用户名不能为空");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            showToast("密码不能为空");
            return false;
        }

        if (TextUtils.isEmpty(confirm)) {
            showToast("确认密码不能为空");
            return false;
        } else if (!password.equals(confirm)) {
            showToast("两次密码输入不一致");
            return false;
        }

        return true;
    }

    private void onRegister() {
        final UserBean user = new UserBean();
        user.setUsername(account);
        user.setPassword(password);
        user.setEmail(email);
        user.setMobilePhoneNumber(phone);
        user.setSex(sex);
        user.setBirth(birth);
        user.setUserType(0);
        user.setNum(num);
        user.setAddress(address);
        user.signUp(new SaveListener<UserBean>() {
            @Override
            public void done(UserBean loginEntity, BmobException e) {
                if (e == null) {
                    startActivity(MainActivity.class);
                    finish();
                    if (loginEntity.getEmailVerified())
                        showToast("请前往邮箱验证");
                } else {
                    showToast(e.toString());
                    finish();
                }
            }
        });
    }

    private boolean checkUser() {
        name = ((TextView) userRegister.findViewById(R.id.register_user_name)).getText().toString().trim();
        phone = ((TextView) userRegister.findViewById(R.id.register_user_phone)).getText().toString().trim();
        email = ((TextView) userRegister.findViewById(R.id.register_user_email)).getText().toString().trim();
        RadioButton man = userRegister.findViewById(R.id.register_user_sex_man);
        sex = man.isChecked() ? 0 : 1;

        String time = ((TextView) userRegister.findViewById(R.id.register_user_birth)).getText().toString().trim();
        birth = DateUtil.convert(time);

        L.i("日期 ：" + time + " ,  birth : " + DateUtil.dateFormat(birth));
//        birth = DateUtil.dateFormat (;
        num = ((TextView) userRegister.findViewById(R.id.register_user_num)).getText().toString().trim();
        address = ((TextView) userRegister.findViewById(R.id.register_user_address)).getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("请输入您的姓名");
            return false;
        }

        if (TextUtils.isEmpty(phone)) {
            showToast("请输入您的电话");
            return false;
        } else if (!CheckUtil.isMobile(phone) && !CheckUtil.isPhone(phone)) {
            showToast("请输入正确的电话");
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            showToast("请输入E-mail");
            return false;
        } else if (!CheckUtil.isEmail(email)){
            showToast("请输入正确的E-mail");
            return false;
        }

        if (TextUtils.isEmpty(num)) {
            num = "";
        } else {
            birth = DateUtil.convert(num.substring(6, 14), "yyyyMMdd");
            L.i("birth :" + DateUtil.dateFormat(birth));
        }

        if (TextUtils.isEmpty(address)) {
            address = "";
        }
        return true;
    }

    private class RegisterAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
            //super.destroyItem(container, position, object);
        }
    }
}
