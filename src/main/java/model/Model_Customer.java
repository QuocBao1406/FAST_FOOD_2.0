package model;

public class Model_Customer {
	private String  Customer_Id;
	private String  Customer_Name;
	private String Customer_Sex;
	private String Customer_Address;
	private String Customer_Rank;
	private double Customer_Total;
	
	public Model_Customer(String customer_Id, String customer_Name, String customer_Sex, String customer_Address, double customer_Total, String customer_Rank) {
		Customer_Id = customer_Id;
		Customer_Name = customer_Name;
		Customer_Sex = customer_Sex;
		Customer_Address = customer_Address;
		Customer_Total = customer_Total;
		Customer_Rank = customer_Rank;
	}
	
	public Model_Customer(String customer_Id, String customer_Name, String customer_Sex, String customer_Address) {
		Customer_Id = customer_Id;
		Customer_Name = customer_Name;
		Customer_Sex = customer_Sex;
		Customer_Address = customer_Address;
	}
	
	
	public Model_Customer(String customer_Name, String customer_Sex, String customer_Address, double customer_Total, String customer_Rank) {
		Customer_Name = customer_Name;
		Customer_Sex = customer_Sex;
		Customer_Address = customer_Address;
		Customer_Total = customer_Total;		
		Customer_Rank = customer_Rank;
	}

	public String getCustomer_Id() {
		return Customer_Id;
	}

	public void setCustomer_Id(String customer_Id) {
		Customer_Id = customer_Id;
	}

	public String getCustomer_Name() {
		return Customer_Name;
	}

	public void setCustomer_Name(String customer_Name) {
		Customer_Name = customer_Name;
	}

	public String getCustomer_Sex() {
		return Customer_Sex;
	}

	public void setCustomer_Sex(String customer_Sex) {
		Customer_Sex = customer_Sex;
	}

	public String getCustomer_Address() {
		return Customer_Address;
	}

	public void setCustomer_Address(String customer_Address) {
		Customer_Address = customer_Address;
	}
	
	public double getCustomer_Total() {
		return Customer_Total;
	}

	public void setCustomer_Total(double customer_Total) {
		Customer_Total = customer_Total;
	}

	public String getCustomer_Rank() {
		return Customer_Rank;
	}

	public void setCustomer_Rank(String customer_Rank) {
		Customer_Rank = customer_Rank;
	}
	
	public double addTotal(double customer_Total) {
		return Customer_Total += customer_Total;
	}
}
