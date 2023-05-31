package com.stkpush.ncba.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse {
    @JsonProperty(value ="TransactionID")
    private String transactionID;
    /*
     0=Success
     1=fail
  */
    @JsonProperty(value ="StatusCode")
    private String statusCode;
    @JsonProperty(value ="StatusDescription")
    private String statusDescription;
    @JsonProperty(value ="ReferenceID")
    private String referenceID;

    public ApiResponse() {
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(String referenceID) {
        this.referenceID = referenceID;
    }
}
