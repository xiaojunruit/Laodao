/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.laoodao.smartagri.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.view.recyclerview.EasyRecyclerView;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

abstract public class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> mItems;

    protected OnItemClickListener mItemClickListener;
    protected OnItemLongClickListener mItemLongClickListener;



    public interface ItemView {
        View onCreateView(ViewGroup parent);

        void onBindView(View headerView);
    }

    private int mPosition;
    private boolean mNotifyOnChange = true;

    private Context mContext;


    public BaseAdapter(Context context) {
        init(context, new ArrayList<T>());
    }


    public BaseAdapter(Context context, T[] objects) {
        init(context, Arrays.asList(objects));
    }


    public BaseAdapter(Context context, List<T> objects) {
        init(context, objects);
    }


    private void init(Context context, List<T> objects) {
        mContext = context;
        mItems = objects;
    }


    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context ctx) {
        mContext = ctx;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final BaseViewHolder viewHolder = OnCreateViewHolder(parent, viewType);
        //itemView 的点击事件
        if (mItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(viewHolder.getCurrentPosition());
                }
            });
        }

        if (mItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mItemLongClickListener.onItemLongClick(viewHolder.getCurrentPosition());
                }
            });
        }
        return viewHolder;
    }

    abstract public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType);


    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.itemView.setId(position);
        OnBindViewHolder(holder, position);
    }


    public void OnBindViewHolder(BaseViewHolder holder, final int position) {
        holder.bindData(position, getItem(position));
    }


    public int getPosition() {
        return mPosition;
    }


    public List<T> getAllData() {
        return new ArrayList<>(mItems);
    }

    public void addAll(Collection<T> items, boolean isClearFirst) {
        if (isClearFirst) {
            this.mItems.clear();
        }

        this.mItems.addAll(items);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }
    }

    public void addAll(Collection<? extends T> collection) {
        this.mItems.addAll(collection);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }


    public void add(int location, T object) {
        this.mItems.add(location, object);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void add(T object) {
        this.mItems.add(object);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void addAll(int location, Collection<? extends T> collection) {
        this.mItems.addAll(location, collection);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }
    }


    public void addAll(T... items) {
        Collections.addAll(this.mItems, items);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void insert(T object, int index) {
        this.mItems.add(index, object);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void remove(T object) {
        this.mItems.remove(object);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public T remove(int location) {
        T item = this.mItems.remove(location);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

        return item;
    }


    public void clear() {
        this.mItems.clear();
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void sort(Comparator<? super T> comparator) {
        Collections.sort(this.mItems, comparator);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }


    /**
     * {@inheritDoc}
     */
    public T getItem(int position) {
        return mItems.get(position);
    }


    public int getPosition(T item) {
        return mItems.indexOf(item);
    }

    /**
     * {@inheritDoc}
     */
    public long getItemId(int position) {
        return position;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mItemLongClickListener = listener;
    }

    private static void log(String content) {
        if (EasyRecyclerView.DEBUG) {
            Log.i(EasyRecyclerView.TAG, content);
        }
    }
}
