package com.ammar.saifiyahschool;

public class NoticeBoardData {

    String notificationTeacherName;
    String notificationTime;
    String notificationMsg;

    public NoticeBoardData(String notificationTeacherName, String notificationTime, String notificationMsg) {
        this.notificationTeacherName = notificationTeacherName;
        this.notificationTime = notificationTime;
        this.notificationMsg = notificationMsg;
    }

    public String getNotificationTeacherName() {
        return notificationTeacherName;
    }

    public void setNotificationTeacherName(String notificationTeacherName) {
        this.notificationTeacherName = notificationTeacherName;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getNotificationMsg() {
        return notificationMsg;
    }

    public void setNotificationMsg(String notificationMsg) {
        this.notificationMsg = notificationMsg;
    }
}
