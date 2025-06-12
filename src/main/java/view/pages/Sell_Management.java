package view.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.Data_Customer;
import database.Data_Food;
import database.Data_Sell;
import model.Model_Customer;
import model.Model_Food;
import model.Model_Sell;
import view.component.Add_Food;
import view.component.Pay_Sell;

public class Sell_Management extends JPanel{
	private static JTextField tf_id;
	private static JTextField tf_rank;
	private static JTextField tf_mucgiam;
	private static JTextField tf_total;
	private JTable table;
	private static JPanel menupanel;
	private static Data_Sell data_sell;
	private Model_Sell model_sell;
	private static DefaultTableModel table_model;
	private long totalAmount = 0;
	private JLabel countLabel;
	private JLabel priceLabel;
	private JSpinner pn_count;
	private JLabel lb_image;
	private JLabel lb_name;
	private static ArrayList<JSpinner> spinnerList = new ArrayList<>();

	public Sell_Management() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setBackground(new Color(255, 242, 189));
		setSize(1250, 820);
		setLayout(null);
		
		JLabel lb_title = new JLabel("");
		lb_title.setIcon(new ImageIcon(Sell_Management.class.getResource("/images/title_logo.png")));
		lb_title.setBounds(372, 10, 500, 80);
		add(lb_title);
		
		JScrollPane scrollPane = new JScrollPane(menupanel);
		scrollPane.setBounds(10, 131, 860, 649);
		scrollPane.setBackground(new Color(225, 253, 241));
		scrollPane.setOpaque(true);
		add(scrollPane);
		
