package com.googlecode.dummyjdbc.resultset.impl;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import com.googlecode.dummyjdbc.resultset.DummyResultSetMetaData;

/**
 * The Csv Implementation of {@link ResultSetMetaData}.
 * 
 * @author <a href="mailto:changyuan.lh@taobao.com">Changyuan.lh</a>
 */
public class CsvResultSetMetaData extends DummyResultSetMetaData {

	private final String tableName;
	private final String[] columns;

	public CsvResultSetMetaData(String tableName, String[] columns) {
		this.tableName = tableName;
		this.columns = columns;
	}

	@Override
	public int getColumnCount() throws SQLException {
		return columns.length;
	}

	@Override
	public String getColumnLabel(int column) throws SQLException {
		return columns[column - 1];
	}

	@Override
	public String getColumnName(int column) throws SQLException {
		return columns[column - 1];
	}

	@Override
	public int getColumnType(int column) throws SQLException {
		String name = columns[column - 1];
		if (name.regionMatches(true, name.length() - 2, "ID", 0, 2)) {
			return Types.BIGINT;
		}
		if (name.regionMatches(true, name.length() - 2, "NO", 0, 2)) {
			return Types.BIGINT;
		}
		return 0;
	}

	@Override
	public String getTableName(int column) throws SQLException {
		return tableName;
	}
}
