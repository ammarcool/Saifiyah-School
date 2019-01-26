package com.ammar.saifiyahschool;

import android.widget.ImageView;

public class SyllabusData {

    public enum StudentItemType {
        ONE_ITEM, TWO_ITEM;
    }

    private String syllabusMonth;
    private String syllabusDay;
    private String SyllabusStudentChapterNo;
    private String syllabusName;
    private String syllabusStatus;
    String examinationName;

    StudentItemType type;

    public SyllabusData(String syllabusMonth, String syllabusDay, String syllabusStudentChapterNo, String syllabusName, String syllabusStatus,StudentItemType type) {
        this.syllabusMonth = syllabusMonth;
        this.syllabusDay = syllabusDay;
        this.SyllabusStudentChapterNo = syllabusStudentChapterNo;
        this.syllabusName = syllabusName;
        this.syllabusStatus = syllabusStatus;
        this.type = type;
    }

    public SyllabusData(String examinationName, StudentItemType type) {
        this.examinationName = examinationName;
        this.type = type;
    }

    public String getSyllabusMonth() {
        return syllabusMonth;
    }

    public void setSyllabusMonth(String syllabusMonth) {
        this.syllabusMonth = syllabusMonth;
    }

    public String getSyllabusDay() {
        return syllabusDay;
    }

    public void setSyllabusDay(String syllabusDay) {
        this.syllabusDay = syllabusDay;
    }

    public String getSyllabusStudentChapterNo() {
        return SyllabusStudentChapterNo;
    }

    public void setSyllabusStudentChapterNo(String syllabusStudentChapterNo) {
        SyllabusStudentChapterNo = syllabusStudentChapterNo;
    }

    public String getSyllabusName() {
        return syllabusName;
    }

    public void setSyllabusName(String syllabusName) {
        this.syllabusName = syllabusName;
    }

    public String getSyllabusStatus() {
        return syllabusStatus;
    }

    public void setSyllabusStatus(String syllabusStatus) {
        this.syllabusStatus = syllabusStatus;
    }

    public String getExaminationName() {
        return examinationName;
    }

    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }

    public StudentItemType getType() {
        return type;
    }

    public void setType(StudentItemType type) {
        this.type = type;
    }
}
