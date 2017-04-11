package com.googlecode.dummyjdbc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileResource implements Resource {
	protected final File file;

	public FileResource(File file) {
		this.file = file;
	}

	public FileResource(String filename) {
		this.file = new File(filename);
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(file);
	}
}
