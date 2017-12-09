package com.laoodao.smartagri.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by WORK on 2017/5/26.
 */

public class ObservableScrollView extends ScrollView {

    private ScrollListener scrollListener;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollListener != null) {
            scrollListener.onScroll(t, l);
        }
    }

    public void registerListener(ScrollListener scrollListener){
        this.scrollListener = scrollListener;
    }

    public interface ScrollListener{
        void onScroll(int t,int l);
    }

}
