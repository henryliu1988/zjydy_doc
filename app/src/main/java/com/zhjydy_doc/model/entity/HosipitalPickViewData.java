package com.zhjydy_doc.model.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
public class HosipitalPickViewData implements IPickerViewData {


    private HospitalDicItem hospitalDicItem;

    public HosipitalPickViewData(HospitalDicItem hospitalDicItem) {
        this.hospitalDicItem = hospitalDicItem;
    }

    public HospitalDicItem getHospitalDicItem() {
        return hospitalDicItem;
    }

    public void setHospitalDicItem(HospitalDicItem hospitalDicItem) {
        this.hospitalDicItem = hospitalDicItem;
    }

    @Override
    public String getPickerViewText() {
        return hospitalDicItem.getHospital();
    }
}
