CREATE TABLE "Bank".account (
	accountnumber int NOT NULL,
	balance int8 NOT NULL,
	username varchar(30) NOT NULL,
	ispending boolean NOT NULL,
	CONSTRAINT account_pk PRIMARY KEY (accountnumber),
);