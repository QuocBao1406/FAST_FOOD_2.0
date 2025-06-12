package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Model_Statistical;

public class Data_Statistical {
	public static List<Model_Statistical> getMonthlySalesData() {
	    List<Model_Statistical> model_overview = new ArrayList<>();

	    String query = "SELECT FORMAT(Sell_Date, 'yyyy-MM') AS Month, " +
	                   "SUM(Sell_Total) AS Total, " +
	                   "SUM(Sell_Quantity) AS Quantity " +
	                   "FROM SELL " +
	                   "GROUP BY FORMAT(Sell_Date, 'yyyy-MM') " +
	                   "ORDER BY Month";

	    Connection connection = null;
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	        connection = ConnectDatabase.getInstance().getConnection();
	        stmt = connection.createStatement();
	        rs = stmt.executeQuery(query);

	        // Tạo danh sách model_overview từ kết quả truy vấn
	        while (rs.next()) {
	            String month = rs.getString("Month");
	            double total = rs.getDouble("Total");
	            int quantity = rs.getInt("Quantity");

	            model_overview.add(new Model_Statistical(month, total, quantity, null));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            ConnectDatabase.getInstance().closeConnection(connection);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return model_overview;
	}
	
	public static List<Model_Statistical> getBestSellerData() {
	    List<Model_Statistical> model_overview = new ArrayList<>();

	    String query = "SELECT FORMAT(Sell_Date, 'yyyy-MM') AS Month, " +
	                   "SUM(Sell_Total) AS Total, " +
	                   "SUM(Sell_Quantity) AS Quantity, " +
	                   "Sell_Name " +
	                   "FROM SELL " +
	                   "GROUP BY FORMAT(Sell_Date, 'yyyy-MM'), Sell_Name " +  // Group by cả Food_Name
	                   "ORDER BY Month";

	    Connection connection = null;
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	        connection = ConnectDatabase.getInstance().getConnection();
	        stmt = connection.createStatement();
	        rs = stmt.executeQuery(query);

	        // Tạo danh sách model_overview từ kết quả truy vấn
	        while (rs.next()) {
	            String month = rs.getString("Month");
	            int quantity = rs.getInt("Quantity");
	            String name = rs.getString("Sell_Name");

	            if (name == null) {
	                name = "Không xác định";  // Tránh lỗi null
	            }

	            model_overview.add(new Model_Statistical(month, 0, quantity, name));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            ConnectDatabase.getInstance().closeConnection(connection);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return model_overview;
	}
}
