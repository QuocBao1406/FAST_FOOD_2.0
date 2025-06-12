package model;

import java.sql.Date;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

public class Model_Employee {
	private int maNhanVien;
	private String ten;
	private String cccd;
	private String gioiTinh;
	private Date ngaySinh;
	private String sdt;
	private String chucVu;
	private int luong;

	public Model_Employee(int maNhanVien, String ten, String cccd, String gioiTinh, Date ngaySinh, String sdt,
			String chucVu, int luong) {
		super();
		this.maNhanVien = maNhanVien;
		this.ten = ten;
		this.cccd = cccd;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.sdt = sdt;
		this.chucVu = chucVu;
		this.luong = luong;
	}

	  public JSONObject toJsonObject(String type) {
	    	try {
				JSONObject json = new JSONObject();
				json.put("type", type);
				json.put("maNhanVien", maNhanVien);
				json.put("ten", ten);
				json.put("cccd", cccd);
				json.put("gioiTinh", gioiTinh);
				json.put("ngaySinh", formatDate(ngaySinh));
				json.put("sdt", sdt);
				json.put("chucVu", chucVu);
				json.put("luong", luong);
				return json;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	    }
	    
		public Model_Employee(Object json) {
	        JSONObject obj = (JSONObject) json;
	        try {
	        	maNhanVien = obj.getInt("maNhanVien");
	        	ten = obj.getString("ten");
	        	cccd = obj.getString("cccd");
	        	gioiTinh = obj.getString("gioiTinh");
	        	sdt = obj.getString("sdt");
	        	chucVu = obj.getString("chucVu");
	        	luong = obj.getInt("luong");
	        	ngaySinh = convertToSqlDate(obj.getString("ngaySinh"));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
		
	    private String formatDate(Date date) {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        return dateFormat.format(date);
	    }
	    
	    private Date convertToSqlDate(String dateString) {
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            java.util.Date date = sdf.parse(dateString);
	            return new Date(date.getTime());
	        } catch (ParseException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	
	public int getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(int maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getCccd() {
		return cccd;
	}

	public void setCccd(String cccd) {
		this.cccd = cccd;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public int getLuong() {
		return luong;
	}

	public void setLuong(int luong) {
		this.luong = luong;
	}
}
