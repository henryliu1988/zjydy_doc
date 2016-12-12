package com.zhjydy_doc.model.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
public class DistricPickViewData implements IPickerViewData {


    private District district;
    public DistricPickViewData(District district) {
        this.district = district;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    @Override
    public String getPickerViewText() {
        return district.getName();
    }
}
