package com.googlecode.dummyjdbc.utils;

import java.io.IOException;
import java.io.InputStream;

public interface Resource {
	InputStream getInputStream() throws IOException;
}
