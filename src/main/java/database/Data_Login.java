package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

import model.Model_Food;
import model.Model_Login;
import view.Main;

public class Data_Login {
	private static Data_Login instance;
	private Connection conn;
	private static Main main;
	
	public Data_Login() {
		
	}
	
	public Data_Login(Main main) {
		this.main = main;
	}

	public static Data_Login getInstance() {
		if(instance == null) {
			instance = new Data_Login(main);
		}
		return instance;
	}
	
	public ArrayList<Model_Login> loadLogin() {
		ArrayList<Model_Login> list = new ArrayList<>();
		try {
			ConnectDatabase cn = new ConnectDatabase();
			conn = cn.getConnection();
			
			String sql = "SELECT * FROM LOGIN";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				String username = rs.getString(1);
				String password = rs.getString(2);
				int active = rs.getInt(3);
				String name = rs.getString(4);
				int floor = rs.getInt(5);
				String email = rs.getString(6);
				
				Model_Login login = new Model_Login(username, password, active, name, floor, email);
				
				list.add(login);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean deleteLogin(String username) {
		int result = 0;
		try {
			ConnectDatabase cn = new ConnectDatabase();
			Connection conn = cn.getConnection();
			
			String sql = "DELETE FROM LOGIN "
					+ "WHERE User_Name=?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, username);
			
			result = st.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Xóa tài khoản thành công!");
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Xóa tài khoản thất bại!");
		}
		return result > 0;
	}
	
	public boolean updateLogin(Model_Login login) {
		int result = 0;
		try {
			ConnectDatabase cn = new ConnectDatabase();
			Connection conn = cn.getConnection();
			
			String sql = "UPDATE LOGIN "
					+ "SET User_Name=?, Password=?, Active=?, Name=?, Floor=? "
					+ "WHERE User_Name=?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, login.getUsername());
			st.setString(2, login.getPassword());
			st.setInt(3, login.getActive());
			st.setString(4, login.getName());
			st.setInt(5, login.getFloor());
			
			st.setString(6, login.getUsername());
			
			result = st.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Cập nhật tài khoản thành công!");
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cập nhật tài khoản thất bại, vui lòng nhập lại!");
		}
		return result > 0;
	}
	
	public boolean addLogin(Model_Login login) {
		int result = 0;
		try {
			ConnectDatabase cn = new ConnectDatabase();
			Connection conn = cn.getConnection();
			
			String sql = "INSERT INTO LOGIN (User_Name, Password, Active, Name, Floor, Email) "
					+ "VALUES (?,?,?,?,?,?)";
			
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, login.getUsername());
			st.setString(2, login.getPassword());
			st.setInt(3, login.getActive());
			st.setString(4, login.getName());
			st.setInt(5, login.getFloor());
			st.setString(6, login.getEmail());
			
			result = st.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Thêm Tài Khoản Thành Công!");
			
			conn.close();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Thêm Tài Khoản Thất Bại, Vui Lòng Nhập Lại!");
			e.printStackTrace();
		}
		return result > 0;
	}
	
	public boolean validateAdmin(String username, String password) {
		try {
			ConnectDatabase cn = new ConnectDatabase();
			conn = cn.getConnection();
			
			String sql = "SELECT * FROM LOGIN WHERE User_Name=? AND Floor=0";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				String hashedPassword = rs.getString("Password");
				return BCrypt.checkpw(password, hashedPassword);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validate1(String username, String password) {
		try {
			ConnectDatabase cn = new ConnectDatabase();
			conn = cn.getConnection();
			
			String sql = "SELECT * FROM LOGIN WHERE User_Name=? AND Floor=1";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				String hashedPassword = rs.getString("Password");
				return BCrypt.checkpw(password, hashedPassword);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validate2(String username, String password) {
		try {
			ConnectDatabase cn = new ConnectDatabase();
			conn = cn.getConnection();
			
			String sql = "SELECT * FROM LOGIN WHERE User_Name=? AND Floor=2";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				String hashedPassword = rs.getString("Password");
				return BCrypt.checkpw(password, hashedPassword);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validate3(String username, String password) {
		try {
			ConnectDatabase cn = new ConnectDatabase();
			conn = cn.getConnection();
			
			String sql = "SELECT * FROM LOGIN WHERE User_Name=? AND Floor=3";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				String hashedPassword = rs.getString("Password");
				return BCrypt.checkpw(password, hashedPassword);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Main getMain() {
		return main;
	}
	
	public boolean isActive(String username) {
		int result = 0;
		try {
			ConnectDatabase cn = new ConnectDatabase();
			conn = cn.getConnection();
			
			String sql = "UPDATE LOGIN SET Active = 1 WHERE User_Name = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, username);
			
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result > 0;
    }
	
	public boolean isInActive(int floor) {
		int result = 0;
		try {
			ConnectDatabase cn = new ConnectDatabase();
			conn = cn.getConnection();
			
			String sql = "UPDATE LOGIN SET Active = 0 WHERE Floor = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, floor);
			
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result > 0;
    }
	
	public boolean updatePassword(Model_Login login) {
		int result = 0;
		try {
			ConnectDatabase cn = new ConnectDatabase();
			Connection conn = cn.getConnection();
			
			String sql = "UPDATE LOGIN "
					+ "SET User_Name=?, Password=?, Active=?, Name=?, Floor=? "
					+ "WHERE User_Name=?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, login.getUsername());
			st.setString(2, login.getPassword());
			st.setInt(3, login.getActive());
			st.setString(4, login.getName());
			st.setInt(5, login.getFloor());
			
			st.setString(6, login.getUsername());
			
			result = st.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Cập nhật tài khoản thành công!");
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cập nhật tài khoản thất bại, vui lòng nhập lại!");
		}
		return result > 0;
	}
	
	public void updatePass(String email, String hashedPassword) {
	    try {
	    	ConnectDatabase cn = new ConnectDatabase();
			Connection conn = cn.getConnection();
			
	        String sql = "UPDATE LOGIN SET Password = ? WHERE Email = ?";
	        PreparedStatement st = conn.prepareStatement(sql);
	        st.setString(1, hashedPassword);
	        st.setString(2, email);
	        st.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
