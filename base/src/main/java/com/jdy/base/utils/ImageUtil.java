package com.jdy.base.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jdy.base.R;


public class ImageUtil {
    /**
     * 加载bitmap，如果是GIF则显示第一帧
     */
    public static String LOAD_BITMAP = "GLIDEUTILS_GLIDE_LOAD_BITMAP";
    /**
     * 加载gif动画
     */
    public static String LOAD_GIF = "GLIDEUTILS_GLIDE_LOAD_GIF";

    private static ImageUtil instance;

    public static ImageUtil getInstance() {
        if (instance == null) {
            synchronized (ImageUtil.class) {
                if (instance == null) {
                    instance = new ImageUtil();
                }
            }
        }
        return instance;
    }


    public static void loadImage(Context context, String path, ImageView imageView){
        Glide.with(context).load(path).placeholder(R.drawable.project_default_place_holder).into(imageView);
    }

    public static void loadImage(Context context, int resourceId, ImageView imageView){
        Glide.with(context).load(resourceId).placeholder(R.drawable.project_default_place_holder).into(imageView);
    }

    public void loadFragmentBitmap(Fragment fragment, String path, ImageView imageView, int placeid, int errorid, String bitmapOrgif) {
        if (bitmapOrgif == null || bitmapOrgif.equals(LOAD_BITMAP)) {
          //  Glide.with(fragment).load(path)placeholder(placeid).error(errorid).crossFade().into(imageView);
        } else if (bitmapOrgif.equals(LOAD_GIF)) {
            //Glide.with(fragment).load(path).asGif().crossFade().into(imageView);
        }
    }
}
