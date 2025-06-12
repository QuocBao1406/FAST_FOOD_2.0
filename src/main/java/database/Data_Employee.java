package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Model_Employee;

public class Data_Employee {
	private final Connection con;
	private static Data_Employee instance;
	private final String INSERT_NHANVIEN = "INSERT INTO EMPLOYEE (Employee_Name, Employee_Cccd, Employee_Sex, Employee_Birth, Employee_Phone, Employee_Role, Employee_Salary) VALUES (?,?,?,?,?,?,?)";
	private final String SELECT_NHANVIEN = "SELECT Employee_Id, Employee_Name, Employee_Cccd, Employee_Sex, Employee_Birth, Employee_Phone, Employee_Role, Employee_Salary FROM EMPLOYEE";
	private final String UPDATE_THONGTIN = "UPDATE EMPLOYEE SET Employee_Name=?, Employee_Cccd=?, Employee_Sex=?, Employee_Birth=?, Employee_Phone=?, Employee_Role=?, Employee_Salary=? WHERE Employee_Id=?";
	private final String DELETE_NHANVIEN = "DELETE FROM EMPLOYEE WHERE Employee_Id=?";
	private final String SELECT_TIMKIEM_NHANVIEN = "SELECT Employee_Id, Employee_Name, Employee_Cccd, Employee_Sex, Employee_Birth, Employee_Phone, Employee_Role, Employee_Salary FROM EMPLOYEE WHERE Employee_Name LIKE ?";

	public static Data_Employee getInstance() {
		if(instance == null) {
			instance = new Data_Employee();
		}
		return instance;
	}
	
	public Data_Employee() {
        this.con = ConnectDatabase.getInstance().getConnection();
	}
	
	public ArrayList<Model_Employee> loadNhanVien() {
		ArrayList<Model_Employee> list = new ArrayList<>();
        try {
            PreparedStatement p = con.prepareStatement(SELECT_NHANVIEN);
            ResultSet r = p.executeQuery();
            while (r.next()) {
            	int maNhanVien = r.getInt(1);
				String ten = r.getString(2);
				String cccd = r.getString(3);
				String gioiTinh = r.getString(4);
				java.sql.Date ngaySinh = r.getDate(5);		
				String sdt = r.getString(6);
				String chucVu = r.getString(7);
				int luong = r.getInt(8);
            	
            	Model_Employee nv = new Model_Employee(maNhanVien, ten, cccd, gioiTinh, ngaySinh, sdt, chucVu, luong);
            	list.add(nv);
            }
            r.close();
            p.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return list;
	}
	
	public Model_Employee themNhanVien(Model_Employee employee) {
	    try {
	        PreparedStatement p = con.prepareStatement(INSERT_NHANVIEN, PreparedStatement.RETURN_GENERATED_KEYS);
	        p.setString(1, employee.getTen());
	        p.setString(2, employee.getCccd());
	        p.setString(3, employee.getGioiTinh());
	        p.setDate(4, employee.getNgaySinh());
	        p.setString(5, employee.getSdt());
	        p.setString(6, employee.getChucVu());
	        p.setInt(7, employee.getLuong());
	        
	        // Thực thi câu lệnh INSERT
	        p.execute();

	        // Lấy khóa sinh tự động
	        ResultSet r = p.getGeneratedKeys();
	        if (r.next()) { // Chuyển đến dòng đầu tiên
	            int maNhanVien = r.getInt(1); // Lấy giá trị cột đầu tiên
	            employee.setMaNhanVien(maNhanVien);
	        } else {
	            throw new SQLException("Không thể lấy mã nhân viên được sinh tự động!");
	        }

	        // Đóng PreparedStatement và ResultSet
	        r.close();
	        p.close();

	        // Thông báo thành công
	        JOptionPane.showMessageDialog(null, "Đã thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	    } catch (SQLException e) {
	        // In lỗi và thông báo thất bại
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Thêm nhân viên thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
	    }
	    return employee;
	}
	
/*	public Model_NhanVien themNhanVien2(Model_NhanVien nhanVien) {
        try {
            PreparedStatement p = con.prepareStatement(INSERT_NHANVIEN, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setString(1, nhanVien.getTen());
            p.setString(2, nhanVien.getCccd());
            p.setString(3, nhanVien.getGioiTinh());
            p.setDate(4, nhanVien.getNgaySinh());
            p.setString(5, nhanVien.getSdt());
            p.setString(6, nhanVien.getChucVu());
            p.setInt(7, nhanVien.getLuong());
                        
            p.execute();
            ResultSet r = p.getGeneratedKeys();
            r.first();
            int maNhanVien = r.getInt(1);
            nhanVien.setMaNhanVien(maNhanVien);
          //  DBAccount.getInstance().updateMaNhanVien(maNhanVien);
            p.close();
            r.close();
            
          } catch (SQLException e) {
          	e.printStackTrace();
          }
        return nhanVien;
	} */
	
	public Model_Employee suaThongTin(Model_Employee employee) {
        try {
            PreparedStatement p = con.prepareStatement(UPDATE_THONGTIN);
            p.setString(1, employee.getTen());
            p.setString(2, employee.getCccd());
            p.setString(3, employee.getGioiTinh());
            p.setDate(4, employee.getNgaySinh());
            p.setString(5, employee.getSdt());
            p.setString(6, employee.getChucVu());
            p.setInt(7, employee.getLuong());
            p.setInt(8, employee.getMaNhanVien());
                        
            p.execute();
            p.close();
            
            JOptionPane.showMessageDialog(null, "Đã cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
          } catch (SQLException e) {
          	e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại XXX", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
          }
        return employee;
	}
	
	public void xoaNhanVien(int maNhanVien) {
        try {
            PreparedStatement p = con.prepareStatement(DELETE_NHANVIEN);
            p.setInt(1, maNhanVien);
                        
            p.execute();
            p.close();
            
            JOptionPane.showMessageDialog(null, "Đã xóa nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
          } catch (SQLException e) {
          	e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Xóa nhân viên thất bại XXX", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
          }
	}
	
	public ArrayList<Model_Employee> timkiem(String name) {
		ArrayList<Model_Employee> list = new ArrayList<>();
        try {
            PreparedStatement p = con.prepareStatement(SELECT_TIMKIEM_NHANVIEN);
            p.setString(1, name);
            ResultSet r = p.executeQuery();
            while (r.next()) {
            	int maNhanVien = r.getInt(1);
				String ten = r.getString(2);
				String cccd = r.getString(3);
				String gioiTinh = r.getString(4);
				java.sql.Date ngaySinh = r.getDate(5);		
				String sdt = r.getString(6);
				String chucVu = r.getString(7);
				int luong = r.getInt(8);
            	
            	Model_Employee nv = new Model_Employee(maNhanVien, ten, cccd, gioiTinh, ngaySinh, sdt, chucVu, luong);
            	list.add(nv);
            }
            r.close();
            p.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return list;
	}
}
