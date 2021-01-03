package com.bank.model;

public class Customer {
	
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private long contact;
	private long balance;
	
	public Customer(String userName, String firstName, String lastName, long contact, long balance) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contact = contact;
		this.balance = balance;
	}
	
	public Customer(String userName, String firstName, String lastName, long contact) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contact = contact;
	}
	
	@Override
	public String toString() {
		return "[Account User Name: " + userName + ", First Name: " + firstName + 
				", Last Name: " + lastName + ", Contact: " + contact + "]";
	}
	
	public Customer() {
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getContact() {
		return contact;
	}
	public void setContact(long contact) {
		this.contact = contact;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}

}
