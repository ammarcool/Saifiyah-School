package com.ammar.saifiyahschool.teachers.LeaveBalance;

public class leaveTypesData {

    String leaveTypeID;
    String leaveType;

    public leaveTypesData(String leaveTypeID, String leaveType) {
        this.leaveTypeID = leaveTypeID;
        this.leaveType = leaveType;
    }

    public String getLeaveTypeID() {
        return leaveTypeID;
    }

    public void setLeaveTypeID(String leaveTypeID) {
        this.leaveTypeID = leaveTypeID;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }
}
