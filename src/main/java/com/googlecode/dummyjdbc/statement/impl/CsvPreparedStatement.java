package com.googlecode.dummyjdbc.statement.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.googlecode.dummyjdbc.statement.PreparedStatementAdapter;
import com.googlecode.dummyjdbc.utils.Resource;

/**
 * Wraps the {@link CsvStatement} as a prepared statement.
 *
 * @author Kai Winter
 */
public class CsvPreparedStatement extends PreparedStatementAdapter {

	private final CsvStatement statement;
	private final String sql;

	/**
	 * Constructs a new {@link CsvPreparedStatement}.
	 *
	 * @param tableResources {@link Map} of table name to CSV file.
	 * @param sql
	 *            the SQL statement.
	 */
	public CsvPreparedStatement(Map<String, Resource> tableResources, String sql) {
		this.statement = new CsvStatement(tableResources);
		this.sql = sql;
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		return statement.executeQuery(sql);
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		return statement.executeQuery(sql);
	}

}
