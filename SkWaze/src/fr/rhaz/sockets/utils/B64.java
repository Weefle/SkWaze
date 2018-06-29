package fr.rhaz.sockets.utils;

import java.util.Base64;

public class B64 {
	public static String to(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}

	public static byte[] from(String data) {
		return Base64.getDecoder().decode(data);
	}
}