		menupanel = new JPanel();
		menupanel.setBackground(new Color(250, 249, 235));
		menupanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		scrollPane.setViewportView(menupanel);
		menupanel.setLayout(new GridLayout(0, 3, 10, 10));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(252, 251, 241));
		panel.setBounds(882, 88, 340, 692);
		add(panel);
		panel.setLayout(null);
		
		JLabel lb_reset = new JLabel("Đặt Lại");
		lb_reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lb_reset.setBackground(new Color(0, 181, 176));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lb_reset.setBackground(new Color(87, 230, 255));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// Đặt lại số lượng món ăn trong bảng
		        for (int i = 0; i < table_model.getRowCount(); i++) {
		            table_model.setValueAt(0, i, 1); // Đặt lại số lượng là 0
		            table_model.setValueAt(0, i, 2); // Đặt lại giá là 0 (giá sẽ được tính lại khi thay đổi số lượng)
		        }

		        // Cập nhật lại tổng tiền sau khi đặt lại
		        tf_total.setText("");

		        // Đặt lại số lượng món ăn trong menu
		        for (JSpinner spinner : spinnerList) {
		            spinner.setValue(0); // Đặt lại giá trị của spinner về 0
		        }
		    }
		});
		lb_reset.setBackground(new Color(87, 230, 255));
		lb_reset.setFont(new Font("Tahoma", Font.BOLD, 25));
		lb_reset.setForeground(new Color(255, 255, 255));
		lb_reset.setHorizontalAlignment(SwingConstants.CENTER);
		lb_reset.setBounds(10, 627, 150, 55);
		lb_reset.setOpaque(true);
		panel.add(lb_reset);
		
		JLabel lb_pay = new JLabel("Thanh Toán");
		lb_pay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lb_pay.setBackground(new Color(41, 154, 29));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lb_pay.setBackground(new Color(124, 242, 121));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// Lấy thông tin bán hàng
		        String saleCode = "BHMN" + System.currentTimeMillis();
		        String currentDate = LocalDate.now().toString();

		        // Tạo chuỗi chứa thông tin chi tiết đơn hàng
		        StringBuilder orderDetails = new StringBuilder();
		        int totalAmount = 0;
		        ArrayList<Object[]> orderData = new ArrayList<>();
		        int total_count = 0;

		        // Lấy thông tin từ bảng
		        for (int i = 0; i < table_model.getRowCount(); i++) {
		            String foodName = (String) table_model.getValueAt(i, 0);
		            int quantity = (int) table_model.getValueAt(i, 1);
		            double price = (double) table_model.getValueAt(i, 2);
		            orderDetails.append(foodName).append(" - ").append(quantity).append(" x ").append(price).append(" VND\n");
		            orderData.add(new Object[] {foodName, quantity, price});
		            totalAmount += price;
		            total_count += quantity;
		        }

		        // Tạo một JDialog
		        JDialog dialog = new JDialog();
		        dialog.setTitle("Thông Tin Thanh Toán");
		        dialog.setSize(500, 600);
		        dialog.setLocationRelativeTo(null);  // Đặt dialog ở giữa màn hình
		        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		        // Tạo panel Pay_Sell
		        Pay_Sell paySellPanel = new Pay_Sell(dialog);
		        paySellPanel.displayOrderDetails(saleCode, orderData.toArray(new Object[0][0]), total_count,  totalAmount);

		        // Thêm panel vào dialog
		        dialog.getContentPane().add(paySellPanel);
		        dialog.setVisible(true);
		    }
		});
		lb_pay.setBackground(new Color(124, 242, 121));
		lb_pay.setForeground(new Color(255, 255, 255));
		lb_pay.setFont(new Font("Tahoma", Font.BOLD, 25));
		lb_pay.setHorizontalAlignment(SwingConstants.CENTER);
		lb_pay.setBounds(170, 627, 160, 55);
		lb_pay.setOpaque(true);
		panel.add(lb_pay);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 234, 221));
		panel_1.setBounds(10, 433, 320, 184);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mã Khách Hàng");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 10, 140, 29);
		panel_1.add(lblNewLabel);
		
		tf_id = new JTextField();
		tf_id.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_id.setBounds(160, 10, 115, 29);
		panel_1.add(tf_id);
		tf_id.setColumns(10);
		
		JLabel Hạng = new JLabel("Hạng");
		Hạng.setHorizontalAlignment(SwingConstants.CENTER);
		Hạng.setFont(new Font("Tahoma", Font.BOLD, 17));
		Hạng.setBounds(10, 55, 140, 29);
		panel_1.add(Hạng);
		
		tf_rank = new JTextField();
		tf_rank.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_rank.setEnabled(false);
		tf_rank.setColumns(10);
		tf_rank.setBounds(160, 55, 150, 29);
		panel_1.add(tf_rank);
		
		JLabel lblNewLabel_2 = new JLabel("Mức Giảm %");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_2.setBounds(10, 100, 140, 29);
		panel_1.add(lblNewLabel_2);
		
		tf_mucgiam = new JTextField();
		tf_mucgiam.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_mucgiam.setEnabled(false);
		tf_mucgiam.setColumns(10);
		tf_mucgiam.setBounds(160, 100, 150, 29);
		panel_1.add(tf_mucgiam);
		
		tf_total = new JTextField();
		tf_total.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_total.setEnabled(false);
		tf_total.setColumns(10);
		tf_total.setBounds(160, 145, 150, 29);
		panel_1.add(tf_total);
		
		JLabel lblNewLabel_2_1 = new JLabel("Tổng Tiền VND");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_2_1.setBounds(10, 145, 140, 29);
		panel_1.add(lblNewLabel_2_1);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(new Color(245, 232, 211));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalPrice();
			}
		});
		btnNewButton.setBounds(279, 10, 30, 29);
		panel_1.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Thanh Toán Hóa Đơn");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(248, 226, 209));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(0, 0, 340, 42);
		panel.add(lblNewLabel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 50, 320, 374);
		panel.add(scrollPane_1);
		
		
		table_model = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"T\u00EAn M\u00F3n", "S\u1ED1 L\u01B0\u1EE3ng", "Gi\u00E1"
			}
		);
		table = new JTable(table_model);
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		Font headerFont = new Font("Arial", Font.BOLD, 10);
		table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 30));
		table.getTableHeader().setFont(headerFont);
		table.setRowHeight(30);
		table.getColumnModel().getColumn(0).setPreferredWidth(140);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		scrollPane_1.setViewportView(table);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(248, 226, 209));
		panel_2.setBounds(10, 88, 860, 45);
		add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Menu Bán Hàng");
		lblNewLabel_3.setBackground(new Color(248, 226, 209));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(0, 0, 860, 45);
		panel_2.add(lblNewLabel_3);
		
		lb_reset.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        lb_reset.setBackground(new Color(0, 181, 176));
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
		        lb_reset.setBackground(new Color(87, 230, 255));
		    }
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // Đặt lại số lượng món ăn trong bảng
		        for (int i = 0; i < table_model.getRowCount(); i++) {
		            table_model.setValueAt(0, i, 1); // Đặt lại số lượng là 0
		            table_model.setValueAt(0, i, 2); // Đặt lại giá là 0
		        }

		        // Cập nhật lại tổng tiền sau khi đặt lại
		        tf_total.setText("");

		        // Đặt lại số lượng món ăn trong menu
		        for (JSpinner spinner : spinnerList) {
		            spinner.setValue(0); // Đặt lại giá trị của spinner về 0
		        }

		        // Làm mới dữ liệu menu
		        menupanel.removeAll(); // Xóa toàn bộ nội dung hiện tại
		        loadFoodToMenu(); // Tải lại dữ liệu từ nguồn
		        menupanel.revalidate();
		        menupanel.repaint();
		    }
		});
		
		loadFoodToMenu();
	}
	
	public static void loadFoodToMenu() {
		ArrayList<Model_Food> list = Data_Food.getInstance().loadFood();
		for(Model_Food model_food : list) {
			JPanel itemPanel = createItemPanel(model_food.getFood_name(), model_food.getFood_price(), model_food.getFood_image());
			menupanel.add(itemPanel);
			menupanel.revalidate();
			menupanel.repaint();
		}
	}
	
	public static JPanel createItemPanel(String name, int price, byte[] image) {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setBorder(BorderFactory.createLineBorder(new Color(255, 238, 238)));

	    // Tạo ảnh món ăn
	    JLabel lb_image = new JLabel();
	    lb_image.setPreferredSize(new Dimension(100, 100));
	    lb_image.setIcon(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));

	    // Tạo spinner số lượng
	    JSpinner pn_count = createQuantitySpinner(name, price);

	    // Panel chứa số lượng
	    JPanel countPanel = new JPanel(new GridLayout(0, 2));
	    JLabel countLabel = new JLabel("Số lượng:");
	    countLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
	    countPanel.add(countLabel);
	    countPanel.add(pn_count);

	    // Thông tin món ăn
	    JPanel infoPanel = new JPanel(new GridLayout(0, 1));
	    JLabel lb_name = new JLabel(name);
	    lb_name.setFont(new Font("Tahoma", Font.BOLD, 13));
	    JLabel priceLabel = new JLabel("Giá: " + price + " VND");
	    priceLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
	    infoPanel.add(lb_name);
	    infoPanel.add(priceLabel);
	    infoPanel.add(countPanel);
	    infoPanel.setBorder(new EmptyBorder(5, 15, 5, 5));

	    panel.add(lb_image, BorderLayout.WEST);
	    panel.add(infoPanel, BorderLayout.CENTER);

	    return panel;
	}
	
	public static JSpinner createQuantitySpinner(String name, double price) {
	    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 100, 1);
	    JSpinner spinner = new JSpinner(spinnerModel);
	    spinner.setFont(new Font("Tahoma", Font.PLAIN, 20));

	    // Lưu spinner vào danh sách
	    spinnerList.add(spinner);

	    spinner.addChangeListener(new ChangeListener() {
	        @Override
	        public void stateChanged(ChangeEvent e) {
	            String tb_name = name;
	            int quantity = (int) spinner.getValue();
	            double tb_price = price;

	            boolean exists = false;
	            for (int i = 0; i < table_model.getRowCount(); i++) {
	                if (table_model.getValueAt(i, 0).equals(tb_name)) {
	                    exists = true;
	                    if (quantity > 0) {
	                        table_model.setValueAt(quantity, i, 1);
	                        table_model.setValueAt(tb_price * quantity, i, 2);
	                    } else {
	                        table_model.removeRow(i);
	                    }
	                    break;
	                }
	            }
	            if (!exists && quantity > 0) {
	                Object[] rowData = {tb_name, quantity, tb_price * quantity};
	                table_model.addRow(rowData);
	            }
	            totalPrice();
	        }
	    });
	    return spinner;
	}

	public static void totalPrice() {
	    Data_Customer data = new Data_Customer();
	    data.getInstance().searchCustomer(tf_id.getText().toString());
	    
	    if(tf_id.getText().equals("")) {
	    	tf_rank.setText("");
	    	tf_mucgiam.setText("");
	    }

	    double total = 0;

	    // Kiểm tra bảng dữ liệu
	    if (table_model != null && table_model.getRowCount() > 0) {
	        for (int i = 0; i < table_model.getRowCount(); i++) {
	            Object value = table_model.getValueAt(i, 2);
	            if (value instanceof Double) {
	                total += (double) value;
	            } else if (value instanceof String) {
	                try {
	                    total += Double.parseDouble((String) value);
	                } catch (NumberFormatException e) {
	                    System.out.println("Lỗi chuyển đổi giá trị tại hàng " + i + ": " + value);
	                }
	            }
	        }
	    }

		// Lấy danh sách khách hàng
		ArrayList<Model_Customer> list = Data_Customer.getInstance().loadCustomer();
		for (Model_Customer model_customer : list) {
			if (tf_id.getText().toString().equals(model_customer.getCustomer_Id())) {
				switch (model_customer.getCustomer_Rank()) {
				case "Đồng":
					tf_rank.setText(model_customer.getCustomer_Rank());
					tf_mucgiam.setText("5");
					total -= total * 5 / 100;
					break;
				case "Bạc":
					tf_rank.setText(model_customer.getCustomer_Rank());
					tf_mucgiam.setText("10");
					total -= total * 10 / 100;
					break;
				case "Vàng":
					tf_rank.setText(model_customer.getCustomer_Rank());
					tf_mucgiam.setText("15");
					total -= total * 15 / 100;
					break;
				case "Kim cương":
					tf_rank.setText(model_customer.getCustomer_Rank());
					tf_mucgiam.setText("20");
					total -= total * 20 / 100;
					System.out.println(model_customer.getCustomer_Rank());
					break;
				default:
					tf_mucgiam.setText("0");
					break;
				}
			}
		}

		// Cập nhật tổng giá trị
		tf_total.setText(total+"");
	}
	
	public static double getTotal() {
		return Double.parseDouble(tf_total.getText());
	}
	
	public static String getCustomer_Id() {
		return tf_id.getText();
	}
	
	
}
