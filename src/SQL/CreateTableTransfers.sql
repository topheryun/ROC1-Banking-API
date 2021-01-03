CREATE TABLE bank.transfers (
	id int NOT NULL,
	amount int8 NOT NULL,
	accountnumber int NOT NULL,
	CONSTRAINT transfers_pk PRIMARY KEY (id),
	CONSTRAINT transfers_fk FOREIGN KEY (accountnumber) REFERENCES bank.account(accountnumber)
);