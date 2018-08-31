package com.ammar.saifiyahschool;

public class classTestData {

    String testSubjects;
    String monthName;
    Integer dayNumber;
    String teacherNames;
    Integer testMark;
    Integer totalMark;

    public classTestData(String testSubjects, String monthName, Integer dayNumber, String teacherNames, Integer testMark, Integer totalMark) {
        this.testSubjects = testSubjects;
        this.monthName = monthName;
        this.dayNumber = dayNumber;
        this.teacherNames = teacherNames;
        this.testMark = testMark;
        this.totalMark = totalMark;
    }

    //Getter and Setter


    public String getTestSubjects() {
        return testSubjects;
    }

    public void setTestSubjects(String testSubjects) {
        this.testSubjects = testSubjects;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getTeacherNames() {
        return teacherNames;
    }

    public void setTeacherNames(String teacherNames) {
        this.teacherNames = teacherNames;
    }

    public Integer getTestMark() {
        return testMark;
    }

    public void setTestMark(Integer testMark) {
        this.testMark = testMark;
    }

    public Integer getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(Integer totalMark) {
        this.totalMark = totalMark;
    }
}
