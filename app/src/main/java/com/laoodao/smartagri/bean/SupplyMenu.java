package com.laoodao.smartagri.bean;

import com.laoodao.smartagri.view.selectionLayout.SelectionItem;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class SupplyMenu implements SelectionItem {
    public String name;
    public String id;
    public List<SupplyMenu> items;
    public boolean select;

    public SupplyMenu(){

    }

    public SupplyMenu(String name,String id){
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
