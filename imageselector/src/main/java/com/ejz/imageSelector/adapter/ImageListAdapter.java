package com.ejz.imageSelector.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ejz.imageSelector.R;
import com.ejz.imageSelector.bean.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 欧源 on 2017/4/22.
 */

public class ImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_CAMERA = 1;
    private static final int TYPE_PICTURE = 2;

    private int maxSelectNum = 9;
    private Context mContext;
    private boolean showCamera = true;
    private List<LocalMedia> mImageList = new ArrayList<LocalMedia>();
    private List<LocalMedia> mSelectedImageList = new ArrayList<LocalMedia>();
    private OnImageSelectChangedListener mImageSelectChangedListener;


    public ImageListAdapter(Context context, int maxSelectNum) {
        this.mContext = context;
        this.maxSelectNum = maxSelectNum;
    }

    public void bindImages(List<LocalMedia> imageList) {
        this.mImageList = imageList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CAMERA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camera, parent, false);
            return new CameraHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        return new PictureHolder(view);
    }

    @Override
    public int getItemViewType(int position) {

        if (showCamera && position == 0) {
            return TYPE_CAMERA;
        }
        return TYPE_PICTURE;
    }

    public List<LocalMedia> getSelectedImageList() {

        return mSelectedImageList;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        int itemViewType = getItemViewType(position);
        if (itemViewType == TYPE_CAMERA) {
            CameraHolder cameraHolder = (CameraHolder) holder;
            cameraHolder.mCameraView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mImageSelectChangedListener != null) {
                        mImageSelectChangedListener.onTakePhoto();
                    }
                }
            });
            return;
        }


        final PictureHolder pictureHolder = (PictureHolder) holder;
        final LocalMedia image = mImageList.get(showCamera ? position - 1 : position);

        Glide.with(mContext.getApplicationContext())
                .load(new File(image.getPath()))
                .thumbnail(0.7f)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .dontAnimate()
                .centerCrop()
                .into(pictureHolder.mPicture);

        //判断当前是否选中
        boolean selected = isSelected(image);
        selectImage(pictureHolder, selected);

        pictureHolder.mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCheckState(pictureHolder, image);
            }
        });


    }

    /**
     * 改变选中状
     *
     * @param image
     */
    private void changeCheckState(PictureHolder holder, LocalMedia image) {

        boolean selected = holder.mCheck.isSelected();

        if (mSelectedImageList.size() >= maxSelectNum && !selected) {
            Toast.makeText(mContext, "你最多可以选择" + maxSelectNum + "图片", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selected) {
            //当前是选中的，则取消选中
            for (LocalMedia localMedia : mSelectedImageList) {
                if (image.getPath().equals(localMedia.getPath())) {
                    mSelectedImageList.remove(localMedia);
                    break;
                }
            }
        } else {
            //设置选中
            mSelectedImageList.add(image);
        }
        selectImage(holder, !selected);
        if (mImageSelectChangedListener != null) {
            mImageSelectChangedListener.onChange(mSelectedImageList);
        }

    }

    private void selectImage(PictureHolder holder, boolean selected) {

        holder.mCheck.setSelected(selected);
        holder.mPicture.setColorFilter(selected ?
                        mContext.getResources().getColor(R.color.image_overlay2) :
                        mContext.getResources().getColor(R.color.image_overlay),
                PorterDuff.Mode.SRC_ATOP);
    }


    /**
     * //判断当前是否选中
     *
     * @param image
     */
    private boolean isSelected(LocalMedia image) {
        for (LocalMedia localMedia : mSelectedImageList) {
            if (image.getPath().equals(localMedia.getPath())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return showCamera ? mImageList.size() + 1 : mImageList.size();
    }


    static class CameraHolder extends RecyclerView.ViewHolder {
        View mCameraView;

        public CameraHolder(View itemView) {
            super(itemView);
            mCameraView = itemView;
        }
    }

    static class PictureHolder extends RecyclerView.ViewHolder {
        ImageView mPicture;
        ImageView mCheck;

        View mContentView;

        public PictureHolder(View itemView) {
            super(itemView);
            mContentView = itemView;
            mPicture = (ImageView) itemView.findViewById(R.id.picture);
            mCheck = (ImageView) itemView.findViewById(R.id.check);
        }


    }

    public interface OnImageSelectChangedListener {
        void onChange(List<LocalMedia> selectImages);

        void onTakePhoto();

        void onPictureClick(LocalMedia media, int position);
    }

    public void setOnImageSelectChangedListener(OnImageSelectChangedListener imageSelectChangedListener) {
        this.mImageSelectChangedListener = imageSelectChangedListener;
    }
}
