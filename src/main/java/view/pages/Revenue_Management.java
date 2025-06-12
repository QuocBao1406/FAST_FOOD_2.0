package view.pages;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser;

import javax.swing.JButton;

import database.Data_Customer;
import database.Data_Food;
import database.Data_Sell;
import encode.En_Image;
import model.Model_Customer;
import model.Model_Food;
import model.Model_Sell;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultComboBoxModel;

public class Revenue_Management extends JPanel {
	private JTextField tf_id;
	private JTextField tf_total;
	private JTextField tf_date;
	private JTextField tf_id_cus;
	private JTextField tf_quantiry;
	private JTable table;
	private JButton bt_save;
	private JButton bt_delete;
	private JButton bt_edit;
	private JDateChooser tf_ngay1;
	private JDateChooser tf_ngay2;
	private JButton bt_reset;
	private JComboBox<String> cb_time;
	private static DefaultTableModel table_model;
	private static Data_Sell data_sell;
	private JTextField tf_name;
	public Revenue_Management() {
		setBackground(new Color(255, 242, 189));
		setSize(1250, 820);
		setLayout(null);
		
		JLabel lb_title = new JLabel("");
		lb_title.setIcon(new ImageIcon(Food_Management.class.getResource("/images/title_logo.png")));
		lb_title.setBounds(372, 10, 500, 80);
		add(lb_title);
		
		JLabel lblNewLabel_4 = new JLabel("Mã đơn hàng");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_4.setBounds(10, 93, 170, 30);
		add(lblNewLabel_4);
		
		tf_id = new JTextField();
		tf_id.setEditable(false);
		tf_id.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_id.setColumns(10);
		tf_id.setBounds(210, 91, 230, 35);
		add(tf_id);
		
		JLabel lblNewLabel_4_2 = new JLabel("Doanh thu");
		lblNewLabel_4_2.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_4_2.setBounds(459, 93, 139, 30);
		add(lblNewLabel_4_2);
		
		tf_total = new JTextField();
		tf_total.setEditable(false);
		tf_total.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_total.setColumns(10);
		tf_total.setBounds(595, 91, 225, 35);
		add(tf_total);
		
		JLabel lblNewLabel_4_3 = new JLabel("Ngày mua");
		lblNewLabel_4_3.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_4_3.setBounds(835, 91, 130, 30);
		add(lblNewLabel_4_3);
		
		tf_date = new JTextField();
		tf_date.setEditable(false);
		tf_date.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_date.setColumns(10);
		tf_date.setBounds(970, 88, 255, 35);
		add(tf_date);
		
		JLabel lblNewLabel_4_1 = new JLabel("Mã khách hàng");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_4_1.setBounds(10, 141, 190, 30);
		add(lblNewLabel_4_1);
		
		tf_id_cus = new JTextField();
		tf_id_cus.setEditable(false);
		tf_id_cus.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_id_cus.setColumns(10);
		tf_id_cus.setBounds(210, 139, 230, 35);
		add(tf_id_cus);
		
		JLabel lblNewLabel_4_1_1 = new JLabel("Số lượng");
		lblNewLabel_4_1_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_4_1_1.setBounds(459, 141, 139, 30);
		add(lblNewLabel_4_1_1);
		
		tf_quantiry = new JTextField();
		tf_quantiry.setEditable(false);
		tf_quantiry.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_quantiry.setColumns(10);
		tf_quantiry.setBounds(595, 139, 225, 35);
		add(tf_quantiry);
		
		bt_edit = new JButton("Sửa");
		bt_edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bt_save.setVisible(true);
				tf_id.setEditable(true);
				tf_id_cus.setEditable(true);
				tf_total.setEditable(true);
				tf_quantiry.setEditable(true);
				tf_date.setEditable(true);
			}
		});
		bt_edit.setBackground(new Color(249, 230, 213));
		bt_edit.setIcon(new ImageIcon(Revenue_Management.class.getResource("/images/edit.png")));
		bt_edit.setFont(new Font("Tahoma", Font.BOLD, 20));
		bt_edit.setBounds(845, 176, 130, 55);
		bt_edit.setFocusable(false);
		add(bt_edit);
		
		bt_delete = new JButton("Xóa");
		bt_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSell();
				table_model.setRowCount(0);
				loadSell();
				reset();
			}
		});
		bt_delete.setBackground(new Color(249, 230, 213));
		bt_delete.setIcon(new ImageIcon(Revenue_Management.class.getResource("/images/delete.png")));
		bt_delete.setFont(new Font("Tahoma", Font.BOLD, 20));
		bt_delete.setBounds(985, 176, 130, 55);
		bt_delete.setFocusable(false);
		add(bt_delete);
		
		bt_save = new JButton("Lưu");
		bt_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editSell();
				reset();
				
				bt_save.setVisible(false);
				tf_id.setEditable(false);
				tf_id_cus.setEditable(false);
				tf_total.setEditable(false);
				tf_quantiry.setEditable(false);
				tf_date.setEditable(false);
			}
		});
		bt_save.setBackground(new Color(249, 230, 213));
		bt_save.setFont(new Font("Tahoma", Font.BOLD, 20));
		bt_save.setBounds(1125, 176, 100, 55);
		bt_save.setFocusable(false);
		add(bt_save);
		bt_save.setVisible(false);
		
		bt_reset = new JButton("");
		bt_reset.setIcon(new ImageIcon(Revenue_Management.class.getResource("/images/reload.png")));
		bt_reset.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Reset các trường nhập liệu về rỗng
		        tf_id.setText("");
		        tf_id_cus.setText("");
		        tf_total.setText("");
		        tf_quantiry.setText("");
		        tf_date.setText("");
		        
		        // Reset các trường ngày về null
		        tf_ngay1.setDate(null);
		        tf_ngay2.setDate(null);

		        // Đặt lại combobox cb_time về giá trị "Tất cả"
		        cb_time.setSelectedIndex(0);
		        
		        // Tải lại tất cả dữ liệu (không lọc)
		        table_model.setRowCount(0); // Xóa hết các dòng dữ liệu hiện tại
		        loadSell(); // Lọc lại toàn bộ dữ liệu
		    }
		});
		bt_reset.setHorizontalTextPosition(SwingConstants.CENTER);
		bt_reset.setBounds(10, 193, 50, 38);
		add(bt_reset);
		
		tf_ngay1 = new JDateChooser();
		tf_ngay1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		tf_ngay1.setDateFormatString("dd/MM/yyyy");
		tf_ngay1.setBackground(new Color(219, 219, 219));
		tf_ngay1.setBounds(70, 193, 237, 38);
		add(tf_ngay1);
		
		JLabel lblNewLabel = new JLabel("đến", SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(207, 191, 188));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(306, 193, 68, 38);
		add(lblNewLabel);
		
		tf_ngay2 = new JDateChooser();
		tf_ngay2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		tf_ngay2.setDateFormatString("dd/MM/yyyy");
		tf_ngay2.setBackground(new Color(219, 219, 219));
		tf_ngay2.setBounds(374, 193, 237, 38);
		add(tf_ngay2);
		
		JButton bt_find = new JButton("");
		bt_find.setIcon(new ImageIcon(Revenue_Management.class.getResource("/images/find.png")));
		bt_find.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        Date startDate = tf_ngay1.getDate();
		        Date endDate = tf_ngay2.getDate();

		        // Kiểm tra nếu cả hai ngày đều hợp lệ
		        if (startDate != null && endDate != null) {
		            filterSellByDateRange(startDate, endDate);
		        } else {
		            // Nếu ngày không hợp lệ, thông báo hoặc tải lại dữ liệu
		            JOptionPane.showMessageDialog(null, "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc.");
		        }
		    }
		});

		bt_find.setHorizontalTextPosition(SwingConstants.CENTER);
		bt_find.setBounds(621, 193, 50, 38);
		add(bt_find);
		
		cb_time = new JComboBox<String>();
		cb_time.setModel(new DefaultComboBoxModel(new String[] {"Tất cả","Hôm nay", "7 ngày qua", "Tháng này", "Tháng trước"}));
		cb_time.setFont(new Font("Tahoma", Font.PLAIN, 17));
		cb_time.setBounds(681, 193, 139, 38);
		add(cb_time);
		
		cb_time.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {
		            String selectedTime = (String) cb_time.getSelectedItem();
		            filterSellByTime(selectedTime);  // Gọi hàm lọc
		        }
		    }
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 241, 1215, 540);
		add(scrollPane);
		
		table_model = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Mã đơn hàng", "S\u1ED1 l\u01B0\u1EE3ng", "Doanh thu", "Ng\u00E0y mua", "M\u00E3 kh\u00E1ch h\u00E0ng", "Tên Món"
			}
		);
		loadSell();
		table = new JTable(table_model);
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(250);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.getColumnModel().getColumn(4).setPreferredWidth(165);
		table.getColumnModel().getColumn(5).setPreferredWidth(300);
		table.setBackground(new Color(255, 255, 255));
		
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
						tf_quantiry.setText(table.getValueAt(selectedRow, 1).toString());
						tf_total.setText(table.getValueAt(selectedRow, 2).toString());
						tf_date.setText(table.getValueAt(selectedRow, 3).toString());
						tf_name.setText(table.getValueAt(selectedRow, 5).toString());
						if (table.getValueAt(selectedRow, 4) == null) {
							tf_id_cus.setText("");
						} else {
							tf_id_cus.setText(table.getValueAt(selectedRow, 4).toString());
						}

						select = table.getValueAt(selectedRow, 0).toString();
					}
				}
			}
		});
		
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_4_4 = new JLabel("Tên Món");
		lblNewLabel_4_4.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_4_4.setBounds(835, 138, 130, 30);
		add(lblNewLabel_4_4);
		
		tf_name = new JTextField();
		tf_name.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_name.setEditable(false);
		tf_name.setColumns(10);
		tf_name.setBounds(970, 133, 255, 35);
		add(tf_name);
	}
	
	public static void loadSell() {
		ArrayList<Model_Sell> list = data_sell.getInstance().loadSell();
		table_model.setRowCount(0);
		for(Model_Sell model_sell : list) {
			Object[] rowData = {model_sell.getSell_id(), model_sell.getSell_quantity(), model_sell.getSell_total(), model_sell.getSell_date(), model_sell.getCustomer_id(), model_sell.getSell_name()};
			table_model.addRow(rowData);
		}
	}
	
	public void editSell() {
		int selectRow = table.getSelectedRow();
		if(selectRow >= 0) {
			String sell_id = tf_id.getText();
			int sell_quantity = Integer.parseInt(tf_quantiry.getText());
			double sell_total = Double.parseDouble(tf_total.getText());
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        String formattedDate = tf_date.getText().formatted(formatter);
	        Timestamp sell_date = Timestamp.valueOf(tf_date.getText());
			
			String customer_id = tf_id_cus.getText();
			String sell_name = tf_name.getText();
			
			Model_Sell model_sell = new Model_Sell(sell_id, sell_quantity, sell_total, sell_date, customer_id, sell_name);
			Data_Sell.getInstance().updateSell(model_sell);
			
			table_model.setValueAt(sell_id, selectRow, 0);  
            table_model.setValueAt(sell_quantity, selectRow, 1); 
            table_model.setValueAt(sell_total, selectRow, 2); 
            table_model.setValueAt(sell_date, selectRow, 3);
            table_model.setValueAt(customer_id, selectRow, 4); 
		}
	}
	
	public void deleteSell() {
		int selectRow = table.getSelectedRow();
		if(selectRow >= 0) {
			String sell_id = table.getValueAt(selectRow, 0).toString();
			table_model.removeRow(selectRow);
			Data_Sell data_sell = new Data_Sell();
			data_sell.deleteSell(sell_id);
		}
	}
	
	public void reset() {
		tf_id.setText("");
		tf_id_cus.setText("");
		tf_total.setText("");
		tf_quantiry.setText("");
		tf_date.setText("");
	}
	
	public static void filterSellByTime(String timeFilter) {
	    ArrayList<Model_Sell> list = data_sell.getInstance().loadSell();
	    ArrayList<Model_Sell> filteredList = new ArrayList<>();

	    // Lấy ngày hiện tại
	    LocalDate today = LocalDate.now();

	    // Lọc theo thời gian
	    for (Model_Sell model_sell : list) {
	        LocalDate sellDate = model_sell.getSell_date().toLocalDateTime().toLocalDate();
	        
	        switch (timeFilter) {
	            case "Hôm nay":
	                if (sellDate.isEqual(today)) {
	                    filteredList.add(model_sell);
	                }
	                break;
	            case "7 ngày qua":
	                if (sellDate.isAfter(today.minusDays(7))) {
	                    filteredList.add(model_sell);
	                }
	                break;
	            case "Tháng này":
	                if (sellDate.getMonth() == today.getMonth() && sellDate.getYear() == today.getYear()) {
	                    filteredList.add(model_sell);
	                }
	                break;
	            case "Tháng trước":
	                if (sellDate.getMonth() == today.minusMonths(1).getMonth() && sellDate.getYear() == today.minusMonths(1).getYear()) {
	                    filteredList.add(model_sell);
	                }
	                break;
	            case "Tất cả":
	            default:
	                filteredList.add(model_sell);
	                break;
	        }
	    }

	    // Xóa các dòng cũ và thêm các dòng đã lọc vào bảng
	    table_model.setRowCount(0);
	    for (Model_Sell model_sell : filteredList) {
	        Object[] rowData = {model_sell.getSell_id(), model_sell.getSell_quantity(), model_sell.getSell_total(), model_sell.getSell_date(), model_sell.getCustomer_id()};
	        table_model.addRow(rowData);
	    }
	}
	
	public static void filterSellByDateRange(Date startDate, Date endDate) {
	    ArrayList<Model_Sell> list = data_sell.getInstance().loadSell();
	    ArrayList<Model_Sell> filteredList = new ArrayList<>();

	    // Chuyển đổi startDate và endDate sang LocalDate
	    LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	    for (Model_Sell model_sell : list) {
	        LocalDate sellDate = model_sell.getSell_date().toLocalDateTime().toLocalDate();
	        
	        // Kiểm tra nếu ngày bán nằm trong khoảng thời gian
	        if ((sellDate.isEqual(startLocalDate) || sellDate.isAfter(startLocalDate)) && 
	            (sellDate.isEqual(endLocalDate) || sellDate.isBefore(endLocalDate))) {
	            filteredList.add(model_sell);
	        }
	    }

	    // Xóa các dòng cũ và thêm các dòng đã lọc vào bảng
	    table_model.setRowCount(0);
	    for (Model_Sell model_sell : filteredList) {
	        Object[] rowData = {model_sell.getSell_id(), model_sell.getSell_quantity(), model_sell.getSell_total(), model_sell.getSell_date(), model_sell.getCustomer_id()};
	        table_model.addRow(rowData);
	    }
	}
}