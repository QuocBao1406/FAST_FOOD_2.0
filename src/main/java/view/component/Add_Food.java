package view.component;

import java.awt.Color;
import java.awt.Dialog;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import database.Data_Food;
import encode.En_Image;
import model.Model_Food;
import view.pages.Food_Management;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class Add_Food extends JPanel{
	private JDialog dialog;
	private JTextField tf_name;
	private JTextField tf_price;
	private JTextField tf_id;
	private JComboBox cb_type;
	private JLabel lb_image;
	private Data_Food data_food;
	private byte[] image;
	private JLabel lb_image_icon;
	
	public Add_Food(JDialog dialog) {
		this.dialog = dialog;
		
		setForeground(new Color(128, 0, 0));
		setBackground(new Color(255, 236, 206));
		setSize(1000, 500);
		setLayout(null);
		
		JLabel lblThmn = new JLabel("THÊM ĐỒ ĂN");
		lblThmn.setIcon(new ImageIcon(Add_Food.class.getResource("/images/add_food.png")));
		lblThmn.setForeground(new Color(128, 64, 64));
		lblThmn.setHorizontalAlignment(SwingConstants.CENTER);
		lblThmn.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblThmn.setBounds(244, 34, 409, 53);
		add(lblThmn);
		
		lb_image_icon = new JLabel("");
		lb_image_icon.setHorizontalAlignment(SwingConstants.CENTER);
		lb_image_icon.setBounds(679, 125, 220, 190);
		add(lb_image_icon);
		
		lb_image = new JLabel("");
		lb_image.setIcon(new ImageIcon(Add_Food.class.getResource("/images/icon_image.png")));
		lb_image.setBounds(664, 110, 250, 220);
		add(lb_image);
		
		JLabel lblTnMn = new JLabel("Tên Món Ăn");
		lblTnMn.setForeground(new Color(128, 0, 0));
		lblTnMn.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblTnMn.setBounds(78, 186, 165, 30);
		add(lblTnMn);
		
		JLabel lblThLoi = new JLabel("Loại");
		lblThLoi.setForeground(new Color(128, 0, 0));
		lblThLoi.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblThLoi.setBounds(78, 250, 145, 30);
		add(lblThLoi);
		
		tf_name = new JTextField();
		tf_name.setText("");
		tf_name.setFont(new Font("Tahoma", Font.BOLD, 28));
		tf_name.setColumns(10);
		tf_name.setBounds(259, 181, 353, 40);
		add(tf_name);
		
		tf_price = new JTextField();
		tf_price.setText("0");
		tf_price.setFont(new Font("Tahoma", Font.BOLD, 28));
		tf_price.setColumns(10);
		tf_price.setBounds(259, 309, 353, 40);
		add(tf_price);
		
		JButton bt_camera = new JButton("");
		bt_camera.setIcon(new ImageIcon(Add_Food.class.getResource("/images/camera.png")));
		bt_camera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				image = En_Image.imageToBytes(dialog, lb_image_icon);
			}
		});
		bt_camera.setBounds(694, 346, 200, 35);
		add(bt_camera);
		
		JButton bt_add = new JButton("THÊM");
		bt_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Model_Food> list = data_food.getInstance().loadFood();
				for(Model_Food model_food : list) {
					if(tf_id.getText().equals(model_food.getFood_id())) {
						JOptionPane.showMessageDialog(null, "Mã món ăn đã tồn tại vui lòng nhập lại!");
						return;
					}
				}
				
				String food_id = tf_id.getText();
				String food_name = tf_name.getText();
				String food_type = cb_type.getSelectedItem().toString();
				int food_price = Integer.valueOf(tf_price.getText());
				
				if(food_id == null || food_name == null || food_price == 0 || image == null) {
					JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
					return;
				}
				
				Model_Food model_food = new Model_Food(food_id, food_name, food_type, food_price, image);
				
				Data_Food.getInstance().addFood(model_food);
				Food_Management.addFood(model_food);
				Food_Management.loadFood();
				
				dialog.dispose();
			}
		});
		bt_add.setIcon(new ImageIcon(Add_Food.class.getResource("/images/add.png")));
		bt_add.setFont(new Font("Tahoma", Font.BOLD, 28));
		bt_add.setBounds(316, 378, 250, 60);
		add(bt_add);
		
		JLabel lblnGi = new JLabel("Đơn giá");
		lblnGi.setForeground(new Color(128, 0, 0));
		lblnGi.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblnGi.setBounds(78, 314, 145, 30);
		add(lblnGi);
		
		tf_id = new JTextField();
		tf_id.setText("");
		tf_id.setFont(new Font("Tahoma", Font.BOLD, 28));
		tf_id.setColumns(10);
		tf_id.setBounds(259, 117, 353, 40);
		add(tf_id);
		
		JLabel lblMMn = new JLabel("Mã Món Ăn");
		lblMMn.setForeground(new Color(128, 0, 0));
		lblMMn.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblMMn.setBounds(78, 122, 165, 30);
		add(lblMMn);
		
		cb_type = new JComboBox();
		cb_type.setForeground(new Color(39, 39, 39));
		cb_type.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cb_type.setModel(new DefaultComboBoxModel(new String[] {"Burger", "Gà", "Pizza", "Cơm", "Ăn Kèm", "Drinks"}));
		cb_type.setBounds(259, 245, 353, 40);
		add(cb_type);
		
		
	}
}
