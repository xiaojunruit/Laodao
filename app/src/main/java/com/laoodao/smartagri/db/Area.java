package com.laoodao.smartagri.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by 欧源 on 2017/4/26.
 */
@Table(database = DBArea.class)
public class Area extends BaseModel {

    @Column
    @PrimaryKey
    public long id;

    @Column
    public String name;

    @Column
    public long pid;

    @Column
    public String pinyin;

    @Column
    public int level;

    public Area() {
    }

    public Area(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }
}
