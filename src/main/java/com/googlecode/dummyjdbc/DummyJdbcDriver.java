package com.googlecode.dummyjdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.googlecode.dummyjdbc.connection.impl.DummyConnection;
import com.googlecode.dummyjdbc.utils.BufferedFileResource;
import com.googlecode.dummyjdbc.utils.FileResource;
import com.googlecode.dummyjdbc.utils.Resource;

/**
 * The {@link DummyJdbcDriver}. The {@link #connect(String, Properties)} method
 * returns the {@link DummyConnection}.
 * 
 * @author Kai Winter
 */
public final class DummyJdbcDriver implements Driver {

	private static Map<String, Resource> tableResources = new ConcurrentHashMap<String, Resource>();

	static {
		try {
			// Register this with the DriverManager
			DriverManager.registerDriver(new DummyJdbcDriver());
		} catch (SQLException e) {
			// ignore
		}
	}

	/**
	 * Registers a CSV file for a database table.
	 * 
	 * When a Query is executed like <code>SELECT * FROM ADDRESSES</code> the
	 * given <code>file</code> for the given <code>tablename</code>
	 * <code>addresses</code> will be used.
	 * 
	 * @param tablename
	 *            The name of the database table like in the SQL statement (e.g.
	 *            addresses).
	 * @param file
	 *            A {@link File} object of a CSV file which should be parsed in
	 *            order to return table data.
	 */
	public static void addTableResource(String tablename, File file) {
		addTableResource(tablename, file, false);
	}

	/**
	 * Registers a CSV file for a database table.
	 * 
	 * When a Query is executed like <code>SELECT * FROM ADDRESSES</code> the
	 * given <code>file</code> for the given <code>tablename</code>
	 * <code>addresses</code> will be used.
	 * 
	 * @param tablename
	 *            The name of the database table like in the SQL statement (e.g.
	 *            addresses).
	 * @param file
	 *            A {@link File} object of a CSV file which should be parsed in
	 *            order to return table data.
	 * @param buffered
	 *            set <code>true</code> to buffered file content in memory.
	 */
	public static void addTableResource(String tablename, File file,
			boolean buffered) {
		tableResources.put(tablename, buffered ? new BufferedFileResource(file)
				: new FileResource(file));
	}

	@Override
	public int getMajorVersion() {
		return 1;
	}

	@Override
	public int getMinorVersion() {
		return 0;
	}

	@Override
	public boolean jdbcCompliant() {
		return false;
	}

	@Override
	public boolean acceptsURL(String url) throws SQLException {
		return true;
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		return new DummyConnection(tableResources);
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(final String url,
			final Properties props) throws SQLException {
		return new DriverPropertyInfo[0];
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}
}
