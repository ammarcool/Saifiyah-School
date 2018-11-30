package com.ammar.saifiyahschool.teachers.LeaveBalance;

public class viewLeaveBalanceData {

    String viewLBDay;
    String viewLBMonth;
    String viewFromDate;
    String viewToDate;
    String viewNoOfLeaves;
    String viewTypesOfLeaves;
    String viewLBStatus;
    String teacherHalfDay;

    public viewLeaveBalanceData(String viewLBDay, String viewLBMonth, String viewFromDate, String viewToDate, String viewNoOfLeaves, String viewTypesOfLeaves, String viewLBStatus, String teacherHalfDay) {
        this.viewLBDay = viewLBDay;
        this.viewLBMonth = viewLBMonth;
        this.viewFromDate = viewFromDate;
        this.viewToDate = viewToDate;
        this.viewNoOfLeaves = viewNoOfLeaves;
        this.viewTypesOfLeaves = viewTypesOfLeaves;
        this.viewLBStatus = viewLBStatus;
        this.teacherHalfDay = teacherHalfDay;
    }

    public String getViewLBDay() {
        return viewLBDay;
    }

    public void setViewLBDay(String viewLBDay) {
        this.viewLBDay = viewLBDay;
    }

    public String getViewLBMonth() {
        return viewLBMonth;
    }

    public void setViewLBMonth(String viewLBMonth) {
        this.viewLBMonth = viewLBMonth;
    }

    public String getViewFromDate() {
        return viewFromDate;
    }

    public void setViewFromDate(String viewFromDate) {
        this.viewFromDate = viewFromDate;
    }

    public String getViewToDate() {
        return viewToDate;
    }

    public void setViewToDate(String viewToDate) {
        this.viewToDate = viewToDate;
    }

    public String getViewNoOfLeaves() {
        return viewNoOfLeaves;
    }

    public void setViewNoOfLeaves(String viewNoOfLeaves) {
        this.viewNoOfLeaves = viewNoOfLeaves;
    }

    public String getViewTypesOfLeaves() {
        return viewTypesOfLeaves;
    }

    public void setViewTypesOfLeaves(String viewTypesOfLeaves) {
        this.viewTypesOfLeaves = viewTypesOfLeaves;
    }

    public String getViewLBStatus() {
        return viewLBStatus;
    }

    public void setViewLBStatus(String viewLBStatus) {
        this.viewLBStatus = viewLBStatus;
    }

    public String getTeacherHalfDay() {
        return teacherHalfDay;
    }

    public void setTeacherHalfDay(String teacherHalfDay) {
        this.teacherHalfDay = teacherHalfDay;
    }
}
