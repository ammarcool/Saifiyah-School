package com.ammar.saifiyahschool.teachers.AddClassTest;

import java.util.List;

public class classSubjectData {

    private int classId;
    private String className;
    private String subjectName;
    private String subjectId;

    public classSubjectData(int classId, String className) {
        this.classId = classId;
        this.className = className;
    }

    public classSubjectData(String subjectName, String subjectId){
        this.subjectName = subjectName;
        this.subjectId = subjectId;
    }

    public int getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectId() {
        return subjectId;
    }
}
