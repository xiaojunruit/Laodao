package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.bean.Enterprise.EnterpriseAddress;
import com.laoodao.smartagri.bean.Enterprise.EnterpriseAddressChild;

import java.util.List;

/**
 * Created by WORK on 2017/7/3.
 */

public class EnterpriseAddressTagInfoAdapter extends BaseAdapter {
    private Context context;
    private List<EnterpriseAddress> tagInfos;


    public EnterpriseAddressTagInfoAdapter(Context context) {
        this.context = context;
    }

    public EnterpriseAddressTagInfoAdapter(Context context, List<EnterpriseAddress> tagInfoList) {
        tagInfos = tagInfoList;
        this.context = context;
    }

    public void addAll(List<EnterpriseAddress> tagInfoList) {
        this.tagInfos = tagInfoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tagInfos.size();
    }

    @Override
    public EnterpriseAddress getItem(int position) {
        return tagInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.tag_info, null, false);
            holder.tv = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv.setText(getItem(position).areaName);
        Holder finalHolder = holder;
        convertView.setOnClickListener(v -> {
            if (onItemClick != null) {
                onItemClick.click(finalHolder.tv, position, getItem(position));
            }
        });
        return convertView;
    }

    static class Holder {
        TextView tv;
    }

    public interface OnItemClick {
        void click(View view, int position, EnterpriseAddress tagInfo);
    }

    public OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}
