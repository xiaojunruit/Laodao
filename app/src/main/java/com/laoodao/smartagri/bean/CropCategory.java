package com.laoodao.smartagri.bean;

import com.laoodao.smartagri.view.selectionLayout.SelectionItem;

/**
 * Created by WORK on 2017/7/31.
 */

public class CropCategory implements SelectionItem {
    public String name;
    public int id;
    public boolean select;
    public CropCategory(String name,int id){
        this.name=name;
        this.id=id;
    }

    @Override
    public boolean isSelect() {
        return select;
    }

    @Override
    public String getTitle() {
        return name;
    }
}
