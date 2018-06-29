package fr.rhaz.sockets.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;

public class Logger extends ByteArrayOutputStream {
	public PrintWriter writer = new PrintWriter(System.out);

	public InputStream getInputStream() {
		return new ByteArrayInputStream(this.buf, 0, this.count);
	}
}