package encode;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class En_Image {
	
	public static byte[] imageToBytes(Component parent, JLabel label) {
		try {
			//JFileChooser cho phép người dùng mở tệp
			JFileChooser filechooser = new JFileChooser();
			//FileNameExtensionFilter cho phép hiển thị các tệp có phần mở rộng cụ thể
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files (*.jpg, *.png, *.gif)", "jpg", "png", "gif");
			filechooser.setFileFilter(filter);
			//cho biết hành động người dùng
			int result = filechooser.showOpenDialog(parent);
			//người dùng đã chọn tệp và nhấn open
			if(result == JFileChooser.APPROVE_OPTION) {
				//chuyển từ filechooser -> file trong java
				File selectedfile = filechooser.getSelectedFile();
				//chuyển file -> byte 
				byte[] imagebytes = fileToBytes(selectedfile);
				
				//ImageIO đọc hình ảnh từ tệp hoặc luồng dữ liệu
				//BufferedImage lưu hình ảnh đã đọc
				BufferedImage image = ImageIO.read(new FileInputStream(selectedfile));
				if(image != null) {
					Image scaledimage = image.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
					ImageIcon icon = new ImageIcon(scaledimage);
					label.setIcon(icon);
				}
				return imagebytes;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] fileToBytes(File file) {
		//FileInputStream chuyển thành byte từ 1 file
		//ByteArrayOutputStream ghi dữ liệu từ byte vào ram
		try(FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024]; //1kb
			int readbytes;
			//FileInputStream -> buffer -> ByteArrayOutputStream
			while((readbytes = fis.read(buffer)) != -1) {
				bos.write(buffer, 0, readbytes);
			}
			//Trả về một mảng byte chứa toàn bộ dữ liệu đã được ghi vào đối tượng (RAM)
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void bytesToImageLabel(byte[] imagebytes, JLabel label) {
		if(imagebytes != null) {
			try {
				//ByteArrayInputStream đọc lại dữ liệu từ bộ nhớ ram
				ByteArrayInputStream bais = new ByteArrayInputStream(imagebytes);
				BufferedImage image = ImageIO.read(bais);
				if(image != null) {
					Image scaledimage = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
					ImageIcon icon = new ImageIcon(scaledimage);
					label.setIcon(icon);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static byte[] convertLabelImageToBytes(JLabel label) {
        try {
            // Lấy ImageIcon từ JLabel
            ImageIcon icon = (ImageIcon) label.getIcon();
            if (icon != null) {
                // Chuyển ImageIcon thành BufferedImage
                BufferedImage bufferedImage = new BufferedImage(
                        icon.getIconWidth(),
                        icon.getIconHeight(),
                        BufferedImage.TYPE_INT_ARGB
                );
                bufferedImage.getGraphics().drawImage(icon.getImage(), 0, 0, null);

                // Sử dụng ByteArrayOutputStream để chuyển BufferedImage thành byte[]
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", byteArrayOutputStream); // Định dạng ảnh là PNG
                return byteArrayOutputStream.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu xảy ra lỗi
    }
}
