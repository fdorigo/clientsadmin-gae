package com.igadmin.data;

public enum PackagePaymentType
{
	CREDITCARD("CreditCard"), CHECK("Check"), CASH("Cash"), OTHER("Other"), UNKNOWN("Unknown");
	
	private String displayName;
	
	private PackagePaymentType(String name)
	{
		this.displayName = name;
	}
	
	public String getDispalyName()
	{
		return this.displayName;
	}
}
