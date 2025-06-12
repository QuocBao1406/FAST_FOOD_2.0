package chat;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private static int port = 3456;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("🚀 Server đang chạy ở port: " + port +"!");

            while (true) {
                Socket socket = server.accept();
                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);
                handler.start();
            }
        } catch (IOException e) {
        	System.out.println("⚠️ Server đã được chạy ở cổng " + port + ", Không thể mở thêm.");
        }
    }

    static void broadcastUserList() {
        StringBuilder sb = new StringBuilder("USERLIST:");
        for (ClientHandler ch : clients) {
            sb.append(ch.username).append(",");
        }

        for (ClientHandler ch : clients) {
            ch.sendMessage(sb.toString());
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader reader;
        private BufferedWriter writer;
        String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(String msg) {
            try {
                writer.write(msg);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                username = reader.readLine();
                System.out.println(username + " đã kết nối.");
                broadcastUserList();

                String msg;
                while ((msg = reader.readLine()) != null) {
                    if (msg.startsWith("/enc:")) {
                        // Tin nhắn mã hóa
                        String[] parts = msg.split(":", 4);
                        if (parts.length < 4) continue;

                        String sender = parts[1];
                        String receiver = parts[2];
                        String encrypted = parts[3];

                        broadcastToRelevantUsers("/enc:" + sender + ":" + receiver + ":" + encrypted, sender, receiver);

                    } else if (msg.startsWith("/img:") || msg.startsWith("/file:")) {
                        // Gửi ảnh hoặc file (chỉ đường dẫn)
                        String[] parts = msg.split(":", 4);
                        if (parts.length < 4) continue;

                        String prefix = parts[0]; // /img hoặc /file
                        String sender = parts[1];
                        String receiver = parts[2];
                        String filePath = parts[3];

                        broadcastToRelevantUsers(prefix + ":" + sender + ":" + receiver + ":" + filePath, sender, receiver);

                    } else {
                        // Tin nhắn văn bản thường
                        String[] parts = msg.split(":", 3);
                        if (parts.length < 3) continue;

                        String sender = parts[0];
                        String receiver = parts[1];
                        String message = parts[2];

                        broadcastToRelevantUsers("MSG:" + sender + ":" + receiver + ":" + message, sender, receiver);
                    }
                }
            } catch (IOException e) {
                System.out.println(username + " đã ngắt kết nối.");
            } finally {
                try {
                    clients.remove(this);
                    socket.close();
                    broadcastUserList();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void broadcastToRelevantUsers(String msg, String sender, String receiver) {
            synchronized (clients) {
                for (ClientHandler ch : clients) {
                    if (receiver.equalsIgnoreCase("ALL") || ch.username.equals(receiver) || ch.username.equals(sender)) {
                        ch.sendMessage(msg);
                    }
                }
            }
        }
    }
}