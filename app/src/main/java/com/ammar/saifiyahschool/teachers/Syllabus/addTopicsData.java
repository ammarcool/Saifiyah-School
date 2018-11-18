package com.ammar.saifiyahschool.teachers.Syllabus;

public class addTopicsData {

    String syllabusTopicNo;
    String syllabusChapterName;

    public addTopicsData(String syllabusTopicNo) {
        this.syllabusTopicNo = syllabusTopicNo;
//        this.syllabusChapterName = syllabusChapterName;
    }

    public String getSyllabusTopicNo() {
        return syllabusTopicNo;
    }

    public void setSyllabusTopicNo(String syllabusTopicNo) {
        this.syllabusTopicNo = syllabusTopicNo;
    }

    public String getSyllabusChapterName() {
        return syllabusChapterName;
    }

    public void setSyllabusChapterName(String syllabusChapterName) {
        this.syllabusChapterName = syllabusChapterName;
    }
}
