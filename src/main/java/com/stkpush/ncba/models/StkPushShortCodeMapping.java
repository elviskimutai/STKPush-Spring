package com.stkpush.ncba.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name ="stk_push_short_code_mapping")
public class StkPushShortCodeMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /*
     0=in active
     1=active
  */
    @Column(nullable = false)
    private int status;
    @Column(nullable = false)
    private String shortcode;
    @Column(nullable = false)
    private String passKey;
    /*
     The organization that receives the funds. The parameter expected is
      a 5 to 6-digit as defined in the Shortcode description above.
      This can be the same as the BusinessShortCode value above.
  */
    @Column(nullable = false)
    private String partyB;
    private String remarks;
    @Column(nullable = false)
    private String consumerKey;
    @Column(nullable = false)
    private String  consumerSecret;
    private String  callBackURL;
    @Column(nullable = false)
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public String getCallBackURL() {
        return callBackURL;
    }

    public void setCallBackURL(String callBackURL) {
        this.callBackURL = callBackURL;
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

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public String getPassKey() {
        return passKey;
    }

    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
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
}
