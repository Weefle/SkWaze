package fr.rhaz.sockets.utils;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	public static SecretKey generateKey() {
		try {
			KeyGenerator KeyGen = KeyGenerator.getInstance("AES");
			KeyGen.init(128);
			return KeyGen.generateKey();
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}

	public static String encrypt(String data, SecretKey key) {
		String str = null;
		try {
			Cipher AesCipher = Cipher.getInstance("AES");
			AesCipher.init(Cipher.ENCRYPT_MODE, key);
			str = B64.to(AesCipher.doFinal(data.getBytes()));
		} catch (Exception e) {
		}
		return str;
	}

	public static String decrypt(String data, SecretKey key) {
		String str = null;
		try {
			Cipher AesCipher = Cipher.getInstance("AES");
			AesCipher.init(Cipher.DECRYPT_MODE, key);
			byte[] bytePlainText = AesCipher.doFinal(B64.from(data));
			str = new String(bytePlainText);
		} catch (Exception e) {
		}
		return str;
	}

	public static SecretKey toKey(String key) {
		byte[] decodedKey = B64.from(key);
		return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	}

	public static String toString(SecretKey key) {
		return B64.to(key.getEncoded());
	}
}
