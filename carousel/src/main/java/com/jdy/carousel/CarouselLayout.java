package com.jdy.carousel;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdy.carousel.loader.ImageLoader;
import com.jdy.carousel.view.CarouselViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CarouselLayout extends FrameLayout implements OnPageChangeListener {
    public static final String TAG = "carousel";

    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int mIndicatorSize;

    private int mIndicatorMargin = CarouselConfig.PADDING_SIZE;
    private int carouselStyle = CarouselConfig.CIRCLE_INDICATOR;
    private int delayTime = CarouselConfig.TIME;
    private int scrollTime = CarouselConfig.DURATION;
    private boolean isAutoPlay = CarouselConfig.IS_AUTO_PLAY;
    private boolean isScroll = CarouselConfig.IS_SCROLL;

    private int titleHeight;
    private int titleBackground;
    private int titleTextColor;
    private int titleTextSize;


    private int mIndicatorSelectedResId = R.drawable.carousel_gray_radius;
    private int mIndicatorUnselectedResId = R.drawable.carousel_white_radius;
    private int mLayoutResId = R.layout.carousel;
    private int carouselBackgroundImage;

    private int count = 0;
    private int currentItem;
    private int gravity = -1;
    private int lastPosition = 1;
    private int scaleType = 1;

    private Context mContext;
    private List<String> mTitles;
    private List mImageUrls;
    private List<View> mImageViews;
    private List<ImageView> mIndicatorImages;
    private DisplayMetrics mDisplayMetrics;

    private ImageView defaultImageView;

    private LinearLayout titleView, indicator, indicatorInside;
    private TextView carouselTitle, numIndicator, numIndicatorInside;

    private CarouselScroller mScroller;
    private ImageLoader imageLoader;
    private CarouselPagerAdapter adapter;
    private OnCarouselClickListener listener;
    private CarouselViewPager viewPager;

    private OnPageChangeListener mOnPageChangeListener;

    private WeakHandler handler = new WeakHandler();

    public CarouselLayout(Context context) {
        this(context, null);
    }

    public CarouselLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarouselLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mTitles = new ArrayList<>();
        mImageUrls = new ArrayList<>();
        mImageViews = new ArrayList<>();
        mIndicatorImages = new ArrayList<>();
        mDisplayMetrics = context.getResources().getDisplayMetrics();
        mIndicatorSize = mDisplayMetrics.widthPixels / 80;
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mImageViews.clear();
        handleTypedArray(context, attrs);
        View view = LayoutInflater.from(context).inflate(mLayoutResId, this, true);
        defaultImageView = view.findViewById(R.id.carouselDefaultImage);
        viewPager = view.findViewById(R.id.carouselViewPager);
        titleView = view.findViewById(R.id.titleView);
        indicator = view.findViewById(R.id.circleIndicator);
        indicatorInside = view.findViewById(R.id.indicatorInside);
        carouselTitle = view.findViewById(R.id.carouselTitle);
        numIndicator = view.findViewById(R.id.numIndicator);
        numIndicatorInside = view.findViewById(R.id.numIndicatorInside);
        defaultImageView.setImageResource(carouselBackgroundImage);
        initViewPagerScroll();
    }


    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Carousel);
        mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.Carousel_indicator_width, mIndicatorSize);
        mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.Carousel_indicator_height, mIndicatorSize);
        mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.Carousel_indicator_margin, CarouselConfig.PADDING_SIZE);
        mIndicatorSelectedResId = typedArray.getResourceId(R.styleable.Carousel_indicator_drawable_selected, R.drawable.carousel_gray_radius);
        mIndicatorUnselectedResId = typedArray.getResourceId(R.styleable.Carousel_indicator_drawable_unselected, R.drawable.carousel_white_radius);
        scaleType = typedArray.getInt(R.styleable.Carousel_image_scale_type, scaleType);
        delayTime = typedArray.getInt(R.styleable.Carousel_delay_time, CarouselConfig.TIME);
        scrollTime = typedArray.getInt(R.styleable.Carousel_scroll_time, CarouselConfig.DURATION);
        isAutoPlay = typedArray.getBoolean(R.styleable.Carousel_is_auto_play, CarouselConfig.IS_AUTO_PLAY);
        titleBackground = typedArray.getColor(R.styleable.Carousel_title_background, CarouselConfig.TITLE_BACKGROUND);
        titleHeight = typedArray.getDimensionPixelSize(R.styleable.Carousel_title_height, CarouselConfig.TITLE_HEIGHT);
        titleTextColor = typedArray.getColor(R.styleable.Carousel_title_textcolor, CarouselConfig.TITLE_TEXT_COLOR);
        titleTextSize = typedArray.getDimensionPixelSize(R.styleable.Carousel_title_textsize, CarouselConfig.TITLE_TEXT_SIZE);
        mLayoutResId = typedArray.getResourceId(R.styleable.Carousel_carousel_layout, mLayoutResId);
        carouselBackgroundImage = typedArray.getResourceId(R.styleable.Carousel_carousel_default_image, R.drawable.carousel_null);
        typedArray.recycle();
    }

    private void initViewPagerScroll() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new CarouselScroller(viewPager.getContext());
            mScroller.setDuration(scrollTime);
            mField.set(viewPager, mScroller);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public CarouselLayout isAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
        return this;
    }

    public CarouselLayout setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    public CarouselLayout setDelayTime(int delayTime) {
        this.delayTime = delayTime;
        return this;
    }

    public CarouselLayout setIndicatorGravity(int type) {
        switch (type) {
            case CarouselConfig.LEFT:
                this.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case CarouselConfig.CENTER:
                this.gravity = Gravity.CENTER;
                break;
            case CarouselConfig.RIGHT:
                this.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        return this;
    }

    public CarouselLayout setCarouselAnimation(Class<? extends PageTransformer> transformer) {
        try {
            setPageTransformer(true, transformer.newInstance());
        } catch (Exception e) {
            Log.e(TAG, "Please set the PageTransformer class");
        }
        return this;
    }

    /**
     * Set the number of pages that should be retained to either side of the
     * current page in the view hierarchy in an idle state. Pages beyond this
     * limit will be recreated from the adapter when needed.
     *
     * @param limit How many pages will be kept offscreen in an idle state.
     * @return Banner
     */
    public CarouselLayout setOffscreenPageLimit(int limit) {
        if (viewPager != null) {
            viewPager.setOffscreenPageLimit(limit);
        }
        return this;
    }

    /**
     * Set a {@link PageTransformer} that will be called for each attached page whenever
     * the scroll position is changed. This allows the application to apply custom property
     * transformations to each page, overriding the default sliding look and feel.
     *
     * @param reverseDrawingOrder true if the supplied PageTransformer requires page views
     *                            to be drawn from last to first instead of first to last.
     * @param transformer         PageTransformer that will modify each page's animation properties
     * @return Banner
     */
    public CarouselLayout setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer) {
        viewPager.setPageTransformer(reverseDrawingOrder, transformer);
        return this;
    }

    public CarouselLayout setCarouselTitles(List<String> titles) {
        this.mTitles = titles;
        return this;
    }

    public CarouselLayout setCarouselStyle(int carouselStyle) {
        this.carouselStyle = carouselStyle;
        return this;
    }

    public CarouselLayout setViewPagerIsScroll(boolean isScroll) {
        this.isScroll = isScroll;
        return this;
    }

    public CarouselLayout setImages(List<?> imageUrls) {
        this.mImageUrls = imageUrls;
        this.count = imageUrls.size();
        return this;
    }

    public void update(List<?> imageUrls, List<String> titles) {
        this.mTitles.clear();
        this.mTitles.addAll(titles);
        update(imageUrls);
    }

    public void update(List<?> imageUrls) {
        this.mImageUrls.clear();
        this.mImageViews.clear();
        this.mIndicatorImages.clear();
        this.mImageUrls.addAll(imageUrls);
        this.count = this.mImageUrls.size();
        start();
    }

    public void updateCarouselStyle(int CarouselStyle) {
        indicator.setVisibility(GONE);
        numIndicator.setVisibility(GONE);
        numIndicatorInside.setVisibility(GONE);
        indicatorInside.setVisibility(GONE);
        carouselTitle.setVisibility(View.GONE);
        titleView.setVisibility(View.GONE);
        this.carouselStyle = carouselStyle;
        start();
    }

    public CarouselLayout start() {
        setCarouselStyleUI();
        setImageList(mImageUrls);
        setData();
        return this;
    }

    private void setTitleStyleUI() {
        if (mTitles.size() != mImageUrls.size()) {
            throw new RuntimeException("[Banner] --> The number of titles and images is different");
        }

        if (titleBackground != -1) {
            titleView.setBackgroundColor(titleBackground);
        }
        if (titleHeight != -1) {
            titleView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, titleHeight));
        }
        if (titleTextColor != -1) {
            carouselTitle.setTextColor(titleTextColor);
        }
        if (titleTextSize != -1) {
            carouselTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
        }

        if (mTitles != null && mTitles.size() > 0) {
            carouselTitle.setText(mTitles.get(0));
            carouselTitle.setVisibility(View.VISIBLE);
            titleView.setVisibility(View.VISIBLE);
        }
    }

    private void setCarouselStyleUI() {
        int visibility = count > 1 ? View.VISIBLE : View.GONE;
        switch (carouselStyle) {
            case CarouselConfig.CIRCLE_INDICATOR:
                indicator.setVisibility(visibility);
                break;
            case CarouselConfig.NUM_INDICATOR:
                numIndicator.setVisibility(visibility);
                break;
            case CarouselConfig.NUM_INDICATOR_TITLE:
                numIndicatorInside.setVisibility(visibility);
                setTitleStyleUI();
                break;
            case CarouselConfig.CIRCLE_INDICATOR_TITLE:
                indicator.setVisibility(visibility);
                setTitleStyleUI();
                break;
            case CarouselConfig.CIRCLE_INDICATOR_TITLE_INSIDE:
                indicatorInside.setVisibility(visibility);
                setTitleStyleUI();
                break;
        }
    }

    private void initImages() {
        mImageViews.clear();
        if (carouselStyle == CarouselConfig.CIRCLE_INDICATOR ||
                carouselStyle == CarouselConfig.CIRCLE_INDICATOR_TITLE ||
                carouselStyle == CarouselConfig.CIRCLE_INDICATOR_TITLE_INSIDE) {
            createIndicator();
        } else if (carouselStyle == CarouselConfig.NUM_INDICATOR_TITLE) {
            numIndicatorInside.setText("1/" + count);
        } else if (carouselStyle == CarouselConfig.NUM_INDICATOR) {
            numIndicator.setText("1/" + count);
        }
    }

    private void setImageList(List<?> imagesUrl) {
        if (imagesUrl == null || imagesUrl.size() <= 0) {
            defaultImageView.setVisibility(VISIBLE);
            Log.e(TAG, "The image data set is empty.");
            return;
        }
        defaultImageView.setVisibility(GONE);
        initImages();
        for (int i = 0; i <= count + 1; i++) {
            View imageView = null;

            if (imageLoader != null) {
                imageView = imageLoader.createImageView(mContext);
            }

            if (imageView == null) {
                imageView = new ImageView(mContext);
            }
            setScaleType(imageView);
            Object url = null;
            if (i == 0) {
                url = imagesUrl.get(count - 1);
            } else if (i == count + 1) {
                url = imagesUrl.get(0);
            } else {
                url = imagesUrl.get(i - 1);
            }
            mImageViews.add(imageView);
            if (imageLoader != null)
                imageLoader.displayImage(mContext, url, imageView);
            else
                Log.e(TAG, "Please set images loader.");
        }
    }

    private void setScaleType(View imageView) {
        if (imageView instanceof ImageView) {
            ImageView view = ((ImageView) imageView);
            switch (scaleType) {
                case 0:
                    view.setScaleType(ScaleType.CENTER);
                    break;
                case 1:
                    view.setScaleType(ScaleType.CENTER_CROP);
                    break;
                case 2:
                    view.setScaleType(ScaleType.CENTER_INSIDE);
                    break;
                case 3:
                    view.setScaleType(ScaleType.FIT_CENTER);
                    break;
                case 4:
                    view.setScaleType(ScaleType.FIT_END);
                    break;
                case 5:
                    view.setScaleType(ScaleType.FIT_START);
                    break;
                case 6:
                    view.setScaleType(ScaleType.FIT_XY);
                    break;
                case 7:
                    view.setScaleType(ScaleType.MATRIX);
                    break;
            }
        }
    }

    private void createIndicator() {
        mIndicatorImages.clear();
        indicator.removeAllViews();
        indicatorInside.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight);
            params.leftMargin = mIndicatorMargin;
            params.rightMargin = mIndicatorMargin;
            if (i == 0) {
                imageView.setImageResource(mIndicatorSelectedResId);
            } else {
                imageView.setImageResource(mIndicatorUnselectedResId);
            }
            mIndicatorImages.add(imageView);
            if (carouselStyle == CarouselConfig.CIRCLE_INDICATOR ||
                    carouselStyle == CarouselConfig.CIRCLE_INDICATOR_TITLE)
                indicator.addView(imageView, params);
            else if (carouselStyle == CarouselConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                indicatorInside.addView(imageView, params);
        }
    }

    private void setData() {
        currentItem = 1;
        if (adapter == null) {
            adapter = new CarouselPagerAdapter();
            viewPager.addOnPageChangeListener(this);
        }
        viewPager.setAdapter(adapter);
        viewPager.setFocusable(true);
        viewPager.setCurrentItem(1);
        if (gravity != -1)
            indicator.setGravity(gravity);
        if (isScroll && count > 1) {
            viewPager.setScrollable(true);
        } else {
            viewPager.setScrollable(false);
        }

        if (isAutoPlay)
            startAutoPlay();
    }

    public void startAutoPlay() {
        handler.removeCallbacks(task);
        handler.postDelayed(task, delayTime);
    }

    public void stopAutoPlay() {
        handler.removeCallbacks(task);
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (count > 1 && isAutoPlay) {
                currentItem = currentItem % (count + 1) + 1;
                //                Log.i(tag, "curr:" + currentItem + " count:" + count);
                if (currentItem == 1) {
                    viewPager.setCurrentItem(currentItem, false);
                    handler.post(task);
                } else {
                    viewPager.setCurrentItem(currentItem);
                    handler.postDelayed(task, delayTime);
                }
            }
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //        Log.i(tag, ev.getAction() + "--" + isAutoPlay);
        if (isAutoPlay) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
                startAutoPlay();
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopAutoPlay();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 返回真实的位置
     *
     * @param position
     * @return 下标从0开始
     */
    public int toRealPosition(int position) {
        int realPosition = (position - 1) % count;
        if (realPosition < 0)
            realPosition += count;
        return realPosition;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(toRealPosition(position), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        currentItem=position;
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(toRealPosition(position));
        }
        if (carouselStyle == CarouselConfig.CIRCLE_INDICATOR ||
                carouselStyle == CarouselConfig.CIRCLE_INDICATOR_TITLE ||
                carouselStyle == CarouselConfig.CIRCLE_INDICATOR_TITLE_INSIDE) {
            mIndicatorImages.get((lastPosition - 1 + count) % count).setImageResource(mIndicatorUnselectedResId);
            mIndicatorImages.get((position - 1 + count) % count).setImageResource(mIndicatorSelectedResId);
            lastPosition = position;
        }
        if (position == 0) position = count;
        if (position > count) position = 1;
        switch (carouselStyle) {
            case CarouselConfig.CIRCLE_INDICATOR:
                break;
            case CarouselConfig.NUM_INDICATOR:
                numIndicator.setText(position + "/" + count);
                break;
            case CarouselConfig.NUM_INDICATOR_TITLE:
                numIndicatorInside.setText(position + "/" + count);
                carouselTitle.setText(mTitles.get(position - 1));
                break;
            case CarouselConfig.CIRCLE_INDICATOR_TITLE:
                carouselTitle.setText(mTitles.get(position - 1));
                break;
            case CarouselConfig.CIRCLE_INDICATOR_TITLE_INSIDE:
                carouselTitle.setText(mTitles.get(position - 1));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
        //        Log.i(tag,"currentItem: "+currentItem);
        switch (state) {
            case 0://No operation
                if (currentItem == 0) {
                    viewPager.setCurrentItem(count, false);
                } else if (currentItem == count + 1) {
                    viewPager.setCurrentItem(1, false);
                }
                break;
            case 1://start Sliding
                if (currentItem == count + 1) {
                    viewPager.setCurrentItem(1, false);
                } else if (currentItem == 0) {
                    viewPager.setCurrentItem(count, false);
                }
                break;
            case 2://end Sliding
                break;
        }
    }


    public CarouselLayout setOnBannerClickListener(OnCarouselClickListener listener) {
        this.listener = listener;
        return this;
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    public void releaseCarousel() {
        handler.removeCallbacksAndMessages(null);
    }

    class CarouselPagerAdapter extends PagerAdapter {

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return mImageViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = mImageViews.get(position);
            container.addView(view);

            if (listener != null) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onCarouselClick(toRealPosition(position));
                    }
                });
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
