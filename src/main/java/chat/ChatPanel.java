package chat;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ChatPanel extends JPanel {
    private JPanel chatAreaPanel = new JPanel();
    private JTextField inputField = new JTextField();
    private JButton encryptButton = new JButton("");
    private String chatWith;
    private ChatClient parent;
    private File tempVoiceFile;

    public ChatPanel(String user, ChatClient parent) {
        this.chatWith = user;
        this.parent = parent;
        
        setLayout(new BorderLayout());
        chatAreaPanel.setBackground(new Color(254, 254, 233));
        
        chatAreaPanel.setLayout(new BoxLayout(chatAreaPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(chatAreaPanel);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton emojiButton = new JButton("");
        emojiButton.setBackground(new Color(254, 250, 233));
        JButton fileButton = new JButton("");
        fileButton.setBackground(new Color(254, 250, 233));
        JButton imageButton = new JButton("");
        imageButton.setBackground(new Color(254, 250, 233));
        JButton hideTextButton = new JButton("");
        hideTextButton.setBackground(new Color(254, 250, 233));
        JButton voiceButton = new JButton("");
        voiceButton.setBackground(new Color(254, 250, 233));
        JButton camerabtn = new JButton("");
        camerabtn.setBackground(new Color(254, 250, 233));
        
        emojiButton.setFocusPainted(false);
        fileButton.setFocusPainted(false);
        imageButton.setFocusPainted(false);
        hideTextButton.setFocusPainted(false);
        voiceButton.setFocusPainted(false);
        camerabtn.setFocusPainted(false);
        
        ImageIcon icon_camera = new ImageIcon(ChatPanel.class.getResource("/images/camera.png"));
        Image img_camera = icon_camera.getImage().getScaledInstance(20, -1, Image.SCALE_SMOOTH);
        camerabtn.setIcon(new ImageIcon(img_camera));
        
        ImageIcon icon_image = new ImageIcon(ChatPanel.class.getResource("/images/image.png"));
        Image img_image = icon_image.getImage().getScaledInstance(20, -1, Image.SCALE_SMOOTH);
        imageButton.setIcon(new ImageIcon((img_image)));
        
        ImageIcon icon_hide_img = new ImageIcon(ChatPanel.class.getResource("/images/hide_infomation_image.png"));
        Image img_hide_img = icon_hide_img.getImage().getScaledInstance(20, -1, Image.SCALE_SMOOTH);
        hideTextButton.setIcon(new ImageIcon(img_hide_img));
        
        ImageIcon icon_file = new ImageIcon(ChatPanel.class.getResource("/images/file.png"));
        Image img_file = icon_file.getImage().getScaledInstance(20, -1, Image.SCALE_SMOOTH);
        fileButton.setIcon(new ImageIcon(img_file));
        
        ImageIcon icon_emotion = new ImageIcon(ChatPanel.class.getResource("/images/emotion.png"));
        Image img_emotion = icon_emotion.getImage().getScaledInstance(20, -1, Image.SCALE_SMOOTH);
        emojiButton.setIcon(new ImageIcon(img_emotion));
        
        ImageIcon icon_encrypt = new ImageIcon(ChatPanel.class.getResource("/images/encryption.png"));
        Image img_encrypt = icon_encrypt.getImage().getScaledInstance(20, -1, Image.SCALE_SMOOTH);
        encryptButton.setBackground(new Color(254, 250, 233));
        encryptButton.setIcon(new ImageIcon(img_encrypt));
        
        ImageIcon icon_mic = new ImageIcon(ChatPanel.class.getResource("/images/mic.png"));
        Image img_mic = icon_mic.getImage().getScaledInstance(20, -1, Image.SCALE_SMOOTH);
        voiceButton.setIcon(new ImageIcon(img_mic));
        
        buttonPanel.setBackground(new Color(249, 235, 193));

        hideTextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooser.getSelectedFile();
                    try {
                        BufferedImage image = ImageIO.read(selectedFile);
                        
                        String message = JOptionPane.showInputDialog(null, "Nhập tin cần giấu:");
                        if (message == null || message.isEmpty()) return;
                        
                        String password = JOptionPane.showInputDialog(null, "Nhập mật khẩu:");
                        if (password == null || password.isEmpty()) return;
                        
                        BufferedImage encoded = SteganographyUtil.hideTextWithPassword(image, message, password);
                        
                        File outFile = new File("encoded_" + selectedFile.getName());
                        ImageIO.write(encoded, "png", outFile);
                        
                        // Gửi ảnh đã giấu tin đi
                        parent.sendFileOrImage(chatWith, outFile, true);
                        appendSystemMessage("Đã gửi ảnh đã giấu tin: " + outFile.getName());

                        JOptionPane.showMessageDialog(null, "Đã giấu tin vào ảnh:\n" + outFile.getAbsolutePath());

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
                    }
                }
            }
        });

        voiceButton.addActionListener(new ActionListener() {
            private AudioFormat audioFormat;
            private TargetDataLine targetDataLine;
            private boolean isRecording = false;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRecording) {
                    // Bắt đầu ghi âm
                    try {
                        audioFormat = new AudioFormat(44100, 16, 1, true, false);
                        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
                        
                        if (!AudioSystem.isLineSupported(info)) {
                            JOptionPane.showMessageDialog(ChatPanel.this, 
                                "Microphone không khả dụng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
                        targetDataLine.open(audioFormat);
                        targetDataLine.start();
                        
                        tempVoiceFile = File.createTempFile("voice_message", ".wav");
                        AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);
                        
                        new Thread(() -> {
                            try {
                                isRecording = true;
                                
                                ImageIcon icon_stop = new ImageIcon(ChatPanel.class.getResource("/images/stop.png"));
                                Image img_stop = icon_stop.getImage().getScaledInstance(20, -1, Image.SCALE_SMOOTH);
                                voiceButton.setIcon(new ImageIcon(img_stop));
                                
                                AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, tempVoiceFile);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }).start();
                        
                    } catch (LineUnavailableException | IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(ChatPanel.this, 
                            "Lỗi khi bắt đầu ghi âm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // Dừng ghi âm và gửi
                    isRecording = false;
                    
                    ImageIcon icon_mic = new ImageIcon(ChatPanel.class.getResource("/images/mic.png"));
                    Image img_mic = icon_mic.getImage().getScaledInstance(20, -1, Image.SCALE_SMOOTH);
                    voiceButton.setIcon(new ImageIcon(img_mic));
                    
                    targetDataLine.stop();
                    targetDataLine.close();
                    
                    // Gửi file ghi âm
                    parent.sendFileOrImage(chatWith, tempVoiceFile, false);
                    appendSystemMessage("Đã gửi tin nhắn thoại");
                }
            }
        });

        buttonPanel.add(emojiButton);
        buttonPanel.add(fileButton);
        buttonPanel.add(imageButton);
        buttonPanel.add(hideTextButton);
        buttonPanel.add(voiceButton);
        buttonPanel.add(camerabtn);
        buttonPanel.add(encryptButton);
        
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        inputPanel.add(inputField, BorderLayout.CENTER);

        JButton btnSend = new JButton("Gửi");
        btnSend.setBackground(new Color(118, 222, 237));
        btnSend.setForeground(new Color(255, 255, 255));
        btnSend.setFont(new Font("Tahoma", Font.BOLD, 16));
        inputPanel.add(btnSend, BorderLayout.EAST);
        btnSend.setFocusable(false);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.NORTH);
        
        camerabtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		BufferedImage image = CaptureApp.showCaptureDialog(null);

                if (image != null) {
                    try {
                        // Lưu ảnh vào file tạm
                        File imageFile = File.createTempFile("captured_", ".png");
                        ImageIO.write(image, "png", imageFile);

                        // Gửi ảnh
                        parent.sendFileOrImage(chatWith, imageFile, true);
                        appendSystemMessage("Đã gửi ảnh chụp từ webcam.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
        	}
        });
        
        southPanel.add(inputPanel, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);

        ActionListener sendTextAction = e -> {
            String msg = inputField.getText().trim();
            if (!msg.isEmpty()) {
                parent.sendMessage(chatWith, msg);
                inputField.setText("");
            }
        };
        inputField.addActionListener(sendTextAction);
        btnSend.addActionListener(sendTextAction);

        encryptButton.addActionListener(e -> {
            String msg = inputField.getText().trim();
            if (!msg.isEmpty()) {
                String pwd = JOptionPane.showInputDialog(this, "Nhập mật khẩu mã hóa:");
                if (pwd != null && !pwd.isEmpty()) {
                    try {
                        parent.sendEncryptedMessage(chatWith, msg, pwd);
                        inputField.setText("");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        emojiButton.addActionListener(e -> {
            String[] emojis = {"😊", "😂", "😍", "👍", "🎉", "❤️", "😭", "😡"};
            String selected = (String) JOptionPane.showInputDialog(
                    this, "Chọn icon:", "Gửi Emoji",
                    JOptionPane.PLAIN_MESSAGE, null, emojis, emojis[0]
            );
            if (selected != null && !selected.isEmpty()) {
                parent.sendMessage(chatWith, selected);
            }
        });

        fileButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                parent.sendFileOrImage(chatWith, file, false);
            }
        });

        imageButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                parent.sendFileOrImage(chatWith, file, true);
                appendSystemMessage("Đã gửi ảnh: " + file.getName());
            }
        });
    }

    public void appendMessage(String sender, String message) {
        JLabel label = new JLabel(sender + ": " + message);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        chatAreaPanel.add(label);
        chatAreaPanel.revalidate();
        chatAreaPanel.repaint();
    }

    public void appendEncryptedMessage(String sender, String encrypted) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        JTextArea label = new JTextArea(sender + ": [Tin nhắn mã hóa]");
        label.setEditable(false);
        label.setBackground(Color.WHITE);
        label.setBorder(null);
        label.setLineWrap(true);
        label.setWrapStyleWord(true);

        JButton decryptButton = new JButton("Giải mã");
        decryptButton.addActionListener(e -> {
            String password = JOptionPane.showInputDialog(this, "Nhập mật khẩu để giải mã tin nhắn:");
            if (password != null) {
                try {
                    String decrypted = SimpleEncryptor.decrypt(encrypted, password);
                    label.setText(sender + ": " + decrypted);
                    decryptButton.setVisible(false);
                    this.revalidate();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Sai mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(label);
        panel.add(decryptButton);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        chatAreaPanel.add(panel);
        chatAreaPanel.revalidate();
        chatAreaPanel.repaint();
    }

    public void appendSystemMessage(String message) {
        JLabel label = new JLabel("[Hệ thống] " + message);
        label.setForeground(Color.GRAY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        chatAreaPanel.add(label);
        chatAreaPanel.revalidate();
        chatAreaPanel.repaint();
    }

    public void appendFileOrImageMessage(String sender, File file, boolean isImage) {
        if (file.getName().endsWith(".wav") || file.getName().endsWith(".mp3")) {
            // Xử lý tin nhắn thoại
            JLabel label = new JLabel(sender + " đã gửi tin nhắn thoại:");
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            chatAreaPanel.add(label);
            
            JPanel voicePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton playButton = new JButton("▶ Phát");
            JLabel durationLabel = new JLabel("");
            
            playButton.addActionListener(e -> {
                try {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                    
                    // Cập nhật thời lượng
                    long duration = clip.getMicrosecondLength() / 1000;
                    durationLabel.setText(String.format("(%d mili giây)", duration));
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ChatPanel.this, "Không thể phát tin nhắn thoại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            voicePanel.add(playButton);
            voicePanel.add(durationLabel);
            voicePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            chatAreaPanel.add(voicePanel);
        }
        else if (isImage && isImageFile(file.getName())) {
            JLabel label = new JLabel(sender + " đã gửi ảnh:");
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            chatAreaPanel.add(label);

            if (isImageFile(file.getName())) {
                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(150, -1, Image.SCALE_SMOOTH);
                JLabel imgLabel = new JLabel(new ImageIcon(img));
                imgLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                JPopupMenu popupMenu = new JPopupMenu();

                JMenuItem saveItem = new JMenuItem("📥 Tải ảnh");
                saveItem.addActionListener(ev -> {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setSelectedFile(file);
                    if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                        try {
                            Files.copy(file.toPath(), chooser.getSelectedFile().toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                            JOptionPane.showMessageDialog(this, "Đã lưu ảnh.");
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(this, "Không thể lưu ảnh.");
                        }
                    }
                });

                JMenuItem decodeItem = new JMenuItem("🕵️ Giải mã ảnh");
                decodeItem.addActionListener(ev -> {
                    try {
                        BufferedImage image = ImageIO.read(file);
                        String password = JOptionPane.showInputDialog(null, "Nhập mật khẩu:");
                        if (password == null || password.isEmpty()) return;

                        String message = SteganographyUtil.revealTextWithPassword(image, password);
                        JOptionPane.showMessageDialog(null, "Nội dung đã giấu:\n" + message);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
                    }
                });

                popupMenu.add(saveItem);
                popupMenu.add(decodeItem);

                imgLabel.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (e.isPopupTrigger()) popupMenu.show(imgLabel, e.getX(), e.getY());
                    }

                    public void mouseReleased(MouseEvent e) {
                        if (e.isPopupTrigger()) popupMenu.show(imgLabel, e.getX(), e.getY());
                    }
                });

                chatAreaPanel.add(imgLabel);

                imgLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                imgLabel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            JFrame viewer = new JFrame("Xem ảnh");
                            viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            viewer.getContentPane().add(new JLabel(new ImageIcon(file.getAbsolutePath())));
                            viewer.pack();
                            viewer.setLocationRelativeTo(null);
                            viewer.setVisible(true);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            showDownloadMenu(imgLabel, file);
                        }
                    }
                });

                chatAreaPanel.add(imgLabel);
            }
        } else {
            JLabel fileLabel = new JLabel("<HTML><U>" + file.getName() + "</U></HTML>");
            fileLabel.setForeground(Color.BLUE);
            fileLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            fileLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            fileLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e) || SwingUtilities.isLeftMouseButton(e)) {
                        showDownloadMenu(fileLabel, file);
                    }
                }
            });

            chatAreaPanel.add(fileLabel);
        }

        chatAreaPanel.revalidate();
        chatAreaPanel.repaint();
    }

    private void showDownloadMenu(Component parentComponent, File file) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem saveItem = new JMenuItem("Tải về");

        saveItem.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new File(file.getName()));
            int option = chooser.showSaveDialog(parentComponent);
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    Files.copy(file.toPath(), chooser.getSelectedFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
                    JOptionPane.showMessageDialog(null, "Tải thành công!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi tải file: " + ex.getMessage());
                }
            }
        });

        menu.add(saveItem);
        menu.show(parentComponent, 0, parentComponent.getHeight());
    }

    private boolean isImageFile(String name) {
        String lower = name.toLowerCase();
        return lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".gif");
    }
    
    
}