package com.ejz.update;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by WORK on 2017/3/20.
 */

public class UpdateInfo {
    public boolean hasUpdate = false;
    public boolean isForce = false;
    public boolean isPatch = false;
    public String versionCode;
    public String versionName = null;
    public String updateContent = null;
    public String url;
    public String md5;
    public long size;
    public String patchUrl;
    public String patchMd5;
    public long patchSize;

    public UpdateInfo() {
    }

    public static UpdateInfo parse(String s) {
        if(s == null) {
            return null;
        } else {
            try {
                JSONObject o = new JSONObject(s);
                return parse(o.has("data")?o.getJSONObject("data"):o);
            } catch (JSONException var2) {
                return null;
            }
        }
    }

    public static UpdateInfo parse(JSONObject o) {
        UpdateInfo info = new UpdateInfo();
        if(o == null) {
            return info;
        } else {
            info.hasUpdate = o.optBoolean("hasUpdate", false);
            if(!info.hasUpdate) {
                return info;
            } else {
                info.isForce = o.optBoolean("isForce", false);
                info.isPatch = o.optBoolean("isPatch", false);
                info.versionName = o.optString("versionName");
                info.updateContent = o.optString("updateContent");
                info.url = o.optString("url");
                info.md5 = o.optString("md5");
                info.size = o.optLong("size", 0L);
                if(!info.isPatch) {
                    return info;
                } else {
                    info.patchUrl = o.optString("patchUrl");
                    info.patchMd5 = o.optString("patchMd5");
                    info.patchSize = o.optLong("patchSize", 0L);
                    return info;
                }
            }
        }
    }
}
