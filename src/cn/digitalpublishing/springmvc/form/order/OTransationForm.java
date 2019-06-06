package cn.digitalpublishing.springmvc.form.order;

import cn.digitalpublishing.springmvc.form.BaseForm;

public class OTransationForm extends BaseForm {
	
	private double balance=0d;
	//存储总额
	private double totalDeposits=0d;
	//消费总额
	private double totalSpending=0d;
	//冻结总额
	private double totalFreeze=0d;
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getTotalDeposits() {
		return totalDeposits;
	}
	public void setTotalDeposits(double totalDeposits) {
		this.totalDeposits = totalDeposits;
	}
	public double getTotalSpending() {
		return totalSpending;
	}
	public void setTotalSpending(double totalSpending) {
		this.totalSpending = totalSpending;
	}
	public double getTotalFreeze() {
		return totalFreeze;
	}
	public void setTotalFreeze(double totalFreeze) {
		this.totalFreeze = totalFreeze;
	}
}
