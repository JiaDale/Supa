package com.jdy.base.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class SpaceItemDecoration extends ItemDecoration {
    private int leftRight;
    private int topBottom;

    /**
     * @param leftRight 横向间的距离
     * @param topBottom 纵向间距离
     */
    public SpaceItemDecoration(int leftRight, int topBottom) {
        this.leftRight = leftRight;
        this.topBottom = topBottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        //竖直方向的
        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            //最后一项需要 bottom
            if (parent.getChildAdapterPosition(view) < layoutManager.getItemCount() - 1) {
                outRect.bottom = topBottom;
            }
            outRect.left = leftRight;
            outRect.right = leftRight;
        } else {
            //最后一项需要right
            if (parent.getChildAdapterPosition(view) < layoutManager.getItemCount() - 1) {
                outRect.right = leftRight;
            }
            outRect.top = topBottom;
            outRect.bottom = topBottom;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {
        super.onDraw(c, parent, state);
    }

    //    private SpacesItemDecorationEntrust getEntrust(RecyclerView.LayoutManager manager) {
    //        SpacesItemDecorationEntrust entrust = null;
    // 要注意这边的GridLayoutManager是继承LinearLayoutManager，所以要先判断GridLayoutManager
    // if (manager instanceof GridLayoutManager) {
    // entrust = new GridEntrust(leftRight, topBottom, mColor);         } else {
    // 其他的都当做Linear来进行计算
    //  entrust = new LinearEntrust(leftRight, topBottom, mColor);
    //   }         return entrust;     }


}
