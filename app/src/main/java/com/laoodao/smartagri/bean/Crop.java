package com.laoodao.smartagri.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 欧源 on 2017/4/29.
 * 作物类
 */

public class Crop implements Serializable {

    public int id;

    public String name;

    public boolean isSelected;

    public List<Plant> plant;
}
