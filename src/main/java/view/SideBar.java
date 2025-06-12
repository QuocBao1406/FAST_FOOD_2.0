package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

import chat.ChatClient;
import database.Data_Login;
import database.Data_Sell;
import database.Data_Statistical;
import model.Model_Sell;
import view.pages.Customer_Management;
import view.pages.Revenue_Management;
import view.pages.Statistical_Management;
import view.pages.Store_Management;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SideBar extends JPanel{
	private JLabel lb_store_management;
	private JLabel lb_sell;
	private JLabel lb_empolyee_management;
	private JLabel lb_food_management;
	private JLabel lb_customer;
	private JLabel lb_revenue;
	private JLabel lb_statistical;
	private Body body;
	private static Data_Login data_login;
	private Main main;
	private String nameuser;
	private static JLabel lb_floor;
	private JLabel lblNewLabel;

	public SideBar(Body body) {
		this.body = body;
		init();
	}
	
	public void setNameuser(String nameuser) {
		this.nameuser = nameuser;
	}
	
	public void init() {
		setForeground(new Color(254, 210, 97));
		setBackground(new Color(255, 128, 0));
		setSize(300, 820);
		setLayout(null);
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon(SideBar.class.getResource("/images/logo.png")));
		logo.setBounds(47, 20, 200, 200);
		add(logo);
		
		lb_store_management = new JLabel("QUẢN LÝ CỬA HÀNG");
		lb_store_management.setIcon(new ImageIcon(SideBar.class.getResource("/images/store.png")));
		lb_store_management.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lb_store_management.setBackground(new Color(249, 194, 121));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lb_store_management.setBackground(new Color(203, 121, 10));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				body.showPanel("store");
			}
		});
		lb_store_management.setForeground(new Color(255, 255, 255));
		lb_store_management.setOpaque(true);
		lb_store_management.setBackground(new Color(249, 194, 121));
		lb_store_management.setHorizontalAlignment(SwingConstants.CENTER);
		lb_store_management.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_store_management.setBounds(0, 239, 300, 60);
		add(lb_store_management);
		
		lb_sell = new JLabel("BÁN HÀNG");
		lb_sell.setIcon(new ImageIcon(SideBar.class.getResource("/images/sell.png")));
		lb_sell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lb_sell.setBackground(new Color(249, 194, 121));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lb_sell.setBackground(new Color(203, 121, 10));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				body.showPanel("sell");
			}
		});
		lb_sell.setOpaque(true);
		lb_sell.setHorizontalAlignment(SwingConstants.CENTER);
		lb_sell.setForeground(Color.WHITE);
		lb_sell.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_sell.setBackground(new Color(249, 194, 121));
		lb_sell.setBounds(0, 309, 300, 60);
		add(lb_sell);
		
		lb_empolyee_management = new JLabel("QUẢN LÝ NHÂN VIÊN");
		lb_empolyee_management.setIcon(new ImageIcon(SideBar.class.getResource("/images/employee.png")));
		lb_empolyee_management.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lb_empolyee_management.setBackground(new Color(249, 194, 121));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lb_empolyee_management.setBackground(new Color(203, 121, 10));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				body.showPanel("employee");
			}
		});
		lb_empolyee_management.setOpaque(true);
		lb_empolyee_management.setHorizontalAlignment(SwingConstants.CENTER);
		lb_empolyee_management.setForeground(Color.WHITE);
		lb_empolyee_management.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_empolyee_management.setBackground(new Color(249, 194, 121));
		lb_empolyee_management.setBounds(0, 379, 300, 60);
		add(lb_empolyee_management);
		
		lb_food_management = new JLabel("QUẢN LÝ ĐỒ ĂN");
		lb_food_management.setIcon(new ImageIcon(SideBar.class.getResource("/images/food.png")));
		lb_food_management.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lb_food_management.setBackground(new Color(249, 194, 121));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lb_food_management.setBackground(new Color(203, 121, 10));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				body.showPanel("food");
			}
		});
		lb_food_management.setOpaque(true);
		lb_food_management.setHorizontalAlignment(SwingConstants.CENTER);
		lb_food_management.setForeground(Color.WHITE);
		lb_food_management.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_food_management.setBackground(new Color(249, 194, 121));
		lb_food_management.setBounds(0, 449, 300, 60);
		add(lb_food_management);
		
		lb_customer = new JLabel("KHÁCH HÀNG");
		lb_customer.setIcon(new ImageIcon(SideBar.class.getResource("/images/customers.png")));
		lb_customer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lb_customer.setBackground(new Color(249, 194, 121));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lb_customer.setBackground(new Color(203, 121, 10));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				body.showPanel("customer");
				Customer_Management.loadData();
			}
		});
		lb_customer.setOpaque(true);
		lb_customer.setHorizontalAlignment(SwingConstants.CENTER);
		lb_customer.setForeground(Color.WHITE);
		lb_customer.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_customer.setBackground(new Color(249, 194, 121));
		lb_customer.setBounds(0, 519, 300, 60);
		add(lb_customer);
		
		lb_revenue = new JLabel("DOANH THU");
		lb_revenue.setIcon(new ImageIcon(SideBar.class.getResource("/images/revenue.png")));
		lb_revenue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lb_revenue.setBackground(new Color(249, 194, 121));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lb_revenue.setBackground(new Color(203, 121, 10));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				body.showPanel("revenue");
				Revenue_Management.loadSell();
			}
		});
		lb_revenue.setOpaque(true);
		lb_revenue.setHorizontalAlignment(SwingConstants.CENTER);
		lb_revenue.setForeground(Color.WHITE);
		lb_revenue.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_revenue.setBackground(new Color(249, 194, 121));
		lb_revenue.setBounds(0, 589, 300, 60);
		add(lb_revenue);
		
		lb_statistical = new JLabel("THỐNG KÊ");
		lb_statistical.setIcon(new ImageIcon(SideBar.class.getResource("/images/statistical.png")));
		lb_statistical.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lb_statistical.setBackground(new Color(249, 194, 121));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lb_statistical.setBackground(new Color(203, 121, 10));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				body.showPanel("statistical");
				Data_Statistical.getMonthlySalesData();
				Statistical_Management.loadStatistical();
			}
		});
		lb_statistical.setOpaque(true);
		lb_statistical.setHorizontalAlignment(SwingConstants.CENTER);
		lb_statistical.setForeground(Color.WHITE);
		lb_statistical.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_statistical.setBackground(new Color(249, 194, 121));
		lb_statistical.setBounds(0, 659, 300, 60);
		add(lb_statistical);
		
		JButton bt_out = new JButton("Đăng xuất");
		bt_out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				if(lb_floor.getText().equals("Tầng 1")) {
					data_login.getInstance().isInActive(1);
					System.exit(0);
				} else if(lb_floor.getText().equals("Tầng 2")) {
					data_login.getInstance().isInActive(2);
					System.exit(0);
				} else if(lb_floor.getText().equals("Tầng 3")) {
					data_login.getInstance().isInActive(3);
					System.exit(0);
				} else if(lb_floor.getText().equals("Admin")) {
					data_login.getInstance().isInActive(0);
					System.exit(0);
				}
			}
		});
		bt_out.setFont(new Font("Tahoma", Font.PLAIN, 15));
		bt_out.setBackground(new Color(210, 172, 134));
		bt_out.setForeground(new Color(255, 255, 255));
		bt_out.setBounds(10, 735, 120, 42);
		bt_out.setFocusPainted(false);
		add(bt_out);
		
		lb_floor = new JLabel("");
		lb_floor.setForeground(new Color(137, 48, 50));
		lb_floor.setFont(new Font("Tahoma", Font.BOLD, 25));
		lb_floor.setHorizontalAlignment(SwingConstants.CENTER);
		lb_floor.setBounds(140, 735, 120, 42);
		add(lb_floor);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ChatClient chatclient = new ChatClient();
				chatclient.setVisible(true);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel.setIcon(new ImageIcon(Store_Management.class.getResource("/images/messenger1.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel.setIcon(new ImageIcon(Store_Management.class.getResource("/images/messenger.png")));
			}
		});
		lblNewLabel.setIcon(new ImageIcon(SideBar.class.getResource("/images/messenger.png")));
		lblNewLabel.setBounds(20, 630, 100, 90);
		lblNewLabel.setVisible(false);
		add(lblNewLabel);
	}
	
	public void hide() {
		lb_empolyee_management.setVisible(false);
		lb_food_management.setVisible(false);
		lb_customer.setVisible(false);
		lb_revenue.setVisible(false);
		lb_statistical.setVisible(false);
		lb_store_management.setVisible(false);
		lblNewLabel.setVisible(true);
	}
	
	public void sell() {
		lb_sell.setBounds(0, 239, 300, 60);
	}
	
	public static void floor0() {
		lb_floor.setText("Admin");
	}
	
	public static void floor1() {
		lb_floor.setText("Tầng 1");
	}
	
	public static void floor2() {
		lb_floor.setText("Tầng 2");
	}
	
	public static void floor3() {
		lb_floor.setText("Tầng 3");
	}
	
	public static String getFloor() {
		String user = null;
		if(lb_floor.getText() == "Tầng 1") {
			user = "Nhân Viên Tầng 1";
		} else if(lb_floor.getText() == "Tầng 2") {
			user = "Nhân Viên Tầng 2";
		} else if(lb_floor.getText() == "Tầng 3") {
			user = "Nhân Viên Tầng 3";
		} else {
			user = "Admin";
		};
		return user;
	}
}
