package com.ammar.saifiyahschool.teachers.Syllabus;

public class AddSyllabusData {

    public enum ItemType {
        ONE_ITEM, TWO_ITEM;
    }

    String addSyllabusDay;
    String addSyllabusMonth;
    String syllabusTopicName;
    String syllabusChapterNo;
    String doneUndoUnit;
    String changeSyllabusName;
    String examinationName;

    ItemType type;

    public AddSyllabusData(String addSyllabusDay, String addSyllabusMonth, String syllabusTopicName, String syllabusChapterNo, String doneUndoUnit,ItemType type) {
        this.addSyllabusDay = addSyllabusDay;
        this.addSyllabusMonth = addSyllabusMonth;
        this.syllabusTopicName = syllabusTopicName;
        this.syllabusChapterNo = syllabusChapterNo;
        this.doneUndoUnit = doneUndoUnit;
        this.type = type;

    }

    public AddSyllabusData(String examinationName, ItemType type) {
        this.examinationName = examinationName;
        this.type = type;
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

    public String getExaminationName() {
        return examinationName;
    }

    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

}
