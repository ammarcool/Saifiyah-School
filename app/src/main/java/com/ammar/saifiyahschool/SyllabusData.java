package com.ammar.saifiyahschool;

import android.widget.ImageView;

public class SyllabusData {

    private String syllabusMonth;
    private String syllabusDay;
    private String SyllabusStudentChapterNo;
    private String syllabusName;
    private String syllabusStatus;

    public SyllabusData(String syllabusMonth, String syllabusDay, String syllabusStudentChapterNo, String syllabusName, String syllabusStatus) {
        this.syllabusMonth = syllabusMonth;
        this.syllabusDay = syllabusDay;
        this.SyllabusStudentChapterNo = syllabusStudentChapterNo;
        this.syllabusName = syllabusName;
        this.syllabusStatus = syllabusStatus;
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
}
