package view.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import database.Data_Customer;
import database.Data_Food;
import encode.En_Image;
import model.Model_Customer;
import model.Model_Food;
import view.pages.Customer_Management;
import view.pages.Food_Management;

public class Add_Cust extends JPanel {

	private JDialog dialog;
	private JTextField tf_name;
	private JTextField tf_id;
	private Data_Food data_food;
	private byte[] image;
	private JTextField tf_adr;
	private JComboBox tf_sex;
	
	public Add_Cust(JDialog dialog) {
		this.dialog = dialog;
		
		setForeground(new Color(128, 0, 0));
		setBackground(new Color(255, 236, 206));
		setSize(1000, 500);
		setLayout(null);
		
		JLabel lblThmn = new JLabel("THÊM KHÁCH HÀNG");
		lblThmn.setIcon(new ImageIcon(Add_Food.class.getResource("/images/add_food.png")));
		lblThmn.setForeground(new Color(128, 64, 64));
		lblThmn.setHorizontalAlignment(SwingConstants.CENTER);
		lblThmn.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblThmn.setBounds(237, 21, 469, 53);
		add(lblThmn);
		
		JLabel lblTnMn = new JLabel("Tên Khách Hàng");
		lblTnMn.setForeground(new Color(128, 0, 0));
		lblTnMn.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblTnMn.setBounds(104, 168, 246, 35);
		add(lblTnMn);
		
		tf_name = new JTextField();
		tf_name.setText("");
		tf_name.setFont(new Font("Tahoma", Font.PLAIN, 28));
		tf_name.setColumns(10);
		tf_name.setBounds(360, 163, 529, 40);
		add(tf_name);
		
		JButton bt_add = new JButton("THÊM");
		bt_add.setBackground(new Color(245, 232, 211));
		bt_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Model_Customer> list = Data_Customer.getInstance().loadCustomer();
				for(Model_Customer model_customer : list) {
					if(tf_id.getText().equals(model_customer.getCustomer_Id())) {
						JOptionPane.showMessageDialog(null, "Mã khách hàng đã tồn tại vui lòng nhập lại!");
						return;
					}
				}
				
				String customer_id = tf_id.getText();
				String customer_name = tf_name.getText();
				String customer_sex = tf_sex.getSelectedItem().toString();
				String customer_address = tf_adr.getText();
				
				if(customer_id.equals("") || customer_name.equals("")) {
					JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
					return;
				}
				
				Model_Customer model_customer = new Model_Customer(customer_id, customer_name, customer_sex, customer_address);
				
				int i = Data_Customer.getInstance().addCustomer(model_customer);
				Customer_Management.loadData();
				
				if (i>0) dialog.dispose();
			}
		});
		
		bt_add.setIcon(new ImageIcon(Add_Food.class.getResource("/images/add.png")));
		bt_add.setFont(new Font("Tahoma", Font.BOLD, 28));
		bt_add.setBounds(360, 365, 250, 60);
		add(bt_add);
		
		tf_id = new JTextField();
		tf_id.setText("");
		tf_id.setFont(new Font("Tahoma", Font.PLAIN, 28));
		tf_id.setColumns(10);
		tf_id.setBounds(360, 103, 529, 40);
		add(tf_id);
		
		JLabel lblMMn = new JLabel("Mã Khách Hàng");
		lblMMn.setForeground(new Color(128, 0, 0));
		lblMMn.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblMMn.setBounds(104, 108, 246, 35);
		add(lblMMn);
		
		tf_sex = new JComboBox();
		tf_sex.setFont(new Font("Tahoma", Font.PLAIN, 28));
		tf_sex.setBounds(360, 226, 529, 40);
		tf_sex.addItem("Nam");
		tf_sex.addItem("Nữ");
		tf_sex.addItem("Khác");
		add(tf_sex);
		
		tf_adr = new JTextField();
		tf_adr.setText("");
		tf_adr.setFont(new Font("Tahoma", Font.PLAIN, 28));
		tf_adr.setColumns(10);
		tf_adr.setBounds(360, 288, 529, 40);
		add(tf_adr);
		
		JLabel lblTnMn_1 = new JLabel("Địa Chỉ");
		lblTnMn_1.setForeground(new Color(128, 0, 0));
		lblTnMn_1.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblTnMn_1.setBounds(104, 293, 246, 30);
		add(lblTnMn_1);
		
		JLabel lblMMn_1 = new JLabel("Giới Tính");
		lblMMn_1.setForeground(new Color(128, 0, 0));
		lblMMn_1.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblMMn_1.setBounds(104, 231, 246, 30);
		add(lblMMn_1);
	}
}
