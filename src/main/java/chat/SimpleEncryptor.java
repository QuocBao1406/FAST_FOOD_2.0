package chat;
import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SimpleEncryptor {
	public static SecretKeySpec createKey(String password) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] keyBytes = md.digest(password.getBytes("UTF-8"));
		return new SecretKeySpec(keyBytes, "AES");
	}
	
	public static String encrypt(String text, String password) throws Exception {
		SecretKeySpec key = createKey(password);
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
		String encryptedBase64 = Base64.getEncoder().encodeToString(encrypted);
		return encryptedBase64;
	}
	
	public static String decrypt(String entext, String password) throws Exception {
		SecretKeySpec dekey = createKey(password);
		Cipher decipher = Cipher.getInstance("AES");
		decipher.init(Cipher.DECRYPT_MODE, dekey);
		byte[] decrypted = decipher.doFinal(Base64.getDecoder().decode(entext));
		String detext = new String(decrypted, "UTF-8");
		return detext;
	}
}
