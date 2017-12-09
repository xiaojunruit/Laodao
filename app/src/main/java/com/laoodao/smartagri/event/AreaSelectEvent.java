package com.laoodao.smartagri.event;

import com.laoodao.smartagri.db.Area;

/**
 * Created by 欧源 on 2017/5/8.
 */

public class AreaSelectEvent {


    public int requestCode;


    public Area area;

    public AreaSelectEvent(int requestCode, Area area) {
        this.requestCode = requestCode;
        this.area = area;
    }
}
