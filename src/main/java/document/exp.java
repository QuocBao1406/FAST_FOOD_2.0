package document;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class exp {
    private DefaultTableModel model;

    public exp(DefaultTableModel model) {
        this.model = model;
    }

    public void loadPDF() {
        try {
        	BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font anotherFont = new Font(baseFont, 12, Font.ITALIC, Color.black);
            Font titleFont = new Font(baseFont, 25, Font.ITALIC, Color.black);
            
            LocalDateTime currentDateTime = LocalDateTime.now();
            
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            
           
            String formattedDateTime = currentDateTime.format(formatter);
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("D:\\hoc_ky_1\\LTHDT OOP\\FAST_FOOD_BHMN\\pdf\\PDF"+formattedDateTime+".pdf"));
            document.open();

            
            PdfPTable pdfTable = new PdfPTable(model.getColumnCount());

            Paragraph title = new Paragraph("Danh Sách Khách Hàng", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER); 
            title.setSpacingAfter(20); 
            document.add(title);
            
            float[] columnWidths = {2f,5f,3f,2f,5f,3f};
            pdfTable.setWidths(columnWidths);


            for (int i = 0; i < model.getColumnCount(); i++) {
                pdfTable.addCell(new Phrase(model.getColumnName(i), anotherFont));
            }


            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Phrase ph = new Phrase(model.getValueAt(row, col).toString(), anotherFont);
                    pdfTable.addCell(ph);
                }
            }

          
            document.add(pdfTable);
            document.close();

          
            JOptionPane.showMessageDialog(null, "Bảng đã được xuất ra file PDF thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi xuất file PDF: ");
            ex.printStackTrace();
        }
    }
}
