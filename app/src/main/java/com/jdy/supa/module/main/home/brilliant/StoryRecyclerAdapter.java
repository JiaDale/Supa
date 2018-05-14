package com.jdy.supa.module.main.home.brilliant;

import android.content.Context;
import android.widget.ImageView;

import com.jdy.base.adapter.BaseRecyclerHolder;
import com.jdy.base.adapter.BaseRecyclerViewAdapter;
import com.jdy.base.utils.ImageUtil;
import com.jdy.supa.R;
import com.jdy.supa.entities.ItemBean;

import java.util.List;

public class StoryRecyclerAdapter extends BaseRecyclerViewAdapter<ItemBean> {

    public StoryRecyclerAdapter(Context context, List<ItemBean> list) {
        super(context, list, R.layout.item_story);
    }

    @Override
    protected void convert(BaseRecyclerHolder baseViewHolder, ItemBean bean) {
        if (bean == null)
            return;
        ImageUtil.loadImage(mContext, bean.getImage(), (ImageView) baseViewHolder.getView(R.id.story_item_image));
        baseViewHolder.setText(R.id.story_item_title, bean.getTitle());
        baseViewHolder.setText(R.id.story_item_content, bean.getContent());
    }
}
