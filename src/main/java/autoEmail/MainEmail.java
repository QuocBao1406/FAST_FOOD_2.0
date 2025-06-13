package autoEmail;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainEmail {
	public static void main(String[] args) {
		BirthdayMailer.sendBirthdayEmployee();
		BirthdayMailer.sendBirthdayCustomer();
		
		try (PrintWriter out = new PrintWriter(new FileWriter("log.txt", true))) {
		    out.println("Đã chạy chương trình vào: " + new java.util.Date());
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
