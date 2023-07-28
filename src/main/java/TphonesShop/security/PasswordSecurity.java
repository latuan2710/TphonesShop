package TphonesShop.security;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class PasswordSecurity {
	public String encode(String password) {
		try {
			byte[] byteEncrypted = null;
			PublicKey publicKey = getPublicKey();
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byteEncrypted = cipher.doFinal(password.getBytes());
			String encrypted = Base64.getEncoder().encodeToString(byteEncrypted);
			return encrypted;
		} catch (Exception e) {
			System.out.println(e);
		}
		return "";
	}

	public String decode(String encrypted) {
		try {
			PrivateKey privateKey = getPrivateKey();
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] byteDecrypted = cipher.doFinal(Base64.getDecoder().decode(encrypted));
			String decrypted = new String(byteDecrypted);
			return decrypted;
		} catch (Exception e) {
			System.out.println(e);
		}
		return "";
	}

	private PrivateKey getPrivateKey() throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File("PrivateKey.rsa").toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	private PublicKey getPublicKey() throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File("PublicKey.rsa").toPath());
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}
}
