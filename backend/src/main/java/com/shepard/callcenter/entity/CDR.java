package com.shepard.callcenter.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity(name = "cdr")
@RestResource(path = "cdr", rel = "cdr")
public class CDR {
    @Id
    @Column(name = "cdr_pkey")
    private Long id;

    @Column(name = "calldate")
    private Timestamp callDate;

    @Column(name = "clid")
    private String clid;

    @Column(name = "src")
    private String src;

    @Column(name = "dst")
    private String dst;

    @Column(name = "dcontext")
    private String dContext;

    @Column(name = "channel")
    private String channel;

    @Column(name = "dstchannel")
    private String dstChannel;

    @Column(name = "lastapp")
    private String lastApp;

    @Column(name = "lastdata")
    private String lastData;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "billsec")
    private Long billSec;

    @Column(name = "disposition")
    private String disposition;

    @Column(name = "amaflags")
    private Integer amaFlags;

    @Column(name = "accountcode")
    private String accountCode;

    @Column(name = "uniqueid")
    private String uniqueId;

    @Column(name = "userfield")
    private String userField;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCallDate() {
        return callDate;
    }

    public void setCallDate(Timestamp callDate) {
        this.callDate = callDate;
    }

    public String getClid() {
        return clid;
    }

    public void setClid(String clid) {
        this.clid = clid;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getdContext() {
        return dContext;
    }

    public void setdContext(String dContext) {
        this.dContext = dContext;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDstChannel() {
        return dstChannel;
    }

    public void setDstChannel(String dstChannel) {
        this.dstChannel = dstChannel;
    }

    public String getLastApp() {
        return lastApp;
    }

    public void setLastApp(String lastApp) {
        this.lastApp = lastApp;
    }

    public String getLastData() {
        return lastData;
    }

    public void setLastData(String lastData) {
        this.lastData = lastData;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getBillSec() {
        return billSec;
    }

    public void setBillSec(Long billSec) {
        this.billSec = billSec;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public Integer getAmaFlags() {
        return amaFlags;
    }

    public void setAmaFlags(Integer amaFlags) {
        this.amaFlags = amaFlags;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUserField() {
        return userField;
    }

    public void setUserField(String userField) {
        this.userField = userField;
    }
}
