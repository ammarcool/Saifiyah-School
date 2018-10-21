package com.ammar.saifiyahschool.teachers.AddClassTest;

public class addCTMarksData {

    String studentRollno;
    String studentCTName;
    String enterCT;

    public addCTMarksData(String studentRollno, String studentCTName) {
        this.studentRollno = studentRollno;
        this.studentCTName = studentCTName;
    }
    public addCTMarksData(String enterCT){
        this.enterCT = enterCT;
    }

    public String getStudentRollno() {
        return studentRollno;
    }

    public void setStudentRollno(String studentRollno) {
        this.studentRollno = studentRollno;
    }

    public String getStudentCTName() {
        return studentCTName;
    }

    public void setStudentCTName(String studentCTName) {
        this.studentCTName = studentCTName;
    }

    public String getEnterCT() {
        return enterCT;
    }

    public void setEnterCT(String enterCT) {
        this.enterCT = enterCT;
    }
}
