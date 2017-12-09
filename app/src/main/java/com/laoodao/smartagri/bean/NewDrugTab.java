package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WORK on 2017/5/17.
 */

public class NewDrugTab {
    public List<TabContent> items;
    public String title;
    public List<ImageArr> imgarr;
    public List<String> methodarr;


    public class ImageArr {
        public String imageurl;
        public String title;
    }

    public class TabContent{
        public String title;
        public String content;
    };

    public String[] getTabTitles(){
        if (items.size()==0)return null;
        String[] str=new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            str[i]=items.get(i).title;
        }
        return str;
    }

    public List<String> getTabContent(){
        if (items.size()==0)return null;
        List<String> str = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            str.add(items.get(i).content);
        }
        return str;
    }

    public List<String> getTitles() {
        List<String> str = new ArrayList<>();
        for (int i = 0; i < imgarr.size(); i++) {
            str.add(imgarr.get(i).title);
        }
        return str;
    }

    public List<String> getUrls() {
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < imgarr.size(); i++) {
            urls.add(imgarr.get(i).imageurl);
        }
        return urls;
    }
}
