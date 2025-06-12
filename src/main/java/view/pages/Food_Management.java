package view.pages;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.Data_Food;
import encode.En_Image;
import model.Model_Food;
import swing.PlaceholderTextField;
import view.component.Add_Food;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Food_Management extends JPanel{
	private JTable table;
	private JTextField tf_id;
	private JTextField tf_name;
	private JTextField tf_price;
	private JLabel lb_add;
	private JTextField tf_find_name;
	private JLabel lb_filter;
	private JLabel lb_edit;
	private JLabel lb_delete;
	private JButton bt_camera;
	private JButton bt_save;
	private JComboBox cb_type;
	private static JLabel lb_image;
	private static Data_Food data_food;
	private Model_Food model_food;
	private JComboBox cb_find_type;
	private static DefaultTableModel table_model;
	private byte[] image;
	private JLabel image_icon;
	
	public Food_Management() {
		setBackground(new Color(255, 242, 189));
		setSize(1250, 820);
		setLayout(null);
		
		JLabel lb_title = new JLabel("");
		lb_title.setIcon(new ImageIcon(Food_Management.class.getResource("/images/title_logo.png")));
		lb_title.setBounds(372, 10, 500, 80);
		add(lb_title);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 460, 1170, 320);
		add(scrollPane);
		
		table_model = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"M\u00E3 S\u1EA3n Ph\u1EA9m", "T\u00EAn S\u1EA3n Ph\u1EA9m", "Lo\u1EA1i S\u1EA3n Ph\u1EA9m", "\u0110\u01A1n Gi\u00E1"
			}
		);
		loadFood();
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table.setModel(table_model);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(500);
		table.getColumnModel().getColumn(2).setPreferredWidth(250);
		table.getColumnModel().getColumn(3).setPreferredWidth(250);
		
		Font headerFont = new Font("Arial", Font.BOLD, 18);
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
                        tf_id.setText(table.getValueAt(selectedRow, 0).toString());
                        tf_name.setText(table.getValueAt(selectedRow, 1).toString());
                        cb_type.setSelectedItem(table.getValueAt(selectedRow, 2).toString());
                        tf_price.setText(table.getValueAt(selectedRow, 3).toString());
                        En_Image.bytesToImageLabel(Data_Food.getInstance().getImage(table.getValueAt(selectedRow, 0).toString()), lb_image);
                        
                        select = table.getValueAt(selectedRow, 0).toString();
                    }
                }
            }
        });
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Mã Món Ăn");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(80, 109, 218, 37);
		add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 226, 200));
		panel.setBounds(40, 380, 1170, 70);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Tên Sản Phẩm");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_2.setBounds(50, 10, 185, 50);
		panel.add(lblNewLabel_2);
		
		tf_find_name = new PlaceholderTextField("Nhập tên món ăn...");
		tf_find_name.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_find_name.setBounds(245, 21, 200, 30);
		tf_find_name.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                searchFood();
            }
            public void removeUpdate(DocumentEvent e) {
            	searchFood();
            }
            public void changedUpdate(DocumentEvent e) {
            	searchFood();
            }
        });
		panel.add(tf_find_name);
		tf_find_name.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("Loại Sản Phẩm");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_2_1.setBounds(476, 10, 185, 50);
		panel.add(lblNewLabel_2_1);
		
		cb_find_type = new JComboBox();
		cb_find_type.setModel(new DefaultComboBoxModel(new String[] {"Tất cả", "Burger", "Gà", "Pizza", "Cơm", "Ăn Kèm", "Drinks"}));
		cb_find_type.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cb_find_type.setBounds(671, 20, 200, 30);
		panel.add(cb_find_type);
		
		lb_filter = new JLabel("Lọc");
		lb_filter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { 
				lb_filter.setBackground(new Color(217, 136, 0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lb_filter.setBackground(new Color(255, 134, 89));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				filterFood();
			}
		});
		lb_filter.setForeground(Color.WHITE);
		lb_filter.setBackground(new Color(255, 143, 89));
		lb_filter.setFont(new Font("Tahoma", Font.BOLD, 25));
		lb_filter.setHorizontalAlignment(SwingConstants.CENTER);
		lb_filter.setOpaque(true);
		lb_filter.setBounds(910, 12, 170, 45);
		panel.add(lb_filter);
		
		tf_id = new JTextField();
		tf_id.setEditable(false);
		tf_id.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tf_id.setBounds(280, 103, 250, 43);
		add(tf_id);
		tf_id.setColumns(10);
		
		JLabel lblTnSnPhm = new JLabel("Tên Món Ăn");
		lblTnSnPhm.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTnSnPhm.setBounds(80, 175, 218, 37);
		add(lblTnSnPhm);
		
		tf_name = new JTextField();
		tf_name.setEditable(false);
		tf_name.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tf_name.setColumns(10);
		tf_name.setBounds(280, 169, 250, 43);
		add(tf_name);
		
		JLabel lblLoiSnPhm = new JLabel("Loại");
		lblLoiSnPhm.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblLoiSnPhm.setBounds(80, 244, 224, 37);
		add(lblLoiSnPhm);
		
		JLabel lblnGi = new JLabel("Đơn Giá");
		lblnGi.setHorizontalAlignment(SwingConstants.LEFT);
		lblnGi.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblnGi.setBounds(80, 314, 218, 37);
		add(lblnGi);
		
		tf_price = new JTextField();
		tf_price.setEditable(false);
		tf_price.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tf_price.setColumns(10);
		tf_price.setBounds(280, 308, 250, 43);
		add(tf_price);
		
		cb_type = new JComboBox();
		cb_type.setEnabled(false);
		cb_type.setForeground(new Color(54, 54, 54));
		cb_type.setFont(new Font("Tahoma", Font.PLAIN, 25));
		cb_type.setModel(new DefaultComboBoxModel(new String[] {"Burger", "Gà", "Pizza", "Cơm", "Ăn Kèm", "Drinks"}));
		cb_type.setBounds(280, 238, 250, 43);
		add(cb_type);
		
		lb_image = new JLabel("");
		lb_image.setHorizontalAlignment(SwingConstants.CENTER);
		lb_image.setBounds(600, 115, 220, 190);
		add(lb_image);
		
		image_icon = new JLabel("");
		image_icon.setHorizontalAlignment(SwingConstants.CENTER);
		image_icon.setIcon(new ImageIcon(Food_Management.class.getResource("/images/icon_image.png")));
		image_icon.setBounds(585, 100, 250, 220);
		add(image_icon);
		
		lb_add = new JLabel("Thêm");
		lb_add.setIcon(new ImageIcon(Food_Management.class.getResource("/images/add_green.png")));
		lb_add.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { 
				lb_add.setBackground(new Color(0, 162, 170));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lb_add.setBackground(new Color(117, 202, 238));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				JDialog dialog = new JDialog();
	        	Add_Food them = new Add_Food(dialog);
	    		dialog.getContentPane().setLayout(new GridLayout(1,1));
	    		dialog.setSize(1000, 550);
	    		dialog.setLocationRelativeTo(null);
	        	dialog.getContentPane().add(them);
	        	dialog.setVisible(true);
			}
		});
		lb_add.setForeground(new Color(255, 255, 255));
		lb_add.setHorizontalAlignment(SwingConstants.CENTER);
		lb_add.setFont(new Font("Tahoma", Font.BOLD, 35));
		lb_add.setBackground(new Color(117, 202, 238));
		lb_add.setOpaque(true);
		lb_add.setBounds(899, 103, 200, 60);
		add(lb_add);
		
		lb_edit = new JLabel("Sửa");
		lb_edit.setIcon(new ImageIcon(Food_Management.class.getResource("/images/edit.png")));
		lb_edit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { 
				lb_edit.setBackground(new Color(200, 0, 194));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lb_edit.setBackground(new Color(255, 147, 255));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
//				tf_id.setEnabled(true);
				tf_name.setEditable(true);
				cb_type.setEnabled(true);
				tf_price.setEditable(true);
				bt_camera.setVisible(true);
				bt_save.setVisible(true);
			}
		});
		lb_edit.setForeground(new Color(255, 255, 255));
		lb_edit.setOpaque(true);
		lb_edit.setHorizontalAlignment(SwingConstants.CENTER);
		lb_edit.setFont(new Font("Tahoma", Font.BOLD, 35));
		lb_edit.setBackground(new Color(255, 147, 255));
		lb_edit.setBounds(899, 174, 200, 60);
		add(lb_edit);
		
		lb_delete = new JLabel("Xóa");
		lb_delete.setIcon(new ImageIcon(Food_Management.class.getResource("/images/delete.png")));
		lb_delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { 
				lb_delete.setBackground(new Color(208, 0, 73));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lb_delete.setBackground(new Color(255, 85, 111));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteFood();
				table_model.setRowCount(0);
				loadFood();
			}
		});
		lb_delete.setForeground(Color.WHITE);
		lb_delete.setOpaque(true);
		lb_delete.setHorizontalAlignment(SwingConstants.CENTER);
		lb_delete.setFont(new Font("Tahoma", Font.BOLD, 35));
		lb_delete.setBackground(new Color(255, 85, 111));
		lb_delete.setBounds(899, 245, 200, 60);
		add(lb_delete);
		
		bt_camera = new JButton("");
		bt_camera.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				image = En_Image.imageToBytes(panel, lb_image);
            }
		});
		bt_camera.setFocusPainted(false);
		bt_camera.setBackground(new Color(245, 241, 226));
		bt_camera.setIcon(new ImageIcon(Food_Management.class.getResource("/images/camera.png")));
		bt_camera.setBounds(651, 327, 125, 35);
		add(bt_camera);
		bt_camera.setVisible(false);
		
		bt_save = new JButton("Lưu");
		bt_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				ArrayList<Model_Food> list = data_food.getInstance().loadFood();
				for(Model_Food model_food : list) {
					if(tf_id.getText().equals(model_food.getFood_id()) && !tf_id.getText().equals(table.getValueAt(selectedRow, 0).toString())) {
						JOptionPane.showMessageDialog(null, "Mã món ăn đã tồn tại vui lòng nhập lại!");
						return;
					}
				}

				editFood();
				reset();
				tf_id.setEditable(false);
				tf_name.setEditable(false);
				cb_type.setEnabled(false);
				tf_price.setEditable(false);
				bt_camera.setVisible(false);
				bt_save.setVisible(false);
			}
		});
		bt_save.setFocusPainted(false);
		bt_save.setBackground(new Color(245, 241, 226));
		bt_save.setFont(new Font("Tahoma", Font.BOLD, 25));
		bt_save.setIcon(new ImageIcon(Food_Management.class.getResource("/images/save.png")));
		bt_save.setBounds(899, 319, 200, 43);
		add(bt_save);
		bt_save.setVisible(false);
		
		reset();
	}
	
	public static void loadFood() {
		ArrayList<Model_Food> list = data_food.getInstance().loadFood();
		table_model.setRowCount(0);
		for(Model_Food model_food : list) {
			Object[] rowData = {model_food.getFood_id(), model_food.getFood_name(), model_food.getFood_type(), model_food.getFood_price(), model_food.getFood_image()};
			table_model.addRow(rowData);
		}
	}
	
	public static void addFood(Model_Food model_food) {
		Object[] newRow = {model_food.getFood_id(), model_food.getFood_name(), model_food.getFood_type(), model_food.getFood_price()};
        table_model.addRow(newRow);
        loadFood();
	}
	
	public void deleteFood() {
		int selectRow = table.getSelectedRow();
		if(selectRow >= 0) {
			String food_id = table.getValueAt(selectRow, 0).toString();
			table_model.removeRow(selectRow);
			Data_Food data_food = new Data_Food();
			data_food.deleteFood(food_id);
		}
	}
	
	public void editFood() {
		int selectRow = table.getSelectedRow();
		if(selectRow >= 0) {
			String food_id = tf_id.getText();
			String food_name = tf_name.getText();
			String food_type = cb_type.getSelectedItem().toString();
			int food_price = Integer.parseInt(tf_price.getText());
			image = En_Image.convertLabelImageToBytes(lb_image);
			
			Model_Food model_food = new Model_Food(food_id, food_name, food_type, food_price, image);
			Data_Food.getInstance().updateFood(model_food);
			
			table_model.setValueAt(food_id, selectRow, 0);  
            table_model.setValueAt(food_name, selectRow, 1); 
            table_model.setValueAt(food_type, selectRow, 2); 
            table_model.setValueAt(food_price, selectRow, 3); 
		}
	}
	
	public void searchFood() {
		String searchname = tf_find_name.getText();
		if(searchname.isEmpty() || searchname.equals("Nhập tên món ăn...")) {
			loadFood();
		} else {
			ArrayList<Model_Food> list = Data_Food.getInstance().searchFood("%" + searchname + "%");
			table_model.setRowCount(0);
			for(Model_Food model_food : list) {
				Object[] rowData = {model_food.getFood_id(), model_food.getFood_name(), model_food.getFood_type(), model_food.getFood_price()};
				table_model.addRow(rowData);
			}
		}
		reset();
	}
	
	public void filterFood() {
		String condition = "1=1";
		if(!cb_find_type.getSelectedItem().toString().equals("Tất cả")) {
			condition += "AND Food_Type='" + cb_find_type.getSelectedItem().toString() + "'";
		}
		ArrayList<Model_Food> list = Data_Food.getInstance().filterFood(condition);
		table_model.setRowCount(0);
		for(Model_Food model_food : list) {
			Object[] rowData = {model_food.getFood_id(), model_food.getFood_name(), model_food.getFood_type(), model_food.getFood_price()};
			table_model.addRow(rowData);
		}
		reset();
	}
	
	public void reset() {
		tf_id.setText("");
		tf_name.setText("");
		cb_type.setSelectedIndex(0);
		tf_price.setText("");
		lb_image.setIcon(null);
	}
}
