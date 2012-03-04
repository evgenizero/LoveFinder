/**
 * Make DB connection
 * @author Nikolay Yanev
 * @email yanev93@gmail.com
 * @date 15 Nov 2011
 */

package com.neya.love.finder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.neya.love.finder.bean.CustomerData;
import com.neya.love.finder.services.CustomerService;
import com.neya.love.finder.utils.LFConstants;
import com.neya.love.finder.utils.StringUtil;
import com.neya.love.finder.utils.DBTables;
import com.neya.love.finder.utils.net.DBManager;

public class CustomerPersistor implements CustomerService {
	private Connection conn = null;

	// private static CustomerPersistor singelton;

	/**
	 * Persist customer data to DB
	 * 
	 * @param customer
	 * @return true if customer persist is successful
	 * @throws SQLException
	 * 
	 * @author Nikolay Yanev
	 * @email yanev93@gmail.com
	 */
	public int addCustomer(CustomerData customer) throws SQLException {
		PreparedStatement stmt = null;
		int customerId = 0;
		try {
			String sql = "INSERT INTO "
					+ DBTables.CUSTOMER_TABLE
					+ " (status, username, password, email, age, country, city, is_hidden) "
					+ "VALUES (?,?,?,?,?,?,?,?)";

			// conn = dbManager.getConnection();
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, LFConstants.CUSTOMER_DEFAULT_STATUS);
			stmt.setString(2, customer.getUsername());
			stmt.setString(3, StringUtil.md5(customer.getPassword()));
			stmt.setString(4, customer.getEmail());
			stmt.setInt(5, customer.getAge());
			stmt.setInt(6, (short) 1); // TODO use customer.getCountry (should
										// convert to short from String)
			stmt.setInt(7, (short) 1); // TODO use customer.getCity (should
										// convert to short from String)
			stmt.setInt(8, customer.getIsHidden());

			if (stmt.executeUpdate() == 1) {
				ResultSet rs = stmt.getGeneratedKeys();

				if (rs != null && rs.next()) {
					customerId = rs.getInt(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(stmt);
		}

		return customerId;
	}

	/**
	 * Login with username and pass
	 * 
	 * @param username
	 *            username of the customer
	 * @param password
	 *            password of the customer
	 * 
	 * @return return id of the customer
	 * 
	 * @author Nikolay Yanev
	 * @throws SQLException
	 */
	public int logIn(String username, String password) throws SQLException {
		int customerId = 0;
		// Connection conn = null;
		PreparedStatement stmt = null;

		try {
			String sql = "SELECT customer_id FROM "
					+ DBTables.CUSTOMER_TABLE 
					+ " WHERE  username = ? AND password = ? ";

			// conn = DBManager.getConnection();
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, username);
			stmt.setString(2, StringUtil.md5(password));

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				customerId = rs.getInt(1);
			}
		} finally {
			closeConnection(stmt);
		}

		return customerId;
	}

	/**
	 * Find customers
	 * 
	 * @param customerId
	 *            id of the customer
	 * @return the founded customer
	 * 
	 * @author Nikolay Yanev
	 * @throws SQLException
	 */
	public CustomerData findById(int customerId) throws SQLException {
		// Connection conn = null;
		PreparedStatement stmt = null;
		CustomerData customer = null;

		try {
			String sql = "SELECT customer_id, status, username, email, age, country, city, is_hidden FROM "
					+ DBTables.CUSTOMER_TABLE + " WHERE  customer_id = ?";

			// conn = DBManager.getConnection();
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, customerId);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				customer = new CustomerData(rs.getInt(1), rs.getInt(2),
						rs.getString(3), null, rs.getString(4), rs.getInt(5),
						String.valueOf(rs.getInt(6)), String.valueOf(rs
								.getInt(7)), 0, rs.getInt(8));
			}
		} finally {
			closeConnection(stmt);
		}

		return customer;
	}

	/**
	 * Find customers
	 * 
	 * @param username
	 *            username of the customer
	 * @return the founded customer
	 * 
	 * @author Nikolay Yanev
	 * @throws SQLException
	 * @email yanev93@gmail.com
	 */
	public CustomerData findByUsername(String username) throws SQLException {
		PreparedStatement stmt = null;
		CustomerData customer = null;

		try {
			String sql = "SELECT customer_id, status, username, email, age, country, city, is_hidden FROM "
					+ DBTables.CUSTOMER_TABLE + " WHERE  username = ?";

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, username);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				customer = new CustomerData(rs.getInt(1), rs.getInt(2),
						rs.getString(3), null, rs.getString(4), rs.getInt(5),
						String.valueOf(rs.getInt(6)), String.valueOf(rs
								.getInt(7)), 0, rs.getInt(8));
			}

			return customer;
		} finally {
			closeConnection(stmt);
		}
	}

	/**
	 * @aut
	 */
	public boolean isFreeUsername(String username) throws SQLException {
		PreparedStatement stmt = null;
		boolean isFree = false;

		try {
			String sql = "SELECT customer_id FROM " + DBTables.CUSTOMER_TABLE
					+ " WHERE  username = ?";

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, username);

			ResultSet rs = stmt.executeQuery();

			if (!rs.next()) {
				isFree = true;
			}

		} finally {
			closeConnection(stmt);
		}

		return isFree;
	}

	public boolean chekForUser(CustomerData customer) throws SQLException {
		return false;

	}

	/**
	 * @param connection
	 * 
	 * @author <a href="mailto:yanev93@gmail.com">Nikolay Yanev</a>
	 */
	@SuppressWarnings("static-access")
	public CustomerPersistor(Connection connection) {
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
	public CustomerPersistor() {
		DBManager dbManager = new DBManager();
		try {
			this.conn = dbManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create new instance of CustomerPersistor if no instance is created
	 * 
	 * @return instance of CustomerPersistor
	 * 
	 * @author Nikolay Yanev
	 * @email yanev93@gmail.com
	 */
	/*
	 * public static synchronized CustomerPersistor getInstance() { if
	 * (singelton == null) { singelton = new CustomerPersistor(); }
	 * 
	 * return singelton; }
	 */

	/**
	 * @author Nikolay Yanev
	 * @email yanev93@gmail.com
	 */
	/*
	 * public Object clone() throws CloneNotSupportedException { throw new
	 * CloneNotSupportedException(); }
	 */

	/**
	 * Change connection
	 * 
	 * @param connection
	 */
	/*
	 * public void setConnection(Connection connection) { this.conn =
	 * connection; }
	 */

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
