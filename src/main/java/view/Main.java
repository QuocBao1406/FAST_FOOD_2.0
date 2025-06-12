package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.Data_Login;
import net.miginfocom.swing.MigLayout;
import view.pages.Store_Management;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private SideBar sidebar;
	private Body body;
	private Login login;
	private static int port = 3456;
    public static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static Data_Login data_login;

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
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

	public Main() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/logo.png")));
		setTitle("BHNM FAST FOOD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(-6, 0, 1550, 823);
		contentPane = new JPanel();
		
		contentPane.setLayout(new MigLayout("fillx, filly", "0[300!]0[fill, 100%]0", "0[fill]0"));
		
		body = new Body();
		sidebar = new SideBar(body);
		
		contentPane.add(sidebar, "width 300:300:300");
		contentPane.add(body, "width 1240:1240:1240");
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
				if(SideBar.getFloor().equals("Nhân Viên Tầng 1")) {
					data_login.getInstance().isInActive(1);
					System.exit(0);
				} else if(SideBar.getFloor().equals("Nhân Viên Tầng 2")) {
					data_login.getInstance().isInActive(2);
					System.exit(0);
				} else if(SideBar.getFloor().equals("Nhân Viên Tầng 3")) {
					data_login.getInstance().isInActive(3);
					System.exit(0);
				} else if(SideBar.getFloor().equals("Admin")) {
					data_login.getInstance().isInActive(0);
					System.exit(0);
				}
            }

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
            	if(SideBar.getFloor().equals("Nhân Viên Tầng 1")) {
					data_login.getInstance().isInActive(1);
					System.exit(0);
				} else if(SideBar.getFloor().equals("Nhân Viên Tầng 2")) {
					data_login.getInstance().isInActive(2);
					System.exit(0);
				} else if(SideBar.getFloor().equals("Nhân Viên Tầng 3")) {
					data_login.getInstance().isInActive(3);
					System.exit(0);
				} else if(SideBar.getFloor().equals("Admin")) {
					data_login.getInstance().isInActive(0);
					System.exit(0);
				}
            }
        });
		
		login = new Login();
		add(login);
		
		Data_Login data_login = new Data_Login(this);
	}
	
	public void displayAdmin() {
		login.setVisible(false);
		setContentPane(contentPane);
	}
	
	public void displayEmployee1() {
		login.setVisible(false);
		setContentPane(contentPane);
		sidebar.hide();
		body.showPanel("sell");
		sidebar.sell();
	}
	
	public void displayEmployee2() {
		login.setVisible(false);
		setContentPane(contentPane);
		sidebar.hide();
		body.showPanel("sell");
		sidebar.sell();
	}
	
	public void displayEmployee3() {
		login.setVisible(false);
		setContentPane(contentPane);
		sidebar.hide();
		body.showPanel("sell");
		sidebar.sell();
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
