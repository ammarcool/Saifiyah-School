package com.ammar.saifiyahschool.teachers;

public class viewCTData {

    Integer ctDay;
    String ctMonth;
    String ctSubject;
    Integer ctMarks;
    String ctClass;
    String deletePosition;

    public viewCTData(Integer ctDay, String ctMonth, String ctSubject, Integer ctMarks, String ctClass, String deletePosition) {
        this.ctDay = ctDay;
        this.ctMonth = ctMonth;
        this.ctSubject = ctSubject;
        this.ctMarks = ctMarks;
        this.ctClass = ctClass;
        this.deletePosition = deletePosition;
    }

    public Integer getCtDay() {
        return ctDay;
    }

    public void setCtDay(Integer ctDay) {
        this.ctDay = ctDay;
    }

    public String getCtMonth() {
        return ctMonth;
    }

    public void setCtMonth(String ctMonth) {
        this.ctMonth = ctMonth;
    }

    public String getCtSubject() {
        return ctSubject;
    }

    public void setCtSubject(String ctSubject) {
        this.ctSubject = ctSubject;
    }

    public Integer getCtMarks() {
        return ctMarks;
    }

    public void setCtMarks(Integer ctMarks) {
        this.ctMarks = ctMarks;
    }

    public String getCtClass() {
        return ctClass;
    }

    public void setCtClass(String ctClass) {
        this.ctClass = ctClass;
    }

    public String getDeletePosition() {
        return deletePosition;
    }

    public void setDeletePosition(String deletePosition) {
        this.deletePosition = deletePosition;
    }
}
