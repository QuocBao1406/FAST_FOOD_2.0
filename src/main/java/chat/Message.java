package chat;
import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable {
    public enum Type {
        TEXT, IMAGE, FILE, VOICE
    }

    private Type type;
    private String sender;
    private String receiver;
    private String content; // Nội dung tin nhắn hoặc tên file
    private byte[] data;    // Dữ liệu file hoặc ảnh
    private Timestamp timestamp;
    private byte[] voiceData;

    public Message(Type type, String sender, String receiver, String content, byte[] data, Timestamp timestamp) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.data = data;
        this.timestamp = timestamp;
    }

    // Getter & Setter
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    public byte[] getVoiceData() { return voiceData; }
    
    public void setVoiceData(byte[] voiceData) { this.voiceData = voiceData; }
}
