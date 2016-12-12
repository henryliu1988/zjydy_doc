package com.zhjydy_doc.model.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
public class NormalPickViewData implements IPickerViewData {


    private NormalDicItem mItem;
    public NormalPickViewData(NormalDicItem item) {
        this.mItem = item;
    }

    public NormalDicItem getmItem() {
        return mItem;
    }

    public void setmItem(NormalDicItem mItem) {
        this.mItem = mItem;
    }

    @Override
    public String getPickerViewText() {
        return mItem.getName();
    }
}
