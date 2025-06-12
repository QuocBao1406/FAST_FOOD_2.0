package view.pages;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import database.Data_Statistical;
import model.Model_Statistical;
import java.awt.Font;

public class Statistical_Management extends JPanel {

    private JTabbedPane tabbedPane;

    public Statistical_Management() {
        setBackground(new Color(255, 242, 189));
        setSize(1250, 820);
        setLayout(null);

        JLabel lb_title = new JLabel("");
        lb_title.setIcon(new ImageIcon(Food_Management.class.getResource("/images/title_logo.png")));
        lb_title.setBounds(372, 10, 500, 80);
        add(lb_title);

        // Khởi tạo TabbedPane
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(251, 235, 221));
        tabbedPane.setBounds(10, 80, 1220, 700);
        add(tabbedPane);

        // Thêm các tab ban đầu
        addTabs();

        // Tạo nút làm mới
        JButton btnRefresh = new JButton("Làm Mới");
        btnRefresh.setForeground(new Color(84, 248, 162));
        btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnRefresh.setBounds(10, 30, 120, 40);
        btnRefresh.setBackground(new Color(253, 239, 208));
        btnRefresh.setFocusable(false);
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTabs();
            }
        });

        add(btnRefresh);
    }

    // Phương thức thêm các tab vào TabbedPane
    private void addTabs() {
    	List<Model_Statistical> totalData = Data_Statistical.getMonthlySalesData();
    	List<Model_Statistical> bestSellerData = Data_Statistical.getBestSellerData();
    	
        tabbedPane.removeAll(); // Xóa các tab cũ

        // Tab 1: Biểu đồ tổng tiền
        JPanel totalPanel = createTotalChartPanel(totalData);
        totalPanel.setBackground(new Color(251, 235, 221));
        tabbedPane.add("Tổng Tiền", totalPanel);

        // Tab 2: Biểu đồ tổng số lượng
        JPanel quantityPanel = createQuantityChartPanel(totalData);
        tabbedPane.add("Tổng Số Lượng", quantityPanel);
        
        JPanel bestSeller = bestSellerChart(bestSellerData);
        tabbedPane.add("Món bán chạy", bestSeller);

        // Cập nhật giao diện
        revalidate();
        repaint();
    }

    // Phương thức tạo biểu đồ tổng tiền
    private JPanel createTotalChartPanel(List<Model_Statistical> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (Model_Statistical model_statistical : data) {
            dataset.addValue(model_statistical.getTotal(), "Doanh Thu (VND)", model_statistical.getMonth());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Tổng Doanh Thu Theo Tháng",
                "Tháng",
                "Tổng Tiền (VND)",
                dataset
        );

        return new ChartPanel(barChart);
    }

    // Phương thức tạo biểu đồ tổng số lượng
    private JPanel createQuantityChartPanel(List<Model_Statistical> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (Model_Statistical model_statistical : data) {
            dataset.addValue(model_statistical.getQuantity(), "Số Lượng", model_statistical.getMonth());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Tổng Số Lượng Theo Tháng",
                "Tháng",
                "Số Lượng",
                dataset
        );

        return new ChartPanel(barChart);
    }
    
    private JPanel bestSellerChart(List<Model_Statistical> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Model_Statistical model : data) {
            if (model.getName() == null || model.getMonth() == null) { 
                System.err.println("Lỗi: Dữ liệu không hợp lệ! " +
                    "Name = " + model.getName() + ", Month = " + model.getMonth() + ", Quantity = " + model.getQuantity());
            } else {
                dataset.addValue(model.getQuantity(), model.getName(), "Tháng " + model.getMonth());
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Món Bán Chạy Theo Tháng",
                "Tháng",
                "Số Lượng",
                dataset
        );

        return new ChartPanel(chart);
    }


    // Phương thức làm mới dữ liệu và biểu đồ
    private void refreshTabs() {
        List<Model_Statistical> newData = loadStatistical(); // Tải lại dữ liệu
        addTabs(); // Cập nhật các tab với dữ liệu mới
    }

    // Phương thức tải dữ liệu từ cơ sở dữ liệu
    public static List<Model_Statistical> loadStatistical() {
        Data_Statistical data_statistical = new Data_Statistical();
        return data_statistical.getMonthlySalesData();
    }
}
