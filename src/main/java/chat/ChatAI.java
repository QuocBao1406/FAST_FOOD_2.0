package chat;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class ChatAI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_chat;
	private JPanel chatPanel;
	private JScrollPane scrollPane;
	private final static String API_KEY = "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";
	private JPanel panel;
	private JLabel lblNewLabel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatAI frame = new ChatAI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ChatAI() {
		setSize(550, 650);
		setLocationRelativeTo(null);
		setTitle("AI ChatBot GPT-3.5");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel bottom_Panel = new JPanel();
		contentPane.add(bottom_Panel, BorderLayout.SOUTH);
		bottom_Panel.setLayout(new BorderLayout());
		
		tf_chat = new JTextField();
		tf_chat.setBackground(new Color(253, 251, 240));
		tf_chat.setColumns(10);
		tf_chat.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tf_chat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		bottom_Panel.add(tf_chat, BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("Gửi");
		btnNewButton.setBackground(new Color(251, 235, 227));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		bottom_Panel.add(btnNewButton, BorderLayout.EAST);
		
		scrollPane = new JScrollPane(chatPanel);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		chatPanel = new JPanel();
		scrollPane.setViewportView(chatPanel);
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
		chatPanel.setBackground(new Color(255, 255, 255));
		
		panel = new JPanel();
		panel.setBackground(new Color(252, 238, 226));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel("AI ChatBot GPT-3.5");
		lblNewLabel.setBackground(new Color(252, 238, 226));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setIcon(new ImageIcon(ChatAI.class.getResource("/images/chatgpt.png")));
		lblNewLabel.setBorder(new LineBorder(new Color(244, 134, 43))); // màu xanh, dày 2px
		panel.add(lblNewLabel);
	}

	
	public void addMessage(String message, boolean isUser) {
		JPanel msgPanel = new JPanel(new FlowLayout(isUser ? FlowLayout.RIGHT : FlowLayout.LEFT));
		JLabel label = new JLabel("<html><body style = 'width: 300px; padding: 4px; margin: 0px' >" + message.replace("\n","<br>") + "</body></html>");
		label.setOpaque(true);
		label.setBackground(isUser ? new Color(220, 248, 198) : new Color(240, 240, 240));
		label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		msgPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		msgPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		msgPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, label.getPreferredSize().height + 10));
		
		msgPanel.setBackground(Color.WHITE);
		msgPanel.add(label);
		
		chatPanel.add(msgPanel);
		chatPanel.add(Box.createVerticalStrut(2)); // khoang cach giua 2 msgPanel
		chatPanel.revalidate(); //cap nhat lai bo cuc layout khi co thay doi
		chatPanel.repaint(); //yeu cau swing ve lai giao dien
		
		SwingUtilities.invokeLater(() -> {
			JScrollBar vertical = scrollPane.getVerticalScrollBar();
			vertical.setValue(vertical.getMaximum());
		});
	}
	
	public void sendMessage() {
		String question = tf_chat.getText().trim();
		if(question.isEmpty()) return;
		addMessage(question, true);
		tf_chat.setText("");
		
		new Thread(() -> {
			try {
				String reply = ask(question);
				addMessage(reply, false);
			} catch (Exception e) {
				addMessage("Lỗi khi gọi AI: " + e.getMessage(), false);
			}
		}).start();
	}
	
	public static String ask(String question) throws Exception {
		String apiUrl = "https://openrouter.ai/api/v1/chat/completions";
		
		String payload = """
		{
			"model" : "openai/gpt-3.5-turbo",
			"messages" : [
				{
				"role" : "user",
				"content" : "%s"
				}
			]
		}
		""".formatted(question.replace("\"", "\\\"")); // chuyen tu " -> \"
		
		URL url = new URL(apiUrl);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("POST"); //gui du lieu di
		conn.setDoOutput(true); //toi se gui du lieu ra ngoai
		conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("HTTP-Referer", "https://localhost");
		conn.setRequestProperty("X-Title", "java-chat-app");
		
		try (OutputStream os = conn.getOutputStream()) {
			os.write(payload.getBytes("UTF-8"));
		}
		
		//conn.getInputStream() lay du lieu dau vao co the la HTML, JSON...
		//InputStreamReader chuyen byte tu server thanh ky tu
		//BufferedReader doc du lieu tung dong mot
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder response = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			response.append(line);
		}
		br.close();
		
		JSONObject obj = new JSONObject(response.toString());
		JSONArray choices = obj.getJSONArray("choices");
		JSONObject message = choices.getJSONObject(0).getJSONObject("message");
		String content = message.getString("content");
		
		return content.trim();
	}
}
