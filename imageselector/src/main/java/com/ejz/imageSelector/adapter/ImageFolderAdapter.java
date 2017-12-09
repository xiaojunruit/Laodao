package com.ejz.imageSelector.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ejz.imageSelector.R;
import com.ejz.imageSelector.bean.LocalMedia;
import com.ejz.imageSelector.bean.LocalMediaFolder;
import com.ejz.imageSelector.utils.LocalMediaLoader;
import com.ejz.imageSelector.widget.FolderWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 欧源 on 2017/4/23.
 */

public class ImageFolderAdapter extends RecyclerView.Adapter<ImageFolderAdapter.FolderViewHolder> {

    private Context mContext;
    private List<LocalMediaFolder> mLocalMediaLoaderList = new ArrayList<>();
    private int mCheckedIndex = 0;

    private OnItemClickListener mOnItemClickListener;


    public ImageFolderAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<LocalMediaFolder> localMediaFolderList) {
        this.mLocalMediaLoaderList = localMediaFolderList;
        notifyDataSetChanged();
    }


    @Override
    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_folder, parent, false);
        return new FolderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FolderViewHolder holder, final int position) {

        final LocalMediaFolder folder = mLocalMediaLoaderList.get(position);
        Glide.with(mContext.getApplicationContext())
                .load(new File(folder.getFirstImagePath()))
                .thumbnail(0.6f)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .dontAnimate()
                .centerCrop()
                .into(holder.mFirstImage);
        holder.mFolderName.setText(folder.getName());
        holder.mImageNum.setText(folder.getImageNum() + "");
        holder.isSelected.setVisibility(mCheckedIndex == position ? View.VISIBLE : View.GONE);
        holder.contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mCheckedIndex = position;
                    notifyDataSetChanged();
                    mOnItemClickListener.onItemClick(folder.getName(), folder.getImages());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLocalMediaLoaderList.size();
    }


    static class FolderViewHolder extends RecyclerView.ViewHolder {
        ImageView mFirstImage;
        TextView mFolderName;
        TextView mImageNum;
        ImageView isSelected;

        View contentView;

        public FolderViewHolder(View itemView) {
            super(itemView);
            contentView = itemView;
            mFirstImage = (ImageView) itemView.findViewById(R.id.first_image);
            mFolderName = (TextView) itemView.findViewById(R.id.folder_name);
            mImageNum = (TextView) itemView.findViewById(R.id.image_num);
            isSelected = (ImageView) itemView.findViewById(R.id.is_selected);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(String folderName, List<LocalMedia> images);
    }
}
