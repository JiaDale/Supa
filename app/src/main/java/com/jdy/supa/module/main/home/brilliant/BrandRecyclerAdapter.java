package com.jdy.supa.module.main.home.brilliant;

import android.content.Context;
import android.widget.ImageView;

import com.jdy.base.adapter.BaseRecyclerHolder;
import com.jdy.base.adapter.BaseRecyclerViewAdapter;
import com.jdy.base.utils.ImageUtil;
import com.jdy.supa.R;
import com.jdy.supa.entities.ItemBean;

import java.util.List;


public class BrandRecyclerAdapter extends BaseRecyclerViewAdapter<ItemBean> {

    public BrandRecyclerAdapter(Context context, List<ItemBean> list) {
        super(context, list, R.layout.item_brand);
    }

    @Override
    protected void convert(BaseRecyclerHolder holder, ItemBean bean) {
        if (bean == null)
            return;
        holder.setText(R.id.brand_item_title, bean.getTitle());
        holder.setText(R.id.brand_item_subhead, resolve(bean.getTitle()));
        ImageUtil.loadImage(mContext, bean.getImage(), (ImageView) holder.getView(R.id.brand_item_image));
    }

    private String resolve(String title) {
        return "000" + title + "000";
    }
}
