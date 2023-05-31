package com.stkpush.ncba.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class stkCallback {
    @JsonProperty(value ="MerchantRequestID")
    private String MerchantRequestID;
    @JsonProperty(value ="CheckoutRequestID")
    private String CheckoutRequestID;
    @JsonProperty(value ="ResultCode")
    private String ResultCode;
    @JsonProperty(value ="ResultDesc")
    private String ResultDesc;
    @JsonProperty(value ="CallbackMetadata")
    private CallbackMetadata CallbackMetadata;

    public String getMerchantRequestID() {
        return MerchantRequestID;
    }

    public void setMerchantRequestID(String merchantRequestID) {
        MerchantRequestID = merchantRequestID;
    }

    public String getCheckoutRequestID() {
        return CheckoutRequestID;
    }

    public void setCheckoutRequestID(String checkoutRequestID) {
        CheckoutRequestID = checkoutRequestID;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResultDesc() {
        return ResultDesc;
    }

    public void setResultDesc(String resultDesc) {
        ResultDesc = resultDesc;
    }

    public com.stkpush.ncba.models.CallbackMetadata getCallbackMetadata() {
        return CallbackMetadata;
    }

    public void setCallbackMetadata(com.stkpush.ncba.models.CallbackMetadata callbackMetadata) {
        CallbackMetadata = callbackMetadata;
    }
}
