package com.neya.love.finder.utils.net;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.StringTokenizer;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.neya.love.finder.utils.LFConstants;

@SuppressWarnings("unused")
public class DBManager {
	private static DataSource dataSource;

	public DBManager() {
		try {
			Context context = new InitialContext();
			if (context == null) {
				throw new Exception("No Context");
			}
			dataSource = (DataSource) context
					.lookup(LFConstants.JNDI_DATASOURCE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public static String getDatabaseDescription() throws SQLException {
		Connection conn = getConnection();
		String dbString = conn.getMetaData().getURL();
		conn.close();
		StringTokenizer str = new StringTokenizer(dbString, ":/?=");
		for (int i = 1; i < 3; i++) {
			str.nextToken();
		}
		return "Using database on machine <b>" + str.nextToken() + "</b>";
	}
}
