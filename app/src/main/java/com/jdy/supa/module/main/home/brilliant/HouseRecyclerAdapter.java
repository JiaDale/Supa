package com.jdy.supa.module.main.home.brilliant;

import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;

import com.jdy.base.adapter.BaseRecyclerHolder;
import com.jdy.base.adapter.BaseRecyclerViewAdapter;
import com.jdy.base.utils.ImageUtil;
import com.jdy.supa.R;
import com.jdy.supa.entities.CommentBean;
import com.jdy.supa.entities.ItemBean;

import java.util.List;

public class HouseRecyclerAdapter extends BaseRecyclerViewAdapter<ItemBean> {

    public HouseRecyclerAdapter(Context context, List<ItemBean> list) {
        super(context, list, R.layout.item_house);
    }


    @RequiresApi(api = VERSION_CODES.JELLY_BEAN)
    @Override
    protected void convert(BaseRecyclerHolder baseViewHolder, ItemBean bean) {
        if (bean == null)
            return;
        View collect = baseViewHolder.getView(R.id.house_item_collect);
        if (bean.isCollect()) {
            collect.setBackground(mContext.getResources().getDrawable(R.drawable.house_item_collect_checked));
        } else {
            collect.setBackground(mContext.getResources().getDrawable(R.drawable.house_item_collect_normal));
        }
        baseViewHolder.setText(R.id.house_item_location, "【" + bean.getLocation() + "】");
        baseViewHolder.setText(R.id.house_item_detail, bean.getDetail());
        ImageUtil.loadImage(mContext, bean.getImage(), (ImageView) baseViewHolder.getView(R.id.house_item_image));

        List<CommentBean> commentBeanList = bean.getCommentBeanList();
        if (commentBeanList != null && !commentBeanList.isEmpty()) {
            CommentBean commentBean = commentBeanList.get(0);
            ImageUtil.loadImage(mContext, commentBean.getUserImage(), (ImageView) baseViewHolder.getView(R.id.house_item_user_image));
            baseViewHolder.setText(R.id.house_item_user_name, commentBean.getUserName());
            baseViewHolder.setText(R.id.house_item_user_comment, commentBean.getUserContent());
        }
    }
}
