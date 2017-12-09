/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.laoodao.smartagri.view.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.utils.KnifeUtil;

import java.lang.reflect.Field;

/**
 * M为这个itemView对应的model。
 * 使用RecyclerArrayAdapter就一定要用这个ViewHolder。
 * 这个ViewHolder将ItemView与Adapter解耦。
 * 推荐子类继承第二个构造函数。并将子类的构造函数设为一个ViewGroup parent。
 * 然后这个ViewHolder就完全独立。adapter在new的时候只需将parentView传进来。View的生成与管理由ViewHolder执行。
 * 实现setData来实现UI修改。Adapter会在onCreateViewHolder里自动调用。
 * <p>
 * 在一些特殊情况下，只能在setData里设置监听。
 *
 * @param <M>
 */
abstract public class BaseViewHolder<M> extends RecyclerView.ViewHolder {


    private int mPosition;

    public BaseViewHolder(View itemView) {
        super(itemView);
        KnifeUtil.bindTarget(this, itemView);
    }

    public BaseViewHolder(ViewGroup parent, @LayoutRes int res) {
        this(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
    }

    public void setData(M data) {

    }

    public void setData(int position, M data) {
        setData(data);
    }

    @CallSuper
    public void bindData(int position,M data){
        this.mPosition = position;
        setData(position,data);
    }

    protected <T extends View> T $(@IdRes int id) {
        return (T) itemView.findViewById(id);
    }

    protected Context getContext() {
        return itemView.getContext();
    }


    public int getCurrentPosition() {
        return mPosition;
    }


    @Deprecated
    protected int getDataPosition() {
        RecyclerView.Adapter adapter = getOwnerAdapter();
        if (adapter != null && adapter instanceof RecyclerArrayAdapter) {
            return getAdapterPosition() - ((RecyclerArrayAdapter) adapter).getHeaderCount();
        }
        if (adapter != null && getOwnerRecyclerView() instanceof XRecyclerView) {
            XRecyclerView recyclerView = (XRecyclerView) getOwnerRecyclerView();
            return getAdapterPosition() - recyclerView.getHeaderViewsCount();
        }
        return getAdapterPosition();
    }

    @Nullable
    protected <T extends RecyclerView.Adapter> T getOwnerAdapter() {
        RecyclerView recyclerView = getOwnerRecyclerView();
        return recyclerView == null ? null : (T) recyclerView.getAdapter();
    }

    @Nullable
    protected RecyclerView getOwnerRecyclerView() {
        try {
            Field field = RecyclerView.ViewHolder.class.getDeclaredField("mOwnerRecyclerView");
            field.setAccessible(true);
            return (RecyclerView) field.get(this);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        return null;
    }


}