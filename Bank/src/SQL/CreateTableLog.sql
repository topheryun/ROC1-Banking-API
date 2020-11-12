CREATE TABLE bank.log (
	id int NOT NULL,
	"type" varchar(15) NOT NULL,
	amount int8 NOT NULL,
	"timestamp" timestamp(0) NOT NULL,
	accountnumber int NOT NULL,
	CONSTRAINT log_pk PRIMARY KEY (id),
	CONSTRAINT log_fk FOREIGN KEY (accountnumber) REFERENCES bank.account(accountnumber)
);