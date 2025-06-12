package autoEmail;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import database.ConnectDatabase;

public class BirthdayMailer {
	public static void sendBirthdayEmployee() {
		LocalDate today = LocalDate.now();
		
		try (Connection conn = ConnectDatabase.getInstance().getConnection()){
			String query = "SELECT Employee_Id, Employee_Name, Employee_Email, Employee_Birth, Employee_Last_Sent FROM EMPLOYEE";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("Employee_Id");
				String name = rs.getString("Employee_Name");
				String email = rs.getString("Employee_Email");
				Date birthday = rs.getDate("Employee_Birth");
				Date lastSent = rs.getDate("Employee_Last_Sent");
				
				LocalDate birthDate = birthday.toLocalDate();
				
				boolean isBirthdayToday = birthDate.getDayOfMonth() == today.getDayOfMonth()
						&& birthDate.getMonthValue() == today.getMonthValue();
				boolean notYetSentToday = lastSent == null || !lastSent.toLocalDate().equals(today);
				
				if(isBirthdayToday && notYetSentToday) {
					SendEmail.sendBirthdayEmail(email, name);
					
					PreparedStatement st = conn.prepareStatement("UPDATE EMPLOYEE SET Employee_Last_Sent = ? WHERE Employee_Id = ?");
					
					st.setDate(1, Date.valueOf(today));
					st.setInt(2, id);
					st.executeUpdate();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
