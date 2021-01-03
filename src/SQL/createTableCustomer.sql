CREATE TABLE "Bank".customer (
	username varchar(30) NOT NULL,
	"password" varchar(30) NOT NULL,
	firstname varchar(30) NOT NULL,
	lastname varchar(30) NOT NULL,
	contact int8 NOT NULL,
	ispending boolean NOT NULL,
	CONSTRAINT customer_pk PRIMARY KEY (username)
);