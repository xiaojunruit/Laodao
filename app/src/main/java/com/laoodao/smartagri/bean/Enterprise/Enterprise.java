package com.laoodao.smartagri.bean.Enterprise;

import com.laoodao.smartagri.utils.LogUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WORK on 2017/7/1.
 */

public class Enterprise implements Serializable {
    public List<EnterpriseData> data;
    public String validate;


    public List<EnterpriseData> normalData() {
        List<EnterpriseData> str = new ArrayList<>();
        if (data.size() < 1) {
            return null;
        }
        for (int i = 0; i < data.size(); i++) {
            if ("正常".equals(data.get(i).state)) {
                str.add(data.get(i));
            }
        }
        return str;
    }

    public List<EnterpriseData> blacklistData() {
        List<EnterpriseData> str = new ArrayList<>();
        if (data.size() < 1) {
            return null;
        }
        for (int i = 0; i < data.size(); i++) {
            if (!"正常".equals(data.get(i).state)) {
                str.add(data.get(i));
            }
        }
        return str;
    }
}
