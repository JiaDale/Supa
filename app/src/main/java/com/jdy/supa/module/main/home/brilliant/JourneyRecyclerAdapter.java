package com.jdy.supa.module.main.home.brilliant;

import android.content.Context;
import android.widget.ImageView;

import com.jdy.base.adapter.BaseRecyclerHolder;
import com.jdy.base.adapter.BaseRecyclerViewAdapter;
import com.jdy.base.utils.ImageUtil;
import com.jdy.supa.R;
import com.jdy.supa.entities.ItemBean;

import java.util.List;

public class JourneyRecyclerAdapter extends BaseRecyclerViewAdapter<ItemBean> {

    public JourneyRecyclerAdapter(Context context, List<ItemBean> list) {
        super(context, list, R.layout.item_journey);
    }

    @Override
    protected void convert(BaseRecyclerHolder baseViewHolder, ItemBean bean) {
        if (bean == null)
            return;
        baseViewHolder.setNeedSetFont(true);
        String[] contents = bean.getContent().split(",");
        baseViewHolder.setText(R.id.journey_item_content_preceding, contents[0]);
        baseViewHolder.setText(R.id.journey_item_content_next, "\n\n" + contents[1]);
        baseViewHolder.setText(R.id.journey_item_location, getLocation(bean.getLocation()));
        baseViewHolder.setText(R.id.journey_item_title, bean.getTitle());
        ImageUtil.loadImage(mContext, bean.getImage(), (ImageView) baseViewHolder.getView(R.id.journey_item_image));
    }

    private String getLocation(String location) {
        return location;
    }
}
