package com.jdy.supa.module.main.home.villa;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jdy.base.BaseFragment;
import com.jdy.supa.R;

public  class VillaFragment extends BaseFragment {
    private ImageView imageView;
    @Override
    public int getRootLayoutID() {
        return  R.layout.fragment_villa;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        imageView = getView(R.id.image_test);
        loadImage();
    }

    private void loadImage() {
//        String url = "https://img-blog.csdn.net/2018041823515099?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI4ODEwODgx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70";
//        ImageUtil.loadImage(getActivity().getApplicationContext(), url, imageView);
//        showToast("加载 ： "+ url + "   完成！");
    }

    @Override
    public void setListener() {
        setOnClickListener(R.id.button_test);
    }

    @Override
    public void onClick(View v) {
        loadImage();
    }
}
