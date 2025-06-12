package chat;
import java.awt.image.BufferedImage;

public class SteganographyUtil {

    // Đánh dấu kết thúc chuỗi
    private static final String END_MARKER = "<END>";

    public static BufferedImage hideText(BufferedImage image, String text) {
        text += END_MARKER;
        int width = image.getWidth();
        int height = image.getHeight();
        int[] bits = toBits(text);

        if (bits.length > width * height * 3) {
            throw new IllegalArgumentException("Ảnh không đủ chỗ để giấu văn bản");
        }

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(image, 0, 0, null); // Sao chép ảnh gốc

        int bitIndex = 0;
        outer:
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = result.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                if (bitIndex < bits.length) r = setLSB(r, bits[bitIndex++]);
                if (bitIndex < bits.length) g = setLSB(g, bits[bitIndex++]);
                if (bitIndex < bits.length) b = setLSB(b, bits[bitIndex++]);

                int newRgb = (r << 16) | (g << 8) | b;
                result.setRGB(x, y, newRgb);

                if (bitIndex >= bits.length) break outer;
            }
        }
        return result;
    }

    public static BufferedImage hideTextWithPassword(BufferedImage image, String message, String password) throws Exception {
        String encrypted = SimpleEncryptor.encrypt(message, password);
        return hideText(image, encrypted);
    }

    public static String revealText(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        StringBuilder binary = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                binary.append(r & 1);
                binary.append(g & 1);
                binary.append(b & 1);
            }
        }

        StringBuilder text = new StringBuilder();
        for (int i = 0; i + 7 < binary.length(); i += 8) {
            int charCode = Integer.parseInt(binary.substring(i, i + 8), 2);
            char c = (char) charCode;
            text.append(c);
            if (text.toString().endsWith(END_MARKER)) {
                return text.substring(0, text.length() - END_MARKER.length());
            }
        }
        return ""; // Không tìm thấy thông điệp
    }

    public static String revealTextWithPassword(BufferedImage image, String password) throws Exception {
        String encrypted = revealText(image);
        return SimpleEncryptor.decrypt(encrypted, password);
    }

    private static int[] toBits(String text) {
        byte[] bytes = text.getBytes();
        int[] bits = new int[bytes.length * 8];
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 7; j >= 0; j--) {
                bits[i * 8 + (7 - j)] = (bytes[i] >> j) & 1;
            }
        }
        return bits;
    }

    private static int setLSB(int value, int bit) {
        return (value & 0xFE) | bit;
    }
}