package com.laoodao.smartagri.bean.cotton;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WORK on 2017/6/29.
 */

public class Cotton implements Serializable {
    public List<CottonData> data;
    public String jsInfo;
    public List<CottonJsxxList> jsxxList;
    public List<CottonJsxxList> jsxxListEnd0131;
    public List<CottonJsxxList> jsxxListEnd31;
    public String validate;
}
