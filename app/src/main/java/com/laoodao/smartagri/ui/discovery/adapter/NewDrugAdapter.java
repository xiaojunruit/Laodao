package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.NewDrug;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/5/17.
 */

public class NewDrugAdapter extends BaseAdapter<NewDrug> {

    public NewDrugAdapter(Context context) {
        super(context);
    }

//    private CollectionListener mCollectionListener;

//    public void setCollectionListener(CollectionListener listener) {
//        this.mCollectionListener = listener;
//    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ManyDrugHolder(parent);
    }

//    class NewDrugHolder extends BaseViewHolder<NewDrug.TopCenter> {
//        @BindView(R.id.tv_formula)
//        TextView mTvFormula;
//        @BindView(R.id.tv_title)
//        TextView mTvTitle;
//        @BindView(R.id.tv_content)
//        TextView mTvContent;
//        @BindView(R.id.collection)
//        LinearLayout mCollection;
//        @BindView(R.id.cb_collection)
//        CheckBox mCbCollection;
//
//        public NewDrugHolder(ViewGroup parent) {
//            super(parent, R.layout.item_new_drug);
//        }
//
//        @Override
//        public void setData(NewDrug.TopCenter data) {
//            super.setData(data);
//            mTvFormula.setText("配方" + (getAdapterPosition() + 1));
////            mTvTitle.setText(data.title);
////            mTvContent.setText(data.content);
////            mCbCollection.setChecked(data.isCollect);
//        }
//
//        @OnClick({R.id.collection, R.id.cb_collection})
//        public void onClick(View view) {
//            isLoginClick(view);
//        }
//
//
//        private void isLoginClick(View view) {
//            if (!Global.getInstance().isLoggedIn()) {
//                UiUtils.startActivity(LoginActivity.class);
//                return;
//            }
//            switch (view.getId()) {
//                case R.id.cb_collection:
//                    mCbCollection.setChecked(mCbCollection.isChecked());
////                    mCollectionListener.onCollection(getItem(getAdapterPosition()).id);
//                    break;
//                case R.id.collection:
//                    mCbCollection.setChecked(!mCbCollection.isChecked());
////                    mCollectionListener.onCollection(getItem(getAdapterPosition()).id);
//                    break;
//            }
//        }
//    }


    class ManyDrugHolder extends BaseViewHolder<NewDrug> {
        @BindView(R.id.list)
        RecyclerView mList;
        private ManyChildDrugAdapter mManyChildDrugAdapter;

        public ManyDrugHolder(ViewGroup parent) {
            super(parent, R.layout.item_many_drug);
            mManyChildDrugAdapter = new ManyChildDrugAdapter(getContext());
            mList.setLayoutManager(new LinearLayoutManager(getContext()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            mList.setAdapter(mManyChildDrugAdapter);
        }

        @Override
        public void setData(NewDrug data) {
            super.setData(data);
            mManyChildDrugAdapter.addAll(data.topCentent);
        }
    }


    public interface CollectionListener {
        void onCollection(int id);
    }


}
