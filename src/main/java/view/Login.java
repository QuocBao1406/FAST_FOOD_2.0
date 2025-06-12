package view;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.swing.*;

import org.mindrot.jbcrypt.BCrypt;

import database.ConnectDatabase;
import database.Data_Login;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import model.Model_Login;
import view.component.Add_Account;

public class Login extends JPanel {
	private JTextField tf_username;
	private JPasswordField tf_password;
	private static Data_Login data_login;
	private static int floor;
	private CardLayout rightCardLayout;
	private JPanel rightPanel;
	private JTextField username_register;
	private JPasswordField passwordField;
	private JPasswordField passwordField_reEnter;
	private JTextField tf_email;
	private int failedAttempts = 0;
	private long lockTime = 0;

	public Login() {
		setSize(1550, 823);
		setBackground(new Color(240, 192, 149));
		setLayout(null);

		JLabel lblWelcome1 = new JLabel("WELCOME TO BH");
		lblWelcome1.setForeground(new Color(224, 255, 255));
		lblWelcome1.setFont(new Font("Tahoma", Font.BOLD, 45));
		lblWelcome1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWelcome1.setBounds(408, 10, 391, 53);
		add(lblWelcome1);

		// Panel bên trái (giữ nguyên)
		JPanel panelLeft = new JPanel();
		panelLeft.setBackground(new Color(255, 216, 113));
		panelLeft.setBounds(0, 0, 800, 823);
		panelLeft.setLayout(null);
		add(panelLeft);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(128, 128, 500, 500);
		lblLogo.setBackground(new Color(236, 226, 140));
		lblLogo.setIcon(new ImageIcon(Login.class.getResource("/images/logo_log.png")));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		panelLeft.add(lblLogo);

		JLabel lblTitleLogo = new JLabel("");
		lblTitleLogo.setBounds(120, 600, 540, 80);
		lblTitleLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleLogo.setIcon(new ImageIcon(Login.class.getResource("/images/title_logo.png")));
		panelLeft.add(lblTitleLogo);

		JLabel lblWelcome2 = new JLabel("NM FAST FOOD");
		lblWelcome2.setHorizontalAlignment(SwingConstants.LEFT);
		lblWelcome2.setForeground(new Color(255, 255, 240));
		lblWelcome2.setFont(new Font("Tahoma", Font.BOLD, 45));
		lblWelcome2.setBounds(801, 10, 359, 53);
		add(lblWelcome2);

		// Phần phải sử dụng CardLayout
		rightCardLayout = new CardLayout();
		rightPanel = new JPanel(rightCardLayout);
		rightPanel.setBounds(1000, 170, 400, 466);
		add(rightPanel);

		// Panel Login
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new Color(252, 220, 220));
		loginPanel.setLayout(null);
		rightPanel.add(loginPanel, "login");

		JLabel lblLoginTitle = new JLabel("Login");
		lblLoginTitle.setForeground(new Color(143, 27, 5));
		lblLoginTitle.setFont(new Font("Tahoma", Font.BOLD, 45));
		lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginTitle.setBounds(10, 10, 380, 72);
		loginPanel.add(lblLoginTitle);

		JLabel lblUsername = new JLabel("User Name:");
		lblUsername.setForeground(new Color(143, 27, 5));
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblUsername.setBounds(25, 100, 204, 30);
		loginPanel.add(lblUsername);

		tf_username = new JTextField();
		tf_username.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tf_username.setBackground(new Color(255, 249, 255));
		tf_username.setBounds(25, 140, 350, 40);
		loginPanel.add(tf_username);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(new Color(143, 27, 5));
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPassword.setBounds(25, 199, 204, 30);
		loginPanel.add(lblPassword);

		tf_password = new JPasswordField();
		tf_password.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tf_password.setBackground(new Color(255, 249, 255));
		tf_password.setBounds(25, 239, 350, 40);
		loginPanel.add(tf_password);

		JButton bt_login = new JButton("Login");
		bt_login.setForeground(new Color(31, 99, 97));
		bt_login.setBackground(new Color(254, 245, 254));
		bt_login.setFont(new Font("Tahoma", Font.BOLD, 20));
		bt_login.setBounds(126, 305, 150, 50);
		loginPanel.add(bt_login);

		// Nút chuyển sang đăng ký
		JButton toRegister = new JButton("<html><u>Chưa có tài khoản? Đăng ký</u></html>");
		toRegister.setBorderPainted(false);
		toRegister.setContentAreaFilled(false);
		toRegister.setFocusPainted(false);
		toRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
		toRegister.setFont(new Font("Tahoma", Font.PLAIN, 17));
		toRegister.setBounds(76, 365, 250, 30);
		loginPanel.add(toRegister);
		
