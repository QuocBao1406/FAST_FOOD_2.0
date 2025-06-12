package model;

public class Model_Statistical {
	private String month;
    private double total;
    private int quantity; // Thêm số lượng bán
    private String name;

    public Model_Statistical(String month, double total, int quantity, String name) {
        this.month = month;
        this.total = total;
        this.quantity = quantity;
        this.name = name;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    
}
