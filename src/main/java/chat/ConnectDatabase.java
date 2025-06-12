package chat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {
    private static ConnectDatabase instance;
    private static final String JDBC_URL = "jdbc:sqlserver://DESKTOP-K4JVGQP\\QUOCBAO:1433;databaseName=FAST_FOOD;trustServerCertificate=true";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "123";

    private ConnectDatabase() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy JDBC Driver!");
            e.printStackTrace();
        }
    }

    public static ConnectDatabase getInstance() {
        if (instance == null) {
            instance = new ConnectDatabase();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            System.out.println("✅ Kết nối thành công với SQL Server");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Không thể kết nối đến cơ sở dữ liệu!");
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔌 Đã đóng kết nối.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đóng kết nối:");
            e.printStackTrace();
        }
    }
}