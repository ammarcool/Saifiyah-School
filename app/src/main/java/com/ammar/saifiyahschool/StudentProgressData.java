package com.ammar.saifiyahschool;

public class StudentProgressData {

    int card;
    String progressDate;
    String progressDesc;

    public StudentProgressData(int card, String progressDate, String progressDesc) {

        this.card = card;
        this.progressDate = progressDate;
        this.progressDesc = progressDesc;
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public String getProgressDate() {
        return progressDate;
    }

    public void setProgressDate(String progressDate) {
        this.progressDate = progressDate;
    }

    public String getProgressDesc() {
        return progressDesc;
    }

    public void setProgressDesc(String progressDesc) {
        this.progressDesc = progressDesc;
    }
}
