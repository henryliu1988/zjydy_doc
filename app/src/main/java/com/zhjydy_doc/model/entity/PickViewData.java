package com.zhjydy_doc.model.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
public class PickViewData implements IPickerViewData {

    private String id;
    private String name;

    public PickViewData(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
