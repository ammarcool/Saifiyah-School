package com.ammar.saifiyahschool;

import android.widget.ImageView;

public class SyllabusData {

    private String month;
    private String UnitName;
    private String UnitDesc;
    private int status;

    public SyllabusData(String month, String UnitName, String UnitDesc, int status){
        this.month = month;
        this.UnitName = UnitName;
        this.UnitDesc = UnitDesc;
        this.status = status;

    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getUnitDesc() {
        return UnitDesc;
    }

    public void setUnitDesc(String unitDesc) {
        UnitDesc = unitDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
