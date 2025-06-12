package view.pages;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import chat.ChatClient;
import database.Data_Login;
import model.Model_Login;
import view.component.Add_Account;
import view.component.Add_Food;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseMotionAdapter;

public class Store_Management extends JPanel{
	private JLabel lb_active_1;
	private JLabel lb_name_1;
	private JLabel lb_active_2;
	private JLabel lb_name_2;
	private JLabel lb_name_3;
	private JLabel lb_active_3;

	public Store_Management() {
		setBackground(new Color(255, 242, 189));
		setSize(1250, 820);
		setLayout(null);
		
		JLabel lb_title = new JLabel("");
		lb_title.setIcon(new ImageIcon(Store_Management.class.getResource("/images/title_logo.png")));
		lb_title.setBounds(372, 10, 500, 80);
		add(lb_title);
		
		JPanel floor1 = new JPanel();
		floor1.setBackground(new Color(237, 194, 199));
		floor1.setBounds(10, 310, 400, 250);
		add(floor1);
		floor1.setLayout(null);
		
		lb_active_1 = new JLabel(" Không hoạt động");
		lb_active_1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_active_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				if(lb_active_1.getForeground().equals(new Color(232, 0, 0))) {
//				lb_active_1.setIcon(new ImageIcon(Store_Management.class.getResource("/images/active.png")));
//				lb_active_1.setText(" Đang hoạt động");
//				lb_active_1.setForeground(new Color(43, 197, 230));
//				} else {
//					lb_active_1.setIcon(new ImageIcon(Store_Management.class.getResource("/images/inactive.png")));
//					lb_active_1.setText(" Không hoạt động");
//					lb_active_1.setForeground(new Color(232, 0, 0));
//				}
			}
		});
		lb_active_1.setForeground(new Color(232, 0, 0));
		lb_active_1.setFont(new Font("Tahoma", Font.BOLD, 35));
		lb_active_1.setIcon(new ImageIcon(Store_Management.class.getResource("/images/inactive.png")));
		lb_active_1.setBounds(10, 70, 375, 45);
		floor1.add(lb_active_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nhân viên trực");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel_2.setBounds(10, 125, 375, 45);
		floor1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Store_Management.class.getResource("/images/active_employee.png")));
		lblNewLabel_3.setBounds(10, 180, 50, 50);
		floor1.add(lblNewLabel_3);
		
		lb_name_1 = new JLabel();
		lb_name_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lb_name_1.setBounds(65, 185, 320, 40);
		floor1.add(lb_name_1);
		
		JLabel lblNewLabel_1 = new JLabel("Trạng thái");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel_1.setBounds(10, 10, 380, 50);
		floor1.add(lblNewLabel_1);
		
		JPanel floor2 = new JPanel();
		floor2.setBackground(new Color(237, 194, 199));
		floor2.setBounds(420, 310, 400, 250);
		add(floor2);
		floor2.setLayout(null);
		
		lb_active_2 = new JLabel(" Không hoạt động");
		lb_active_2.setHorizontalAlignment(SwingConstants.CENTER);
		lb_active_2.setIcon(new ImageIcon(Store_Management.class.getResource("/images/inactive.png")));
		lb_active_2.setForeground(new Color(232, 0, 0));
		lb_active_2.setFont(new Font("Tahoma", Font.BOLD, 35));
		lb_active_2.setBounds(10, 70, 375, 45);
		floor2.add(lb_active_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Nhân viên trực");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel_2_1.setBounds(20, 125, 370, 45);
		floor2.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_3_1 = new JLabel("");
		lblNewLabel_3_1.setIcon(new ImageIcon(Store_Management.class.getResource("/images/active_employee.png")));
		lblNewLabel_3_1.setBounds(10, 180, 50, 50);
		floor2.add(lblNewLabel_3_1);
		
		lb_name_2 = new JLabel("");
		lb_name_2.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lb_name_2.setBounds(65, 185, 320, 40);
		floor2.add(lb_name_2);
		
		JLabel lblNewLabel_1_2 = new JLabel("Trạng thái");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel_1_2.setBounds(10, 10, 380, 50);
		floor2.add(lblNewLabel_1_2);
		
		JPanel floor3 = new JPanel();
		floor3.setBackground(new Color(237, 194, 199));
		floor3.setBounds(830, 310, 400, 250);
		add(floor3);
		floor3.setLayout(null);
		
		lb_active_3 = new JLabel(" Không hoạt động");
		lb_active_3.setHorizontalAlignment(SwingConstants.CENTER);
		lb_active_3.setIcon(new ImageIcon(Store_Management.class.getResource("/images/inactive.png")));
		lb_active_3.setForeground(new Color(232, 0, 0));
		lb_active_3.setFont(new Font("Tahoma", Font.BOLD, 35));
		lb_active_3.setBounds(10, 70, 380, 40);
		floor3.add(lb_active_3);
		
		JLabel lblNewLabel_2_2 = new JLabel("Nhân viên trực");
		lblNewLabel_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel_2_2.setBounds(10, 125, 380, 45);
		floor3.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_3_2 = new JLabel("");
		lblNewLabel_3_2.setIcon(new ImageIcon(Store_Management.class.getResource("/images/active_employee.png")));
		lblNewLabel_3_2.setBounds(10, 180, 50, 50);
		floor3.add(lblNewLabel_3_2);
		
		lb_name_3 = new JLabel("");
		lb_name_3.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lb_name_3.setBounds(65, 185, 320, 40);
		floor3.add(lb_name_3);
		
		JLabel lblNewLabel_1_3 = new JLabel("Trạng thái");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel_1_3.setBounds(10, 10, 380, 50);
		floor3.add(lblNewLabel_1_3);
		
		JButton btnNewButton = new JButton("Cập Nhật");
		btnNewButton.setBackground(new Color(242, 219, 183));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				active();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnNewButton.setBounds(10, 720, 150, 60);
		btnNewButton.setFocusPainted(false);
		add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Tầng 1");
		lblNewLabel.setBounds(10, 250, 400, 50);
		add(lblNewLabel);
		lblNewLabel.setForeground(new Color(170, 53, 77));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblTng = new JLabel("Tầng 2");
		lblTng.setBounds(420, 250, 400, 50);
		add(lblTng);
		lblTng.setHorizontalAlignment(SwingConstants.CENTER);
		lblTng.setForeground(new Color(170, 53, 77));
		lblTng.setFont(new Font("Tahoma", Font.BOLD, 40));
		
		JLabel lblNewLabel_1_1 = new JLabel("Tầng 3");
		lblNewLabel_1_1.setBounds(830, 250, 400, 50);
		add(lblNewLabel_1_1);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setForeground(new Color(170, 53, 77));
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 40));
		
		JButton btnTiKhon = new JButton("Tài khoản");
		btnTiKhon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
				Add_Account acc = new Add_Account(dialog);
	    		dialog.getContentPane().setLayout(new GridLayout(1,1));
	    		dialog.setSize(1000, 550);
	    		dialog.setLocationRelativeTo(null);
	        	dialog.getContentPane().add(acc);
	        	dialog.setVisible(true);
			}
		});
		btnTiKhon.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnTiKhon.setFocusPainted(false);
		btnTiKhon.setBackground(new Color(242, 219, 183));
		btnTiKhon.setBounds(170, 720, 170, 60);
		add(btnTiKhon);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ChatClient chatclient = new ChatClient();
				chatclient.setVisible(true);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_4.setIcon(new ImageIcon(Store_Management.class.getResource("/images/messenger1.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_4.setIcon(new ImageIcon(Store_Management.class.getResource("/images/messenger.png")));
			}
		});
		lblNewLabel_4.setIcon(new ImageIcon(Store_Management.class.getResource("/images/messenger.png")));
		lblNewLabel_4.setBounds(10, 620, 100, 90);
		add(lblNewLabel_4);
		
		active();
	}
	
	public void active() {
		ArrayList<Model_Login> list = Data_Login.getInstance().loadLogin();
		for(Model_Login model_login : list) {
			if(model_login.getFloor() == 1 && model_login.getActive() == 1) {
				lb_active_1.setIcon(new ImageIcon(Store_Management.class.getResource("/images/active.png")));
				lb_active_1.setText(" Đang hoạt động");
				lb_active_1.setForeground(new Color(43, 197, 230));
				lb_name_1.setText(model_login.getName());
			} else if(model_login.getFloor() == 2 && model_login.getActive() == 1) {
				lb_active_2.setIcon(new ImageIcon(Store_Management.class.getResource("/images/active.png")));
				lb_active_2.setText(" Đang hoạt động");
				lb_active_2.setForeground(new Color(43, 197, 230));
				lb_name_2.setText(model_login.getName());
			} else if(model_login.getFloor() == 3 && model_login.getActive() == 1) {
				lb_active_3.setIcon(new ImageIcon(Store_Management.class.getResource("/images/active.png")));
				lb_active_3.setText(" Đang hoạt động");
				lb_active_3.setForeground(new Color(43, 197, 230));
				lb_name_3.setText(model_login.getName());
			} else if(model_login.getFloor() == 1 && model_login.getActive() == 0) {
				lb_active_1.setIcon(new ImageIcon(Store_Management.class.getResource("/images/inactive.png")));
				lb_active_1.setText(" Không hoạt động");
				lb_active_1.setForeground(new Color(232, 0, 0));
				lb_name_1.setText("");
			} else if(model_login.getFloor() == 2 && model_login.getActive() == 0) {
				lb_active_2.setIcon(new ImageIcon(Store_Management.class.getResource("/images/inactive.png")));
				lb_active_2.setText(" Không hoạt động");
				lb_active_2.setForeground(new Color(232, 0, 0));
				lb_name_2.setText("");
			} else if(model_login.getFloor() == 3 && model_login.getActive() == 0) {
				lb_active_3.setIcon(new ImageIcon(Store_Management.class.getResource("/images/inactive.png")));
				lb_active_3.setText(" Không hoạt động");
				lb_active_3.setForeground(new Color(232, 0, 0));
				lb_name_3.setText("");
			}
		}
	}
}
