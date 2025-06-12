package autoEmail;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class SendEmail {
	public static void sendBirthdayEmail(String toEmail, String name) {
		final String fromEmail = "baosenpai1213@gmail.com";
		final String password = "fdwmegrwyvrhlqgi";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject("🎂 Chúc Mừng Sinh Nhật " + name + ", Cảm Ơn Bạn Đã Là Một Phần Của Đại Gia Đình BHMN Fast Food!");
			message.setText("Chào " + name + ",\r\n"
					+ "Hôm nay là một ngày thật đặc biệt – ngày sinh nhật của bạn! Thay mặt cho toàn bộ đội ngũ tại BHMN Fast Food, chúng tôi xin gửi tới bạn những lời chúc mừng tốt đẹp và chân thành nhất.\r\n"
					+ "Chúc bạn có một ngày sinh nhật thật nhiều niềm vui, luôn khỏe mạnh, hạnh phúc và thành công trong mọi lĩnh vực. Hy vọng trong thời gian tới, bạn sẽ tiếp tục gắn bó và cùng nhau lan tỏa năng lượng tích cực đến khách hàng mỗi ngày.\r\n"
					+ "Cảm ơn bạn vì những đóng góp nhiệt tình, tinh thần làm việc siêng năng và luôn vui vẻ trong suốt thời gian qua. Bạn chính là một phần không thể thiếu trong “bếp lửa” của chúng ta!\r\n"
					+ "Một lần nữa, chúc bạn sinh nhật vui vẻ và một năm mới tràn đầy năng lượng!\r\n"
					+ "Thân mến,\r\n"
					+ "Nguyễn Hữu Quốc Bảo\r\n"
					+ "BHMN Fast Food");
			Transport.send(message);
			System.out.println("Đã gửi email cho " + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
