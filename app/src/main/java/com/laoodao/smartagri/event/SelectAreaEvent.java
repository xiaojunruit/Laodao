package com.laoodao.smartagri.event;

import com.laoodao.smartagri.db.Area;

/**
 * Created by 欧源 on 2017/5/6.
 */

public class SelectAreaEvent {

    public int requestCode;

    public Area province;
    public Area city;
    public Area county;
    public Area town;
    public int lastLevelAreaId;
    public SelectAreaEvent() {
    }

    public SelectAreaEvent(Area province, Area city, Area county, Area town) {
        this.province = province;
        this.city = city;
        this.county = county;
        this.town = town;
    }


}
