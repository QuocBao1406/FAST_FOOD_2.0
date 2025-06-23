package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Model_Customer;

public class Data_Customer {
	private ConnectDatabase JDBC;
	private static Data_Customer instance;
	private Connection conn;
	
	public static Data_Customer getInstance() {
		if(instance == null) {
			instance = new Data_Customer();
		}
		return instance;
	}
	
	public ArrayList<Model_Customer> loadCustomer() {
		ArrayList<Model_Customer> list = new ArrayList<>();
		String sql = "SELECT * FROM CUSTOMER";
		try(Connection connec = ConnectDatabase.getInstance().getConnection();
			PreparedStatement ps = connec.prepareStatement(sql)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Model_Customer ctm = new Model_Customer(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getDouble(5),
						rs.getString(6),
						rs.getDate(9),
						rs.getString(7)
						);
				list.add(ctm);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public Model_Customer rank(String id) {
		Model_Customer model_customer = null;
		try {
			ConnectDatabase cn = new ConnectDatabase();
			Connection conn = cn.getConnection();
			
			String sql = "SELECT Customer_Rank FROM Food_Id";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, id);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				model_customer = new Model_Customer(null, null, null, null, 0, rs.getString(1));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return model_customer;
	}

	public int addCustomer(Model_Customer customer) {
		int res = 0;
		String sql = "INSERT INTO CUSTOMER (Customer_Id,Customer_Name,Customer_Sex,Customer_Address, Customer_Rank, Customer_Birth, Customer_Email)"
				+ "VALUES (?,?,?,?,'',?,?)";
		try(Connection connec = ConnectDatabase.getInstance().getConnection();
			PreparedStatement ps = connec.prepareStatement(sql)){
			ps.setString(1,customer.getCustomer_Id());
			ps.setString(2, customer.getCustomer_Name());
			ps.setString(3, customer.getCustomer_Sex());
			ps.setString(4, customer.getCustomer_Address());
			ps.setDate(5, customer.getCustomer_Birth());
			ps.setString(6, customer.getCustomer_Email());
			res = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public int deleteCustomer(String id) {
		int res = 0;
		String sql = "DELETE FROM CUSTOMER WHERE Customer_Id = ?;";
		try(Connection conec = ConnectDatabase.getInstance().getConnection();
			PreparedStatement ps = conec.prepareStatement(sql)){
			ps.setString(1, id);
			res = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();		
		}
		return res;
	}
	
	public ArrayList<Model_Customer> searchCustomer(String customer_id) {
		ArrayList<Model_Customer> list = new ArrayList<>();
		String sql = "SELECT Customer_Rank FROM CUSTOMER WHERE Customer_Id = ?;";
		try(Connection connec = ConnectDatabase.getInstance().getConnection();
			PreparedStatement ps = connec.prepareStatement(sql)){
			ps.setString(1, customer_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Model_Customer ctm = new Model_Customer("","","","",0.0,rs.getString(1));
				list.add(ctm);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<Model_Customer> findCustomerName(String name,String total1, String total2, String rank) {
		
		ArrayList<Model_Customer> list = new ArrayList<>();
		List<Object> params = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM CUSTOMER WHERE 1=1");
    if(name != null && !name.isEmpty()) {
    	query.append(" AND Customer_Name LIKE ? ");
    	params.add("%"+name+"%");
    }
    
    if (total1 != null && !total1.isEmpty()) {
        try {
            Double chi1Value = Double.parseDouble(total1);
            query.append(" AND Customer_Total >= ?");
            params.add(chi1Value);
        } catch (NumberFormatException e) {
            System.out.println("Invalid format for chi1: " + total1);
        }
    }

    if (total2 != null && !total2.isEmpty()) {
        try {
            Double chi2Value = Double.parseDouble(total2);
            query.append(" AND Customer_Total <= ?");
            params.add(chi2Value);
        } catch (NumberFormatException e) {
            System.out.println("Invalid format for chi2: " + total2);
        }
    }
    
    if(rank != null && !rank.isEmpty()) {
    	query.append(" AND Customer_Rank = ?");
    	params.add(rank);
    }
    
		try(Connection conec = ConnectDatabase.getInstance().getConnection();
				PreparedStatement ps = conec.prepareStatement(query.toString())){
				
				for (int i = 0; i < params.size(); i++) {
					ps.setObject(i + 1, params.get(i));
				}
				
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					Model_Customer ctm = new Model_Customer(
							rs.getString(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getDouble(5),
							rs.getString(6),
							rs.getDate(9),
							rs.getString(7)
							);
					list.add(ctm);
				}
			} catch(Exception e) {
				e.printStackTrace();		
			}
		return list;
	}
	
	public boolean updateCustomer(Model_Customer ctm) {
		int result = 0;
		
		try {
			String sql = "UPDATE CUSTOMER SET Customer_Id = ?, Customer_Name = ?, Customer_Sex = ?,"
				+ " Customer_Address = ?, Customer_Total = ?, Customer_Rank = ?, Customer_Birth=?, Customer_Email=?"
				+ " WHERE Customer_Id = ?";
			
			Connection conec = ConnectDatabase.getInstance().getConnection();
			PreparedStatement ps = conec.prepareStatement(sql);
			ps.setString(1, ctm.getCustomer_Id());
			ps.setString(2, ctm.getCustomer_Name());
			ps.setString(3, ctm.getCustomer_Sex());
			ps.setString(4, ctm.getCustomer_Address());
			ps.setDouble(5, ctm.getCustomer_Total());
			ps.setString(6, ctm.getCustomer_Rank());
			ps.setDate(7, ctm.getCustomer_Birth());
			ps.setString(8, ctm.getCustomer_Email());
			
			ps.setString(9, ctm.getCustomer_Id());
				
			result = ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Cập nhật khách hàng thành công!");
			} catch(SQLException e) {
				e.printStackTrace();	
			}
		return result > 0;
	}
	
	public boolean updateRank(double total, String id) {
		int res = 0;
		String sql = "UPDATE CUSTOMER SET Customer_Rank = ? WHERE Customer_Id = ?;";
		try {
			Connection conec = ConnectDatabase.getInstance().getConnection();
			PreparedStatement ps = conec.prepareStatement(sql);
			if (total >= 0) {
				ps.setString(1, "");
			} else if (total >= 500000) {
				ps.setString(1, "Đồng");
			} else if (total >= 1000000) {
				ps.setString(1, "Bạc");
			} else if (total >= 2000000) {
				ps.setString(1, "Vàng");
			} else if (total >= 3000000) {
				ps.setString(1, "Kim cương");
			}

			ps.setString(2, id);

			res = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res > 0;
	}
	public Data_Customer() {
		
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public static void setInstance(Data_Customer instance) {
		Data_Customer.instance = instance;
	}
	
}

