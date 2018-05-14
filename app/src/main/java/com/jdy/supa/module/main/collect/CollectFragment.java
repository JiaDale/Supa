package com.jdy.supa.module.main.collect;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdy.base.BaseFragment;
import com.jdy.base.adapter.BaseRecyclerHolder;
import com.jdy.base.adapter.BaseRecyclerViewAdapter;
import com.jdy.base.decoration.LineItemDecoration;
import com.jdy.base.decoration.SpaceItemDecoration;
import com.jdy.base.utils.DateUtil;
import com.jdy.base.utils.ImageUtil;
import com.jdy.base.utils.ResourceUtil;
import com.jdy.supa.R;
import com.jdy.supa.entities.CollectBean;
import com.jdy.supa.entities.NewsBean;
import com.jdy.supa.module.main.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SuppressLint("ValidFragment")
public class CollectFragment extends BaseFragment implements OnRefreshListener {
    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private List<CollectBean> collectBeanList;
    private List<NewsBean> newsBeanList;
    private BaseRecyclerViewAdapter mAdapter;
    private boolean isRefresh = false;//是否刷新中
    private String mType;

    public CollectFragment(String type) {
        mType = type;
    }

    @Override
    public int getRootLayoutID() {
        return R.layout.fragment_collect_news;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524078479216&di=e9516840008ca4758504b316b3a91729&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201604%2F05%2F20160405133326_B48YR.thumb.700_0.jpeg";
        String character = ResourceUtil.getString(R.string.collect_item_character);
        String content = ResourceUtil.getString(R.string.collect_item_content);
        String capacity = ResourceUtil.getString(R.string.collect_item_capacity);

        if (isCollect()) {
            collectBeanList = new ArrayList<>();
            ArrayList<String> advantages = new ArrayList<>();
            advantages.add("免押金");
            advantages.add("实拍");
            collectBeanList.add(new CollectBean(url, 2001.85, null, character, url, capacity, "022", content, advantages));
            //        collectBeanList.add(new CollectBean(url, 2001.85, null, character, url, capacity, "022", content, advantages));
            //        collectBeanList.add(new CollectBean(url, 2001.85, null, character, url, capacity, "022", content, advantages));
            //        collectBeanList.add(new CollectBean(url, 2001.85, null, character, url, capacity, "022", content, advantages));
        } else {
            newsBeanList = new ArrayList<>();
            newsBeanList.add(new NewsBean(url, true, "服务号", character, new Date()));
            newsBeanList.add(new NewsBean(url, false, "服务号", character, new Date()));
        }
    }


    @Override
    public void initView() {
        mSwipeLayout = getView(R.id.collect_refresh);
        setSwipeRefresh();

        mRecyclerView = getView(R.id.collect_recycler);
        setRecyclerView();
    }

    private void setRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayout.VERTICAL, false));
        mRecyclerView.addItemDecoration(isCollect() ? new SpaceItemDecoration(0, 48) :
                new LineItemDecoration(getActivity(), LineItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(getAdapter());
    }

    private boolean isCollect() {
        return mType == MainActivity.COLLECT;
    }

    private BaseRecyclerViewAdapter getAdapter() {
        if (isCollect()) {
            setText(R.id.toolbar_title, ResourceUtil.getString(R.string.main_collect));
            mAdapter = new CollectAdapter();
        } else {
            setText(R.id.toolbar_title, ResourceUtil.getString(R.string.main_news));
            mAdapter = new NewsAdapter();
        }
        return mAdapter;
    }

    private void setSwipeRefresh() {
        //设置进度条的颜色主题，最多能设置四种 加载颜色是循环播放的，只要没有完成刷新就会一直循环，holo_blue_bright>holo_green_light>holo_orange_light>holo_red_light
        mSwipeLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED);

        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        mSwipeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);
    }

    @Override
    public void setListener() {
        //设置下拉刷新的监听
        mSwipeLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        if (!isRefresh) {
            isRefresh = true;
            //模拟加载网络数据，这里设置4秒，正好能看到4色进度条
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    //显示或隐藏刷新进度条
                    mSwipeLayout.setRefreshing(false);
                    mAdapter.addData(isCollect() ? collectBeanList : newsBeanList);
                    isRefresh = false;
                }
            }, 4000);
        }
    }

    private class NewsAdapter extends BaseRecyclerViewAdapter<NewsBean> {
        public NewsAdapter() {
            super(CollectFragment.this.getActivity(), newsBeanList, R.layout.item_news);
        }

        @Override
        protected void convert(BaseRecyclerHolder holder, NewsBean bean) {
            if (bean == null)
                return;
            ImageUtil.loadImage(mContext, bean.getImage(), (ImageView) holder.getView(R.id.news_item_image));
            if (bean.isNew()) {
                holder.getView(R.id.news_item_new).setVisibility(View.VISIBLE);
            } else {
                holder.getView(R.id.news_item_new).setVisibility(View.INVISIBLE);
            }
            holder.setText(R.id.news_item_content, bean.getContent());
            holder.setText(R.id.news_item_title, bean.getTitle());
            holder.setText(R.id.news_item_time, DateUtil.dateFormat(bean.getLastRefreshTime(),  "yyyy-MM-dd"));
        }
    }


    private class CollectAdapter extends BaseRecyclerViewAdapter<CollectBean> {
        private boolean needSetUnit = false;

        public CollectAdapter() {
            super(CollectFragment.this.getActivity(), collectBeanList, R.layout.item_collect);
        }

        @Override
        protected void convert(BaseRecyclerHolder holder, CollectBean bean) {
            if (bean == null)
                return;
            holder.setText(R.id.collect_item_price, getPrice(bean.getPrice()));
            if (!TextUtils.isEmpty(bean.getPriceUnit()) && needSetUnit) {
                holder.setText(R.id.collect_item_price_unit, getConvertUnit(bean.getPriceUnit()));
            }
            ImageUtil.loadImage(mContext, bean.getImage(), (ImageView) holder.getView(R.id.collect_item_image));
            ImageUtil.loadImage(mContext, bean.getHeadImage(), (ImageView) holder.getView(R.id.collect_item_head_image));
            holder.setText(R.id.collect_item_content, bean.getContent());
            holder.setText(R.id.collect_item_capacity, bean.getCapacity());
            holder.setText(R.id.collect_item_character, bean.getCharacter());

            List<String> advantages = bean.getAdvantages();
            LinearLayout linearLayout = holder.getView(R.id.collect_item_advantages);
            if (advantages != null && !advantages.isEmpty() && linearLayout.getChildCount() == 0) {
                for (int i = 0; i < advantages.size(); i++) {
                    TextView textView = new TextView(mContext);
                    textView.setText(advantages.get(i));
                    textView.setTextColor(ResourceUtil.getColor(R.color.blue));
                    textView.setTextSize(8);
                    if (i != 0) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(24, 0, 0, 0);
                        textView.setLayoutParams(params);
                    }
                    linearLayout.addView(textView);
                }
            }
            holder.setText(R.id.collect_item_content, bean.getContent());
        }

        private String getConvertUnit(String priceUnit) {
            if (priceUnit.contains("美元"))
                return "$";
            if (priceUnit.contains("元"))
                return "￥";
            return "";
        }

        private String getPrice(double price) {
            return "" + (int) price;
        }
    }

}
