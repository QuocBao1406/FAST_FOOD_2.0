package model;

public class Model_Food {
	private String food_id;
	private String food_name;
	private String food_type;
	private int food_price;
	private byte[] food_image;
	
	public Model_Food(String food_id, String food_name, String food_type, int food_price, byte[] food_image) {
		this.food_id = food_id;
		this.food_name = food_name;
		this.food_type = food_type;
		this.food_price = food_price;
		this.food_image = food_image;
	}

	public String getFood_id() {
		return food_id;
	}

	public void setFood_id(String food_id) {
		this.food_id = food_id;
	}

	public String getFood_name() {
		return food_name;
	}

	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}

	public String getFood_type() {
		return food_type;
	}

	public void setFood_type(String food_type) {
		this.food_type = food_type;
	}

	public int getFood_price() {
		return food_price;
	}

	public void setFood_price(int food_price) {
		this.food_price = food_price;
	}

	public byte[] getFood_image() {
		return food_image;
	}

	public void setFood_image(byte[] food_image) {
		this.food_image = food_image;
	}
	
	
}
