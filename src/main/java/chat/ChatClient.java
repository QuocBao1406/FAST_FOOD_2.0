package chat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

public class ChatClient extends JFrame {
    private String username;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private DefaultListModel<String> userListModel = new DefaultListModel<>();
    private JList<String> userList = new JList<>(userListModel);
    private JPanel chatContainer = new JPanel(new CardLayout());
    private Map<String, ChatPanel> chatPanels = new HashMap<>();

    public ChatClient() {
        setTitle("Chat App");
        setSize(580, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        loginDialog();
        userList.setFont(new Font("Tahoma", Font.PLAIN, 15));

        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane userScroll = new JScrollPane(userList);
        userScroll.setPreferredSize(new Dimension(140, getHeight()));
        getContentPane().add(userScroll, BorderLayout.WEST);

        getContentPane().add(chatContainer, BorderLayout.CENTER);

        userList.addListSelectionListener(e -> {
            String selected = userList.getSelectedValue();
            if (selected != null) {
                showChatPanel(selected);
            }
        });

        new MessageReceiver().start();
    }

    private void loginDialog() {
        username = JOptionPane.showInputDialog(this, "Nhập tên người dùng:");
        if (username == null || username.trim().isEmpty()) {
            System.exit(0);
        }

        try {
            socket = new Socket("localhost", 3456);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(username);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến server");
            System.exit(1);
        }
    }

    private void showChatPanel(String user) {
        CardLayout cl = (CardLayout) chatContainer.getLayout();
        if (!chatPanels.containsKey(user)) {
            ChatPanel panel = new ChatPanel(user, this);
            chatPanels.put(user, panel);
            chatContainer.add(panel, user);
        }
        cl.show(chatContainer, user);
    }

    public void sendMessage(String receiver, String message) {
        try {
            writer.write(username + ":" + receiver + ":" + message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Không thể gửi tin nhắn");
        }
    }

    public void sendEncryptedMessage(String receiver, String message, String password) throws Exception {
        String encrypted = SimpleEncryptor.encrypt(message, password);
        try {
            writer.write("/enc:" + username + ":" + receiver + ":" + encrypted);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Không thể gửi tin nhắn mã hóa");
        }
    }

    public void sendFileOrImage(String receiver, File file, boolean isImage) {
        try {
            String prefix = isImage ? "/img" : "/file";
            writer.write(prefix + ":" + username + ":" + receiver + ":" + file.getAbsolutePath());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Không thể gửi file/ảnh");
        }
    }

    class MessageReceiver extends Thread {
        public void run() {
            try {
                String msg;
                while ((msg = reader.readLine()) != null) {
                    if (msg.startsWith("USERLIST:")) {
                        updateUserList(msg.substring(9));
                    } else if (msg.startsWith("MSG:")) {
                        String[] parts = msg.split(":", 4);
                        String sender = parts[1];
                        String receiver = parts[2];
                        String message = parts[3];

                        String chatWith = receiver.equalsIgnoreCase("ALL") ? "ALL" : (sender.equals(username) ? receiver : sender);

                        SwingUtilities.invokeLater(() -> {
                            if (!chatPanels.containsKey(chatWith)) {
                                ChatPanel panel = new ChatPanel(chatWith, ChatClient.this);
                                chatPanels.put(chatWith, panel);
                                chatContainer.add(panel, chatWith);
                            }
                            chatPanels.get(chatWith).appendMessage(sender, message);
                        });
                    } else if (msg.startsWith("/enc")) {
                        String[] parts = msg.split(":", 4);
                        String sender = parts[1];
                        String receiver = parts[2];
                        String encrypted = parts[3];

                        String chatWith = receiver.equalsIgnoreCase("ALL") ? "ALL" : (sender.equals(username) ? receiver : sender);

                        SwingUtilities.invokeLater(() -> {
                            if (!chatPanels.containsKey(chatWith)) {
                                ChatPanel panel = new ChatPanel(chatWith, ChatClient.this);
                                chatPanels.put(chatWith, panel);
                                chatContainer.add(panel, chatWith);
                            }
                            chatPanels.get(chatWith).appendEncryptedMessage(sender, encrypted);
                        });
                    } else if (msg.startsWith("/img") || msg.startsWith("/file")) {
                        String[] parts = msg.split(":", 4);
                        String sender = parts[1];
                        String receiver = parts[2];
                        String filePath = parts[3];
                        boolean isImage = msg.startsWith("/img");

                        String chatWith = receiver.equalsIgnoreCase("ALL") ? "ALL" : (sender.equals(username) ? receiver : sender);

                        SwingUtilities.invokeLater(() -> {
                            if (!chatPanels.containsKey(chatWith)) {
                                ChatPanel panel = new ChatPanel(chatWith, ChatClient.this);
                                chatPanels.put(chatWith, panel);
                                chatContainer.add(panel, chatWith);
                            }
                            File file = new File(filePath);
                            chatPanels.get(chatWith).appendFileOrImageMessage(sender, file, isImage);
                        });
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(ChatClient.this, "Đã ngắt kết nối server");
            }
        }

        private void updateUserList(String csv) {
            SwingUtilities.invokeLater(() -> {
                userListModel.clear();
                List<String> names = Arrays.asList(csv.split(","));
                for (String name : names) {
                    if (!name.equals(username) && !name.isEmpty()) {
                        userListModel.addElement(name);
                    }
                }

                if (!chatPanels.containsKey("ALL")) {
                    ChatPanel allPanel = new ChatPanel("ALL", ChatClient.this);
                    chatPanels.put("ALL", allPanel);
                    chatContainer.add(allPanel, "ALL");
                }
                if (!userListModel.contains("ALL")) {
                    userListModel.add(0, "ALL");
                }

                if (userList.getSelectedValue() == null && userListModel.size() > 0) {
                    userList.setSelectedIndex(0);
                }
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatClient client = new ChatClient();
            client.setVisible(true);
        });
    }
}