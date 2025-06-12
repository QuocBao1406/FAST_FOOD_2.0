package view.component;

import java.awt.Color;
import java.awt.Dialog;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import database.Data_Food;
import database.Data_Login;
import model.Model_Food;
import model.Model_Login;
import view.pages.Food_Management;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Add_Trong_Add extends JPanel{
	private JTextField tf_username;
	private JTextField tf_password;
	private JTextField tf_active;
	private JTextField tf_name;
	private JTextField tf_floor;
	private JDialog dialog;
	private static Data_Login data_login;
	public Add_Trong_Add(JDialog dialog) {
		this.dialog = dialog;
		
		setForeground(new Color(128, 0, 0));
		setBackground(new Color(255, 236, 206));
		setSize(500, 400);
		setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("User Name");
		lblNewLabel_1.setForeground(new Color(4, 97, 236));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_1.setBounds(20, 83, 150, 30);
		add(lblNewLabel_1);
		
		tf_username = new JTextField();
		tf_username.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_username.setColumns(10);
		tf_username.setBounds(173, 81, 300, 35);
		add(tf_username);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setForeground(new Color(4, 97, 236));
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_1_1.setBounds(20, 125, 150, 30);
		add(lblNewLabel_1_1);
		
		tf_password = new JTextField();
		tf_password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_password.setColumns(10);
		tf_password.setBounds(173, 123, 300, 35);
		add(tf_password);
		
		JLabel lblNewLabel_1_2 = new JLabel("Hoạt động");
		lblNewLabel_1_2.setForeground(new Color(4, 97, 236));
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_1_2.setBounds(20, 170, 150, 30);
		add(lblNewLabel_1_2);
		
		tf_active = new JTextField();
		tf_active.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_active.setColumns(10);
		tf_active.setBounds(173, 168, 300, 35);
		add(tf_active);
		
		JLabel lblNewLabel_1_3 = new JLabel("Tên");
		lblNewLabel_1_3.setForeground(new Color(4, 97, 236));
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_1_3.setBounds(20, 212, 150, 30);
		add(lblNewLabel_1_3);
		
		tf_name = new JTextField();
		tf_name.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_name.setColumns(10);
		tf_name.setBounds(173, 210, 300, 35);
		add(tf_name);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Tầng");
		lblNewLabel_1_3_1.setForeground(new Color(4, 97, 236));
		lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_1_3_1.setBounds(20, 254, 150, 30);
		add(lblNewLabel_1_3_1);
		
		tf_floor = new JTextField();
		tf_floor.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_floor.setColumns(10);
		tf_floor.setBounds(173, 252, 300, 35);
		add(tf_floor);
		
		JLabel lblThmTiKhon = new JLabel("Thêm tài khoản");
		lblThmTiKhon.setIcon(new ImageIcon(Add_Trong_Add.class.getResource("/images/account.png")));
		lblThmTiKhon.setHorizontalAlignment(SwingConstants.CENTER);
		lblThmTiKhon.setForeground(new Color(13, 108, 251));
		lblThmTiKhon.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblThmTiKhon.setBounds(10, 10, 480, 53);
		add(lblThmTiKhon);
		
		JButton btnNewButton = new JButton("Thêm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Model_Login> list = data_login.getInstance().loadLogin();
				for(Model_Login model_Login : list) {
					if(tf_username.getText().equals(model_Login.getUsername())) {
						JOptionPane.showMessageDialog(null, "User Name đã tồn tại vui lòng nhập lại!");
						return;
					}
				}
				
				String username = tf_username.getText();
				String password = tf_password.getText();
				int active = Integer.parseInt(tf_active.getText());
				String name = tf_name.getText();
				int floor = Integer.valueOf(tf_floor.getText());
				
				if(username == null || password == null || active >= 1 || name == null || floor >= 4) {
					JOptionPane.showMessageDialog(null, "Vui lòng điền lại thông tin!");
					return;
				}
				
				Model_Login model_Login = new Model_Login(username, password, active, name, floor, "");
				
				Data_Login.getInstance().addLogin(model_Login);
				Add_Account.addAccount(model_Login);
				Add_Account.loadAccount();
				
				dialog.dispose();
			}
		});
		btnNewButton.setBackground(new Color(214, 248, 254));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnNewButton.setIcon(new ImageIcon(Add_Trong_Add.class.getResource("/images/add.png")));
		btnNewButton.setBounds(161, 314, 158, 53);
		add(btnNewButton);
	}
}
