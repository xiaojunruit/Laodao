package com.laoodao.smartagri.ui.market.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BaseDialog;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.utils.KnifeUtil;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/4/18.
 * <p>
 * 求购，菜单筛选
 */

public class SelectSecondTypeDialog extends BaseDialog<SelectSecondTypeDialog> {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private View mAnchorView;
    private List<SupplyMenu> mData;
    private SelectionAdapter mAdapter;

    private OnSelectedResultListener mListener;

    public SelectSecondTypeDialog(Context context, List<SupplyMenu> data) {
        super(context);
        // this.mAnchorView = anchorView;
        this.mData = data;
        this.mAdapter = new SelectionAdapter(mContext);
        widthScale(0.8f);

    }


    @Override
    public View onCreateView() {
        View view = getLayoutInflater().inflate(R.layout.dialog_selected, null);
        KnifeUtil.bindTarget(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.addAll(mData);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        this.mAdapter.setOnItemClickListener(position -> {
            dismiss();
            if (mListener != null) {
                mListener.onSelected(mData.get(position));
            }
        });
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        int location[] = new int[2];
//        final Rect rect = new Rect();
//
//        //获取显示范围，不包括状态栏
//        mAnchorView.getWindowVisibleDisplayFrame(rect);
//        //获取anchorView 在屏幕的坐标
//        mAnchorView.getLocationOnScreen(location);
//
//        int y = location[1] + mAnchorView.getHeight();
//        params.dimAmount = 0;
//        params.gravity = Gravity.TOP;
//        params.x = location[0];
//        params.y = y;
//        mLlTop.setBackgroundColor(0x80000000);
//        mLlTop.setGravity(Gravity.TOP);
//        mLlTop.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mContext.getResources().getDisplayMetrics().heightPixels - y));
//
//    }

    class SelectionAdapter extends RecyclerArrayAdapter<SupplyMenu> {

        public SelectionAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new BaseViewHolder<SupplyMenu>(parent, R.layout.item_selection) {
                @Override
                public void setData(SupplyMenu data) {

                    ((TextView) itemView).setText(data.name);
                }
            };
        }
    }


    public interface OnSelectedResultListener {
        void onSelected(SupplyMenu selectionItem);
    }

    public void setOnSelectedResultListener(OnSelectedResultListener listener) {
        this.mListener = listener;
    }
}
