package com.jdy.supa.module.main.home.brilliant;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.jdy.base.BaseFragment;
import com.jdy.base.adapter.BaseRecyclerHolder;
import com.jdy.base.adapter.BaseRecyclerViewAdapter;
import com.jdy.base.decoration.SpaceItemDecoration;
import com.jdy.base.utils.L;
import com.jdy.base.utils.ResourceUtil;
import com.jdy.carousel.CarouselConfig;
import com.jdy.carousel.CarouselLayout;
import com.jdy.carousel.loader.GlideImageLoader;
import com.jdy.supa.R;
import com.jdy.supa.entities.BrilliantBean;
import com.jdy.supa.entities.CommentBean;
import com.jdy.supa.entities.ItemBean;

import java.util.ArrayList;
import java.util.List;

public class BrilliantFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<ItemBean> itemBeanList;
    private List<BrilliantBean> brilliantBeanList = new ArrayList<>();
    private List<String> urls = new ArrayList<>();
    private CarouselLayout carouselLayout;
    private String url;

    @Override
    public int getRootLayoutID() {
        return R.layout.fragment_brilliant;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524078479216&di=e9516840008ca4758504b316b3a91729&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201604%2F05%2F20160405133326_B48YR.thumb.700_0.jpeg";
//        // *********************** //
//        itemBeanList = new ArrayList<>();
//        itemBeanList.add(new ItemBean("逗呆萌熊猫", "趁春光正好,逗呆萌熊猫", url, "张家港"));
//        itemBeanList.add(new ItemBean("逗呆萌熊猫", "趁春光正好,逗呆萌熊猫", url, "张家港"));
//        itemBeanList.add(new ItemBean("逗呆萌熊猫", "趁春光正好,逗呆萌熊猫", url, "张家港"));
//        itemBeanList.add(new ItemBean("逗呆萌熊猫", "趁春光正好,逗呆萌熊猫", url, "张家港"));
//        JourneyRecyclerAdapter journeyRecyclerAdapter = new JourneyRecyclerAdapter(getActivity(), itemBeanList);
//        // *********************** //
//
//
        urls.add(url);
        urls.add(url);
        urls.add(url);
//
//        // *********************** //
//        itemBeanList = new ArrayList<>();
//        itemBeanList.add(new ItemBean("豪宅", url));
//        itemBeanList.add(new ItemBean("豪宅", url));
//        itemBeanList.add(new ItemBean("豪宅", url));
//        itemBeanList.add(new ItemBean("豪宅", url));
//        itemBeanList.add(new ItemBean("豪宅", url));
//        StyleRecyclerAdapter styleRecyclerAdapter = new StyleRecyclerAdapter(getActivity(), itemBeanList);
//        // *********************** //
//
//
//        // *********************** //
//        itemBeanList = new ArrayList<>();
//        List<CommentBean> commentBeanList = new ArrayList<>();
//        String content = ResourceUtil.getString(R.string.house_item_detail);
//        commentBeanList.add(new CommentBean("孙悟空", url, "001" + content));
//        commentBeanList.add(new CommentBean("孙悟空02", url, "002" + content));
//
//        itemBeanList.add(new ItemBean(url, "江苏", "沙洲湖科创园江帆路8号", true, commentBeanList));
//        itemBeanList.add(new ItemBean(url, "江苏", "沙洲湖科创园江帆路8号", true, commentBeanList));
//        itemBeanList.add(new ItemBean(url, "江苏", "沙洲湖科创园江帆路8号", true, commentBeanList));
//        itemBeanList.add(new ItemBean(url, "江苏", "沙洲湖科创园江帆路8号", true, commentBeanList));
//        itemBeanList.add(new ItemBean(url, "江苏", "沙洲湖科创园江帆路8号", true, commentBeanList));
//        HouseRecyclerAdapter houseRecyclerAdapter = new HouseRecyclerAdapter(getActivity(), itemBeanList);
//        // *********************** //
//
//
//
//        // *********************** //
//        itemBeanList = new ArrayList<>();
//        itemBeanList.add(new ItemBean("他改造的房子是海边最时髦的民宿", content, url));
//        itemBeanList.add(new ItemBean("他改造的房子是海边最时髦的民宿", content, url));
//        itemBeanList.add(new ItemBean("他改造的房子是海边最时髦的民宿", content, url));
//        itemBeanList.add(new ItemBean("他改造的房子是海边最时髦的民宿", content, url));
//        StoryRecyclerAdapter storyRecyclerAdapter = new StoryRecyclerAdapter(getActivity(), itemBeanList);
//        // *********************** //
//
//
//        // *********************** //
//        itemBeanList = new ArrayList<>();
//        itemBeanList.add(new ItemBean(url, "江苏", "沙洲湖科创园江帆路8号", true, commentBeanList));
//        itemBeanList.add(new ItemBean(url, "江苏", "沙洲湖科创园江帆路8号", true, commentBeanList));
//        itemBeanList.add(new ItemBean(url, "江苏", "沙洲湖科创园江帆路8号", true, commentBeanList));
//        itemBeanList.add(new ItemBean(url, "江苏", "沙洲湖科创园江帆路8号", true, commentBeanList));
//        itemBeanList.add(new ItemBean(url, "江苏", "沙洲湖科创园江帆路8号", true, commentBeanList));
//        BrandRecyclerAdapter brandRecyclerAdapter = new BrandRecyclerAdapter(getActivity(), itemBeanList);
//        // *********************** //
//
//        // *********************** //
//        itemBeanList = new ArrayList<>();
//        itemBeanList.add(new ItemBean("豪宅", url));
//        itemBeanList.add(new ItemBean("豪宅", url));
//        itemBeanList.add(new ItemBean("豪宅", url));
//        itemBeanList.add(new ItemBean("豪宅", url));
//        itemBeanList.add(new ItemBean("豪宅", url));
//        BrandRecyclerAdapter brandRecyclerAdapter1 = new BrandRecyclerAdapter(getActivity(), itemBeanList);
//        // *********************** //


        brilliantBeanList.add( new BrilliantBean(ResourceUtil.getString(R.string.brilliant_perfect_journey), false));
        brilliantBeanList.add(new BrilliantBean(ResourceUtil.getString(R.string.brilliant_character_style), false));
        brilliantBeanList.add(new BrilliantBean(ResourceUtil.getString(R.string.brilliant_beautiful_house), true));
        brilliantBeanList.add(new BrilliantBean(ResourceUtil.getString(R.string.brilliant_wonderful_story), true));
        brilliantBeanList.add(new BrilliantBean(ResourceUtil.getString(R.string.brilliant_quality_brand), true));
    }

    @Override
    public void initView() {
        mRecyclerView = getView(R.id.brilliant_RecyclerView);
        setRecyclerView();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (carouselLayout != null)
            carouselLayout.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (carouselLayout != null)
            carouselLayout.stopAutoPlay();
    }

    private void setRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayout.VERTICAL, false));
        mRecyclerView.setAdapter(new BrilliantAdapter());

        //        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(24, 0);
        //        journeyRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayout.HORIZONTAL, false));
        //        journeyRecyclerView.setAdapter(new JourneyRecyclerAdapter(getActivity(), journeyBeanList));
        //        journeyRecyclerView.addItemDecoration(spaceItemDecoration);
        //
        //        styleRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayout.HORIZONTAL, false));
        //        styleRecyclerView.addItemDecoration(spaceItemDecoration);
        //        styleRecyclerView.setAdapter(new StyleRecyclerAdapter(getActivity(), styleBeanList));
        //
        //        houseRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayout.HORIZONTAL, false));
        //        houseRecyclerView.addItemDecoration(spaceItemDecoration);
        //        houseRecyclerView.setAdapter(new HouseRecyclerAdapter(getActivity(), houseBeanList));
    }

    @Override
    public void setListener() {
        mRecyclerView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        carouselLayout.stopAutoPlay();
    }

    private class BrilliantAdapter extends BaseRecyclerViewAdapter<BrilliantBean> {
        public static final int TYPE_FIRST = 0;
        public static final int TYPE_SECOND = 1;
        public static final int TYPE_NORMAL = 2;

        public BrilliantAdapter() {
            super(BrilliantFragment.this.getActivity(), brilliantBeanList, R.layout.item_brilliant);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0)
                return TYPE_FIRST;
            if (position == 1)
                return TYPE_SECOND;
            return TYPE_NORMAL;
        }

        @Override
        protected View onCreateViewHolder(View view, int viewType) {
            if (viewType == TYPE_FIRST) {
                view.findViewById(R.id.brilliant_separate_top).setVisibility(View.VISIBLE);
                view.findViewById(R.id.brilliant_include).setVisibility(View.VISIBLE);
            } else if (viewType == TYPE_SECOND) {
                view.findViewById(R.id.brilliant_separate_top).setVisibility(View.VISIBLE);
                view.findViewById(R.id.brilliant_carousel).setVisibility(View.VISIBLE);
                carouselLayout = view.findViewById(R.id.carousel);
                carouselLayout.setImages(urls).setIndicatorGravity(CarouselConfig.RIGHT).setImageLoader(new GlideImageLoader()).start();
            }
            return view;
        }

        @Override
        protected void convert(BaseRecyclerHolder holder, BrilliantBean bean) {
            if (bean == null)
                return;
            holder.setText(R.id.brilliant_title, bean.getTitle());
            if (bean.hasMore()) {
                holder.getView(R.id.brilliant_more).setVisibility(View.VISIBLE);
            } else {
                holder.getView(R.id.brilliant_more).setVisibility(View.INVISIBLE);
            }
            RecyclerView recyclerView = holder.getView(R.id.brilliant_RecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(BrilliantFragment.this.getActivity(), LinearLayout.HORIZONTAL, false));
            recyclerView.addItemDecoration(new SpaceItemDecoration(24, 0));
            recyclerView.setAdapter(getAdapter(holder.position));
        }

        private BaseRecyclerViewAdapter getAdapter(int position) {
            switch (position) {
                case 0 : return createJourneyAdapter();
                case 1 : return createStyleAdapter();
                case 2 : return createHouseAdapter();
                case 3 : return createStoryAdapter();
                case 4 : return createBrandAdapter();
            }
            L.e("No Adapter could be create!");
            return null;
        }
    }

    private BaseRecyclerViewAdapter createBrandAdapter() {
        // *********************** //
        itemBeanList = new ArrayList<>();
        itemBeanList.add(new ItemBean("豪宅", url));
        itemBeanList.add(new ItemBean("豪宅", url));
        itemBeanList.add(new ItemBean("豪宅", url));
        itemBeanList.add(new ItemBean("豪宅", url));
        itemBeanList.add(new ItemBean("豪宅", url));
       return new BrandRecyclerAdapter(getActivity(), itemBeanList);
    }

    private BaseRecyclerViewAdapter createStoryAdapter() {
        itemBeanList = new ArrayList<>();
        String content = ResourceUtil.getString(R.string.house_item_detail);
        itemBeanList.add(new ItemBean("他改造的房子是海边最时髦的民宿", content, url));
        itemBeanList.add(new ItemBean("他改造的房子是海边最时髦的民宿", content, url));
        itemBeanList.add(new ItemBean("他改造的房子是海边最时髦的民宿", content, url));
        itemBeanList.add(new ItemBean("他改造的房子是海边最时髦的民宿", content, url));
        return new StoryRecyclerAdapter(getActivity(), itemBeanList);
    }

    private BaseRecyclerViewAdapter createHouseAdapter() {
        itemBeanList = new ArrayList<>();
        List<CommentBean> commentBeanList = new ArrayList<>();
        String content = ResourceUtil.getString(R.string.house_item_detail);
        commentBeanList.add(new CommentBean("孙悟空", url, "001" + content));
        commentBeanList.add(new CommentBean("孙悟空02", url, "002" + content));

        itemBeanList.add(new ItemBean(url, "江苏", "沙洲湖科创园江帆路8号", true, commentBeanList));
        itemBeanList.add(new ItemBean(url, "江苏", "沙洲湖科创园江帆路8号", true, commentBeanList));
        itemBeanList.add(new ItemBean(url, "江苏", "沙洲湖科创园江帆路8号", true, commentBeanList));
        itemBeanList.add(new ItemBean(url, "江苏", "沙洲湖科创园江帆路8号", true, commentBeanList));
        itemBeanList.add(new ItemBean(url, "江苏", "沙洲湖科创园江帆路8号", true, commentBeanList));
        return new HouseRecyclerAdapter(getActivity(), itemBeanList);
    }

    private BaseRecyclerViewAdapter createStyleAdapter() {
        // *********************** //
        itemBeanList = new ArrayList<>();
        itemBeanList.add(new ItemBean("豪宅", url));
        itemBeanList.add(new ItemBean("豪宅", url));
        itemBeanList.add(new ItemBean("豪宅", url));
        itemBeanList.add(new ItemBean("豪宅", url));
        itemBeanList.add(new ItemBean("豪宅", url));
        return new StyleRecyclerAdapter(getActivity(), itemBeanList);
        // *********************** //
    }

    private BaseRecyclerViewAdapter createJourneyAdapter() {
        // *********************** //
        itemBeanList = new ArrayList<>();
        itemBeanList.add(new ItemBean("逗呆萌熊猫", "趁春光正好,逗呆萌熊猫", url, "张家港"));
        itemBeanList.add(new ItemBean("逗呆萌熊猫", "趁春光正好,逗呆萌熊猫", url, "张家港"));
        itemBeanList.add(new ItemBean("逗呆萌熊猫", "趁春光正好,逗呆萌熊猫", url, "张家港"));
        itemBeanList.add(new ItemBean("逗呆萌熊猫", "趁春光正好,逗呆萌熊猫", url, "张家港"));
        return new JourneyRecyclerAdapter(getActivity(), itemBeanList);
        // *********************** //
    }
}
