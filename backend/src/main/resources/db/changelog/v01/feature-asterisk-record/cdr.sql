CREATE TABLE cdr (
        cdr_pkey serial PRIMARY KEY,
        calldate timestamp NOT NULL ,
        clid varchar (80) NOT NULL ,
        src varchar (80) NOT NULL ,
        dst varchar (80) NOT NULL ,
        dcontext varchar (80) NOT NULL ,
        channel varchar (80) NOT NULL ,
        dstchannel varchar (80) NOT NULL ,
        lastapp varchar (80) NOT NULL ,
        lastdata varchar (80) NOT NULL ,
        duration int NOT NULL ,
        billsec int NOT NULL ,
        disposition varchar (45) NOT NULL ,
        amaflags int NOT NULL ,
        accountcode varchar (20) NOT NULL ,
        uniqueid varchar (150) NOT NULL ,
        userfield varchar (255) NOT NULL
);