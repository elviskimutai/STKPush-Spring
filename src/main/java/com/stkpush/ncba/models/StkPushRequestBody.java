package com.stkpush.ncba.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StkPushRequestBody {
    @JsonProperty(value ="Username")
    private String username;
    @JsonProperty(value ="Password")
    private String password;
    @JsonProperty(value ="TelephoneNo")
    private String telephoneNo;
    @JsonProperty(value ="Amount")
    private String amount;
    @JsonProperty(value ="TransactionID")
    private String transactionID;
    @JsonProperty(value ="PayBillNo")
    private String payBillNo;
    @JsonProperty(value ="AccountNo")
    private String accountNo;
    @JsonProperty(value ="Network")
    private String network;
    @JsonProperty(value ="TransactionType")
    private String transactionType;

    public StkPushRequestBody() {
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getPayBillNo() {
        return payBillNo;
    }

    public void setPayBillNo(String payBillNo) {
        this.payBillNo = payBillNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }
}
