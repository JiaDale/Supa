package com.jdy.supa.utils;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;

public class LocateUtil {
    private static LocateUtil instance;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private OnLocationChangedListener mListener;
    private AMapLocationListener mLocationListener;
    private Context mContext;

    private LocateUtil(Context context) {
        mContext = context;
    }

    public static LocateUtil getInstance(Context context) {
        if (instance == null)
            synchronized (LocateUtil.class) {
                if (instance == null)
                    instance = new LocateUtil(context);
            }
        return instance;
    }

    public void locate(AMapLocationListener listener) {
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(mContext);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            mLocationOption.isNeedAddress();
            //设置定位回调监听
            mlocationClient.setLocationListener(mLocationListener = listener);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    public void locate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null && mLocationListener != null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(mContext);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            // mLocationOption.isNeedAddress();
            //设置定位回调监听
            mlocationClient.setLocationListener(mLocationListener);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    public void start() {
        if (mlocationClient != null && !mlocationClient.isStarted())
            mlocationClient.startLocation();
    }



    /**
     * 停止定位
     */
    public void stop() {
        mListener = null;
        if (mlocationClient != null && mlocationClient.isStarted()) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


//    public void onLocationChanged(AMapLocation aMapLocation) {
//        if (mListener != null && aMapLocation != null) {
//            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
//                Log.i("===定位信息", "地址：" + aMapLocation.getAddress() + "省" + aMapLocation.getProvince() + "城市" + aMapLocation.getCity() +
//                        "地区" + aMapLocation.getDistrict() + "街道" + aMapLocation.getStreet());
//                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
//            } else {
//                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
//                Log.e("AmapErr", errText);
//            }
//        }
//    }

    public void onDestroy() {
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }
}
