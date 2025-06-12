package model;

public class Model_Login {
	private String username;
	private String password;
	private int active;
	private String name;
	private int floor;
	private String email;

	public Model_Login(String username, String password, int active, String name, int floor, String email) {
		this.username = username;
		this.password = password;
		this.active = active;
		this.name = name;
		this.floor = floor;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
