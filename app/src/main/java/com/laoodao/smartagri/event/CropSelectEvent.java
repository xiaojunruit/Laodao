package com.laoodao.smartagri.event;

import com.laoodao.smartagri.bean.Plant;

import java.util.List;

/**
 * Created by 欧源 on 2017/5/4.
 */

public class CropSelectEvent {

    public List<Plant> plantList;

    public CropSelectEvent(List<Plant> plantList) {
        this.plantList = plantList;
    }
}
