package view.component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import database.Data_Customer;
import database.Data_Sell;
import model.Model_Customer;
import model.Model_Sell;
import view.pages.Sell_Management;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pay_Sell extends JPanel {
    private JDialog dialog;
    private DefaultTableModel tableModel;
    private JTable table;
	private JLabel lb_id;
	private JLabel lb_date;
	private JLabel lb_total;
	private JLabel lb_count;
	private String sell_id;
	private int sell_quantity;
	private double sell_total;
	private Timestamp sell_date;
	private JLabel lb_id_cus;
	private String customer_id;
	private String sell_name;
	private List<Model_Sell> sellList = new ArrayList<>();

	public Pay_Sell(JDialog dialog) {
        setForeground(new Color(128, 0, 0));
        setBackground(new Color(255, 236, 206));
        setSize(500, 600);
        setLayout(null);

        // Tiêu đề
        JLabel lblTitle = new JLabel("Thanh Toán Hóa Đơn");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblTitle.setBounds(78, 10, 324, 40);
        add(lblTitle);

        // Mã bán hàng
        lb_id = new JLabel("Mã Bán Hàng: ");
        lb_id.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lb_id.setBounds(10, 60, 400, 30);
        add(lb_id);

        // Ngày giờ
        lb_date = new JLabel("Ngày: ");
        lb_date.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lb_date.setBounds(10, 140, 400, 30);
        add(lb_date);

        // Thông tin chi tiết đơn hàng
        JLabel lblOrderDetails = new JLabel("Chi Tiết Đơn Hàng:");
        lblOrderDetails.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblOrderDetails.setBounds(10, 180, 200, 30);
        add(lblOrderDetails);

        // Tạo bảng để hiển thị chi tiết món ăn
        tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] { "Tên Món", "Số Lượng", "Giá" }
        );
        
        table = new JTable(tableModel);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(30);

        // Scroll pane cho bảng
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 215, 470, 200);
        add(scrollPane);

        // Tổng tiền
        lb_total = new JLabel("Tổng Tiền: ");
        lb_total.setFont(new Font("Tahoma", Font.BOLD, 20));
        lb_total.setBounds(10, 460, 400, 30);
        add(lb_total);

        // Thêm nút để xác nhận và thêm dữ liệu
        JButton bt_confirm = new JButton("Xác Nhận");
        bt_confirm.addActionListener(new ActionListener() {
            private boolean success;

			public void actionPerformed(ActionEvent e) {
                if (sellList.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn món, vui lòng chọn món!");
                    return;
                }
                
            

                success = Data_Sell.getInstance().addSell(sellList);

                if (success) {
                    ArrayList<Model_Customer> list = Data_Customer.getInstance().loadCustomer();
                    for (Model_Customer model_customer : list) {
                        if (model_customer.getCustomer_Id().equals(customer_id)) {
                            double totalSpent = sellList.stream().mapToDouble(Model_Sell::getSell_total).sum();
                            double newTotal = model_customer.addTotal(totalSpent);
                            
                            Model_Customer customer = new Model_Customer(
                                model_customer.getCustomer_Id(), 
                                model_customer.getCustomer_Name(),
                                model_customer.getCustomer_Sex(), 
                                model_customer.getCustomer_Address(), 
                                newTotal,
                                model_customer.getCustomer_Rank()
                            );
                            Data_Customer.getInstance().updateCustomer(customer);
                            break;
                        }
                    }
                    
                    generatePDF(sell_id, customer_id, sellList);

                    sellList.clear();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Lỗi khi thanh toán, vui lòng thử lại!");
                }
            }
        });


        bt_confirm.setBackground(new Color(255, 223, 224));
        bt_confirm.setFont(new Font("Tahoma", Font.BOLD, 20));
        bt_confirm.setBounds(165, 500, 150, 50);
        add(bt_confirm);
        
        lb_count = new JLabel("Tổng Số Lượng: ");
        lb_count.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lb_count.setBounds(10, 425, 400, 30);
        add(lb_count);
        
        lb_id_cus = new JLabel("Mã Khách Hàng:");
        lb_id_cus.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lb_id_cus.setBounds(10, 102, 400, 30);
        add(lb_id_cus);
    }

	public void displayOrderDetails(String saleCode, Object[][] orderData, int total_count, double total) {
	    // Cập nhật thông tin mã bán hàng
	    lb_id.setText("Mã Bán Hàng: " + saleCode);
	    sell_id = saleCode;

	    // Lấy ngày giờ hiện tại
	    LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String formattedDate = now.format(formatter);
	    Timestamp date = Timestamp.valueOf(now);
	    sell_date = date;
	    
	    // Cập nhật ngày giờ
	    lb_date.setText("Ngày: " + formattedDate);
	    
	    customer_id = Sell_Management.getCustomer_Id();
	    lb_id_cus.setText("Mã Khách Hàng: " + customer_id);

	    // Xóa dữ liệu cũ trong bảng
	    tableModel.setRowCount(0);
	    sellList.clear(); // Xóa danh sách cũ

	    // Cập nhật chi tiết món ăn
	    for (Object[] row : orderData) {
	        tableModel.addRow(row);

	        // Giả sử dữ liệu trong bảng gồm: {"Tên Món", "Số Lượng", "Giá"}
	        String itemName = (String) row[0];
	        int quantity = (int) row[1];
	        double price = (double) row[2];

	        // Thêm vào danh sách sellList
	        addSellItem(sell_id, quantity, price, sell_date, customer_id, itemName);
	    }
	    
	    lb_count.setText("Tổng Số Lượng: " + total_count);
	    sell_quantity = total_count;
	    
	    // Cập nhật tổng tiền
	    lb_total.setText("Tổng Tiền VND: " + Sell_Management.getTotal());
	    sell_total = Sell_Management.getTotal();
	}

    
	public void addSellItem(String sell_id, int sell_quantity, double sell_total, Timestamp sell_date, String customer_id, String sell_name) {
	    Model_Sell sell = new Model_Sell(sell_id, sell_quantity, sell_total, sell_date, customer_id, sell_name);
	    sellList.add(sell);
	}

	public void generatePDF(String sellId, String customerId, List<Model_Sell> sellList) {
	    try {
	        // Lấy thời gian hiện tại để đặt tên file PDF
	        LocalDateTime currentDateTime = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	        String formattedDateTime = currentDateTime.format(formatter);

	        String basePath = System.getProperty("user.dir");
            String directoryPath = basePath + "/pdf";
            File directory = new File(directoryPath);
            if(!directory.exists()) {
            	directory.mkdirs();
            }
            // Gọi hàm tạo hóa đơn PDF
            String outputPath = directoryPath + "/bill_" + sellId + ".pdf";

	        // Tạo tài liệu PDF
	        Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
	        document.open();

	        // Định dạng font (Times New Roman)
	        BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	        com.lowagie.text.Font vietnameseFont = new com.lowagie.text.Font(baseFont, 12, com.lowagie.text.Font.NORMAL);
	        com.lowagie.text.Font titleFont = new com.lowagie.text.Font(baseFont, 16, com.lowagie.text.Font.BOLD);

	        // Tiêu đề hóa đơn
	        Paragraph title = new Paragraph("PAYMENT RECEIPT", titleFont);
	        title.setAlignment(Element.ALIGN_CENTER);
	        document.add(title);
	        document.add(new Paragraph(" ")); // Khoảng trắng

	        // Thông tin hóa đơn
	        document.add(new Paragraph("Receipt ID: " + sellId));
	        document.add(new Paragraph("Client ID: " + customerId));

	        // Lấy ngày giờ hiện tại
	        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	        String dateStr = sdf.format(new Date()); // Lấy ngày hiện tại
	        document.add(new Paragraph("Date: " + dateStr));

	        document.add(new Paragraph(" ")); // Khoảng trắng

	        // Tạo bảng hóa đơn
	        PdfPTable table = new PdfPTable(3); // 3 cột: Tên Món, Số Lượng, Giá
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(10f);
	        table.setSpacingAfter(10f);

	        // Thêm tiêu đề cột
	        table.addCell(new PdfPCell(new Phrase("Dish name")));
	        table.addCell(new PdfPCell(new Phrase("Quantity")));
	        table.addCell(new PdfPCell(new Phrase("Cost")));

	        // Thêm dữ liệu vào bảng
	        double totalPrice = 0;
	        for (Model_Sell item : sellList) {
	            table.addCell(new PdfPCell(new Phrase(item.getSell_name(), vietnameseFont)));
	            table.addCell(new PdfPCell(new Phrase(String.valueOf(item.getSell_quantity()))));
	            table.addCell(new PdfPCell(new Phrase(String.format("%.2f", item.getSell_total()))));
	            totalPrice += item.getSell_total();
	        }

	        // Thêm bảng vào tài liệu
	        document.add(table);

	        // Tổng tiền
	        document.add(new Paragraph("Total cost: " + String.format("%.2f VND", totalPrice)));

	        // Đóng tài liệu
	        document.close();
	        System.out.println("Hóa đơn PDF đã được tạo thành công! Đường dẫn: " + outputPath);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}