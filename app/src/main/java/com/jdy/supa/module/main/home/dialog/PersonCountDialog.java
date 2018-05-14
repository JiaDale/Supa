package com.jdy.supa.module.main.home.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdy.base.BaseDialog;
import com.jdy.base.adapter.BaseRecyclerHolder;
import com.jdy.base.adapter.BaseRecyclerViewAdapter;
import com.jdy.base.adapter.ListenerWithPosition.OnClickWithPositionListener;
import com.jdy.base.decoration.LineItemDecoration;
import com.jdy.base.utils.SharedPreferencesUtil;
import com.jdy.supa.R;


public class PersonCountDialog extends BaseDialog implements OnClickWithPositionListener {
    public static final String FLAG = PersonCountDialog.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private Handler handler = new Handler();
    private OnDialogItemClickListener listener;
    private Integer[] counts;
    private BaseRecyclerViewAdapter adapter;

    public interface OnDialogItemClickListener {
        void onDialogItemClick(int position, String item);
    }

    public PersonCountDialog(Context context) {
        super(context, R.style.Fullscreen);
    }

    @Override
    public int getRootLayoutID() {
        return R.layout.dialog_person_count;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        counts = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0};
    }

    @Override
    public void initView() {
        showView(R.id.toolbar_cancel);
        setText(R.id.toolbar_title,"选择入住人数");
        mRecyclerView = getView(R.id.dialog_person_recycler);
        setRecyclerView();
    }

    private void setRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        mRecyclerView.addItemDecoration(new LineItemDecoration(mContext, LineItemDecoration.VERTICAL, true, true));
        mRecyclerView.setAdapter(adapter = new PersonCountAdapter(mContext));
    }

    @Override
    public void setListener() {
        setOnClickListener(R.id.toolbar_cancel);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case R.id.toolbar_cancel:
                dialog.cancel();
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }


    @Override
    public void onClick(View v, final BaseRecyclerHolder holder) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                SharedPreferencesUtil.putInt(FLAG, holder.position);
                adapter.notifyDataSetChanged();
            }
        });
        String item = ((TextView) holder.getView(R.id.person_count_text)).getText().toString().trim();
        if (listener == null)
            throw new NullPointerException("You should init Listener first!");
        listener.onDialogItemClick(holder.position, item);
        cancel();
    }


    private class PersonCountAdapter extends BaseRecyclerViewAdapter<Integer> {
        public PersonCountAdapter(Context context) {
            super(context, counts, R.layout.item_person_count);
        }

        @Override
        protected void convert(BaseRecyclerHolder holder, Integer bean) {
            if (bean == null)
                return;
            holder.setOnClickListener(PersonCountDialog.this);
            int lastSelected = SharedPreferencesUtil.getInt(FLAG, -10);
            lastSelected = lastSelected < 0 ? 0 : lastSelected;
            String text;
            if (holder.position < 9)
                text = bean + "人";
            else if (holder.position == 9) {
                text = bean + "人以上";
            } else
                text = "不限人数";
            holder.setText(R.id.person_count_text, text);

            if (holder.position == lastSelected )
                showView(holder.getView(R.id.person_count_icon));
            else
                hiddenView(holder.getView(R.id.person_count_icon));
        }
    }

    public void setOnDialogItemClickListener(OnDialogItemClickListener listener) {
        this.listener = listener;
    }
}
