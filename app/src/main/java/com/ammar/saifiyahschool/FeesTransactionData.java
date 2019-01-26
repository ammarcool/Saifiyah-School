package com.ammar.saifiyahschool;

public class FeesTransactionData {

    String transactionName;
    String transactionStartDate;
    String transactionEndDate;
    String transactionAmount;
    String transactionSubmitDate;

    public FeesTransactionData(String transactionName, String transactionStartDate, String transactionEndDate, String transactionAmount, String transactionSubmitDate) {
        this.transactionName = transactionName;
        this.transactionStartDate = transactionStartDate;
        this.transactionEndDate = transactionEndDate;
        this.transactionAmount = transactionAmount;
        this.transactionSubmitDate = transactionSubmitDate;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getTransactionStartDate() {
        return transactionStartDate;
    }

    public void setTransactionStartDate(String transactionStartDate) {
        this.transactionStartDate = transactionStartDate;
    }

    public String getTransactionEndDate() {
        return transactionEndDate;
    }

    public void setTransactionEndDate(String transactionEndDate) {
        this.transactionEndDate = transactionEndDate;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionSubmitDate() {
        return transactionSubmitDate;
    }

    public void setTransactionSubmitDate(String transactionSubmitDate) {
        this.transactionSubmitDate = transactionSubmitDate;
    }
}
