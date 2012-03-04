package com.neya.love.finder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.neya.love.finder.bean.CustomerData;
import com.neya.love.finder.services.FriendshipService;
import com.neya.love.finder.utils.DBTables;
import com.neya.love.finder.utils.net.DBManager;

public class FriendshipPersistor implements FriendshipService {
	private Connection conn = null;

	@Override
	public boolean addFrirend(CustomerData customerOne, CustomerData customerTwo) throws SQLException {
		PreparedStatement stmt = null;
		boolean isFriendshipDone = false;

		try {
			String sql = "INSERT INTO "
					+ DBTables.FRIENDSHIP_TABLE
					+ " (first_customer_id, second_customer_id) "
					+ "VALUES (?,?)";

			// conn = dbManager.getConnection();
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, customerOne.getCustomerId());
			stmt.setInt(2, customerTwo.getCustomerId());

			if (stmt.executeUpdate() == 1)
				isFriendshipDone = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(stmt);
		}
		
		return isFriendshipDone;
	}

	@Override
	public boolean removeFrined(CustomerData customerOne,
			CustomerData customerTwo) throws SQLException {
		PreparedStatement stmt = null;
		boolean isDeleteDone = false;

		try {
			String sql = "DELETE FROM "
					+ DBTables.FRIENDSHIP_TABLE
					+ " WHERE first_customer_id=? AND second_customer_id=? ";

			// conn = dbManager.getConnection();
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, customerOne.getCustomerId());
			stmt.setInt(2, customerTwo.getCustomerId());

			if (stmt.executeUpdate() == 1)
				isDeleteDone = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(stmt);
		}
		
		return isDeleteDone;
	}

	/**
	 * @param connection
	 * 
	 * @author <a href="mailto:yanev93@gmail.com">Nikolay Yanev</a>
	 */
	@SuppressWarnings("static-access")
	public FriendshipPersistor(Connection connection) {
		try {
			if (connection == null) {
				DBManager dbManager = new DBManager();
				this.conn = dbManager.getConnection();
			} else {
				this.conn = connection;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author <a href="mailto:yanev93@gmail.com">Nikolay Yanev</a>
	 */
	@SuppressWarnings("static-access")
	public FriendshipPersistor() {
		DBManager dbManager = new DBManager();
		try {
			this.conn = dbManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param conn
	 * @param stmt
	 * @throws SQLException
	 * 
	 * @author Nikolay Yanev
	 * @email yanev93@gmail.com
	 */
	private void closeConnection(PreparedStatement stmt) throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
		/*
		 * if (conn != null) { conn.close(); }
		 */
	}
}
