package com.ammar.saifiyahschool.teachers.Progress;

public class viewProgressData {

    String studentProgressClass;
    String studentProgressName;
    String studentProgresstitle;
    String studentProgressReward;
    String studentProgressmonth;
    String studentProgressDay;

    public viewProgressData(String studentProgressClass, String studentProgressName, String studentProgresstitle, String studentProgressReward, String studentProgressmonth, String studentProgressDay) {
        this.studentProgressClass = studentProgressClass;
        this.studentProgressName = studentProgressName;
        this.studentProgresstitle = studentProgresstitle;
        this.studentProgressReward = studentProgressReward;
        this.studentProgressmonth = studentProgressmonth;
        this.studentProgressDay = studentProgressDay;
    }

    public String getStudentProgressClass() {
        return studentProgressClass;
    }

    public void setStudentProgressClass(String studentProgressClass) {
        this.studentProgressClass = studentProgressClass;
    }

    public String getStudentProgressName() {
        return studentProgressName;
    }

    public void setStudentProgressName(String studentProgressName) {
        this.studentProgressName = studentProgressName;
    }

    public String getStudentProgresstitle() {
        return studentProgresstitle;
    }

    public void setStudentProgresstitle(String studentProgresstitle) {
        this.studentProgresstitle = studentProgresstitle;
    }

    public String getStudentProgressReward() {
        return studentProgressReward;
    }

    public void setStudentProgressReward(String studentProgressReward) {
        this.studentProgressReward = studentProgressReward;
    }

    public String getStudentProgressmonth() {
        return studentProgressmonth;
    }

    public void setStudentProgressmonth(String studentProgressmonth) {
        this.studentProgressmonth = studentProgressmonth;
    }

    public String getStudentProgressDay() {
        return studentProgressDay;
    }

    public void setStudentProgressDay(String studentProgressDay) {
        this.studentProgressDay = studentProgressDay;
    }
}
