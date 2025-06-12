package model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Model_Sell {
	private String sell_id;
	private int sell_quantity;
	private double sell_total;
	private Timestamp sell_date;
	private String customer_id;
	private String sell_name;
	
	public Model_Sell(String sell_id, int sell_quantity, double sell_total, Timestamp sell_date, String customer_id, String sell_name) {
		this.sell_id = sell_id;
		this.sell_quantity = sell_quantity;
		this.sell_total = sell_total;
		this.sell_date = sell_date;
		this.customer_id = customer_id;
		this.sell_name = sell_name;
	}

	public String getSell_id() {
		return sell_id;
	}

	public void setSell_id(String sell_id) {
		this.sell_id = sell_id;
	}

	public int getSell_quantity() {
		return sell_quantity;
	}

	public void setSell_quantity(int sell_quantity) {
		this.sell_quantity = sell_quantity;
	}

	public Timestamp getSell_date() {
		return sell_date;
	}

	public void setSell_date(Timestamp sell_date) {
		this.sell_date = sell_date;
	}

	public double getSell_total() {
		return sell_total;
	}

	public void setSell_total(double sell_total) {
		this.sell_total = sell_total;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getSell_name() {
		return sell_name;
	}

	public void setSell_name(String sell_name) {
		this.sell_name = sell_name;
	}
	
	
}
