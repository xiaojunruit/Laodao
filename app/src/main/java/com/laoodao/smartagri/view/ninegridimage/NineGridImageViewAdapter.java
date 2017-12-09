package com.laoodao.smartagri.view.ninegridimage;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.ShapedImageView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import static com.laoodao.smartagri.view.ShapedImageView.SHAPE_MODE_ROUND_RECT;


public abstract class NineGridImageViewAdapter<T> {
    public abstract void onDisplayImage(Context context, ImageView imageView, T t);

    public void onItemImageClick(Context context, int index, List<T> list) {
    }

    public ImageView generateImageView(Context context) {
        ShapedImageView imageView = new ShapedImageView(context);
        imageView.setShapeMode(SHAPE_MODE_ROUND_RECT);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

}