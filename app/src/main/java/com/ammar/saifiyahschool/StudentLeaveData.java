package com.ammar.saifiyahschool;

public class StudentLeaveData {

    Integer leaveNo;
    String leaveMonth;
    Integer leaveDays;

    public StudentLeaveData(Integer leaveNo, String leaveMonth, Integer leaveDays) {
        this.leaveNo = leaveNo;
        this.leaveMonth = leaveMonth;
        this.leaveDays = leaveDays;
    }

    public Integer getLeaveNo() {
        return leaveNo;
    }

    public void setLeaveNo(Integer leaveNo) {
        this.leaveNo = leaveNo;
    }

    public String getLeaveMonth() {
        return leaveMonth;
    }

    public void setLeaveMonth(String leaveMonth) {
        this.leaveMonth = leaveMonth;
    }

    public Integer getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(Integer leaveDays) {
        this.leaveDays = leaveDays;
    }
}
