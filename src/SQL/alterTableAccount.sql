ALTER TABLE "Bank".account ADD CONSTRAINT account_fk FOREIGN KEY (username) REFERENCES "Bank".customer(username);
