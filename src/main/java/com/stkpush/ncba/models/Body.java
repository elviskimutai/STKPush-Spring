package com.stkpush.ncba.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Body {
    @JsonProperty(value ="stkCallback")
    private stkCallback stkCallback;

    public com.stkpush.ncba.models.stkCallback getStkCallback() {
        return stkCallback;
    }

    public void setStkCallback(com.stkpush.ncba.models.stkCallback stkCallback) {
        this.stkCallback = stkCallback;
    }
}
