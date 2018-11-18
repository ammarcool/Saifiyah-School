package com.ammar.saifiyahschool.teachers.Syllabus;

public class AddSyllabusData {

    String addSyllabusDay;
    String addSyllabusMonth;
    String syllabusTopicName;
    String syllabusChapterNo;
    String doneUndoUnit;
    String changeSyllabusName;

    public AddSyllabusData(String addSyllabusDay, String addSyllabusMonth, String syllabusTopicName, String syllabusChapterNo, String doneUndoUnit) {
        this.addSyllabusDay = addSyllabusDay;
        this.addSyllabusMonth = addSyllabusMonth;
        this.syllabusTopicName = syllabusTopicName;
        this.syllabusChapterNo = syllabusChapterNo;
        this.doneUndoUnit = doneUndoUnit;


    }

    public String getAddSyllabusDay() {
        return addSyllabusDay;
    }

    public void setAddSyllabusDay(String addSyllabusDay) {
        this.addSyllabusDay = addSyllabusDay;
    }

    public String getAddSyllabusMonth() {
        return addSyllabusMonth;
    }

    public void setAddSyllabusMonth(String addSyllabusMonth) {
        this.addSyllabusMonth = addSyllabusMonth;
    }

    public String getSyllabusTopicName() {
        return syllabusTopicName;
    }

    public void setSyllabusTopicName(String syllabusTopicName) {
        this.syllabusTopicName = syllabusTopicName;
    }

    public String getSyllabusChapterNo() {
        return syllabusChapterNo;
    }

    public void setSyllabusChapterNo(String syllabusChapterNo) {
        this.syllabusChapterNo = syllabusChapterNo;
    }

    public String getDoneUndoUnit() {
        return doneUndoUnit;
    }

    public void setDoneUndoUnit(String doneUndoUnit) {
        this.doneUndoUnit = doneUndoUnit;
    }

    public String getChangeSyllabusName() {
        return changeSyllabusName;
    }

    public void setChangeSyllabusName(String changeSyllabusName) {
        this.changeSyllabusName = changeSyllabusName;
    }
}
