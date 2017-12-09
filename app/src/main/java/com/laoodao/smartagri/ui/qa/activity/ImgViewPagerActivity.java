package com.laoodao.smartagri.ui.qa.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jaeger.library.StatusBarUtil;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.Img;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.qa.adapter.ImgViewPagerAdapter;
import com.laoodao.smartagri.ui.qa.contract.ImgViewPagerContract;
import com.laoodao.smartagri.ui.qa.presenter.ImgViewPagerPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/12/9.
 */

public class ImgViewPagerActivity extends BaseActivity<ImgViewPagerPresenter> implements ImgViewPagerContract.ImgViewPagerContactView {
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.tipsBox)
    LinearLayout mTipsBox;
    @BindView(R.id.btn_save)
    TextView mBtnSave;
    private ImageView[] tips;
    private int currentPage = 0;
    private ImgViewPagerAdapter adapter;
    private List<Img> imgs;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_img_viewpager;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        StatusBarUtil.setColor(this, ResourcesCompat.getColor(getResources(), R.color.black, getTheme()), 0);
        imgs = (List<Img>) getIntent().getSerializableExtra("imgs");
        currentPage = getIntent().getIntExtra("position", 0);
        initData();
        initListener();
        currentItem();
    }


    public static void start(Context context, ArrayList<Img> imgs, int position) {
        Intent intent = new Intent(context, ImgViewPagerActivity.class);
        intent.putExtra("imgs", imgs);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    private void initListener() {
        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tips[currentPage].setBackgroundResource(R.mipmap.point_grey);
                currentPage = position;
                tips[position].setBackgroundResource(R.mipmap.point_white);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adapter.setOnClickListener(() -> {
            finish();
        });

        mBtnSave.setOnClickListener(v -> {
            saveImg();
        });
    }


    private void initData() {
        mViewpager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        adapter = new ImgViewPagerAdapter(ImgViewPagerActivity.this, imgs);
        tips = new ImageView[imgs.size()];
        for (int i = 0; i < tips.length; i++) {
            ImageView img = new ImageView(this);
            img.setLayoutParams(new LinearLayout.LayoutParams(10, 10));
            tips[i] = img;
            if (i == currentPage) {
                img.setBackgroundResource(R.mipmap.point_white);
            } else {
                img.setBackgroundResource(R.mipmap.point_grey);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            params.leftMargin = 5;

            params.rightMargin = 5;

            mTipsBox.addView(img, params);
        }
        mViewpager.setAdapter(adapter);
    }

    public void currentItem() {
        mViewpager.setCurrentItem(currentPage);
    }


    @Override
    public void complete() {

    }


    /**
     * 保存图片
     */
    public void saveImg() {
        Glide.with(this).load(imgs.get(currentPage).url).downloadOnly(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                String destFileDir = Environment.getExternalStorageDirectory() + "/laoodao";
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(resource));
                    File dir = new File(destFileDir);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    File imageFile = new File(destFileDir, new Date().getTime() + ".jpg");
                    FileOutputStream out = new FileOutputStream(imageFile);
                    if (bitmap != null) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    }
                    out.flush();
                    out.close();
                    bitmap.recycle();
                    Intent mediaScanIntent = new Intent(
                            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    File file = new File(imageFile.getAbsolutePath());
                    Uri contentUri = Uri.fromFile(file);
                    mediaScanIntent.setData(contentUri);
                    ImgViewPagerActivity.this.sendBroadcast(mediaScanIntent);
                    UiUtils.makeText("保存图片至" + imageFile.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                    UiUtils.makeText("保存失败");
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                UiUtils.makeText("保存失败");
            }
        });
    }
}
