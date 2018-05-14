package com.jdy.carousel;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class CarouselScroller extends Scroller {
    private int mDuration = CarouselConfig.DURATION;

    public CarouselScroller(Context context) {
        super(context);
    }

    public CarouselScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public CarouselScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setDuration(int scrollTime) {
        mDuration = scrollTime;
    }
}
