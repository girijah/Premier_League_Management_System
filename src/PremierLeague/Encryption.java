package PremierLeague;

import java.io.Serializable;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption implements Serializable{

	// 128 bit key
	String key = "Bar12345Bar12345";

	// Create key and cipher
	Key aesKey = new SecretKeySpec(key.getBytes(), "AES");

	public Encryption() {

	}

	public byte[] encrypt(byte[] byteDataToEncrpt) {
		byte[] encrypted = null;
		try {
			Cipher cipher = Cipher.getInstance("AES");

			// encrypt the text
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			encrypted = cipher.doFinal(byteDataToEncrpt);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return encrypted;
	}

	public byte[] decrypt(byte[] encrypted) {
		byte[] decryptedByte = null;

		try {
			Cipher cipher = Cipher.getInstance("AES");

			// decrypt
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			decryptedByte = cipher.doFinal(encrypted);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedByte;

	}

}
