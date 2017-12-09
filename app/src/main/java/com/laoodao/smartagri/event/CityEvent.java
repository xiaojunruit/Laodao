package com.laoodao.smartagri.event;

import com.laoodao.smartagri.db.Area;

/**
 * Created by WORK on 2017/5/5.
 */

public class CityEvent {
    public Area area;
    public String location;
    public String lat;
    public String lon;
    public int ActivityId;  //1首页 2发布求购 3发布供销

    public CityEvent(Area area, String lat, String lon, String location, int ActivityId) {
        this.area = area;
        this.lat = lat;
        this.lon = lon;
        this.location = location;
        this.ActivityId = ActivityId;
    }
}
