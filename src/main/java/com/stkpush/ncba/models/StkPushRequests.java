package com.stkpush.ncba.models;





import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name ="stk_push_requests")
public class StkPushRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /*
     0=Pending
     1=sent to safaricom
     2=send to safaricom failed
     3=processing
  */
    @Column(nullable = false)
    private int status;
    @Column(nullable = false)
    private String telephoneNo;
    @Column(nullable = false)
    private String amount;
    @Column(nullable = false)
    private String transactionID;
    @Column(nullable = false)
    private String payBillNo;
    @Column(nullable = false)
    private String accountNo;
    @Column(nullable = false)
    private String network;
    private String remarks;
    /*
   CustomerPayBillOnline
   CustomerBuyGoodsOnline
    */
    @Column(nullable = false)
    private String transactionType;
    @Column(nullable = false)
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String  merchantRequestID;
    private String  checkoutRequestID;
    private String responseDesc;
    private String  responseCode;
    @Column(length=5000)
    private String  responseBody;
    private String  runId;
    public StkPushRequests() {
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getMerchantRequestID() {
        return merchantRequestID;
    }

    public void setMerchantRequestID(String merchantRequestID) {
        this.merchantRequestID = merchantRequestID;
    }

    public String getCheckoutRequestID() {
        return checkoutRequestID;
    }

    public void setCheckoutRequestID(String checkoutRequestID) {
        this.checkoutRequestID = checkoutRequestID;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "StkPushRequests{" +
                "id=" + id +
                ", status=" + status +
                ", telephoneNo='" + telephoneNo + '\'' +
                ", amount='" + amount + '\'' +
                ", transactionID='" + transactionID + '\'' +
                ", payBillNo='" + payBillNo + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", network='" + network + '\'' +
                ", remarks='" + remarks + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
