package com.ammar.saifiyahschool;

public class FeesTransactionData {

    String transactionName;
    String transactionDate;
    Integer transactionAmount;
    Integer feesDue;

    public FeesTransactionData(String transactionName, String transactionDate, int transactionAmount,int feesDue) {
        this.transactionName = transactionName;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.feesDue = feesDue;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Integer transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Integer getFeesDue() {
        return feesDue;
    }

    public void setFeesDue(Integer feesDue) {
        this.feesDue = feesDue;
    }
}