		JButton btnqunMtKhu = new JButton("<html><u>Quên mật khẩu? Gửi về Email</u></html>");
		btnqunMtKhu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newPass = generateRandomPassword(6);
				String hashedPass = BCrypt.hashpw(newPass, BCrypt.gensalt(12));
				JOptionPane.showMessageDialog(null, "Mật khẩu mới đã được gửi tới email của bạn.");
				
				ArrayList<Model_Login> list = data_login.getInstance().loadLogin();
				for(Model_Login model_Login : list) {
					if(tf_username.getText().equals(model_Login.getUsername())) {
						data_login.getInstance().updatePass(model_Login.getEmail(), hashedPass);
						sendEmail(model_Login.getEmail(), newPass);
					}
				}
			}
		});
		btnqunMtKhu.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnqunMtKhu.setFocusPainted(false);
		btnqunMtKhu.setContentAreaFilled(false);
		btnqunMtKhu.setBorderPainted(false);
		btnqunMtKhu.setBounds(68, 420, 275, 30);
		loginPanel.add(btnqunMtKhu);

		// Panel Register (tạm thời)
		JPanel registerPanel = new JPanel();
		registerPanel.setLayout(null);
		registerPanel.setBackground(new Color(255, 220, 220));
		rightPanel.add(registerPanel, "register");

		JButton toLogin = new JButton("<html><u>Đã có tài khoản? Đăng nhập</u></html>");
		toLogin.setBorderPainted(false);
		toLogin.setContentAreaFilled(false);
		toLogin.setFocusPainted(false);
		toLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		toLogin.setFont(new Font("Tahoma", Font.PLAIN, 17));
		toLogin.setBounds(80, 426, 250, 30);
		registerPanel.add(toLogin);
		
		JLabel lblRegister = new JLabel("Register");
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegister.setForeground(new Color(143, 27, 5));
		lblRegister.setFont(new Font("Tahoma", Font.BOLD, 45));
		lblRegister.setBounds(10, 10, 380, 72);
		registerPanel.add(lblRegister);
		
		JLabel lblUsername_1 = new JLabel("User Name:");
		lblUsername_1.setForeground(new Color(143, 27, 5));
		lblUsername_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblUsername_1.setBounds(20, 81, 204, 30);
		registerPanel.add(lblUsername_1);
		
		username_register = new JTextField();
		username_register.setFont(new Font("Tahoma", Font.PLAIN, 25));
		username_register.setBackground(new Color(255, 249, 255));
		username_register.setBounds(20, 110, 350, 40);
		registerPanel.add(username_register);
		
		JLabel lblPassword_1 = new JLabel("Password:");
		lblPassword_1.setForeground(new Color(143, 27, 5));
		lblPassword_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPassword_1.setBounds(20, 216, 204, 30);
		registerPanel.add(lblPassword_1);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		passwordField.setBackground(new Color(255, 249, 255));
		passwordField.setBounds(20, 246, 350, 40);
		registerPanel.add(passwordField);
		
		JButton bt_login_1 = new JButton("Register");
		bt_login_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] passwordEnterChar = passwordField.getPassword();
				char[] passwordReEnterChar = passwordField_reEnter.getPassword();
				String passwordEnter = new String(passwordEnterChar);
				String passwordReEnter = new String(passwordReEnterChar);
				
				if(username_register.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập Username!", "Thông báo", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(tf_email.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập Email!", "Thông báo", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
				if(!tf_email.getText().matches(emailRegex)) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập Email hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(passwordEnter.equals("")) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập Password!", "Thông báo", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(passwordReEnter.equals("")) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập lại Password!", "Thông báo", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(!passwordEnter.equals(passwordReEnter)) {
					JOptionPane.showMessageDialog(null, "Mật khẩu không khớp, vui lòng nhập lại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				ArrayList<Model_Login> list = data_login.getInstance().loadLogin();
				for(Model_Login model_Login : list) {
					if(username_register.getText().equals(model_Login.getUsername())) {
						JOptionPane.showMessageDialog(null, "User Name đã tồn tại vui lòng nhập lại!");
						return;
					}
				}
				
				String username = username_register.getText();
				String hashedPassword = BCrypt.hashpw(passwordEnter, BCrypt.gensalt(12));
				
				if(username == null || passwordEnter == null) {
					JOptionPane.showMessageDialog(null, "Vui lòng điền lại thông tin!");
					return;
				}
				
				Model_Login model_Login = new Model_Login(username, hashedPassword, 0, "Nhân viên mới", 1, tf_email.getText());
				
				Data_Login.getInstance().addLogin(model_Login);
				Add_Account.addAccount(model_Login);
				Add_Account.loadAccount();
				
				JOptionPane.showMessageDialog(null, "Đăng ký thành công, vui lòng đăng nhập", "Thông báo", JOptionPane.WARNING_MESSAGE);
			}
		});
		bt_login_1.setForeground(new Color(31, 99, 97));
		bt_login_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		bt_login_1.setBackground(new Color(254, 245, 254));
		bt_login_1.setBounds(130, 371, 150, 50);
		registerPanel.add(bt_login_1);
		
		passwordField_reEnter = new JPasswordField();
		passwordField_reEnter.setFont(new Font("Tahoma", Font.PLAIN, 25));
		passwordField_reEnter.setBackground(new Color(255, 249, 255));
		passwordField_reEnter.setBounds(20, 315, 350, 40);
		registerPanel.add(passwordField_reEnter);
		
		JLabel lblPassword_1_1 = new JLabel("Re-enter Password");
		lblPassword_1_1.setForeground(new Color(143, 27, 5));
		lblPassword_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPassword_1_1.setBounds(20, 285, 204, 30);
		registerPanel.add(lblPassword_1_1);
		
		JLabel lblUsername_1_1 = new JLabel("Email");
		lblUsername_1_1.setForeground(new Color(143, 27, 5));
		lblUsername_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblUsername_1_1.setBounds(20, 148, 204, 30);
		registerPanel.add(lblUsername_1_1);
		
		tf_email = new JTextField();
		tf_email.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tf_email.setBackground(new Color(255, 249, 255));
		tf_email.setBounds(20, 177, 350, 40);
		registerPanel.add(tf_email);

		// Xử lý login
		bt_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = tf_username.getText();
				char[] passwordchar = tf_password.getPassword();
				String password = new String(passwordchar);

				ArrayList<Model_Login> list = Data_Login.getInstance().loadLogin();
				for (Model_Login model_login : list) {
					if (username.equals(model_login.getUsername())) {
						floor = model_login.getFloor();
						System.out.println(floor);
					}
				}

				if (username.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(System.currentTimeMillis() < lockTime) {
					JOptionPane.showMessageDialog(null, "Tài khoản bị tạm khóa, thử lại sau 1 phút.");
					return;
				}

				if (data_login.getInstance().validateAdmin(username, password) && floor == 0) {
					data_login.getInstance().getMain().displayAdmin();
					data_login.getInstance().isActive(username);
					failedAttempts = 0;
					SideBar.floor0();
				} else if (data_login.getInstance().validate1(username, password) && floor == 1) {
					data_login.getInstance().getMain().displayEmployee1();
					data_login.getInstance().isActive(username);
					failedAttempts = 0;
					SideBar.floor1();
				} else if (data_login.getInstance().validate2(username, password) && floor == 2) {
					data_login.getInstance().getMain().displayEmployee2();
					data_login.getInstance().isActive(username);
					failedAttempts = 0;
					SideBar.floor2();
				} else if (data_login.getInstance().validate3(username, password) && floor == 3) {
					data_login.getInstance().getMain().displayEmployee3();
					data_login.getInstance().isActive(username);
					failedAttempts = 0;
					SideBar.floor3();
				} else {
					JOptionPane.showMessageDialog(null, "Sai UserName hoặc Password, vui lòng nhập lại!");
					tf_password.setText("");
					failedAttempts++;
					System.out.println("false: " + failedAttempts);
					
					if(failedAttempts >= 3) {
						lockTime = System.currentTimeMillis() + 60_000;
						JOptionPane.showMessageDialog(null, "Sai 3 lần. Tài Khoản bị khóa 1 phút");
						return;
					}
				}
			}
		});

		// Sự kiện chuyển sang đăng ký
		toRegister.addActionListener(e -> rightCardLayout.show(rightPanel, "register"));
		toLogin.addActionListener(e -> rightCardLayout.show(rightPanel, "login"));
	}
	
	public String generateRandomPassword(int length) {
	    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    StringBuilder sb = new StringBuilder();
	    Random rand = new Random();
	    for (int i = 0; i < length; i++) {
	        sb.append(chars.charAt(rand.nextInt(chars.length())));
	    }
	    return sb.toString();
	}
	
	public boolean emailExists(String email) {
	    try {
	        Connection conn = new ConnectDatabase().getConnection();
	        String sql = "SELECT * FROM LOGIN WHERE Email = ?";
	        PreparedStatement st = conn.prepareStatement(sql);
	        st.setString(1, email);
	        ResultSet rs = st.executeQuery();
	        return rs.next();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public void sendEmail(String toEmail, String newPassword) {
	    final String fromEmail = "baosenpai1213@gmail.com"; // email bạn
	    final String password = "fdwmegrwyvrhlqgi"; // App Password đã tạo (KHÔNG chứa dấu cách)

	    Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");

	    Session session = Session.getInstance(props, new Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(fromEmail, password);
	        }
	    });

	    try {
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(fromEmail));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
	        message.setSubject("Đặt lại mật khẩu - Hệ thống BH");
	        message.setText("Mật khẩu mới của bạn là: " + newPassword);
	        Transport.send(message);
	        System.out.println("Email đã được gửi thành công!");
	    } catch (MessagingException e) {
	        System.err.println("Gửi email thất bại:");
	        e.printStackTrace();
	    }
	}



}
