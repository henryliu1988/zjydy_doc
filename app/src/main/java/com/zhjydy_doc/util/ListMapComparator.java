package com.zhjydy_doc.util;

import android.text.TextUtils;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/10 0010.
 */
public class ListMapComparator implements Comparator <Map<String,Object>>{

    private String key;
    private Object defaultValue;
    public ListMapComparator(String key,Object defaultValue) {
        super();
        this.key = key;
        this.defaultValue = defaultValue;
    }
    @Override
    public int compare(Map<String, Object> lhs, Map<String, Object> rhs) {
        if (TextUtils.isEmpty(key) || defaultValue == null) {
            return 0;
        }
        if (defaultValue instanceof Integer) {
            int lh = Utils.toInteger(lhs.get(key));
            int rh = Utils.toInteger(rhs.get(key));

            if (lh == rh) {
                return 0;
            } else if (lh > rh) {
                return -1;
            } else {
                return 1;
            }
        }
        else if (defaultValue instanceof Long){
            long lh = Utils.toLong(lhs.get(key));
            long rh = Utils.toLong(rhs.get(key));
            if (lh == rh) {
                return 0;
            } else if (lh > rh) {
                return -1;
            } else {
                return 1;
            }

        }
        else if (defaultValue instanceof String) {
            String lh = Utils.toString(lhs.get(key));
            String rh = Utils.toString(rhs.get(key));
            return 0;

        }
        return 0;
    }

}
