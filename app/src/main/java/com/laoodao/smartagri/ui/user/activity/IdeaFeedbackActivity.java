package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ejz.imageSelector.activity.ImagePreviewActivity;
import com.ejz.imageSelector.activity.ImageSelectorActivity;
import com.ejz.imageSelector.bean.LocalMedia;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.Suggestion;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.ImagePickerAdapter;
import com.laoodao.smartagri.ui.user.adapter.SuggestionAdapter;
import com.laoodao.smartagri.ui.user.contract.IdeaFeedbackContract;
import com.laoodao.smartagri.ui.user.presenter.IdeaFeedbackPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.TagGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 意见反馈
 * Created by WORK on 2017/4/13.
 */

public class IdeaFeedbackActivity extends BaseActivity<IdeaFeedbackPresenter> implements IdeaFeedbackContract.IdeaFeedbackView, TextWatcher {


    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_commit)
    RoundTextView mBtnCommit;
    @BindView(R.id.image_recyclerView)
    RecyclerView mImageRecyclerView;
    @BindView(R.id.iv_add_image)
    ImageView mIvAddImage;
    @BindView(R.id.et_suggestion)
    EditText mEtSuggestion;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.tv_input_num)
    TextView mTvInputNum;
    //    @BindView(R.id.tg_idea)
//    TagGroup mTgIdea;
    private SuggestionAdapter mAdapter;
    private ImagePickerAdapter mImagePickerAdapter;
    private List<LocalMedia> mSelectedImageList = new ArrayList<>();

    private int maxSelectNum = 3; //图片最多选择数量
    private String title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_idea_feedback;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    public static void start(Context context, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        UiUtils.startActivity(context, IdeaFeedbackActivity.class, bundle);
    }

    @Override
    protected void configViews() {
        title = getIntent().getStringExtra("title");
        setTitle(title);
        initRecyclerView();
        initImageRecyclerView();
        mEtSuggestion.addTextChangedListener(this);
        mTvInputNum.setText(getString(R.string._0_500, String.valueOf(0)));
        mPresenter.getSuggestionList();
//        mTgIdea.setOnTagItemClickListener((view, position, tag) -> {
//            mTgIdea.setCheckTag(position);
//        });
    }

    private void initImageRecyclerView() {
        mImagePickerAdapter = new ImagePickerAdapter(this, maxSelectNum);
        mImageRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });

        mImageRecyclerView.setAdapter(mImagePickerAdapter);
        mImagePickerAdapter.setOnItemClickListener(position -> {
            ImagePreviewActivity.startPreview(this, mSelectedImageList, mSelectedImageList, mSelectedImageList.size(), position, true);
        });
    }

    private void initRecyclerView() {
        mAdapter = new SuggestionAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int position, RecyclerView parent) {
                outRect.left = position == 0 ? UiUtils.dip2px(15) : UiUtils.dip2px(5);
                if (position == parent.getAdapter().getItemCount() - 1) {
                    outRect.right = UiUtils.dip2px(15);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(position -> {
            mRecyclerView.scrollToPosition(position);
            List<Suggestion> suggestionList = mAdapter.getAllData();
            for (int i = 0; i < suggestionList.size(); i++) {
                Suggestion item = mAdapter.getItem(i);
                item.isSelected = position == i;
            }
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void complete() {
    }

    @Override
    public void showSuggestionList(List<Suggestion> suggestionList) {
        mAdapter.clear();
        mAdapter.addAll(suggestionList);
        mAdapter.notifyDataSetChanged();
//        mTgIdea.setList(suggestionList);
    }

    @Override
    public void addSuggestionSuccess() {
        finish();
    }

    @OnClick({R.id.iv_add_image, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                commit();
                break;
            case R.id.iv_add_image:
                ImageSelectorActivity.start(this, maxSelectNum - mImagePickerAdapter.getCount(), true);
                break;
        }
    }

    private void commit() {

        Suggestion selectedItem = mAdapter.getSelectedItem();
        String suggestion = mEtSuggestion.getText().toString();
        String phone = mEtPhone.getText().toString();
        if (selectedItem == null) {
            UiUtils.makeText("请选择反馈类型");
            return;
        }
        if (TextUtils.isEmpty(suggestion)) {
            UiUtils.makeText("请输入您好反馈的内容");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            UiUtils.makeText("手机号码不能为空");
            return;
        }
        mPresenter.commitSuggestion(this, selectedItem.id, suggestion, phone, mSelectedImageList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
                List<LocalMedia> imageList = (ArrayList) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
                addImage(imageList);
            } else if (requestCode == ImagePreviewActivity.REQUEST_PREVIEW) {
                List<LocalMedia> imageList = (ArrayList) data.getSerializableExtra(ImagePreviewActivity.OUTPUT_LIST);
                mSelectedImageList.clear();
                addImage(imageList);
            }
        }
    }

    private void addImage(List<LocalMedia> imageList) {
        mSelectedImageList.addAll(imageList);
        mImagePickerAdapter.clear();
        mImagePickerAdapter.addAll(mSelectedImageList);
        boolean isVisible = mSelectedImageList.size() < maxSelectNum;
        if (isVisible) {
            visible(mIvAddImage);
        } else {
            gone(mIvAddImage);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int length = mEtSuggestion.getText().toString().length();
        mTvInputNum.setText(getString(R.string._0_500, String.valueOf(length)));
    }
}
