package com.bank.model;

public class Log {
	
	private int id;
	private String type;
	private float amount;
	private String ldt;
	
	public Log(int id, String type, float amount, String ldt) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.ldt = ldt;
	}
	
	@Override
	public String toString() {
		String ret = "[" + ldt + "] ";
		if (type.equals("deposit")) {
			ret += String.format("Deposited: $%.2f", amount);
		}
		else if (type.equals("withdraw")) {
			amount *= -1;
			ret += String.format("Withdrew: $%.2f", amount);
		}
		else if (type.equals("transferTo")) {
			ret += String.format("Transfered: $%.2f", amount);
		}
		else if (type.equals("transferFrom")) {
			ret += String.format("Received: $%.2f", amount);
		}
		return ret;
	}
	
	public Log() {
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getLdt() {
		return ldt;
	}
	public void setLdt(String ldt) {
		this.ldt = ldt;
	}
	
}
