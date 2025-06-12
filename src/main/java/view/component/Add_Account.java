package view.component;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;

import database.Data_Food;
import database.Data_Login;
import encode.En_Image;
import model.Model_Food;
import model.Model_Login;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Add_Account extends JPanel {
	private JTable table;
	private JDialog dialog;
	private JTextField tf_username;
	private JTextField tf_password;
	private JTextField tf_active;
	private JTextField tf_name;
	private JTextField tf_floor;
	private JButton bt_save;
	private static DefaultTableModel table_model;
	private static Data_Login data_login;
	public Add_Account(JDialog dialog) {
		this.dialog = dialog;
		
		setForeground(new Color(128, 0, 0));
		setBackground(new Color(255, 236, 206));
		setSize(1000, 550);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 291, 950, 219);
		add(scrollPane);
		
		table_model = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"User Name", "Password", "Ho\u1EA1t \u0110\u1ED9ng", "T\u00EAn", "T\u1EA7ng", "Email"
			}
		);
		loadAccount();
		Font headerFont = new Font("Arial", Font.BOLD, 18);
		table = new JTable(table_model);
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 30));
		table.getTableHeader().setFont(headerFont);
		table.setRowHeight(30);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            private String select;

			@Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        tf_username.setText(table.getValueAt(selectedRow, 0).toString());
                        tf_password.setText(table.getValueAt(selectedRow, 1).toString());
                        tf_active.setText(table.getValueAt(selectedRow, 2).toString());
                        tf_name.setText(table.getValueAt(selectedRow, 3).toString());
                        tf_floor.setText(table.getValueAt(selectedRow, 4).toString());
                        
                        select = table.getValueAt(selectedRow, 0).toString();
                    }
                }
            }
        });
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Quản lý tài khoản");
		lblNewLabel.setForeground(new Color(13, 108, 251));
		lblNewLabel.setIcon(new ImageIcon(Add_Account.class.getResource("/images/account.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel.setBounds(10, 10, 980, 53);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("User Name");
		lblNewLabel_1.setForeground(new Color(4, 97, 236));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_1.setBounds(150, 80, 150, 30);
		add(lblNewLabel_1);
		
		tf_username = new JTextField();
		tf_username.setEditable(false);
		tf_username.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_username.setBounds(303, 78, 300, 35);
		add(tf_username);
		tf_username.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setForeground(new Color(4, 97, 236));
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_1_1.setBounds(150, 122, 150, 30);
		add(lblNewLabel_1_1);
		
		tf_password = new JTextField();
		tf_password.setEditable(false);
		tf_password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_password.setColumns(10);
		tf_password.setBounds(303, 120, 300, 35);
		add(tf_password);
		
		JLabel lblNewLabel_1_2 = new JLabel("Hoạt động");
		lblNewLabel_1_2.setForeground(new Color(4, 97, 236));
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_1_2.setBounds(150, 167, 150, 30);
		add(lblNewLabel_1_2);
		
		tf_active = new JTextField();
		tf_active.setEditable(false);
		tf_active.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_active.setColumns(10);
		tf_active.setBounds(303, 165, 300, 35);
		add(tf_active);
		
		JLabel lblNewLabel_1_3 = new JLabel("Tên");
		lblNewLabel_1_3.setForeground(new Color(4, 97, 236));
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_1_3.setBounds(150, 209, 150, 30);
		add(lblNewLabel_1_3);
		
		tf_name = new JTextField();
		tf_name.setEditable(false);
		tf_name.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_name.setColumns(10);
		tf_name.setBounds(303, 207, 300, 35);
		add(tf_name);
		
		JButton btnNewButton = new JButton("Thêm");
		btnNewButton.setIcon(new ImageIcon(Add_Account.class.getResource("/images/add.png")));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
	        	Add_Trong_Add them = new Add_Trong_Add(dialog);
	    		dialog.getContentPane().setLayout(new GridLayout(1,1));
	    		dialog.setSize(500, 420);
	    		dialog.setLocationRelativeTo(null);
	        	dialog.getContentPane().add(them);
	        	dialog.setVisible(true);
			}
		});
		btnNewButton.setBackground(new Color(251, 244, 191));
		btnNewButton.setBounds(637, 91, 170, 50);
		btnNewButton.setFocusable(false);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Sửa");
		btnNewButton_1.setIcon(new ImageIcon(Add_Account.class.getResource("/images/edit.png")));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf_password.setEditable(true);
				tf_active.setEditable(true);
				tf_name.setEditable(true);
				tf_floor.setEditable(true);
				bt_save.setVisible(true);
			}
		});
		btnNewButton_1.setBackground(new Color(251, 244, 191));
		btnNewButton_1.setBounds(637, 151, 170, 50);
		btnNewButton_1.setFocusable(false);
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Xóa");
		btnNewButton_2.setIcon(new ImageIcon(Add_Account.class.getResource("/images/delete.png")));
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteAccount();
				loadAccount();
			}
		});
		btnNewButton_2.setBackground(new Color(251, 244, 191));
		btnNewButton_2.setBounds(637, 211, 170, 50);
		btnNewButton_2.setFocusable(false);
		add(btnNewButton_2);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Tầng");
		lblNewLabel_1_3_1.setForeground(new Color(4, 97, 236));
		lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel_1_3_1.setBounds(150, 251, 150, 30);
		add(lblNewLabel_1_3_1);
		
		tf_floor = new JTextField();
		tf_floor.setEditable(false);
		tf_floor.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_floor.setColumns(10);
		tf_floor.setBounds(303, 249, 300, 35);
		add(tf_floor);
		
		bt_save = new JButton("Lưu");
		bt_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				ArrayList<Model_Login> list = data_login.getInstance().loadLogin();
				for(Model_Login model_Login : list) {
					if(tf_username.getText().equals(model_Login.getUsername()) && !tf_username.getText().equals(table.getValueAt(selectedRow, 0).toString())) {
						JOptionPane.showMessageDialog(null, "Mã món ăn đã tồn tại vui lòng nhập lại!");
						return;
					}
				}
				
				editAccount();
				reset();
				
				tf_password.setEditable(false);
				tf_active.setEditable(false);
				tf_name.setEditable(false);
				tf_floor.setEditable(false);
				bt_save.setVisible(false);
			}
		});
		bt_save.setFont(new Font("Tahoma", Font.BOLD, 25));
		bt_save.setFocusable(false);
		bt_save.setBackground(new Color(251, 244, 191));
		bt_save.setBounds(817, 151, 100, 50);
		bt_save.setVisible(false);
		add(bt_save);
		
		
	}
	
	public static void loadAccount() {
		ArrayList<Model_Login> list = data_login.getInstance().loadLogin();
		table_model.setRowCount(0);
		for(Model_Login login : list) {
			Object[] rowData = {login.getUsername(), login.getPassword(), login.getActive(), login.getName(), login.getFloor(), login.getEmail()};
			table_model.addRow(rowData);
		}
	}
	
	public void deleteAccount() {
		int selectRow = table.getSelectedRow();
		if(selectRow >= 0) {
			String user_name = table.getValueAt(selectRow, 0).toString();
			table_model.removeRow(selectRow);
			Data_Login data_login = new Data_Login();
			data_login.deleteLogin(user_name);
		}
	}
	
	public static void addAccount(Model_Login model_login) {
		Object[] newRow = {model_login.getUsername(), model_login.getPassword(), model_login.getActive(), model_login.getName(), model_login.getFloor()};
        table_model.addRow(newRow);
        loadAccount();
	}
	
	public void editAccount() {
		int selectRow = table.getSelectedRow();
		if(selectRow >= 0) {
			String username = tf_username.getText();
			String password = tf_password.getText();
			int active = Integer.parseInt(tf_active.getText());
			String name = tf_name.getText();
			int floor = Integer.parseInt(tf_floor.getText());
			
			Model_Login model_Login = new Model_Login(username, password, active, name, floor, "");
			Data_Login.getInstance().updateLogin(model_Login);
			
			table_model.setValueAt(username, selectRow, 0);  
            table_model.setValueAt(password, selectRow, 1); 
            table_model.setValueAt(active, selectRow, 2); 
            table_model.setValueAt(name, selectRow, 3);
            table_model.setValueAt(floor, selectRow, 4);
		}
	}
	
	public void reset() {
		tf_username.setText("");
		tf_password.setText("");
		tf_active.setText("");
		tf_name.setText("");
		tf_floor.setText("");
	}
}
