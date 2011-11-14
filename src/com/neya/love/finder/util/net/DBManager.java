package com.neya.love.finder.util.net;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.StringTokenizer;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

@SuppressWarnings("unused")
public class DBManager {
	private static DataSource dataSource;

	public DBManager() {
		try {
			Context context = new InitialContext();
			if (context == null) {
				throw new Exception("No Context");
			}
			// TODO extract "java:comp/env/jdbc/mysqlDB" in new Constants class
			dataSource = (DataSource) context
					.lookup("java:comp/env/jdbc/mysqlDB");
		} catch (Exception e) {
			// TODO Add to log
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
