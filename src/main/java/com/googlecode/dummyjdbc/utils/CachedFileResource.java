package com.googlecode.dummyjdbc.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CachedFileResource extends FileResource {
	private volatile byte[] buffer;

	public CachedFileResource(File file) {
		super(file);
	}

	public CachedFileResource(String filename) {
		super(filename);
	}

	static byte[] readAll(InputStream in) throws IOException {
		ByteArrayOutputStream o = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		for (int r = in.read(buf); r > 0; r = in.read(buf)) {
			o.write(buf, 0, r);
		}
		return o.toByteArray();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if (buffer == null) {
			FileInputStream fin = new FileInputStream(file);
			try {
				buffer = readAll(fin);
			} finally {
				fin.close();
			}
		}
		return new ByteArrayInputStream(buffer);
	}
}
