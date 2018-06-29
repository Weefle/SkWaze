package fr.rhaz.sockets;

import com.google.gson.Gson;

public class Sockets {
	public static Gson gson = new Gson();

	public static Gson gson() {
		return gson;
	}

	public static String[] split(String input, int max) {
		return input.split("(?<=\\G.{" + max + "})");
	}

	public static Sockets instance() {
		return new Sockets();
	}
}
