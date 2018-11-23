package com.ammar.saifiyahschool.teachers.LeaveApproval;

public class LeaveApprovalData {

    String leaveApproveDate;
    String leaveApproveMonth;
    String leaveApproveTeacherName;
    String leaveApproveTypeOfLeave;
    String leaveApproveFromDate;
    String leaveApproveToDate;
    String leaveApproveReason;
    String leaveStatusAfterAction;

    public LeaveApprovalData(String leaveApproveDate, String leaveApproveMonth, String leaveApproveTeacherName, String leaveApproveTypeOfLeave, String leaveApproveFromDate, String leaveApproveToDate, String leaveApproveReason, String leaveStatusAfterAction) {
        this.leaveApproveDate = leaveApproveDate;
        this.leaveApproveMonth = leaveApproveMonth;
        this.leaveApproveTeacherName = leaveApproveTeacherName;
        this.leaveApproveTypeOfLeave = leaveApproveTypeOfLeave;
        this.leaveApproveFromDate = leaveApproveFromDate;
        this.leaveApproveToDate = leaveApproveToDate;
        this.leaveApproveReason = leaveApproveReason;
        this.leaveStatusAfterAction = leaveStatusAfterAction;
    }

    public String getLeaveApproveDate() {
        return leaveApproveDate;
    }

    public void setLeaveApproveDate(String leaveApproveDate) {
        this.leaveApproveDate = leaveApproveDate;
    }

    public String getLeaveApproveMonth() {
        return leaveApproveMonth;
    }

    public void setLeaveApproveMonth(String leaveApproveMonth) {
        this.leaveApproveMonth = leaveApproveMonth;
    }

    public String getLeaveApproveTeacherName() {
        return leaveApproveTeacherName;
    }

    public void setLeaveApproveTeacherName(String leaveApproveTeacherName) {
        this.leaveApproveTeacherName = leaveApproveTeacherName;
    }

    public String getLeaveApproveTypeOfLeave() {
        return leaveApproveTypeOfLeave;
    }

    public void setLeaveApproveTypeOfLeave(String leaveApproveTypeOfLeave) {
        this.leaveApproveTypeOfLeave = leaveApproveTypeOfLeave;
    }

    public String getLeaveApproveFromDate() {
        return leaveApproveFromDate;
    }

    public void setLeaveApproveFromDate(String leaveApproveFromDate) {
        this.leaveApproveFromDate = leaveApproveFromDate;
    }

    public String getLeaveApproveToDate() {
        return leaveApproveToDate;
    }

    public void setLeaveApproveToDate(String leaveApproveToDate) {
        this.leaveApproveToDate = leaveApproveToDate;
    }

    public String getLeaveApproveReason() {
        return leaveApproveReason;
    }

    public void setLeaveApproveReason(String leaveApproveReason) {
        this.leaveApproveReason = leaveApproveReason;
    }

    public String getLeaveStatusAfterAction() {
        return leaveStatusAfterAction;
    }

    public void setLeaveStatusAfterAction(String leaveStatusAfterAction) {
        this.leaveStatusAfterAction = leaveStatusAfterAction;
    }
}
