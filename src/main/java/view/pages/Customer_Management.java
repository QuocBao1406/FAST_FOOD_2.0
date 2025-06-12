package view.pages;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import database.Data_Customer;
import database.Data_Food;
import document.exp;
import encode.En_Image;
import model.Model_Customer;
import model.Model_Food;
import view.component.Add_Cust;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class Customer_Management extends JPanel{
	private JTextField tf_total;
	private JTextField tf_adr;
	private JTextField tf_name;
	private JTextField tf_id;
	private JTextField tf_loctotal1;
	private JTextField tf_loctotal2;
	private JTextField tf_locname;
	private static JTable table;
	private static DefaultTableModel model;
	private JComboBox tf_rank;
	private JComboBox tf_sex;
	private JComboBox tf_locrank;
	private JButton sua;
	private JButton bt_save;
	
	public static void loadData() {
		ArrayList<Model_Customer> list = Data_Customer.getInstance().loadCustomer();
		
		model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 8;  
            }
        };
        
        model.addColumn("Mã");
        model.addColumn("Họ và tên");
        model.addColumn("Giới");
        model.addColumn("Địa Chỉ");
        model.addColumn("Tổng chi");
        model.addColumn("Hạng");
        
        for(Model_Customer ctm : list) {
        	model.addRow(new Object[] {
        			ctm.getCustomer_Id(),
        			ctm.getCustomer_Name(),
        			ctm.getCustomer_Sex(),
        			ctm.getCustomer_Address(),
        			ctm.getCustomer_Total(),
        			ctm.getCustomer_Rank()
        	});
        }

        table.setModel(model);
        table.setFont(new Font("Tahoma", Font.PLAIN, 20));
        JTableHeader tablehd = table.getTableHeader();
        tablehd.setFont(new Font("Arial", Font.BOLD, 20));
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
        TableColumnModel columnModel = table.getColumnModel();
        
        columnModel.getColumn(0).setPreferredWidth(100); 
        columnModel.getColumn(1).setPreferredWidth(300); 
        columnModel.getColumn(2).setPreferredWidth(100); 
        columnModel.getColumn(3).setPreferredWidth(300);  
        columnModel.getColumn(4).setPreferredWidth(250); 
        columnModel.getColumn(5).setPreferredWidth(150); 
        
        table.setRowHeight(30);
	}
	public Customer_Management() {
		setBackground(new Color(255, 242, 189));
		setSize(1250, 820);
		setLayout(null);
		
		JLabel lb_title = new JLabel("");
		lb_title.setIcon(new ImageIcon(Customer_Management.class.getResource("/images/title_logo.png")));
		lb_title.setBounds(372, 10, 500, 80);
		add(lb_title);
		
		JLabel lblNewLabel = new JLabel("Mã khách hàng");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel.setBounds(27, 95, 192, 38);
		add(lblNewLabel);
		
		JLabel lblTnKhchHng = new JLabel("Tên khách hàng");
		lblTnKhchHng.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblTnKhchHng.setBounds(27, 143, 192, 36);
		add(lblTnKhchHng);
		
		JLabel lblS_1 = new JLabel("Giới tính");
		lblS_1.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblS_1.setBounds(27, 190, 192, 36);
		add(lblS_1);
		
		JLabel lblNewLabel_3 = new JLabel("Địa chỉ");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel_3.setBounds(27, 236, 192, 38);
		add(lblNewLabel_3);
		
		JLabel lblNewLabel_5 = new JLabel("Tổng chi");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel_5.setBounds(27, 284, 192, 39);
		add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Hạng thành viên");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel_6.setBounds(27, 334, 192, 40);
		add(lblNewLabel_6);
		
		tf_rank = new JComboBox();
		tf_rank.setModel(new DefaultComboBoxModel(new String[] {"", "Đồng", "Bạc", "Vàng", "Kim cương"}));
		tf_rank.setEnabled(false);
		tf_rank.setFont(new Font("Tahoma", Font.PLAIN, 19));
		tf_rank.setBounds(218, 333, 330, 41);
		add(tf_rank);
		
		tf_total = new JTextField();
		tf_total.setFont(new Font("Tahoma", Font.PLAIN, 19));
		tf_total.setColumns(10);
		tf_total.setAutoscrolls(false);
		tf_total.setBounds(218, 284, 330, 39);
		add(tf_total);
		
		tf_adr = new JTextField();
		tf_adr.setFont(new Font("Tahoma", Font.PLAIN, 19));
		tf_adr.setColumns(10);
		tf_adr.setAutoscrolls(false);
		tf_adr.setBounds(218, 236, 330, 38);
		add(tf_adr);
		
		tf_sex = new JComboBox();
		tf_sex.setEditable(true);
		tf_sex.setEnabled(false);
		tf_sex.setFont(new Font("Tahoma", Font.PLAIN, 19));
		tf_sex.setBounds(218, 189, 330, 37);
		tf_sex.addItem("Nam");
		tf_sex.addItem("Nữ");
		tf_sex.addItem("Khác");
		add(tf_sex);
		
		tf_name = new JTextField();
		tf_name.setFont(new Font("Tahoma", Font.PLAIN, 19));
		tf_name.setColumns(10);
		tf_name.setAutoscrolls(false);
		tf_name.setBounds(218, 143, 330, 36);
		add(tf_name);
		
		tf_id = new JTextField();
		tf_id.setFont(new Font("Tahoma", Font.PLAIN, 19));
		tf_id.setColumns(10);
		tf_id.setAutoscrolls(false);
		tf_id.setBounds(218, 95, 330, 38);
		add(tf_id);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(251, 249, 238));
		panel.setBounds(583, 95, 417, 279);
		add(panel);
		
		tf_loctotal1 = new JTextField();
		tf_loctotal1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		tf_loctotal1.setColumns(10);
		tf_loctotal1.setBounds(10, 126, 160, 32);
		panel.add(tf_loctotal1);
		
		tf_loctotal2 = new JTextField();
		tf_loctotal2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		tf_loctotal2.setColumns(10);
		tf_loctotal2.setBounds(247, 126, 160, 32);
		panel.add(tf_loctotal2);
		
		JLabel lbln = new JLabel("đến");
		lbln.setHorizontalAlignment(SwingConstants.CENTER);
		lbln.setForeground(Color.GRAY);
		lbln.setFont(new Font("Tahoma", Font.BOLD, 24));
		lbln.setBounds(183, 126, 50, 32);
		panel.add(lbln);
		
		tf_locrank = new JComboBox();
		tf_locrank.setModel(new DefaultComboBoxModel(new String[] {"", "Đồng", "Bạc", "Vàng", "Kim cương"}));
		tf_locrank.setFont(new Font("Tahoma", Font.PLAIN, 17));
		tf_locrank.setBackground(Color.WHITE);
		tf_locrank.setBounds(94, 170, 313, 33);
		panel.add(tf_locrank);
		
		JLabel lblMKh_3 = new JLabel("Hạng");
		lblMKh_3.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblMKh_3.setBounds(10, 170, 74, 32);
		panel.add(lblMKh_3);
		
		JLabel lblTngChi = new JLabel("Tổng chi");
		lblTngChi.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblTngChi.setBounds(10, 89, 116, 33);
		panel.add(lblTngChi);
		
		tf_locname = new JTextField();
		tf_locname.setFont(new Font("Tahoma", Font.PLAIN, 17));
		tf_locname.setColumns(10);
		tf_locname.setBounds(10, 46, 397, 33);
		panel.add(tf_locname);
		
		JLabel lww = new JLabel("Tên khách hàng");
		lww.setFont(new Font("Tahoma", Font.BOLD, 25));
		lww.setBounds(10, 12, 215, 32);
		panel.add(lww);
		
		JButton reset = new JButton("RESET");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf_locname.setText("");
				tf_loctotal1.setText("");
				tf_loctotal2.setText("");
				tf_locrank.setSelectedItem("");
				tf_id.setText("");
		        tf_name.setText("");
		        tf_sex.setSelectedItem("");
		        tf_adr.setText("");
		        tf_total.setText(String.valueOf(""));
		        tf_rank.setSelectedItem("");
		        loadData();
			}
		});
		reset.setForeground(Color.WHITE);
		reset.setFont(new Font("Tahoma", Font.BOLD, 17));
		reset.setBackground(Color.GRAY);
		reset.setBounds(20, 220, 116, 42);
		panel.add(reset);
		
		bt_save = new JButton("Lưu");
		bt_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCustomer();
				tf_name.setEditable(false);
				tf_sex.setEnabled(false);
				tf_adr.setEditable(false);
				tf_total.setEditable(false);
				tf_rank.setEnabled(false);
				bt_save.setVisible(false);
			}
		});
		bt_save.setForeground(Color.WHITE);
		bt_save.setFont(new Font("Tahoma", Font.BOLD, 24));
		bt_save.setBackground(Color.GRAY);
		bt_save.setBounds(160, 220, 116, 42);
		bt_save.setVisible(false);
		panel.add(bt_save);
		
		JButton xoa = new JButton("Xóa Khách Hàng");
		xoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tf_id.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Chọn khách hàng cần xóa thông tin", 
                            "Lỗi", JOptionPane.ERROR_MESSAGE);				
					return;
				}
				 int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa khách hàng?", "Xác nhận", JOptionPane.YES_NO_OPTION);

			        if (result == JOptionPane.YES_OPTION) {
			        	String id = tf_id.getText();
						Data_Customer.getInstance().deleteCustomer(id);
						loadData();
			        } 
				
			}
		});
		xoa.setFont(new Font("Tahoma", Font.BOLD, 17));
		xoa.setBorder(null);
		xoa.setBackground(new Color(254, 135, 135));
		xoa.setBounds(1020, 299, 200, 66);
		add(xoa);
		
		JButton them = new JButton("Thêm Khách Hàng");
		them.setForeground(new Color(0, 0, 0));
		them.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
	        	Add_Cust them = new Add_Cust(dialog);
	    		dialog.getContentPane().setLayout(new GridLayout(1,1));
	    		dialog.setSize(1000, 500);
	    		dialog.setLocationRelativeTo(null);
	        	dialog.getContentPane().add(them);
	        	dialog.setVisible(true);
			}
		});
		them.setFont(new Font("Tahoma", Font.BOLD, 17));
		them.setBorder(null);
		them.setBackground(new Color(139, 184, 250));
		them.setBounds(1020, 147, 200, 66);
		add(them);
		
		JButton xuat = new JButton("Xuất Danh Sách");
		xuat.setForeground(new Color(0, 0, 0));
		xuat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exp es = new exp(model);
				es.loadPDF();
			}
		});
		xuat.setFont(new Font("Tahoma", Font.BOLD, 17));
		xuat.setBorder(null);
		xuat.setBackground(new Color(133, 247, 100));
		xuat.setBounds(1020, 63, 200, 74);
		add(xuat);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(20, 384, 1200, 399);
		add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int rowIndex = table.getSelectedRow();
		        tf_id.setText(table.getValueAt(rowIndex,0).toString());
		        tf_name.setText(table.getValueAt(rowIndex, 1).toString());
		        tf_sex.setSelectedItem(table.getValueAt(rowIndex, 2).toString());
		        tf_adr.setText(table.getValueAt(rowIndex, 3).toString());
		        tf_total.setText(table.getValueAt(rowIndex, 4).toString());
		        tf_rank.setSelectedItem(table.getValueAt(rowIndex, 5).toString());
		        
		        tf_id.setEditable(false);
				tf_name.setEditable(false);
				tf_sex.setEditable(false);
				tf_adr.setEditable(false);
				tf_total.setEditable(false);
				tf_rank.setEditable(false);
		    }
		});
		
		
		tf_id.setEditable(false);
		tf_name.setEditable(false);
		tf_adr.setEditable(false);
		tf_total.setEditable(false);
		tf_rank.setEditable(true);
		
		sua = new JButton("Sửa Thông Tin");
		sua.setForeground(new Color(0, 0, 0));
		sua.setBounds(1020, 223, 200, 66);
		sua.setVisible(true);
		add(sua);
		sua.setFont(new Font("Tahoma", Font.BOLD, 17));
		sua.setBorder(null);
		sua.setBackground(new Color(254, 224, 135));
		sua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				tf_id.setEditable(true);
				if(tf_id.getText().isEmpty()) {
					  JOptionPane.showMessageDialog(null, "Chọn khách hàng cần sửa thông tin", 
                              "Lỗi", JOptionPane.ERROR_MESSAGE);				
					return;
				}
				
				tf_name.setEditable(true);
				tf_sex.setEnabled(true);
				tf_adr.setEditable(true);
				tf_total.setEditable(true);
				bt_save.setVisible(true);
			}
		});
		
		
		tf_locname.getDocument().addDocumentListener(new DocumentListener() {
			 @Override
	            public void insertUpdate(DocumentEvent e) {
				 		List<Model_Customer> list = Data_Customer.getInstance().findCustomerName(tf_locname.getText(),tf_loctotal1.getText(),tf_loctotal2.getText(),tf_locrank.getSelectedItem().toString());
	                	model.setRowCount(0);
	                	for(Model_Customer ctm : list) {
	                		model.addRow(new Object[] {
	                				ctm.getCustomer_Id(),
	                				ctm.getCustomer_Name(),
	                				ctm.getCustomer_Sex(),
	                				ctm.getCustomer_Address(),
	                				ctm.getCustomer_Total(),
	                				ctm.getCustomer_Rank()
	                		});
	                	}
	                	
	            		table.setModel(model);
	                
	            }
	            
	            
	            @Override
	            public void removeUpdate(DocumentEvent e) {
	            	List<Model_Customer> list = Data_Customer.getInstance().findCustomerName(tf_locname.getText(),tf_loctotal1.getText(),tf_loctotal2.getText(),tf_locrank.getSelectedItem().toString());
                	model.setRowCount(0);
                	for(Model_Customer ctm : list) {
                		model.addRow(new Object[] {
                				ctm.getCustomer_Id(),
                				ctm.getCustomer_Name(),
                				ctm.getCustomer_Sex(),
                				ctm.getCustomer_Address(),
                				ctm.getCustomer_Total(),
                				ctm.getCustomer_Rank()
                		});
                	}
                	
            		table.setModel(model);
	            }
	            
	            
	            @Override
	            public void changedUpdate(DocumentEvent e) {
	            	List<Model_Customer> list = Data_Customer.getInstance().findCustomerName(tf_locname.getText(),tf_loctotal1.getText(),tf_loctotal2.getText(),tf_locrank.getSelectedItem().toString());
                	model.setRowCount(0);
                	for(Model_Customer ctm : list) {
                		model.addRow(new Object[] {
                				ctm.getCustomer_Id(),
                				ctm.getCustomer_Name(),
                				ctm.getCustomer_Sex(),
                				ctm.getCustomer_Address(),
                				ctm.getCustomer_Total(),
                				ctm.getCustomer_Rank()
                		});
                	}
                	
            		table.setModel(model);
	            }
       });
		
		tf_loctotal1.getDocument().addDocumentListener(new DocumentListener() {
			 @Override
	            public void insertUpdate(DocumentEvent e) {
				 		List<Model_Customer> list = Data_Customer.getInstance().findCustomerName(tf_locname.getText(),tf_loctotal1.getText(),tf_loctotal2.getText(),tf_locrank.getSelectedItem().toString());
	                	model.setRowCount(0);
	                	for(Model_Customer ctm : list) {
	                		model.addRow(new Object[] {
	                				ctm.getCustomer_Id(),
	                				ctm.getCustomer_Name(),
	                				ctm.getCustomer_Sex(),
	                				ctm.getCustomer_Address(),
	                				ctm.getCustomer_Total(),
	                				ctm.getCustomer_Rank()
	                		});
	                	}
	                	
	            		table.setModel(model);
	                
	            }
	            
	            
	            @Override
	            public void removeUpdate(DocumentEvent e) {
	            	List<Model_Customer> list = Data_Customer.getInstance().findCustomerName(tf_locname.getText(),tf_loctotal1.getText(),tf_loctotal2.getText(),tf_locrank.getSelectedItem().toString());
               	model.setRowCount(0);
               	for(Model_Customer ctm : list) {
               		model.addRow(new Object[] {
               				ctm.getCustomer_Id(),
               				ctm.getCustomer_Name(),
               				ctm.getCustomer_Sex(),
               				ctm.getCustomer_Address(),
               				ctm.getCustomer_Total(),
               				ctm.getCustomer_Rank()
               		});
               	}
               	
           		table.setModel(model);
	            }
	            
	            
	            @Override
	            public void changedUpdate(DocumentEvent e) {
	            	List<Model_Customer> list = Data_Customer.getInstance().findCustomerName(tf_locname.getText(),tf_loctotal1.getText(),tf_loctotal2.getText(),tf_locrank.getSelectedItem().toString());
               	model.setRowCount(0);
               	for(Model_Customer ctm : list) {
               		model.addRow(new Object[] {
               				ctm.getCustomer_Id(),
               				ctm.getCustomer_Name(),
               				ctm.getCustomer_Sex(),
               				ctm.getCustomer_Address(),
               				ctm.getCustomer_Total(),
               				ctm.getCustomer_Rank()
               		});
               	}
               	
           		table.setModel(model);
	            }
      });
		
		tf_loctotal2.getDocument().addDocumentListener(new DocumentListener() {
			 @Override
	            public void insertUpdate(DocumentEvent e) {
				 		List<Model_Customer> list = Data_Customer.getInstance().findCustomerName(tf_locname.getText(),tf_loctotal1.getText(),tf_loctotal2.getText(),tf_locrank.getSelectedItem().toString());
	                	model.setRowCount(0);
	                	for(Model_Customer ctm : list) {
	                		model.addRow(new Object[] {
	                				ctm.getCustomer_Id(),
	                				ctm.getCustomer_Name(),
	                				ctm.getCustomer_Sex(),
	                				ctm.getCustomer_Address(),
	                				ctm.getCustomer_Total(),
	                				ctm.getCustomer_Rank()
	                		});
	                	}
	                	
	            		table.setModel(model);
	                
	            }
	            
	            
	            @Override
	            public void removeUpdate(DocumentEvent e) {
	            	List<Model_Customer> list = Data_Customer.getInstance().findCustomerName(tf_locname.getText(),tf_loctotal1.getText(),tf_loctotal2.getText(),tf_locrank.getSelectedItem().toString());
               	model.setRowCount(0);
               	for(Model_Customer ctm : list) {
               		model.addRow(new Object[] {
               				ctm.getCustomer_Id(),
               				ctm.getCustomer_Name(),
               				ctm.getCustomer_Sex(),
               				ctm.getCustomer_Address(),
               				ctm.getCustomer_Total(),
               				ctm.getCustomer_Rank()
               		});
               	}
               	
           		table.setModel(model);
	            }
	            
	            
	            @Override
	            public void changedUpdate(DocumentEvent e) {
	            	List<Model_Customer> list = Data_Customer.getInstance().findCustomerName(tf_locname.getText(),tf_loctotal1.getText(),tf_loctotal2.getText(),tf_locrank.getSelectedItem().toString());
               	model.setRowCount(0);
               	for(Model_Customer ctm : list) {
               		model.addRow(new Object[] {
               				ctm.getCustomer_Id(),
               				ctm.getCustomer_Name(),
               				ctm.getCustomer_Sex(),
               				ctm.getCustomer_Address(),
               				ctm.getCustomer_Total(),
               				ctm.getCustomer_Rank()
               		});
               	}
               	
           		table.setModel(model);
	            }
      });
		
		tf_locrank.addItemListener(new ItemListener()  {
			 @Override
			 public void itemStateChanged(ItemEvent e) {
				 		List<Model_Customer> list = Data_Customer.getInstance().findCustomerName(tf_locname.getText(),tf_loctotal1.getText(),tf_loctotal2.getText(),tf_locrank.getSelectedItem().toString());
	                	model.setRowCount(0);
	                	for(Model_Customer ctm : list) {
	                		model.addRow(new Object[] {
	                				ctm.getCustomer_Id(),
	                				ctm.getCustomer_Name(),
	                				ctm.getCustomer_Sex(),
	                				ctm.getCustomer_Address(),
	                				ctm.getCustomer_Total(),
	                				ctm.getCustomer_Rank()
	                		});
	                	}
	                	
	            		table.setModel(model);
	                
	            }
      });
		
		
		loadData();
	}


	public JTextField getTf_total() {
		return tf_total;
	}


	public void setTf_total(JTextField tf_total) {
		this.tf_total = tf_total;
	}


	public JTextField getTf_adr() {
		return tf_adr;
	}


	public void setTf_adr(JTextField tf_adr) {
		this.tf_adr = tf_adr;
	}

	
	public JTextField getTf_name() {
		return tf_name;
	}


	public void setTf_name(JTextField tf_name) {
		this.tf_name = tf_name;
	}


	public JTextField getTf_id() {
		return tf_id;
	}


	public void setTf_id(JTextField tf_id) {
		this.tf_id = tf_id;
	}


	public JTextField getTf_loctotal1() {
		return tf_loctotal1;
	}


	public void setTf_loctotal1(JTextField tf_loctotal1) {
		this.tf_loctotal1 = tf_loctotal1;
	}


	public JTextField getTf_loctotal2() {
		return tf_loctotal2;
	}


	public void setTf_loctotal2(JTextField tf_loctotal2) {
		this.tf_loctotal2 = tf_loctotal2;
	}


	public JTextField getTf_locten() {
		return tf_locname;
	}


	public void setTf_locten(JTextField tf_locten) {
		this.tf_locname = tf_locten;
	}


	public JTable getTable() {
		return table;
	}


	public void setTable(JTable table) {
		this.table = table;
	}


	public DefaultTableModel getModel() {
		return model;
	}


	public void setModel(DefaultTableModel model) {
		this.model = model;
	}


	public JComboBox getTf_rank() {
		return tf_rank;
	}


	public void setTf_rank(JComboBox tf_rank) {
		this.tf_rank = tf_rank;
	}


	public JComboBox getTf_sex() {
		return tf_sex;
	}


	public void setTf_sex(JComboBox tf_sex) {
		this.tf_sex = tf_sex;
	}


	public JComboBox getTf_locrank() {
		return tf_locrank;
	}


	public void setTf_locrank(JComboBox tf_locrank) {
		this.tf_locrank = tf_locrank;
	}
	
	public void editCustomer() {
		int selectRow = table.getSelectedRow();
		if(selectRow >= 0) {
			String customer_id = tf_id.getText().toString();
			String customer_name = tf_name.getText().toString();
			String customer_sex = tf_sex.getSelectedItem().toString();
			String customer_address = tf_adr.getText().toString();
			double customer_total = Double.parseDouble(tf_total.getText());

			if(customer_total >= 3000000) {
				tf_rank.setSelectedItem("Kim cương");
			} else if(customer_total >= 2000000) {
				tf_rank.setSelectedItem("Vàng");
			} else if(customer_total >= 1000000) {
				tf_rank.setSelectedItem("Bạc");
			} else if(customer_total >= 500000) {
				tf_rank.setSelectedItem("Đồng");
			} else if(customer_total >= 0) {
				tf_rank.setSelectedItem("");
			}
			
			String customer_rank = tf_rank.getSelectedItem().toString();
			
			Model_Customer model_customer = new Model_Customer(customer_id, customer_name, customer_sex, customer_address, customer_total, customer_rank);
			Data_Customer.getInstance().updateCustomer(model_customer);
			
			model.setValueAt(customer_id, selectRow, 0);  
			model.setValueAt(customer_name, selectRow, 1); 
			model.setValueAt(customer_sex, selectRow, 2); 
			model.setValueAt(customer_address, selectRow, 3); 
			model.setValueAt(customer_total, selectRow, 4); 
			model.setValueAt(customer_rank, selectRow, 5);
			
			loadData();
		}
	}
}
