package com.shepard.callcenter.entity;

import java.sql.Timestamp;

public class CDR {
    private Long id;
    private Timestamp callDate;
    private String clid;
    private String src;
    private String dst;
    private String dContext;
    private String channel;
    private String dstChannel;
    private String lastApp;
    private String lastData;
    private Long duration;
    private Long billSec;
    private String disposition;
    private Integer amaFlags;
    private String accountCode;
    private String uniqueId;
    private String userField;
}
