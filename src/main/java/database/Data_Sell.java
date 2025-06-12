package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Model_Food;
import model.Model_Sell;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Data_Sell {
	private static Data_Sell instance;
	private Connection conn;
	
	public static Data_Sell getInstance() {
		if(instance == null) {
			instance = new Data_Sell();
		}
		return instance;
	}
	
	public Data_Sell() {
		
	}
	
	public ArrayList<Model_Sell> loadSell() {
		ArrayList<Model_Sell> list = new ArrayList<>();
		try {
			ConnectDatabase cn = new ConnectDatabase();
			conn = cn.getConnection();
			
			String sql = "SELECT * FROM SELL";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				String sell_id = rs.getString(1);
				int sell_quantity = rs.getInt(2);
				double sell_total = rs.getDouble(3);
				Timestamp sell_date = rs.getTimestamp(4);
				String customer_id = rs.getString(5);
				String sell_name = rs.getString(6);

				Model_Sell sell = new Model_Sell(sell_id, sell_quantity, sell_total, sell_date, customer_id, sell_name);
				
				list.add(sell);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean addSell(List<Model_Sell> list) {
		int[] result;
		try {
			ConnectDatabase cn = new ConnectDatabase();
			conn = cn.getConnection();
			
			String sql = "INSERT INTO SELL (Sell_Id, Sell_Quantity, Sell_Total, Sell_Date, Customer_Id, Sell_Name) "
					+ "VALUES (?,?,?,?,?,?)";
			PreparedStatement st = conn.prepareStatement(sql);
			
			conn.setAutoCommit(false);
			
			for(Model_Sell sell : list) {
			st.setString(1, sell.getSell_id());
			st.setInt(2, sell.getSell_quantity());
			st.setDouble(3, sell.getSell_total());
			st.setTimestamp(4, sell.getSell_date());
			st.setString(5, sell.getCustomer_id());
			st.setString(6, sell.getSell_name());
			
			st.addBatch();
			}
			
			result = st.executeBatch();
			
			conn.commit();
			
			JOptionPane.showMessageDialog(null, "Thanh toán thành công, bill đã được lưu!");
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if(conn != null) conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return false;
		}
		return result.length == list.size();
	}
	
	public boolean updateSell(Model_Sell sell) {
		int result = 0;
		try {
			ConnectDatabase cn = new ConnectDatabase();
			Connection conn = cn.getConnection();
			
			String sql = "UPDATE SELL "
					+ "SET Sell_Id=?, Sell_Quantity=?, Sell_Total=?, Sell_Date=?, Customer_Id=? "
					+ "WHERE Sell_Id=?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, sell.getSell_id());
			st.setInt(2, sell.getSell_quantity());
			st.setDouble(3, sell.getSell_total());
			st.setTimestamp(4, sell.getSell_date());
			st.setString(5, sell.getCustomer_id());
			
			st.setString(6, sell.getSell_id());
			
			result = st.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Cập nhật đơn hàng thành công!");
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cập nhật đơn hàng thất bại, vui lòng nhập lại!");
		}
		return result > 0;
	}
	
	public boolean deleteSell(String id) {
		int result = 0;
		try {
			ConnectDatabase cn = new ConnectDatabase();
			Connection conn = cn.getConnection();
			
			String sql = "DELETE FROM SELL "
					+ "WHERE Sell_Id=?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, id);
			
			result = st.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Xóa đơn hàng thành công!");
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Xóa đơn hàng thất bại!");
		}
		return result > 0;
	}
}
