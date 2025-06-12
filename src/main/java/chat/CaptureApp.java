package chat;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamLockException;
import com.github.sarxos.webcam.WebcamPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CaptureApp {

    /**
     * @wbp.parser.entryPoint
     */
    public static BufferedImage showCaptureDialog(JFrame parent) {
        Webcam webcam = Webcam.getDefault();
        if (webcam == null) {
            JOptionPane.showMessageDialog(parent, "Không tìm thấy webcam!");
            return null;
        }

        // Chọn độ phân giải tốt nhất
        Dimension[] sizes = webcam.getViewSizes();
        Dimension best = sizes[0];
        for (Dimension d : sizes) {
            if (d.width * d.height > best.width * best.height) {
                best = d;
            }
        }
        webcam.setViewSize(best);

        try {
            webcam.open();
        } catch (WebcamLockException e) {
            JOptionPane.showMessageDialog(parent, "Webcam đang được sử dụng bởi ứng dụng khác!");
            return null;
        } finally {
            if (webcam.isOpen()) {
                webcam.close();
            }
        }

        BufferedImage[] capturedImage = {null};

        // Tạo webcam panel
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setBounds(10, 10, 640, 480);
        panel.setMirrored(true);

        // Tạo nút chụp ảnh
        JButton captureButton = new JButton("");
        captureButton.setBackground(new Color(251, 248, 232));
        captureButton.setBounds(289, 501, 81, 57);
        captureButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        captureButton.setIcon(new ImageIcon(CaptureApp.class.getResource("/images/capture.png")));
        captureButton.setFocusPainted(false);

        // Panel chứa webcam và nút
        JPanel container = new JPanel();
        container.setBackground(new Color(235, 248, 239));
        container.setLayout(null);
        container.add(panel);
        container.add(captureButton);

        // Tạo dialog
        JDialog dialog = new JDialog(parent, "Chụp ảnh", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(container);
        dialog.setSize(674, 605);
        dialog.setLocationRelativeTo(parent);

        // Bắt sự kiện nút chụp
        captureButton.addActionListener(e -> {
            capturedImage[0] = webcam.getImage();
            System.out.println("📸 Ảnh đã được chụp.");
            dialog.dispose();
        });

        // Bắt sự kiện khi dialog bị đóng
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                if (webcam.isOpen()) {
                    webcam.close();
                    System.out.println("✅ Webcam đã được đóng (windowClosed)");
                }
            }

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (webcam.isOpen()) {
                    webcam.close();
                    System.out.println("✅ Webcam đã được đóng (windowClosing)");
                }
            }
        });

        dialog.setVisible(true);

        // Đảm bảo đóng webcam kể cả khi có vấn đề
        if (webcam.isOpen()) {
            webcam.close();
            System.out.println("✅ Webcam đã được đóng (safety check)");
        }

        return capturedImage[0];
    }
}